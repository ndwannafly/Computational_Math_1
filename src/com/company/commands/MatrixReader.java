package com.company.commands;

import com.company.enums.ReadOption;
import com.company.receivers.Matrix;

public class MatrixReader implements Command{

    private final Matrix matrix;
    private final ReadOption readOption;
    public MatrixReader(Matrix matrix, ReadOption readOption){
        this.matrix = matrix;
        this.readOption = readOption;
    }

    @Override
    public void execute() {
        this.matrix.read(readOption);
    }
}
