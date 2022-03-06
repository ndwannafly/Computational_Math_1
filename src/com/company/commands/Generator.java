package com.company.commands;

import com.company.receivers.Matrix;


import java.util.Scanner;

public class Generator implements Command{
    private final Matrix matrix;

    public Generator(Matrix matrix){
        this.matrix = matrix;
    }

    @Override
    public void execute(){
        Scanner sc = new Scanner((System.in));
        System.out.print("> Please enter accuracy: ");
        this.matrix.setAccuracy(Double.parseDouble(sc.next()));
        System.out.print("Please enter the size of random matrix: ");
        this.matrix.seedMatrix(sc.nextInt());
    }
}
