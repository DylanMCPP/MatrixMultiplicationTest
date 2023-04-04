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
        return null;
    }

    /*
     * internal helper method that divides a 2d, square, 2^k size array into 4 quartiles,
     * and returns a 3d array containing a different quartile in each of its 4 layers
     */
    private int[][][] matrixSplit(int[][] startMatrix) {

        // define newSize variable equal to half the width/height of the starting square matrix
        int newSize = startMatrix.length / 2;

        // define a 3d array, that can store 4 newSize x newSize matrices
        int[][][] newMatrix = new int[newSize][newSize][4];

        // Quartile 1 | Quartile 2
        // -----------|------------
        // Quartile 3 | Quartile 4
        for (int quartile = 1; quartile <=4; quartile++) {
            switch(quartile) {

                // stores the first matrix quartile as a 2d array in the first layer of the 3d matrix
                case 1:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[i][j][quartile] = startMatrix[i][j];
                        }
                    } break;
                // stores the second matrix quartile as a 2d array in the second layer of the 3d matrix
                case 2:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[i][j][quartile] = startMatrix[i][j + newSize];
                        }
                    } break;
                // stores the third matrix quartile as a 2d array in the third layer of the 3d matrix
                case 3:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[i][j][quartile] = startMatrix[i + newSize][j];
                        }
                    } break;
                // stores the fourth matrix quartile as a 2d array in the first layer of the 3d matrix
                case 4:
                    for (int i = 0; i < newSize; i++) {
                        for (int j = 0; j < newSize; j++) {
                            newMatrix[i][j][quartile] = startMatrix[i + newSize][j + newSize];
                        }
                    } break;
            }
        }
        return newMatrix;
    }

    /**
     * This method takes two 2d int arrays of some size 2^k, where k is a positive integer, and returns
     * the product of their multiplication, using Strassen's matrix multiplication algorithm
     * @param arrayA the first multiplicand
     * @param arrayB the second multiplicand
     * @return
     */
    public static int[][] strassensMult(int[][] matrixA, int[][] matrixB) {
        return null;
    }
}