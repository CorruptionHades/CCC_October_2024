package level1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main1 {
    public static void main(String[] args) throws Exception {

        File inDir = new File("/src/level1/in");
        for (File file : inDir.listFiles()) {
            lev1(file);
        }
    }

    private static void lev1(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int numberOfRooms = Integer.parseInt(reader.readLine());

        File outFile = new File("/src/level1/out/" + file.getName() + ".out");
        FileWriter writer = new FileWriter(outFile);

        String line = "";
        for (int i = 0; i < numberOfRooms; i++) {
            line = reader.readLine();
            String[] split = line.split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);

            int size = x*y;
            int tableAmount = size / 3;


            writer.append(tableAmount + "\n");
        }

        writer.close();
        reader.close();
    }
}