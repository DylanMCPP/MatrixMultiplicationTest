import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestDriver {

    public static void main(String[] args) {
        
        boolean keepRunning = true;

        Scanner input = new Scanner(System.in);
        /*
        * MENU FOR DECIDING TO SELECT A TEST CASE
        */
        while(keepRunning) {

            //Prompt the user on what program feature they want to use and take an integer input
            System.out.println("(1)Select test cases to display\n(2)Generate 2^k x 2^k matrices with random values\n" +
            "(3)Test Algorithm Speeds\n(0)Exit");
            int testChoice = input.nextInt();

            //Runs if user chooses to run test cases
            if (testChoice == 1) {

                //define a List to store all the entries in testCases.txt
                boolean keepTesting = true;
                List<int[]> caseList = new ArrayList<>();

                /*
                 * read testCases.txt file within this program's directory, store entires as arrays 
                 * of integers in the arrayList, caseList
                 * CATCH: a fileNotFoundException, if testCases.txt does not exist
                */
                try {
                    Scanner fileReader = new Scanner(new File("./testCases.txt"));

                    /*
                     * TAKE TEST CASE FILE AND PARSE EACH CASE INTO AN ArrayList ENTRY
                     */
                    while(fileReader.hasNext()) {

                        String[] testCaseRaw =  fileReader.nextLine().split(":");
                        int[] testCaseInt = new int[testCaseRaw.length];

                        for (int i = 0; i < testCaseRaw.length; i++) {
                            testCaseInt[i] = Integer.parseInt(testCaseRaw[i]);
                        }

                        caseList.add(testCaseInt);
                    }
                    fileReader.close();
                } catch (FileNotFoundException e) {
                    // Where in the world are the test cases????
                    e.printStackTrace();
                }

                /*
                * USER MENU FOR VIEWING INDIVIDUAL TEST CASES
                */
                while(keepTesting) {

                    /*
                     * Ask the user what test case they want to display
                     * input: the test case #
                     */
                    System.out.println("(1-10)Select a test case\n(0)Exit");
                    int caseChoice = input.nextInt();
                    if (caseChoice > 0 && caseChoice < 11) {

                        //process user input to correctly align with ArrayList indexes
                        caseChoice--;

                        //declare 3 square matrices to use for the multiplicands and their product, as well as set variables for organizing the data in 2d arrays
                        int[] chosenCase = caseList.get(caseChoice);
                        int[][] testMatrixA, testMatrixB;

                        //get the two multiplicands in the format of layers of a 3d array, and store them each in their own 2d array
                        int[][][] inputMatrices = getMultiplicands(chosenCase);
                        testMatrixA = inputMatrices[0];
                        testMatrixB = inputMatrices[1];
                    
                        //display the test results
                        testAlgos(testMatrixA, testMatrixB);
                    } else
                        keepTesting = false;
                }

            // end while loop if user selects 0, exit test case menu and return to main menu
            } else if(testChoice == 0)
                keepRunning = false;
            // re-prompt use if use enters invalid input
            else
                System.out.println("Error: Invalid input.");

        } 
        // close the user input scanner
        input.close();
    }

    /**
     * take an int array of (2 * n^2) length, and return two square 2d arrays of n * n dimensions
     * @param rawProblem 1d integer array
     * @return a 3d array with 2 layers, each layer containing a different n * n dimension 2d array, representing a square matrix
     */
    private static int[][][] getMultiplicands(int[] rawProblem) {
        int index = 1;
        int size = rawProblem[0];
        int[][][] returnMatrix = new int[2][size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                returnMatrix[0][i][j] = rawProblem[index];
                index++;
            }
        }

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                returnMatrix[1][i][j] = rawProblem[index];
                index++;
            }
        }

        return returnMatrix;
    }

    /**
     * Tests the speed of 3 matrix multiplication algorithms in miliseconds using java's currentTimeMillis() method
     * @param matrixA first multiplicand
     * @param matrixB second multiplicand
     * @return an integer array of length 3, where the 1st entry is speed of classical, 2nd is speed of Divide &
     * conquer, and 3rd is speed of strassen's
     */
    private static double[] testAlgos(int[][] matrixA, int[][] matrixB) {

        double startTime, endTime;        
        double[] returnTimes = new double[3];
        int[][] resultMatrix = new int[matrixA.length][matrixA[0].length];
        System.out.println("Problem:");

        printMatrix(matrixA);

        System.out.print("X\n");

        printMatrix(matrixA);

        startTime = System.currentTimeMillis();
        resultMatrix = MatrixAlgorithms.classicalMult(matrixA, matrixB);
        endTime = System.currentTimeMillis();
        returnTimes[0] = (endTime - startTime) / 1000;

        System.out.print("Classical result:\n");

        printMatrix(resultMatrix);

        startTime = System.currentTimeMillis();
        resultMatrix = MatrixAlgorithms.divAndConqMult(matrixA, matrixB);
        endTime = System.currentTimeMillis();
        returnTimes[1] = (endTime - startTime) / 1000;

        System.out.print("Divide and Conquer result:\n");

        printMatrix(resultMatrix);

        startTime = System.currentTimeMillis();
        resultMatrix = MatrixAlgorithms.strassensMult(matrixA, matrixB);
        endTime = System.currentTimeMillis();
        returnTimes[2] = (endTime - startTime) / 1000;

        System.out.print("Strassen's result:\n");
        printMatrix(resultMatrix);

        return returnTimes;
    }

    /**
     * Outputs a visual representation of a 2d array (positive integer values > 10^9 or negative integer values < 10^8 get cut off)
     * @param matrix 2d array to be printed
     */
    private static void printMatrix(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            System.out.print("| ");
            for(int j = 0; j < matrix.length; j++) {
                System.out.printf("%9d ", matrix[i][j]);
            }
            System.out.print(" |\n");
        }
    }
}
    
