package com.company.receivers;

import com.company.enums.ReadOption;


public interface Matrix {

    void read(ReadOption option);

    double[][] getMatrix();

    void printCoefficients();

    void seedMatrix(int size);

    void setAccuracy(double accuracy);

    void printMatrix();

    double getElement(int i, int j);

    double[] getCoefficients();

    boolean isStrictDiagonalDominant();

    boolean canSearchDiagonalDominant();

    void setTransformedMatrix();

    void printSolution();

    int findSolution();
}
