import java.util.List;

public class Player {


    public Player() {
        List<Palmon> palmons = CSVreader.ladePalmonsAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv");

        int gesuchtesPalmon = SenderEmpfänger.getZahl();


        for (Palmon palmon : palmons) {
            if (palmon.getId() == gesuchtesPalmon) {
                System.out.println(palmon.getName());
                break;
            }
        }
    }
}
