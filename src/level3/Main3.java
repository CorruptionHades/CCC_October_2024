package level3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Main3 {

    public static void main(String[] args) throws Exception {
        File inDir = new File("/src/level3/in");
        for (File file : inDir.listFiles()) {
            lev3(file);
        }

        lev3(new File("/src/level3/in/test"));
    }

    static void lev3(File file) throws Exception {
        Scanner sc = new Scanner(file);
        int numberOfRooms = sc.nextInt();

        File outFile = new File("/src/level3/out/" + file.getName() + ".out");
        FileWriter writer = new FileWriter(outFile);

        for (int i = 0; i < numberOfRooms; i++) {
            int x = sc.nextInt();
            int xDiv = x - x % 3;
            int y = sc.nextInt();
            int yDiv = y - y % 3;
            sc.nextInt(); // Desks

            int[][] matrix = new int[y][x];

            for (int j = 0; j < y; j++) {
                for (int k = 0; k < xDiv; k++) {
                    matrix[j][k] = j * xDiv / 3 + k / 3 + 1;
                }
            }
            for (int j = 0; j < x % 3; j++) {
                for (int k = 0; k < yDiv; k++) {
                    matrix[k][j + xDiv] = y * xDiv / 3 + j * yDiv / 3 + k / 3;
                }
            }

            for (int[] row : matrix) {
                for (int val : row) {
                    writer.append(val + " ");
                }
                writer.append("\n");
            }
            writer.append("\n");
        }

        writer.close();
        sc.close();
    }
}