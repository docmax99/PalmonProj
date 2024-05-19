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


            // Gegner Team generieren
            Team GegnerTeamRandom = generiere_RandomTeam(eingabeAnzahlderPalmons); // Noch eine Auswhal Bauen ob es ein Random Team oder ein Team mit bestimmten Palmons ist
            List<Integer> GegnerpalmonIDS = new ArrayList<>(GegnerTeamRandom.getTeamMember().keySet());
            System.out.println("Das Gegner Team besteht aus: ");
            GegnerTeamRandom.printTeamMember();
            Palmon FirstGegnerPalmon = GegnerTeamRandom.getPalmonById(GegnerpalmonIDS.get(0));

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
        System.out.println("Das erste Palmon in Ihrem Team ist: " + FirstMyPalmon.getName() + " der Schaden Beträgt " + FirstMyPalmon.getAttack()+" Speed: "+FirstMyPalmon.getSpeed());
        System.out.println("Das erste Palmon aus dem Gegner Team ist: " + FirstGegnerPalmon.getName() + " der Schaden Beträgt " + FirstGegnerPalmon.getAttack()+" Speed: "+FirstGegnerPalmon.getSpeed());

        Scanner sc = new Scanner(System.in);
        Random random = new Random();



        List<Palmon> palmons = CSVreader.ladePalmonsAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv");
        Map<Integer, Palmon> palmonMap = Platzhalter.ladePalmonsInHashMap(palmons);

        List<Moves> moves = CSVreader.ladeMovesAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/moves.csv");
        Map<Integer, Moves> movesMap = Platzhalter.ladeMovesInHashMap(moves);

        List<Palmon_move> palmon_moves = CSVreader.ladePalmon_moveAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon_move.csv");
        Map<Integer, Palmon_move> palmon_movesMap = Platzhalter.ladePalmon_movesInHashMap(palmon_moves);

        List<Effectivity> effectivities = CSVreader.ladeEffectivityAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/effectivity.csv");
        Map<String, Effectivity> effectivityMap = Platzhalter.ladeEffectivityInHashMap(effectivities);




        if(FirstMyPalmon.getSpeed()>FirstGegnerPalmon.getSpeed()){
            System.out.println("Ihr Palmon ist schneller");
            System.out.println("Mein Palmon hat die id: "+FirstMyPalmon.getId());
            PriorityQueue<Moves> strongestMoves = new PriorityQueue<>(Comparator.comparing(Moves::getDamage).reversed());
            List<Moves> topFourMoves = new ArrayList<>();
            int DamageMyPalmon = FirstMyPalmon.getAttack();

            for (Palmon_move palmon_move : palmon_moves){
                if (palmon_move.getPalmon_id().equals(FirstMyPalmon.getId())){// learned level hinzufügen
                    //System.out.println("Name des Palmon_moves: " + palmon_move.getMove_id());
                    for (Moves moves1 : moves){
                        if (moves1.getId().equals(palmon_move.getMove_id())){
                            System.out.println("Name des Moves: " + moves1.getName());
                            strongestMoves.add(moves1);

                        }
                    }

                }

            }
            for (int i = 0; i < 4; i++) {
                if (!strongestMoves.isEmpty()) {
                    Moves strongestMove = strongestMoves.poll();
                    topFourMoves.add(strongestMove);
                    System.out.print(strongestMove.getName() + ", Schaden: " + strongestMove.getDamage()); System.out.print("  //  ");
                }
            }
            int eingabederNummerAuswahl = sc.nextInt();
            if (eingabederNummerAuswahl==1){
                if (!topFourMoves.isEmpty()){
                    Moves firstMove = topFourMoves.get(0); // Hol denn ersten Move aus der Liste
                    Integer damage = firstMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+" Accuracy: "+firstMove.getAccuracy());
                    int moveAccuracy = firstMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                        DamageMyPalmon = FirstMyPalmon.getAttack()+firstMove.getDamage();
                    } else{System.out.println("Der Move hat leider nicht getroffen");  DamageMyPalmon = FirstMyPalmon.getAttack();}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            } else if (eingabederNummerAuswahl==2) {
                if (!topFourMoves.isEmpty()){
                    Moves SecMove = topFourMoves.get(1); // Hol denn zweiten Move aus der Liste
                    Integer damage = SecMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+ " Accuracy: "+SecMove.getAccuracy());
                    int moveAccuracy = SecMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                        DamageMyPalmon = FirstMyPalmon.getAttack()+SecMove.getDamage();
                    } else{System.out.println("Der Move hat leider nicht getroffen"); DamageMyPalmon = FirstMyPalmon.getAttack();}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            } else if (eingabederNummerAuswahl==3) {
                if (!topFourMoves.isEmpty()){
                    Moves ThirdMove = topFourMoves.get(2); // Hol denn ersten Move aus der Liste
                    Integer damage = ThirdMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+ " Accuracy: "+ThirdMove.getAccuracy());
                    int moveAccuracy = ThirdMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                        DamageMyPalmon = FirstMyPalmon.getAttack()+ThirdMove.getDamage();
                    } else{System.out.println("Der Move hat leider nicht getroffen"); DamageMyPalmon = FirstMyPalmon.getAttack();}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            } else if (eingabederNummerAuswahl==4){
                if (!topFourMoves.isEmpty()){
                    Moves LastMove = topFourMoves.get(3); // Hol denn ersten Move aus der Liste
                    Integer damage = LastMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+ " Accuracy: "+LastMove.getAccuracy());
                    int moveAccuracy = LastMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                        DamageMyPalmon = FirstMyPalmon.getAttack()+LastMove.getDamage();
                    } else{System.out.println("Der Move hat leider nicht getroffen"); DamageMyPalmon = FirstMyPalmon.getAttack();}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            }else {
                System.out.println("Falsche Eingabe");
            }
            System.out.println("Der Schaden des eigenen Palmons ist: "+DamageMyPalmon);



        }else {
            System.out.println("Der Gegner ist schneller");
            System.out.println("Gegner Palmon hat die id: "+FirstGegnerPalmon.getId());
            PriorityQueue<Moves> strongestMoves = new PriorityQueue<>(Comparator.comparing(Moves::getDamage).reversed());
            List<Moves> topFourMoves = new ArrayList<>();
            int DamageGegnerPalmon = FirstGegnerPalmon.getAttack();

            for (Palmon_move palmon_move : palmon_moves){
                if (palmon_move.getPalmon_id().equals(FirstGegnerPalmon.getId())){// learned level hinzufügen
                    //System.out.println("Name des Palmon_moves: " + palmon_move.getMove_id());
                    for (Moves moves1 : moves){
                        if (moves1.getId().equals(palmon_move.getMove_id())){
                            System.out.println("Name des Moves: " + moves1.getName());
                            strongestMoves.add(moves1);


                        }
                    }
                }

            }
            for (int i = 0; i < 4; i++) {
                if (!strongestMoves.isEmpty()) {
                    Moves strongestMove = strongestMoves.poll();
                    topFourMoves.add(strongestMove);
                    System.out.print(strongestMove.getName() + ", Schaden: " + strongestMove.getDamage()); System.out.print("  //  ");
                }
            }
            int eingabederNummerAuswahl = sc.nextInt();

            if (eingabederNummerAuswahl==1){
                if (!topFourMoves.isEmpty()){
                    Moves firstMove = topFourMoves.get(0); // Hol denn ersten Move aus der Liste
                    Integer damage = firstMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+" Accuracy: "+firstMove.getAccuracy());
                    int moveAccuracy = firstMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                    } else{System.out.println("Der Move hat leider nicht getroffen");DamageGegnerPalmon = FirstGegnerPalmon.getAttack();;}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            } else if (eingabederNummerAuswahl==2) {
                if (!topFourMoves.isEmpty()){
                    Moves SecMove = topFourMoves.get(1); // Hol denn zweiten Move aus der Liste
                    Integer damage = SecMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+ " Accuracy: "+SecMove.getAccuracy());
                    int moveAccuracy = SecMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                    } else{System.out.println("Der Move hat leider nicht getroffen");DamageGegnerPalmon = FirstGegnerPalmon.getAttack();;}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            } else if (eingabederNummerAuswahl==3) {
                if (!topFourMoves.isEmpty()){
                    Moves ThirdMove = topFourMoves.get(2); // Hol denn ersten Move aus der Liste
                    Integer damage = ThirdMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+ " Accuracy: "+ThirdMove.getAccuracy());
                    int moveAccuracy = ThirdMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                    } else{System.out.println("Der Move hat leider nicht getroffen");DamageGegnerPalmon = FirstGegnerPalmon.getAttack();;}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            } else if (eingabederNummerAuswahl==4){
                if (!topFourMoves.isEmpty()){
                    Moves LastMove = topFourMoves.get(3); // Hol denn ersten Move aus der Liste
                    Integer damage = LastMove.getDamage(); // Speicher denn Schaden des Moves
                    System.out.println("Der Schaden des Moves ist: "+damage+ " Accuracy: "+LastMove.getAccuracy());
                    int moveAccuracy = LastMove.getAccuracy();
                    int randomNumber = random.nextInt(100)+1;
                    if(randomNumber <= moveAccuracy) {
                        System.out.println("Der Move hat getroffen");
                    } else{System.out.println("Der Move hat leider nicht getroffen");DamageGegnerPalmon = FirstGegnerPalmon.getAttack();;}
                } else {
                    System.out.println("Keine Moves vorhanden");
                }

            }else {
                System.out.println("Falsche Eingabe");
            }

            System.out.println("Der Schaden des Gegner Palmons ist: "+DamageGegnerPalmon);

        }




    }

}