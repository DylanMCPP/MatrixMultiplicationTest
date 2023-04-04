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
    public static int[][] divAndConqMult(int[][] arrayA, int[][] arrayB) {
        return null;
    }

    /**
     * This method takes two 2d int arrays of some size 2^k, where k is a positive integer, and returns
     * the product of their multiplication, using Strassen's matrix multiplication algorithm
     * @param arrayA the first multiplicand
     * @param arrayB the second multiplicand
     * @return
     */
    public static int[][] strassensMult(int[][] arrayA, int[][] arrayB) {
        return null;
    }
}