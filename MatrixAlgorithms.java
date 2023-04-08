// Dylan Michalak
// CS 3310-03
// Project 1: Matrix Multiplication
// Due: 4/6/2023

public class MatrixAlgorithms {

    /**
     * This method takes two 2d int arrays of some size 2^k, where k is a positive integer, and returns
     * the product of their multiplication, using a classical matrix multiplication algorithm
     * @param arrayA the first multiplicand
     * @param arrayB the second multiplicand
     * @return
     */
    public static int[][] classicalMult(int[][] matrixA, int[][] matrixB) {

        int size = matrixA.length; // initializes a variable equal to "n", in the given n x n matrices

        int[][] finalMatrix = new int[size][size];

        // the first two loops go to each spot in the final product matrix
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                int dotProduct = 0; // initializes a dot product variable at 0

                /* the final loop calculates, for every location in the product matrix, 
                the dot product of that row in the first multiplicand matrix, and that 
                column in the second multiplicand matrix*/
                for (int k = 0; k < size; k++) {
                    dotProduct += matrixA[i][k] * matrixB[k][j];
                }

                // store each dot product in its appropriate location
                finalMatrix[i][j] = dotProduct;
            }
        }
        // return the product matrix
        return finalMatrix;
    }
    
    /**
     * This method takes two 2d int arrays of some size 2^k, where k is a positive integer, and returns
     * the product of their multiplication, using a divide-and-conquer approach matrix multiplication algorithm
     * @param arrayA the first multiplicand
     * @param arrayB the second multiplicand
     * @return
     */
    public static int[][] divAndConqMult(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] finalMatrix = new int[size][size];
    
        
        if (size == 2) {

            finalMatrix[0][0] = matrixA[0][0] * matrixB[0][0] + matrixA[0][1] * matrixB[1][0];
            finalMatrix[0][1] = matrixA[0][0] * matrixB[0][1] + matrixA[0][1] * matrixB[1][1];
            finalMatrix[1][0] = matrixA[1][0] * matrixB[0][0] + matrixA[1][1] * matrixB[1][0];
            finalMatrix[1][1] = matrixA[1][0] * matrixB[0][1] + matrixA[1][1] * matrixB[1][1];
            
        } else {
            // declare 8 sub-matrices, 4 for each multiplicand matrix
            int[][] a11, a12, a21, a22, b11, b12, b21, b22;
    
            // define each sub-matrix as it's appropriate quartile for both multiplicands
            int[][][] matrixASplit = matrixSplit(matrixA);
            a11 = copyOf(matrixASplit[0]);
            a12 = copyOf(matrixASplit[1]);
            a21 = copyOf(matrixASplit[2]);
            a22 = copyOf(matrixASplit[3]);
    
            int[][][] matrixBSplit = matrixSplit(matrixB);
            b11 = copyOf(matrixBSplit[0]);
            b12 = copyOf(matrixBSplit[1]);
            b21 = copyOf(matrixBSplit[2]);
            b22 = copyOf(matrixBSplit[3]);
    
            // declare and compute sub-matrices with the addition helper method and recursive calls
            int[][] c11, c12, c21, c22;
            c11 = matrixAdd(divAndConqMult(a11, b11), divAndConqMult(a12, b21));
            c12 = matrixAdd(divAndConqMult(a11, b12), divAndConqMult(a12, b22));
            c21 = matrixAdd(divAndConqMult(a21, b11), divAndConqMult(a22, b21));
            c22 = matrixAdd(divAndConqMult(a21, b12), divAndConqMult(a22, b22));
    
            // combine the sub-matrices into the final multiplication product for this function call
            for (int i = 0; i < (size / 2); i++) {
                for (int j = 0; j < (size / 2); j++) {
                    finalMatrix[i][j] = c11[i][j];
                    finalMatrix[i][j + (size / 2)] = c12[i][j];
                    finalMatrix[i + (size / 2)][j] = c21[i][j];
                    finalMatrix[i + (size / 2)][j + (size / 2)] = c22[i][j];
                }
            }
        }
    
        //returns the result of this call of matrix multiplication
        return finalMatrix;
    }
    
    private static int[][] copyOf(int[][] original) {

        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[0].length; j++) {
                copy[i][j] = original[i][j];
            }
        }

        return copy;
    }
    

    /*
     * internal helper method that divides a 2d, square, 2^k size array into 4 quartiles,
     * and returns a 3d array containing a different quartile in each of its 4 layers
     */
    private static int[][][] matrixSplit(int[][] startMatrix) {

        // define newSize variable equal to half the width/height of the starting square matrix
        int newSize = startMatrix.length / 2;

        // define a 3d array, that can store 4 newSize x newSize matrices
        int[][][] newMatrix = new int[4][newSize][newSize];

        // VISUALIZATION OF THE SPLIT:
        // Quartile 0 | Quartile 1
        // -----------|------------
        // Quartile 2 | Quartile 3
        for (int quartile = 0; quartile <= 3; quartile++) {
            switch(quartile) {

                // stores the first matrix quartile as a 2d array in the first layer of the 3d matrix
                case 0:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[quartile][i][j] = startMatrix[i][j];
                        }
                    } break;
                // stores the second matrix quartile as a 2d array in the second layer of the 3d matrix
                case 1:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[quartile][i][j] = startMatrix[i][j + newSize];
                        }
                    } break;
                // stores the third matrix quartile as a 2d array in the third layer of the 3d matrix
                case 2:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[quartile][i][j] = startMatrix[i + newSize][j];
                        }
                    } break;
                // stores the fourth matrix quartile as a 2d array in the first layer of the 3d matrix
                case 3:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[quartile][i][j] = startMatrix[i + newSize][j + newSize];
                        }
                    } break;
            }
        }
        // return the resulting 4 split matrices
        return newMatrix;
    }

    /*
     * internal helper method that adds two square 2d arrays of identical dimensions,
     * and returns the sum
     */
    private static int[][] matrixAdd(int[][] matrixA, int[][] matrixB) {

        // copy the first array to a new memory location
        int[][] sumMatrix = copyOf(matrixA);
        // define the length of the square matrices as a varaible
        int size = sumMatrix.length;

        // adds each value in the second array to the first array
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sumMatrix[i][j] += matrixB[i][j];
            }
        }
        // return the result
        return sumMatrix;
    }

    /**
     * This method takes two 2d int arrays of some size 2^k, where k is a positive integer, and returns
     * the product of their multiplication, using Strassen's matrix multiplication algorithm
     * @param arrayA the first multiplicand
     * @param arrayB the second multiplicand
     * @return
     */
    public static int[][] strassensMult(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] finalMatrix = new int[size][size];


        if (size == 2) {

            finalMatrix[0][0] = matrixA[0][0] * matrixB[0][0] + matrixA[0][1] * matrixB[1][0];
            finalMatrix[0][1] = matrixA[0][0] * matrixB[0][1] + matrixA[0][1] * matrixB[1][1];
            finalMatrix[1][0] = matrixA[1][0] * matrixB[0][0] + matrixA[1][1] * matrixB[1][0];
            finalMatrix[1][1] = matrixA[1][0] * matrixB[0][1] + matrixA[1][1] * matrixB[1][1];

        }
        // else statement runs for any matrix larger than 1 x 1, and recursively calls until a 1 x 1 matrix is reached
        else {

            // declare 8 sub-matrices, 4 for each multiplicand matrix
            int[][] a11, a12, a21, a22, b11, b12, b21, b22;

            // define each sub-matrix as it's appropriate quartile for both multiplicands
            int[][][] matrixASplit = matrixSplit(matrixA);
            a11 = copyOf(matrixASplit[0]);
            a12 = copyOf(matrixASplit[1]);
            a21 = copyOf(matrixASplit[2]);
            a22 = copyOf(matrixASplit[3]);
    
            int[][][] matrixBSplit = matrixSplit(matrixB);
            b11 = copyOf(matrixBSplit[0]);
            b12 = copyOf(matrixBSplit[1]);
            b21 = copyOf(matrixBSplit[2]);
            b22 = copyOf(matrixBSplit[3]);

            // declare and compute strassen's 7 multiplications
            int[][] P, Q, R, S, T, U, V;
            P = strassensMult(matrixAdd(a11, a22), matrixAdd(b11,b22));
            Q = strassensMult(matrixAdd(a21, a22), b11);
            R = strassensMult(a11, matrixSub(b12,b22));
            S = strassensMult(a22, matrixSub(b21, b11));
            T = strassensMult(matrixAdd(a11,a12), b22);
            U = strassensMult(matrixSub(a21, a11), matrixAdd(b11, b12));
            V = strassensMult(matrixSub(a12, a22), matrixAdd(b21, b22));

            // compute sub-matrices by adding and subtracting strassen's 7 multiplications
            int[][] c11, c12, c21, c22;
            c11 = matrixAdd(matrixAdd(P, matrixSub(S, T)), V);
            c12 = matrixAdd(R, T);
            c21 = matrixAdd(Q, S);
            c22 = matrixAdd(matrixAdd(P, matrixSub(R, Q)), U);


            // combine the sub-matrices into the final multiplication product for this function call
            for (int i = 0; i < (size / 2); i++) {
                for (int j = 0; j < (size / 2); j++) {
                    finalMatrix[i][j] = c11[i][j];
                    finalMatrix[i][j + (size / 2)] = c12[i][j];
                    finalMatrix[i + (size / 2)][j] = c21[i][j];
                    finalMatrix[i + (size / 2)][j + (size / 2)] = c22[i][j];
                }
            }
        }

        //returns the result of this call of matrix multiplication
        return finalMatrix;
    }

    /*
     * internal helper method that subtracts two square 2d arrays of identical dimensions,
     * and returns the sum (matrixA minus matrixB)
     */
    private static int[][] matrixSub(int[][] matrixA, int[][] matrixB) {

        // copy the first array to a new memory location
        int[][] sumMatrix = copyOf(matrixA);
        // define the length of the square matrices as a varaible
        int size = sumMatrix.length;

        // adds each value in the second array to the first array
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sumMatrix[i][j] -= matrixB[i][j];
            }
        }
        // return the result
        return sumMatrix;
    }
}