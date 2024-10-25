package level5;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main5 {

    public static void main(String[] args) throws Exception {
        File inDir = new File("/src/level5/in");
        for (File file : inDir.listFiles()) {
            lev5(file);
        }
    }

    static Pair<char[][], Integer> placeDesksSpiral(char[][] matrix, int tableNum) {
        int desksPlaced = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Start from the bottom-right corner
        int row = rows - 1;
        int col = cols - 1;

        Vec2 playerPos = new Vec2(col, row);
        boolean first = true;
        int rotation = 0;

        while (desksPlaced < tableNum) {

            System.out.println(playerPos.x + " " + playerPos.y);

            // check if player is back at beginning to stop loop
            if(rotation == 4) {

                System.out.println("Free spots:");
                printMatrix(getFreeSpots(matrix));

                break;
            }

            int remaining = playerPos.x;

            if(playerPos.x == 2 && !isValidPlacementVertical(matrix, playerPos.y-1, playerPos.x)) {
                System.out.println("Now rotating matrix");
                matrix = rotate(matrix);
                rotation++;
                rows = matrix.length;
                cols = matrix[0].length;
                playerPos.y = rows - 1;
                playerPos.x = cols - 1;
            }
            else if(playerPos.x == 0) {

                if(isValidPlacementVertical(matrix, playerPos.y-1, playerPos.x)) {
                    placeVerticalDesk(matrix, playerPos.y - 1, playerPos.x);
                }

                System.out.println("Now rotating matrix");
                matrix = rotate(matrix);
                rotation++;
                rows = matrix.length;
                cols = matrix[0].length;
                playerPos.y = rows - 1;
                playerPos.x = cols - 1;
            }

            if(isValidPlacementVertical(matrix, playerPos.y-1, playerPos.x)) {
                placeVerticalDesk(matrix, playerPos.y-1, playerPos.x);

                if(first) {
                    first = false;
                }

                desksPlaced++;
                playerPos.x--;
            }
            else {
                playerPos.x--;
            }

          /*  printMatrix(matrix);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } */

        }

        return new Pair<>(matrix, desksPlaced);
    }

    static char[][] getFreeSpots(char[][] matrix) {
        // get all spots where a desk could be placed
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] freeSpots = new char[rows][cols];

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(matrix[row][col] == '.') {
                    freeSpots[row][col] = 'X';
                }
                else {
                    freeSpots[row][col] = '.';
                }
            }
        }

        return freeSpots;
    }

    static char[][] rotate(char[][] matrix) {
        // rotate the matrix 90 degrees anti-clockwise
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] rotated = new char[cols][rows];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                rotated[cols - 1 - col][row] = matrix[row][col];
            }
        }

        return rotated;
    }
    
    static void lev5(File file) throws Exception {
        Scanner sc = new Scanner(file);
        int numberOfRooms = sc.nextInt();

        File outFile = new File("/src/level5/out/" + file.getName().replace(".in", "") + ".out");
        FileWriter writer = new FileWriter(outFile);

        for (int i = 0; i < numberOfRooms; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int tableNum = sc.nextInt();

            char[][] matrix = new char[y][x];
            initializeMatrix(matrix);
            Pair<char[][], Integer> desksPlaced = placeDesksSpiral(matrix, tableNum);

            System.out.println("FINAL MATRIX:");
            printMatrix(desksPlaced.first);

            writeMatrixToFile(desksPlaced.first, writer);
            System.out.println("Desks placed: " + desksPlaced);
            System.out.println("Desks needed: " + tableNum);
        }

        writer.close();
    }

    static void placeHorizontalDesk(char[][] matrix, int row, int col) {
        matrix[row][col] = 'X';
        matrix[row][col + 1] = 'X';
    }

    static void placeVerticalDesk(char[][] matrix, int row, int col) {
        matrix[row][col] = 'X';
        matrix[row + 1][col] = 'X';
    }

    static void initializeMatrix(char[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = '.';
            }
        }
    }

    static boolean isValidPlacementHorizontal(char[][] matrix, int row, int col) {
        int y = matrix.length;
        int x = matrix[0].length;

        if (col + 1 >= x) return false;

        for (int i = Math.max(0, row - 1); i <= Math.min(y - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(x - 1, col + 2); j++) {
                if (matrix[i][j] == 'X') return false;
            }
        }
        return true;
    }

    static boolean isValidPlacementVertical(char[][] matrix, int row, int col) {
        int y = matrix.length;
        int x = matrix[0].length;

        if (row + 1 >= y) return false;

        for (int i = Math.max(0, row - 1); i <= Math.min(y - 1, row + 2); i++) {
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

    static void printMatrix(char[][] matrix, Vec2 playerPos) {
        System.out.println(matrix[0].length + "x" + matrix.length);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (row == playerPos.y && col == playerPos.x) {
                    System.out.print("[P] ");
                } else {
                    System.out.print("[" + matrix[row][col] + "] ");
                }
            }
            System.out.println();
        }
        System.out.println();
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

    static class Pair<N, T> {
        N first;
        T second;
        Pair(N first, T second) {
            this.first = first;
            this.second = second;
        }
    }

    static class Vec2 {
        int x, y;
        Vec2(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}