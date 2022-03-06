package com.company.commands;

import com.company.receivers.Matrix;

public class CoeffGetter implements Command{
    private final Matrix matrix;

    public CoeffGetter(Matrix matrix){
        this.matrix = matrix;
    }


    @Override
    public void execute() {
        this.matrix.printCoefficients();
    }
}
