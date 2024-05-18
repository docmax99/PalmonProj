import java.io.IOException;
import java.util.*;

public class Gameplay {


    public static void main(String[] args) throws InterruptedException {

        Startabfragen();


    }


    public static Team generiere_RandomTeam(int AnzahlDerPalmons) {
        List<Palmon> palmons = CSVreader.ladePalmonsAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv");
        Team team = new Team();

        while (team.getTeamMember().size() < AnzahlDerPalmons) {
            int randomId = (int) (Math.random() * 1000);

            for (Palmon palmon : palmons) {
                if (palmon.getId() == randomId && !team.getTeamMember().containsKey(palmon.getId())) {
                    team.addMember(palmon);
                    break;
                }
            }
        }

        return team;
    }

    public static Team nachID_bestimmen(int AnzahlDerPalmons) {
        List<Palmon> palmons = CSVreader.ladePalmonsAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv");
        Team team = new Team();
        System.out.println("Bitte geben Sie " + AnzahlDerPalmons + " IDs der gewünschten Palmons ein: ");
        Scanner sc = new Scanner(System.in);
        while (team.getTeamMember().size() < AnzahlDerPalmons) {
            int eingabeID = sc.nextInt();
            for (Palmon palmon : palmons) {
                if (palmon.getId() == eingabeID && !team.getTeamMember().containsKey(palmon.getId())) {
                    team.addMember(palmon);
                    break;
                }
            }
        }

        return team;
    }


    public static void Startabfragen() throws InterruptedException {

        final String RESET = "\u001B[0m"; // Setzt die Farbe zurück
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";


        int totalWidth = 50; // Gesamtbreite des Ladebalkens
        System.out.println("Ladevorgang startet...");

        for (int i = 0; i <= totalWidth; i++) {
            int progress = (i * 100) / totalWidth;
            System.out.print("\r["); // Beginne den Ladebalken


            for (int j = 0; j < totalWidth; j++) {
                if (j < i) {
                    System.out.print(RED + "=" + RESET); // Gefüllter Teil des Balkens
                } else {
                    System.out.print(" "); // Noch nicht gefüllter Teil
                }
            }
            System.out.print("] " + progress + "%");
            Thread.sleep(100); // Simuliere eine Wartezeit von 100 Millisekunden
        }
        System.out.println("\nLadevorgang abgeschlossen!");

        // Eingaben des Nutzers zum Start

        System.out.println("Bitte geben Sie die Anzahl der Palmons ein: ");
        Scanner sc = new Scanner(System.in);
        int eingabeAnzahlderPalmons = sc.nextInt();
        int AnzahlDerPalmons = eingabeAnzahlderPalmons;
        if (eingabeAnzahlderPalmons > 1195) {
            System.out.println("Leider haben wir nicht Genügend Palmons für so ein gewalltiges Event :-( ");
        }
        System.out.println("Anzahl der Palmons: " + AnzahlDerPalmons); // Sicherung noch ein Bauen Das es nicht mehr als 1195 sein kann
        System.out.println("Wollen Sie die Palmons nach id, Typ oder random auswählen?");
        String eingabewiesollenPalmonsAusgewähltWerden = sc.next();


        if (eingabewiesollenPalmonsAusgewähltWerden.equals("random")) {
            Team MyTeamRandom = generiere_RandomTeam(eingabeAnzahlderPalmons);
            List<Integer> palmonIDS = new ArrayList<>(MyTeamRandom.getTeamMember().keySet());
            System.out.println("Ihr Team besteht aus: ");
            MyTeamRandom.printTeamMember();
            Palmon FirstPalmon = MyTeamRandom.getPalmonById(palmonIDS.get(0));
            //System.out.println("Das erste Palmon in Ihrem Team ist: " + FirstPalmon.getName() + " der Schaden Beträgt " + FirstPalmon.getAttack());

            // Gegner Team generieren
            Team GegnerTeamRandom = generiere_RandomTeam(eingabeAnzahlderPalmons); // Noch eine Auswhal Bauen ob es ein Random Team oder ein Team mit bestimmten Palmons ist
            List<Integer> GegnerpalmonIDS = new ArrayList<>(GegnerTeamRandom.getTeamMember().keySet());
            System.out.println("Das Gegner Team besteht aus: ");
            GegnerTeamRandom.printTeamMember();
            Palmon FirstGegnerPalmon = GegnerTeamRandom.getPalmonById(GegnerpalmonIDS.get(0));
            //System.out.println("Das erste Palmon in Ihrem Team ist: " + FirstGegnerPalmon.getName() + " der Schaden Beträgt " + FirstGegnerPalmon.getAttack());
            // Kampf starten

            startFight(MyTeamRandom, GegnerTeamRandom);


        } else if (eingabewiesollenPalmonsAusgewähltWerden.equals("id")) {
            Team MyTeamID = nachID_bestimmen(eingabeAnzahlderPalmons);
            System.out.println("Ihr Team besteht aus: ");
            MyTeamID.printTeamMember();

        } else if (eingabewiesollenPalmonsAusgewähltWerden.equals("Typ")) {
            System.out.println("Bitte geben Sie den Typ ein: ");
            // Noch Fertig machen !!!!
        } else {
            System.out.println("Falsche Eingabe");
        }


    }

