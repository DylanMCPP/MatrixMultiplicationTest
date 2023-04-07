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

            
            System.out.println("(1)Select a test case to display\n(0)Exit");
            int testChoice = input.nextInt();

            if (testChoice == 1) {

                boolean keepTesting = true;
                List<int[]> caseList = new ArrayList<>();

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

            } else if(testChoice == 0)
                keepRunning = false;
            else
                System.out.println("Error: Invalid input.");

        } 

        input.close();
    }

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

    private static double[] testAlgos(int[][] MatrixA, int[][] MatrixB) {

        double startTime, endTime;        
        int size = MatrixA.length;
        double[] returnTimes = new double[3];
        int[][] resultMatrix = new int[size][size];
        System.out.println("Problem:");

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(MatrixA[i][j] + " ");
            }
            System.out.print("\n");
        }

        System.out.print("X\n");

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(MatrixB[i][j] + " ");
            }
            System.out.print("\n");
        }

        startTime = System.currentTimeMillis();
        resultMatrix = MatrixAlgorithms.classicalMult(MatrixA, MatrixB);
        endTime = System.currentTimeMillis();
        returnTimes[0] = (endTime - startTime) / 1000;

        System.out.print("Classical result:\n");

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }

        startTime = System.currentTimeMillis();
        resultMatrix = MatrixAlgorithms.divAndConqMult(MatrixA, MatrixB);
        endTime = System.currentTimeMillis();
        returnTimes[1] = (endTime - startTime) / 1000;

        System.out.print("Divide and Conquer result:\n");

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }

        startTime = System.currentTimeMillis();
        resultMatrix = MatrixAlgorithms.strassensMult(MatrixA, MatrixB);
        endTime = System.currentTimeMillis();
        returnTimes[2] = (endTime - startTime) / 1000;

        System.out.print("Strassen's result:\n");

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }

        return returnTimes;
    }
}
    
