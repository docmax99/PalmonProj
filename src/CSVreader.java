import java.io.*;
import java.util.*;


public class CSVreader {


    String path1 = "/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv";
    String path2 = "/Users/louis/IdeaProjects/Palmon/src/CSVinput/moves.csv";
    String path3 = "/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon_move.csv";
    String path4 = "/Users/louis/IdeaProjects/Palmon/src/CSVinput/effectivity.csv";

    public String getPath1() {
        return path1;
    }

    public String getPath2() {
        return path2;
    }

    public String getPath3() {
        return path3;
    }

    public String getPath4() {
        return path4;
    }




    public static List<Palmon> ladePalmonsAusCsv(String path1) {
        List<Palmon> registerderPalmonsList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path1));
            br.readLine();
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

    public static List<Moves> ladeMovesAusCsv(String path2){
        List<Moves> registerderMovesList =new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path2));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length >= 6 ){
                    try {
                        Moves registerderMoves = new Moves(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]),Integer.parseInt(values[4]),values[5]);
                        registerderMovesList.add(registerderMoves);
                    } catch (NumberFormatException e) {

                        System.err.println("Fehler beim persen einer Zahl " + e.getMessage());
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return registerderMovesList;
    }

    public static List<Palmon_move> ladePalmon_moveAusCsv(String path3) {
        List<Palmon_move> registerderPalmon_moveList = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path3));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null){
                String[] values = line.split(";");

                if (values.length >= 3){
                    try {
                        Palmon_move registerderPalmon_move = new Palmon_move(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                        registerderPalmon_moveList.add(registerderPalmon_move);
                    } catch (NumberFormatException e) {

                        System.err.println("Fehler beim persen einer Zahl " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registerderPalmon_moveList;
    }


    public static List<Effectivity> ladeEffectivityAusCsv(String path4) {
        List<Effectivity> registerderEffectivityList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path4));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length >= 3){
                    try {
                        String damage_FactorString = values[2].replace("%","");
                        int damageFactor = Integer.parseInt(damage_FactorString);

                        Effectivity registerderEffectivity = new Effectivity(values[0], values[1], damageFactor);
                        registerderEffectivityList.add(registerderEffectivity);
                    } catch (NumberFormatException e) {

                        System.err.println("Fehler beim persen einer Zahl " + e.getMessage());
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registerderEffectivityList;
    }





}
