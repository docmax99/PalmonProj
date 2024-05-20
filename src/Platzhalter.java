import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Platzhalter {

    public static void main(String[] args) {

        CSVreader reader = new CSVreader();
        List<Palmon> palmons = CSVreader.ladePalmonsAusCsv(reader.getPath_palmon());
        Map<Integer, Palmon> palmonMap = ladePalmonsInHashMap(palmons);
        Map<String, Palmon> palmonMaptyp = ladePalmonsInHashMapTyp(palmons);

        List<Moves> moves = CSVreader.ladeMovesAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/moves.csv");
        Map<Integer, Moves> movesMap = ladeMovesInHashMap(moves);

        List<Palmon_move> palmon_moves = CSVreader.ladePalmon_moveAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon_move.csv");
        Map<Integer, Palmon_move> palmon_movesMap = ladePalmon_movesInHashMap(palmon_moves);

        List<Effectivity> effectivities = CSVreader.ladeEffectivityAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/effectivity.csv");
        Map<String, Effectivity> effectivityMap = ladeEffectivityInHashMap(effectivities);


        // Zugriff auf ein Move in der HashMap
        for (Moves moves1 : moves){
            if (moves1.getId()==2){
                System.out.println("Name des Moves: " + moves1.getName());

            }
        }

        // Zugriff auf ein Palmon_moves in der HashMap
        int gesuchterPalmonzumlink = 2;

        for (Palmon_move palmon_move : palmon_moves){
            if (palmon_move.getPalmon_id()==gesuchterPalmonzumlink){
                System.out.println("Name des Palmon_moves: " + palmon_move.getMove_id());
            }
        }

        // Zugriff auf ein Effectivity in der HashMap
        String Arten = "normal";
        for (Effectivity effectivity : effectivities){
            if (effectivity.getInitial_type().equalsIgnoreCase(Arten)){
                System.out.println("Typ" + effectivity.getInitial_type()+ " ist effektiv gegen: " + effectivity.getTarget_type());
            }
        }

        // Zugriff auf ein Palmon in der HashMap
        for (Palmon palmon : palmonMap.values()) {
            if (palmon.getTyp1().equalsIgnoreCase("fire")) {
                System.out.println("Name des Wasser-Palmons: " + palmon.getName());
            }
        }
    }




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

    public static Map<Integer,Moves> ladeMovesInHashMap(List<Moves> moves){
        Map<Integer,Moves> movesMap = new HashMap<>();

        for (Moves move : moves){
            movesMap.put(move.getId(),move);
        }

        return movesMap;
    }

    public static Map<Integer, Palmon_move> ladePalmon_movesInHashMap(List<Palmon_move> palmon_moves) {
        Map<Integer, Palmon_move> palmon_movesMap = new HashMap<>();

        for (Palmon_move palmon_move : palmon_moves) {
            palmon_movesMap.put(palmon_move.getPalmon_id(), palmon_move);
        }

        return palmon_movesMap;
    }

    public static Map<String, Effectivity> ladeEffectivityInHashMap(List<Effectivity> effectivities) {
        Map<String, Effectivity> effectivityMap = new HashMap<>();

        for (Effectivity effectivity : effectivities) {
            effectivityMap.put(effectivity.getInitial_type(), effectivity);
        }

        return effectivityMap;
    }




}
