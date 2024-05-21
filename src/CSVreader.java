import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CSVreader {


    // Pfadangaben zu den relativemPfad CSV-Dateien
    private String relativePath_palmon = "src/CSVinput/palmon.csv";//
    private String relativePath_moves = "src/CSVinput/moves.csv";
    private String relativePath_palmon_move = "src/CSVinput/palmon_move.csv";
    private String relativePath_effectivity = "src/CSVinput/effectivity.csv";

    // Pfadangaben zu den absoluten Pfad CSV-Dateien
    private final Path absolutePath_palmon = Paths.get(relativePath_palmon).toAbsolutePath(); // Pfadangabe wird in absoluten Pfad umgewandelt
    private final Path absolutePath_moves = Paths.get(relativePath_moves).toAbsolutePath();
    private final Path absolutePath_palmon_move = Paths.get(relativePath_palmon_move).toAbsolutePath();
    private final Path absolutePath_effectivity = Paths.get(relativePath_effectivity).toAbsolutePath();




    public String getPath_palmon(){
        return absolutePath_palmon.toString();
    }

    public String getPath_moves(){
        return absolutePath_moves.toString();
    }

    public String getPath_palmon_move(){
        return absolutePath_palmon_move.toString();
    }

    public String getPath_effectivity(){
        return absolutePath_effectivity.toString();
    }




    public static List<Palmon> ladePalmonsAusCsv(String path) { // Methode ladePalmonsAusCsv wird erstellt
        List<Palmon> registerderPalmonsList = new ArrayList<>(); // Liste registerderPalmonsList wird erstellt
        String relativePath_palmon = "CSVinput/palmon.csv"; // Pfadangabe zu der CSV-Datei palmon.csv
        Path file = Paths.get(relativePath_palmon).toAbsolutePath(); // Pfadangabe wird in absoluten Pfad umgewandelt



        try {
            BufferedReader br = new BufferedReader(new FileReader(path)); // BufferedReader wird erstellt
            br.readLine(); // erste Zeile wird übersprungen
            String line;
            while ((line = br.readLine()) != null) { // solange die Zeile nicht leer ist
                String[] values = line.split(";"); // Zeile wird anhand des Semikolons getrennt

                if (values.length >= 10) { // wenn die Länge der Werte größer oder gleich 10 ist
                    try {
                        Palmon registerderPalmons = new Palmon(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[4], values[5], Integer.parseInt(values[6]), Integer.parseInt(values[7]), Integer.parseInt(values[8]), Integer.parseInt(values[9])); //Unsere Palmon werden hier erschaffen wie es einst Satoshi Tajiri tat
                        registerderPalmonsList.add(registerderPalmons); // Palmon wird der Liste hinzugefügt
                    } catch (NumberFormatException e) { // Fehlerbehandlung

                        System.err.println("Fehler beim persen einer Zahl " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace(); // Fehlerbehandlung
        }
        return registerderPalmonsList; // Liste wird zurückgegeben
    }
    // Das gleiche wie bei ladePalmonsAusCsv nur für Moves
    public static List<Moves> ladeMovesAusCsv(String path){
        List<Moves> registerderMovesList =new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
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

    public static List<Palmon_move> ladePalmon_moveAusCsv(String path) {
        List<Palmon_move> registerderPalmon_moveList = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
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


    public static List<Effectivity> ladeEffectivityAusCsv(String path) {
        List<Effectivity> registerderEffectivityList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (values.length >= 3){
                    try {
                        String damage_FactorString = values[2].replace("%",""); // Hier ist es ein Kleinbisschen anders da wir das Prozentzeichen entfernen
                        int damageFactor = Integer.parseInt(damage_FactorString); // und dann die Zahl parsen

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
    // Das war der CSVreader hoffe meine Misserablen Kommentare haben geholfen





}