    public static void startFight(Team MyTeam, Team GegnerTeam) {
        List<Integer> palmonIDS = new ArrayList<>(MyTeam.getTeamMember().keySet());
        List<Integer> GegnerpalmonIDS = new ArrayList<>(GegnerTeam.getTeamMember().keySet());
        Palmon FirstMyPalmon = MyTeam.getPalmonById(palmonIDS.get(0));
        Palmon FirstGegnerPalmon = GegnerTeam.getPalmonById(GegnerpalmonIDS.get(0));
        System.out.println("Das erste Palmon in Ihrem Team ist: " + FirstMyPalmon.getName() + " der Schaden Beträgt " + FirstMyPalmon.getAttack());
        System.out.println("Das erste Palmon in Ihrem Team ist: " + FirstGegnerPalmon.getName() + " der Schaden Beträgt " + FirstGegnerPalmon.getAttack());

        if (FirstMyPalmon.getSpeed() > FirstGegnerPalmon.getSpeed()) {
            int GesuchtePalmonIDfürlinks = FirstMyPalmon.getId();
            List<Palmon_move> palmon_moves = CSVreader.ladePalmon_moveAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon_move.csv");
            Map<Integer, Palmon_move> palmon_movesMap = Platzhalter.ladePalmon_movesInHashMap(palmon_moves);
            for (Palmon_move palmon_move : palmon_moves) {
                if (palmon_move.getPalmon_id() == GesuchtePalmonIDfürlinks) {
                    System.out.println("Name des Palmon_moves: " + palmon_move.getMove_id());
                    List<Moves> moves = CSVreader.ladeMovesAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/moves.csv");
                    Map<Integer, Moves> movesMap = Platzhalter.ladeMovesInHashMap(moves);
                    if (palmon_move.getMove_id() == moves.getFirst().getId()) {
                        System.out.println("Name des Moves: " + moves.getFirst().getName());
                        if (moves.getFirst().getAccuracy() == 100) { // Ab Hier noch mal Drüber Schauen Wichtig
                            System.out.println("Der Move hat eine Genauigkeit von 100%");
                            int random = (int) (Math.random() * 100);
                            if (random <= 100) {
                                System.out.println("Der Move hat getroffen");
                                int Schaden = (int) (FirstMyPalmon.getAttack() + moves.getFirst().getDamage());
                                System.out.println("Der Schaden beträgt: " + Schaden);
                                int newHP = FirstGegnerPalmon.getHp() - Schaden;
                                FirstGegnerPalmon.setHp(newHP);
                                System.out.println("Die HP des Gegner Palmons beträgt: " + FirstGegnerPalmon.getHp());
                            } else {
                                System.out.println("Der Move hat verfehlt");
                            }
                        } else {
                            System.out.println("Der Move hat eine Genauigkeit von: " + moves.getFirst().getAccuracy());
                            int random = (int) (Math.random() * 100);
                            if (random <= moves.getFirst().getAccuracy()) {
                                System.out.println("Der Move hat getroffen");
                                int Schaden = (int) (FirstMyPalmon.getAttack() * moves.getFirst().getDamage());
                                System.out.println("Der Schaden beträgt: " + Schaden);
                                int newHP = FirstGegnerPalmon.getHp() - Schaden;
                                FirstGegnerPalmon.setHp(newHP);
                                System.out.println("Die HP des Gegner Palmons beträgt: " + FirstGegnerPalmon.getHp());
                            } else {
                                System.out.println("Der Move hat verfehlt");
                            }
                        }
                    }
                }
            }
        } else {
            int GesuchtePalmonIDfürlinks = FirstGegnerPalmon.getId();
            List<Palmon_move> palmon_moves = CSVreader.ladePalmon_moveAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon_move.csv");
            Map<Integer, Palmon_move> palmon_movesMap = Platzhalter.ladePalmon_movesInHashMap(palmon_moves);
            for (Palmon_move palmon_move : palmon_moves) {
                if (palmon_move.getPalmon_id() == GesuchtePalmonIDfürlinks) {
                    System.out.println("Name des Palmon_moves: " + palmon_move.getMove_id());
                    List<Moves> moves = CSVreader.ladeMovesAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/moves.csv");
                    Map<Integer, Moves> movesMap = Platzhalter.ladeMovesInHashMap(moves);
                    if (palmon_move.getMove_id() == moves.getFirst().getId()) {
                        System.out.println("Name des Moves: " + moves.getFirst().getName());
                        if (moves.getFirst().getAccuracy() == 100) { // Ab Hier noch mal Drüber Schauen Wichtig
                            System.out.println("Der Move hat eine Genauigkeit von 100%");
                            int random = (int) (Math.random() * 100);
                            if (random <= 100) {
                                System.out.println("Der Move hat getroffen");
                                int Schaden = (int) (FirstGegnerPalmon.getAttack() + moves.getFirst().getDamage());
                                System.out.println("Der Schaden beträgt: " + Schaden);
                                int newHP = FirstMyPalmon.getHp() - Schaden;
                                FirstMyPalmon.setHp(newHP);
                                System.out.println("Die HP dienes Palmons beträgt: " + FirstMyPalmon.getHp());
                            } else {
                                System.out.println("Der Move hat verfehlt");
                            }
                        } else {
                            System.out.println("Der Move hat eine Genauigkeit von: " + moves.getFirst().getAccuracy());
                            int random = (int) (Math.random() * 100);
                            if (random <= moves.getFirst().getAccuracy()) {
                                System.out.println("Der Move hat getroffen");
                                int Schaden = (int) (FirstGegnerPalmon.getAttack() * moves.getFirst().getDamage());
                                System.out.println("Der Schaden beträgt: " + Schaden);
                                int newHP = FirstMyPalmon.getHp() - Schaden;
                                FirstMyPalmon.setHp(newHP);
                                System.out.println("Die HP deines Palmons beträgt: " + FirstMyPalmon.getHp());
                            } else {

                                System.out.println("Der Move hat verfehlt");
                            }

                            // Noch Fertig machen !!!!
                        }
                    }
                }
            }
        }
    }

}