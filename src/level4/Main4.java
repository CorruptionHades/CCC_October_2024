package level4;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main4 {

    public static void main(String[] args) throws Exception {
        File inDir = new File("/src/level4/in");
        for (File file : inDir.listFiles()) {
            lev4(file);
        }

        //lev4(new File("H:\\IntelijProjects\\CCC_October_2024\\src\\level4\\in\\test"));
       // lev4(new File("H:\\IntelijProjects\\CCC_October_2024\\src\\level4\\in\\level4_1.in"));
    }

    static void lev4(File file) throws Exception {
        Scanner sc = new Scanner(file);
        int numberOfRooms = sc.nextInt();

        File outFile = new File("/src/level4/out/" + file.getName().replace(".in", "") + ".out");
        FileWriter writer = new FileWriter(outFile);

        for (int i = 0; i < numberOfRooms; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int tableNum = sc.nextInt();

            char[][] matrix = new char[y][x];
            initializeMatrix(matrix);

            // horizontal desks
            int desksPlaced = placeDesksHorizontal(matrix, tableNum);

            // fill remaining spaces with vertical desks
            desksPlaced += placeDesksVertical(matrix, tableNum - desksPlaced);

            writeMatrixToFile(matrix, writer);

            System.out.println("Desks placed: " + desksPlaced);
            System.out.println("Desks needed: " + tableNum);
            printMatrix(matrix);
        }

        writer.close();
    }

    static void initializeMatrix(char[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = '.';
            }
        }
    }

    static int placeDesksHorizontal(char[][] matrix, int tableNum) {
        int desksPlaced = 0;
        int y = matrix.length;
        int x = matrix[0].length;
        
        // 2 row gap
        for (int row = 0; row < y && desksPlaced < tableNum; row += 2) {
            for (int col = 0; col < x - 2 && desksPlaced < tableNum; col += 4) {
                if (isValidPlacementHorizontal(matrix, row, col)) {
                    for (int k = 0; k < 3; k++) {
                        matrix[row][col + k] = 'X';
                    }
                    desksPlaced++;
                }
            }
        }
        return desksPlaced;
    }

    static int placeDesksVertical(char[][] matrix, int remainingDesks) {
        int desksPlaced = 0;
        int y = matrix.length;
        int x = matrix[0].length;

        // vertical desks
        for (int col = x - 1; col >= 0 && desksPlaced < remainingDesks; col--) {
            for (int row = 0; row < y - 2 && desksPlaced < remainingDesks; row += 2) {
                if (isValidPlacementVertical(matrix, row, col)) {
                    for (int k = 0; k < 3; k++) {
                        matrix[row + k][col] = 'X';
                    }
                    desksPlaced++;
                }
            }
        }
        return desksPlaced;
    }

    static boolean isValidPlacementHorizontal(char[][] matrix, int row, int col) {
        int y = matrix.length;
        int x = matrix[0].length;

        if (col + 2 >= x) return false;

        // check surrounding area including diagonals
        for (int i = Math.max(0, row - 1); i <= Math.min(y - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(x - 1, col + 3); j++) {
                if (matrix[i][j] == 'X') return false;
            }
        }
        return true;
    }

    static boolean isValidPlacementVertical(char[][] matrix, int row, int col) {
        int y = matrix.length;
        int x = matrix[0].length;

        if (row + 2 >= y) return false;

        // check surrounding area including diagonals
        for (int i = Math.max(0, row - 1); i <= Math.min(y - 1, row + 3); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(x - 1, col + 1); j++) {
                if (matrix[i][j] == 'X') return false;
            }
        }
        return true;
    }

    static void writeMatrixToFile(char[][] matrix, FileWriter writer) throws Exception {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                writer.write(matrix[row][col]);
            }
            writer.write("\n");
        }
        writer.write("\n");
    }

    static void printMatrix(char[][] matrix) {
        System.out.println(matrix[0].length + "x" + matrix.length);
        for (char[] row : matrix) {
            for (char val : row) {
                System.out.print("[" + val + "] ");
            }
            System.out.println();
        }
        System.out.println();
    }
}