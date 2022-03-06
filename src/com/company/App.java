package com.company;

import com.company.commands.*;
import com.company.enums.ReadOption;
import com.company.receivers.Matrix;
import com.company.receivers.SimpleItrMatrixImpl;

import java.util.HashSet;
import java.util.Set;

public class App {
    public void start(String[] args){
        System.out.println("> Welcome to simple iterative method for solving linear systems!!\n");

        Set<String> opts = new HashSet<>();

        for(String arg: args){
            if(arg.charAt(0) == '-') {
                opts.add(arg);
            }
        }

        if(opts.contains("-h")){
            help();
        }

        ReadOption readOption = ReadOption.CLI;
        if(opts.contains("-f")) readOption = ReadOption.FILE;

        Matrix matrix = new SimpleItrMatrixImpl();
        Command matrixReader = new MatrixReader(matrix, readOption);
        Command printMatrix = new MatrixPrinter(matrix);
        Command generator = new Generator(matrix);
        Command jacobiMethodSolver = new JacobiMethodSolver(matrix);
        CoeffGetter coeffGetter = new CoeffGetter(matrix);

        if(opts.contains("-r")) generator.execute();
        else matrixReader.execute();

        jacobiMethodSolver.execute();

    }

    private void help(){
        System.out.println("> Usage: [options]\n" +
                "> Options:\n" +
                " -h        Help\n" +
                " -f        Read data from file\n" +
                " -r        Randomly generate data\n");
    }
}
