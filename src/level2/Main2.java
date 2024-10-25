package level2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main2 {

    public static void main(String[] args) throws Exception {

        File inDir = new File("/src/level2/in");
        for (File file : inDir.listFiles()) {
            lev2(file);
        }


    }

    static void lev2(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // N
        int roomAmount = Integer.parseInt(reader.readLine());

        File outFile = new File("/src/level2/out/" + file.getName() + ".out");
        FileWriter writer = new FileWriter(outFile);

        for (int i = 0; i < roomAmount; i++) {
            String[] split = reader.readLine().split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);

            int numberOfTablesInRow = x / 3;

            String matrix = "";
            int tableNum = 0;
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < numberOfTablesInRow; k++) {
                    matrix += "" + tableNum + tableNum + tableNum;
                }
            }

            writer.append(matrix);
        }


    }
}
