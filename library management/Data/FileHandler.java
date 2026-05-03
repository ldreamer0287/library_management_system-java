package Data;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    // LOAD DATA FROM FILE
    public static ArrayList<String> load(String filename) {
        ArrayList<String> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error loading file");
        }

        return list;
    }

    // SAVE DATA TO FILE
    public static void save(String filename, ArrayList<String> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            for (String item : list) {
                bw.write(item);
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }
}