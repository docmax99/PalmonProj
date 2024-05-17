import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Platzhalter {

    public static void main(String[] args) {


        List<Palmon> palmons = CSVreader.ladePalmonsAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv");
        Map<Integer, Palmon> palmonMap = ladePalmonsInHashMap(palmons);
        Map<String, Palmon> palmonMaptyp = ladePalmonsInHashMapTyp(palmons);

        // Zugriff auf ein Palmon in der HashMap
        int id = 200; // Ersetzen Sie dies durch die tatsächliche ID des Palmons, auf das Sie zugreifen möchten
        //Palmon palmon = palmonMap.get(id);
        Palmon palmonTyp = palmonMaptyp.get("Grass");

        for (Palmon palmon : palmonMap.values()) {
            if (palmon.getTyp1().equalsIgnoreCase("water")) {
                System.out.println("Name des Wasser-Palmons: " + palmon.getName());
            }
        }
    }


//        if (palmon != null) {
//            System.out.println("Name des Palmons: " + palmon.getName());
//        } else {
//            Sy
//           stem.out.println("Kein Palmon mit der ID " + id + " gefunden.");
//        }


    public static Map<Integer, Palmon> ladePalmonsInHashMap(List<Palmon> palmons) {
        Map<Integer, Palmon> palmonMap = new HashMap<>();

        for (Palmon palmon : palmons) {
            palmonMap.put(palmon.getId(), palmon);
        }

        return palmonMap;

    }

    public static Map<String, Palmon> ladePalmonsInHashMapTyp(List<Palmon> palmons) {
        Map<String, Palmon> palmonMaptyp = new HashMap<>();

        for (Palmon palmon : palmons) {
            palmonMaptyp.put(palmon.getTyp1(), palmon);
        }

        return palmonMaptyp;

    }


}
