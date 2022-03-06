package com.company.receivers;

import com.company.enums.ReadOption;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.UnexpectedException;
import java.util.Scanner;

public class SimpleItrMatrixImpl implements Matrix{

    private int size;
    private double[][] matrix;
    private double[] coefficients;
    private double[][] transformedMatrix;
    private double accuracy;
    private double [] solution;
    private double [] error;


    public SimpleItrMatrixImpl(int size, double[][] matrix, double[] coefficients, double accuracy){
        this.size = size;
        this.matrix = matrix;
        this.coefficients = coefficients;
        this.accuracy = accuracy;
    }

    public SimpleItrMatrixImpl(){};

    @Override
    public void read(ReadOption option) {
        Scanner sc = new Scanner(System.in);
        try {
            switch (option) {
                case CLI:
                    System.out.println("> Read matrix from console...");
                    break;
                case FILE:
                    System.out.println("> Reading from file...");
                    System.out.print("> Please enter file name: ");
                    File file = new File(sc.next());
                    sc = new Scanner(file);
                    break;
                default: throw new UnexpectedException("Can not parse input option");
            }
        } catch (UnexpectedException | FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        try {
            if (option == ReadOption.CLI) System.out.print("> Please enter the accuracy: ");
            this.accuracy = Double.parseDouble(sc.next());
            if (option == ReadOption.CLI) System.out.print("> Please enter the matrix's size: ");
            this.size = sc.nextInt();
            if (option == ReadOption.CLI) System.out.println("> Please enter the matrix: ");
            matrix = new double[this.size][this.size];
            coefficients = new double[this.size];
            for (int row = 0; row < this.size; ++row) {
                for (int col = 0; col < this.size + 1; ++col) {
                    String val = sc.next();
                    val = val.replace(",", ".");
                    if(col == this.size) this.coefficients[row] = Double.parseDouble(val);
                    else this.matrix[row][col] = Double.parseDouble(val);
                }
            }
        } catch(NumberFormatException e){
            System.out.println("Input is incorrect format, please check again!");
            System.exit(0);
        }
    }

    @Override
    public double[][] getMatrix() {
        return this.matrix;
    }

    @Override
    public double getElement(int i, int j){
        return this.matrix[i][j];
    }

    @Override
    public double[] getCoefficients(){
        return this.coefficients;
    }

    @Override
    public boolean isStrictDiagonalDominant() {
        boolean ok = false;
        for(int i = 0; i < size;i++){
            double sum = 0;

            for(int j = 0 ;j < size; j++){
                if(i != j) sum += Math.abs(this.matrix[i][j]);
            }
            if(Math.abs(this.matrix[i][i]) > sum) ok = true;
            else if (Math.abs(this.matrix[i][i]) < sum) return false;
        }
        double product = 1;
        for (int i = 0; i < size; i++) product *= this.matrix[i][i];
        if(product == 0) {
            ok = false;
        }
        return ok;
    }

    @Override
    public boolean canSearchDiagonalDominant() {
        Matrix copy = new SimpleItrMatrixImpl(this.size, new double[this.size][this.size], new double[this.size], this.accuracy);
        boolean[] done = new boolean[this.size];
        for (int i = 0; i < size; i++) done[i] = false;

        for(int i = 0; i < this.size; i++){
            double sum = 0;
            for (int j = 0 ; j < this.size; j++){
                sum += Math.abs(this.matrix[i][j]);
            }
            boolean flag = false;
            int index = 0;
            for (int j = 0; j < size; j++){
                if(Math.abs(matrix[i][j]) >= sum - Math.abs(matrix[i][j])){
                    flag = true;
                    index = j;
                    break;
                }
            }
            if (flag){
                if (!done[index]){
                    for (int k = 0; k < size; k++){
                        copy.getMatrix()[index][k] = this.matrix[i][k];
                    }
                    copy.getCoefficients()[index] = this.coefficients[i];
                    done[index] = true;
                } else return false;
            } else return false;
        }
        this.matrix = copy.getMatrix();
        this.coefficients = copy.getCoefficients();
        return true;
    }

    @Override
    public void setTransformedMatrix() {
        this.transformedMatrix = new double[size][size + 1];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size + 1; j++){
                if (i == j) transformedMatrix[i][j] = 0;
                else {
                   if(j == size) transformedMatrix[i][j] = this.coefficients[i] / this.matrix[i][i];
                   else transformedMatrix[i][j] = -1 * (this.matrix[i][j] / (this.matrix[i][i]));
                }
            }
        }
    }

    @Override
    public int findSolution() {
        double[] newApproxVector = new double[this.size];
        solution = new double[this.size];
        error = new double[this.size];
        int iteration = 0;
        for (int i = 0; i < size; i++) {
            solution[i] = 0;
        }
        while (true) {
            for (int i = 0; i < size; i++) {
                double x = 0;
                for (int j = 0; j < size; j++) {
                    x += this.transformedMatrix[i][j] * solution[j];
                    if (j == size - 1) {
                        x += this.transformedMatrix[i][j + 1];
                    }
                }
                error[i] = Math.abs(solution[i] - (x));
                newApproxVector[i] = x;
                if (i == size - 1) {
                    for (int l = 0; l < size; l++) {
                        solution[l] = newApproxVector[l];
                    }
                    iteration++;
                    boolean flag = true;
                    for (int k = 0; k < size; k++) {
                        if (error[k] > accuracy) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) return iteration;
                }
            }
        }
    }

    @Override
    public void setAccuracy(double accuracy){
        this.accuracy = accuracy;
    }

    @Override
    public void printMatrix(){
        System.out.println("> Matrix accuracy: " + this.accuracy);
        System.out.println("> Matrix size: " + this.size);
        System.out.println("> Matrix: ");
        for(int row = 0; row < this.size; ++row){
            System.out.print("> ");
            for(int col = 0; col < this.size; ++col){
                System.out.printf("%5f | ", matrix[row][col]);
            }
            System.out.printf("%5f | ", coefficients[row]);
            System.out.println();
        }
    }

    @Override
    public void printSolution(){
        System.out.println();
        System.out.println("Vector Solution:");
        for (int k = 0; k < size; k++) {
            System.out.printf("%5f | ", solution[k]);
        }
        System.out.println();
        System.out.println("Vector Error:");
        for (int k = 0; k < size; k++) {
            System.out.printf("%5f | ", error[k]);
        }
        System.out.println();
    }

    @Override
    public void printCoefficients(){
        System.out.println("> Current Coefficients: ");
        for(int i = 0; i < this.size; i++){
            System.out.printf("%5f | ", coefficients[i]);
        }
        System.out.println();
    }

    @Override
    public void seedMatrix(int n){
        this.size = n;
        this.matrix = new double[n][n];
        this.coefficients = new double[n];
        for(int row = 0; row < n; ++row){
            for(int col = 0; col < n; ++col){
                this.matrix[row][col] = Math.random() * 1e3;
            }
            this.coefficients[row] = Math.random() * 1e3;
        }
    }

}
