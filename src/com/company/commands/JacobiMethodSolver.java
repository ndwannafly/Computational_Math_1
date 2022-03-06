package com.company.commands;

import com.company.receivers.Matrix;

public class JacobiMethodSolver implements Command{
    private final Matrix matrix;

    public JacobiMethodSolver(Matrix matrix){
        this.matrix = matrix;
    }
    @Override
    public void execute() {
        System.out.println("> Initial matrix:");
        matrix.printMatrix();
        if(this.matrix.isStrictDiagonalDominant()){
            System.out.println("> Matrix is strictly diagonally dominant");
        } else{
            System.out.println("> Matrix is NOT strictly diagonally dominant");
            if(matrix.canSearchDiagonalDominant()){
                if(matrix.isStrictDiagonalDominant()){
                    System.out.println("> Interchanging row...");
                    System.out.println("> New matrix:");
                    matrix.printMatrix();
                } else{
                    System.out.println("> Can't find solution!");
                    System.exit(0);
                }
            } else{
                System.out.println("> Can't find solution!");
                System.exit(0);
            }
        }
        matrix.setTransformedMatrix();
        System.out.println();
        System.out.print("Number of iterations: " + matrix.findSolution());
        matrix.printSolution();
    }
}
