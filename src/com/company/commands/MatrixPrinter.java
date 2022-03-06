package com.company.commands;

import com.company.receivers.Matrix;


public class MatrixPrinter implements Command{
    private final Matrix matrix;

    public MatrixPrinter(Matrix matrix){
        this.matrix = matrix;
    }


    @Override
    public void execute() {
        this.matrix.printMatrix();
    }
}
