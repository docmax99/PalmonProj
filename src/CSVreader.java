import java.io.*;
import java.util.*;


public class CSVreader {


    String path1 = "/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv";
    String path2 = "/Users/louis/IdeaProjects/Palmon/src/CSVinput/moves.csv";
    String path3 = "/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon_move.csv";

    public String getPath1() {
        return path1;
    }

    public String getPath2() {
        return path2;
    }

    public String getPath3() {
        return path3;
    }




    public static List<Palmon> ladePalmonsAusCsv(String path1) {
        List<Palmon> registerderPalmonsList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path1));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length >= 10) {
                    try {
                        Palmon registerderPalmons = new Palmon(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[4], values[5], Integer.parseInt(values[6]), Integer.parseInt(values[7]), Integer.parseInt(values[8]), Integer.parseInt(values[9]));
                        registerderPalmonsList.add(registerderPalmons);
                    } catch (NumberFormatException e) {

                        System.err.println("Fehler beim persen einer Zahl " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return registerderPalmonsList;
    }

}
