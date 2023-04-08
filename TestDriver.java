import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TestDriver {

    public static void main(String[] args) throws IOException {
        
        boolean keepRunning = true;

        Scanner input = new Scanner(System.in);
        /*
        * MENU FOR DECIDING TO SELECT A TEST CASE
        */
        while(keepRunning) {

            //Prompt the user on what program feature they want to use and take an integer input
            System.out.println("(1)Select test cases to display\n(2)Generate 2^k x 2^k matrices with random values\n" +
            "(3)Test Algorithm Speeds\n(0)Exit");
            int inpChoice = input.nextInt();

            //Runs if user chooses to run test cases
            if (inpChoice == 1) {

                //define a List to store all the entries in testCases.txt
                boolean keepTesting = true;
                List<int[]> caseList = new ArrayList<>();

                /*
                 * read testCases.txt file within this program's directory, store entires as arrays 
                 * of integers in the arrayList, caseList
                 * CATCH: a fileNotFoundException, if testCases.txt does not exist
                */
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
                        testAlgos(testMatrixA, testMatrixB, true);
                    } else
                        keepTesting = false;
                }
            
            // if the user selects "2", bring them to random matrix generation menu
            }else if (inpChoice == 2) {

                boolean keepGenerating = true;

                /*
                * create BufferedWriter, to write new multiplicand pairs to SquareMatrices.txt
                * OVERWRITES list of problems previously stored in SquareMatrices.txt
                */ 
                BufferedWriter output = new BufferedWriter(new FileWriter("./SquareMatrices.txt"));

                while(keepGenerating) {

                    System.out.println("Enter \"0\" to exit, or enter an integer value \"k\" for a pair of n^k x n^k dimension\n" + 
                    "square matrices you want to generate and store, containing random integer values: ");
                    int generateChoice = input.nextInt();

                    if (generateChoice == 0)
                        keepGenerating = false;

                    /*
                     * Code block for randomly generating, and saving 2 square matrices of size 2^k * 2^k to a new
                     * line in SquareMatrices.txt
                     */
                    else if (generateChoice >= 1) {

                        String newMultiplicands = generateMatrix(generateChoice);
                        output.append(newMultiplicands);
                        output.newLine();

                        System.out.println("Done generating a " + Math.pow(2, generateChoice) + " x " + Math.pow(2, generateChoice) + "matrix.");

                    } else 
                        System.out.println("Error: Invalid input.");
                }

                output.close();

            // if the user selects "3", bring them to algorithm timing menu
            }else if (inpChoice == 3) {
                
                /*
                 * READS AND STORES ALL MULTIPLICAND PAIR ENTRIES in SquareMatrices.txt
                 * Then, performs 10 multiplication calculations on each pair with each of the 3 algorithms
                 * displays the mean runtime for each algorithm based on the 10 trials, excluding the MAX and MIN
                 */

                // somewhat more efficient way of reading SquareMatrices.txt than used to read testCases.txt, since may be large
                try (BufferedReader br = Files.newBufferedReader(Paths.get("SquareMatrices.txt"), StandardCharsets.UTF_8)) {
                    
                    // for loop to repeat the timing & averaging process for every entry
                    for (String line = null; (line = br.readLine()) != null;) {
                        String[] inputRaw = line.split(":");

                        int[] inputInt = new int[inputRaw.length];

                        // stores all entries, seperated by ":"  in an integer array
                        for (int i = 0; i < inputRaw.length; i++) {
                            inputInt[i] = Integer.parseInt(inputRaw[i]);
                        }

                        // converts 1d array into two defined square, multiplicand matrices
                        int[][][] inputMatrices = getMultiplicands(inputInt);
                        int[][] matrixA = inputMatrices[0];
                        int[][] matrixB = inputMatrices[1];
                    
                        // allocates 10x3 array for the execution times
                        double[][] timeTable = new double[10][3];

                        // runs each algorithm 10 times and store execution time in a different column of the timeTable 2d array
                        for (int i = 0; i < 10; i++) {
                            timeTable[i] = testAlgos(matrixA, matrixB, false);
                        }

                        // display average execution time for classical multiplication
                        System.out.println("Results for " + inputInt[0] + " x " + inputInt[0] + " matrix multiplication:");
                        System.out.print("\nClassical: ");
                        
                        double average = 0;
                        double[] classicalTimes = new double[10];

                        for (int i = 0; i < 10; i++) {
                            classicalTimes[i] = timeTable[i][0];
                        }

                        Arrays.sort(classicalTimes);
                        classicalTimes[0] = 0;
                        classicalTimes[9] = 9;

                        for (int i = 1; i < 9; i++) {
                            average += classicalTimes[i];
                        }

                        average = average / 8;

                        System.out.print(average + " seconds");

                        // display average execution time for classical multiplication
                        System.out.print("\nDivide and Conquer: ");
                        
                        average = 0;
                        double[] dACTimes = new double[10];

                        for (int i = 0; i < 10; i++) {
                            dACTimes[i] = timeTable[i][1];
                        }

                        Arrays.sort(dACTimes);
                        dACTimes[0] = 0;
                        dACTimes[9] = 9;

                        for (int i = 1; i < 9; i++) {
                            average += dACTimes[i];
                        }

                        average = average / 8;

                        System.out.print(average + " seconds");

                        // display average execution time for classical multiplication
                        System.out.print("\nStrassens: ");
                        
                        average = 0;
                        double[] strasTimes = new double[10];

                        for (int i = 0; i < 10; i++) {
                            strasTimes[i] = timeTable[i][2];
                        }

                        Arrays.sort(strasTimes);
                        strasTimes[0] = 0;
                        strasTimes[9] = 9;

                        for (int i = 1; i < 9; i++) {
                            average += strasTimes[i];
                        }

                        average = average / 8;

                        System.out.print(average + " seconds\n");
                    }
                }

            // end while loop if user selects 0, exit test case menu and return to main menu
            }else if(inpChoice == 0)
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
    private static double[] testAlgos(int[][] matrixA, int[][] matrixB, boolean print) {
       
        // allocate ram for array of times to return & result of multiplication
        double[] returnTimes = new double[3];
        int[][] resultMatrix = new int[matrixA.length][matrixA[0].length];

        // visually display problem, if "print" is true
        if (print) {
            System.out.println("Problem:");

            printMatrix(matrixA);

            System.out.print("X\n");

            printMatrix(matrixA);
        }


        final double classicalStartTime = System.nanoTime();
        resultMatrix = MatrixAlgorithms.classicalMult(matrixA, matrixB);
        final double classicalEndTime = System.nanoTime();
        returnTimes[0] = (classicalEndTime - classicalStartTime) / 1000000000;

        // visually classical multiplication result, if "print" is true
        if (print) {
            System.out.print("Classical result:\n");

            printMatrix(resultMatrix);
        }

        final double dACStartTime = System.nanoTime();
        resultMatrix = MatrixAlgorithms.divAndConqMult(matrixA, matrixB);
        final double dACEndTime = System.nanoTime();
        returnTimes[1] = (dACEndTime - dACStartTime) / 1000000000;

        // visually Div & Conq multiplication result, if "print" is true
        if (print) {
            System.out.print("Divide and Conquer result:\n");

            printMatrix(resultMatrix);
        }

        final double strasStartTime = System.nanoTime();
        resultMatrix = MatrixAlgorithms.strassensMult(matrixA, matrixB);
        final double strasEndTime = System.nanoTime();
        returnTimes[2] = (strasEndTime - strasStartTime) / 1000000000;

        // visually strassen's multiplication result, if "print" is true
        if (print) {
            System.out.print("Strassen's result:\n");
            printMatrix(resultMatrix);
        }

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

    private static String generateMatrix(int k) {
        // cast is okay, since we know that both the base and power are integers
        int size = (int)Math.pow(2, k);
        Random rand = new Random();
        
        int[] newMultiplicands = new int[1 + (size * size * 2)];

        newMultiplicands[0] = size;

        for (int i = 1; i < newMultiplicands.length; i++) {
                newMultiplicands[i] = rand.nextInt(-99, 99);
        }
        
        StringBuilder multiplicandValues = new StringBuilder();
        multiplicandValues.append(size);
        for (int i = 1; i < newMultiplicands.length; i++) {
            multiplicandValues.append(":");
            multiplicandValues.append(newMultiplicands[i]);
        }
        
        return multiplicandValues.toString();
    }
}
    
