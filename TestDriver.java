public class TestDriver {

    public static void main(String[] args) {
        /*
        int[][] testCaseA = {
            {1, 0},
            {0, 1}
        };
        int[][] testCaseB = {
            {5, 7},
            {9, 23}
        };
        */
        int[][] testCaseA = {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };

        int[][] testCaseB = {
            {7, 1, 19, 40},
            {0, 9, 72, 0},
            {4, 0, 8, 44},
            {0, 2, 2, 36}
        };

        int[][] testCaseC_CLA = MatrixAlgorithms.classicalMult(testCaseA, testCaseB);
        int[][] testCaseC_DAC = MatrixAlgorithms.divAndConqMult(testCaseA, testCaseB);
        int[][] testCaseC_STR = MatrixAlgorithms.strassensMult(testCaseA, testCaseB);

        System.out.print("Classical:\n");

        for (int i = 0; i < testCaseC_CLA.length; i++) {
            for (int j = 0; j < testCaseC_CLA.length; j++) {
                System.out.print(testCaseC_CLA[i][j] + " ");
            }
            System.out.print("\n");
        }

        System.out.print("Divide and Conquer:\n");

        for (int i = 0; i < testCaseC_DAC.length; i++) {
            for (int j = 0; j < testCaseC_DAC.length; j++) {
                System.out.print(testCaseC_DAC[i][j] + " ");
            }
            System.out.print("\n");
        }

        System.out.print("Strassen's:\n");

        for (int i = 0; i < testCaseC_STR.length; i++) {
            for (int j = 0; j < testCaseC_STR.length; j++) {
                System.out.print(testCaseC_STR[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    
}
