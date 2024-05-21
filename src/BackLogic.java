import java.util.*;
import java.util.stream.Collectors;

public class BackLogic {

    private final CSVreader reader;
    private final List<Palmon> palmons;
    private final Map<Integer, List<Palmon_move>> palmonMovesMap;
    private final Map<Integer, Moves> movesMap;
    private final Map<String, Map<String, Double>> effectivityMap;

    public BackLogic() {
        reader = new CSVreader();
        palmons = CSVreader.ladePalmonsAusCsv(reader.getPath_palmon());
        palmonMovesMap = getPalmonMovesMap(CSVreader.ladePalmon_moveAusCsv(reader.getPath_palmon_move()));
        movesMap = CSVreader.ladeMovesAusCsv(reader.getPath_moves()).stream().collect(Collectors.toMap(Moves::getId, move -> move));
        effectivityMap = loadEffectivity();
    }

    private List<Palmon> loadPalmons() {
        return palmons;
    }

    private Map<String, Map<String, Double>> loadEffectivity() {
        List<Effectivity> effectivities = CSVreader.ladeEffectivityAusCsv(reader.getPath_effectivity());
        Map<String, Map<String, Double>> effectivityMap = new HashMap<>();

        for (Effectivity effectivity : effectivities) {
            effectivityMap.computeIfAbsent(effectivity.getTarget_type(), k -> new HashMap<>())
                    .put(effectivity.getInitial_type(), effectivity.getDamage_factor() / 100.0);
        }
        return effectivityMap;
    }

    private Team createTeam(int AnzahlDerPalmons, List<Palmon> palmons, String Kriterium, int minLevel, int maxLevel) {
        Team team = new Team();
        Scanner sc = new Scanner(System.in);

        switch (Kriterium) {
            case "random":
                Random rand = new Random();
                while (team.getTeamMember().size() < AnzahlDerPalmons) {
                    int randomId = rand.nextInt(palmons.size());
                    Palmon palmon = palmons.get(randomId);
                    if (!team.getTeamMember().containsKey(palmon.getId())) {
                        setRandomLevel(palmon, minLevel, maxLevel);
                        team.addMember(palmon);
                    }
                }
                break;
            case "id":
                System.out.println("Bitte geben Sie " + AnzahlDerPalmons + " IDs der gewünschten Palmons ein: ");
                while (team.getTeamMember().size() < AnzahlDerPalmons) {
                    int eingabeID = getValidId(sc, palmons);
                    //Palmon palmon = getPalmonById(palmons, eingabeID);
                    Palmon palmon = palmons.stream().filter(p -> p.getId() == eingabeID).findFirst().orElse(null);
                    if (palmon != null && !team.getTeamMember().containsKey(palmon.getId())) {
                        setRandomLevel(palmon, minLevel, maxLevel);
                        team.addMember(palmon);
                    }
                }
                break;
            case "typ":
                System.out.println("Bitte geben Sie den Typ der gewünschten Palmons ein: ");
                String eingabeTyp = getValidTyp(sc, palmons);
                while (team.getTeamMember().size() < AnzahlDerPalmons) {
                    for (Palmon palmon : palmons) {
                        if (palmon.getTyp1().equalsIgnoreCase(eingabeTyp) && !team.getTeamMember().containsKey(palmon.getId())) {
                            setRandomLevel(palmon, minLevel, maxLevel);
                            team.addMember(palmon);
                            break;
                        }
                    }
                }
                break;
            default:
                System.out.println("Falsche Eingabe");
                break;
        }

        return team;
    }

    private String getValidTyp(Scanner scanner, List<Palmon> palmons){
        String eingabeTyp;
        while(true){
            try {
                eingabeTyp = scanner.next().toLowerCase();
                if (eingabeTyp.equals("bug")||eingabeTyp.equals("dark")||eingabeTyp.equals("dragon")||eingabeTyp.equals("electric")||eingabeTyp.equals("fairy")||eingabeTyp.equals("fighting")||eingabeTyp.equals("fire")||eingabeTyp.equals("flying")||eingabeTyp.equals("ghost")||eingabeTyp.equals("grass")||eingabeTyp.equals("ground")||eingabeTyp.equals("ice")||eingabeTyp.equals("normal")||eingabeTyp.equals("poison")||eingabeTyp.equals("psychic")||eingabeTyp.equals("rock")||eingabeTyp.equals("steel")||
                        eingabeTyp.equals("water")){
                        break;
                } else {
                    System.out.println("Ungültiger Typ eingegeben. Bitte versuchen Sie es erneut.");
                }

            }catch (NumberFormatException e){
                System.out.println("Ungültige Eingabe. Bitte geben Sie einen gültigen Typ ein.");
            }
        }
        return eingabeTyp;
    }

    private int getValidId(Scanner sc, List<Palmon> palmons) {
        int eingabeID;
        while (true) {
            try {
                eingabeID = Integer.parseInt(sc.next());
                if (isValidId(palmons, eingabeID)) {
                    break;
                } else {
                    System.out.println("Ungültige ID eingegeben. Bitte versuchen Sie es erneut.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine numerische ID eingeben.");
            }
        }
        return eingabeID;
    }

    private void setRandomLevel(Palmon palmon, int minLevel, int maxLevel) {
        Random random = new Random();
        int level = random.nextInt((maxLevel - minLevel) + 1) + minLevel;
        palmon.setLevel(level);
        System.out.println("Palmon ID " + palmon.getId() + " wurde auf Level " + level + " gesetzt."); // Debug-Ausgabe
    }

    private boolean isValidId(List<Palmon> palmons, int id) {
        for (Palmon palmon : palmons) {
            if (palmon.getId() == id) {
                return true;
            }
        }
        return false;
    }

//    private void addPalmonToTeamById(List<Palmon> palmons, Team team, int id) {
//        for (Palmon palmon : palmons) {
//            if (palmon.getId() == id && !team.getTeamMember().containsKey(palmon.getId())) {
//                team.addMember(palmon);
//                break;
//            }
//        }
//    }

    public void startAbfragen() throws InterruptedException {
        final String RESET = "\u001B[0m"; // Setzt die Farbe zurück
        final String RED = "\u001B[31m";
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

        System.out.println("Willkommen bei Palmon! \n" +
                "In diesem Spiel kannst du dein eigenes Team aus Palmons zusammenstellen und gegen andere Teams kämpfen. \n" +
                "Jedes Palmon hat eigene Werte wie Angriff, Verteidigung, Level und Geschwindigkeit. \n" +
                "Wähle weise, um die besten Chancen auf den Sieg zu haben! \n");

        Scanner sc = new Scanner(System.in);
        int AnzahlPalmonsimEigenenTeam;
        while (true) {
            System.out.println("Aus wie vielen Palmons soll dein Team bestehen: ");
            try {
                AnzahlPalmonsimEigenenTeam = Integer.parseInt(sc.next());
                if (AnzahlPalmonsimEigenenTeam > 1092 || AnzahlPalmonsimEigenenTeam < 1) {
                    System.out.println("Leider haben wir nicht genügend Palmons für so ein gewaltiges Event :-( ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
            }
        }

        System.out.println("Wollen Sie die Palmons nach id, Typ oder random auswählen?");
        String eingabewiesollenPalmonsAusgewähltWerden;
        while (true) {
            eingabewiesollenPalmonsAusgewähltWerden = sc.next().toLowerCase();
            if (eingabewiesollenPalmonsAusgewähltWerden.equals("id") || eingabewiesollenPalmonsAusgewähltWerden.equals("typ") || eingabewiesollenPalmonsAusgewähltWerden.equals("random")) {
                break;
            } else {
                System.out.println("Falsche Eingabe. Bitte wählen Sie zwischen id, Typ oder random.");
            }
        }


        int minLevel;
        while (true){
            System.out.println("Geben Sie das minimale Level ein: ");
            try {
                minLevel = Integer.parseInt(sc.next());
                if (minLevel < 1 || minLevel > 99) {
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen 1 und 99 eingeben.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
            }
        }


        int maxLevel;
        while (true){
            System.out.println("Geben Sie das maximale Level ein: ");
            try {
            maxLevel = Integer.parseInt(sc.next());
            if (maxLevel < minLevel || maxLevel > 100){
                System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen dem MiniLevel und 100 eingeben.");
            } else {
                break;
            }

            }catch (NumberFormatException e){
                System.out.println("Ungültige Eingabe. Bitte geben sie eine Zahl ein: ");
            }
        }

        List<Palmon> palmons = loadPalmons();
        Team MyTeam = createTeam(AnzahlPalmonsimEigenenTeam, palmons, eingabewiesollenPalmonsAusgewähltWerden, minLevel, maxLevel);
        MyTeam.printTeamMember();

        System.out.println("Wie soll das Gegner Team generiert werden, random oder selbstgewählt?");
        String eingabewieGegnerTeamGeneriertWerden;
        while (true) {
            eingabewieGegnerTeamGeneriertWerden = sc.next().toLowerCase();
            if (eingabewieGegnerTeamGeneriertWerden.equals("random") || eingabewieGegnerTeamGeneriertWerden.equals("selbstgewählt")) {
                break;
            } else {
                System.out.println("Falsche Eingabe. Bitte wählen Sie zwischen random oder selbstgewählt.");
            }
        }

        int AnzahlPGegnerTeam;
        if (eingabewieGegnerTeamGeneriertWerden.equals("random")) {
            AnzahlPGegnerTeam = new Random().nextInt(1194) + 1;
        } else {
            while (true) {
                System.out.println("Wie viele Palmons soll das Gegnerteam haben?");
                try {
                    AnzahlPGegnerTeam = Integer.parseInt(sc.next());
                    if (AnzahlPGegnerTeam > 1092 || AnzahlPGegnerTeam < 1) {
                        System.out.println("Leider haben wir nicht genügend Palmons für so ein gewaltiges Event :-( ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                }
            }
        }

        System.out.println("Anzahl der Palmons im Gegner Team: " + AnzahlPGegnerTeam);
        Team GegnerTeam = createTeam(AnzahlPGegnerTeam, palmons, "random", minLevel, maxLevel);
        GegnerTeam.printTeamMember();

        // Kampf starten
        startFight(MyTeam, GegnerTeam);

        // Sieger ermitteln
        if (GegnerTeam.getTeamMember().isEmpty()) {
            System.out.println("Herzlichen Glückwunsch! Du hast gewonnen!");
            MyTeam.printTeamMember();
        } else {
            System.out.println("Der Gegner hat gewonnen! Dein Team wurde besiegt.");
            GegnerTeam.printTeamMember();
        }
    }

    private Map<Integer, List<Palmon_move>> getPalmonMovesMap(List<Palmon_move> palmon_moves) {
        Map<Integer, List<Palmon_move>> palmonMovesMap = new HashMap<>();

        for (Palmon_move palmonMove : palmon_moves) {
            int palmonId = palmonMove.getPalmon_id();
            palmonMovesMap.computeIfAbsent(palmonId, k -> new ArrayList<>()).add(palmonMove);
        }

        return palmonMovesMap;
    }

    public void startFight(Team team1, Team team2) {
        Palmon palmon1 = getNextPalmon(team1);
        Palmon palmon2 = getNextPalmon(team2);
        final String RESET = "\u001B[0m"; // Setzt die Farbe zurück
        final String RED = "\u001B[31m"; // Setzte die Farbe auf Rot Gegner
        final String BLUE = "\u001B[34m"; // Setzte die Farbe auf Blau Spieler

        while (palmon1 != null && palmon2 != null) {
            while (palmon1.getHp() > 0 && palmon2.getHp() > 0) {
                System.out.println("\n-------------------------------------");
                System.out.println("KAMPF: " + BLUE + palmon1.getName() + RESET + " (HP: " + palmon1.getHp() + ") vs. " + RED + palmon2.getName() + RESET + " (HP: " + palmon2.getHp() + ")");
                System.out.println("-------------------------------------");

                if (palmon1.getSpeed() > palmon2.getSpeed()) {
                    performAttack(palmon1, palmon2, true);
                    if (palmon2.getHp() > 0) {
                        performAttack(palmon2, palmon1, false);
                    }
                } else {
                    performAttack(palmon2, palmon1, false);
                    if (palmon1.getHp() > 0) {
                        performAttack(palmon1, palmon2, true);
                    }
                }
            }

            if (palmon1.getHp() <= 0) {
                System.out.println(BLUE + palmon1.getName() + RESET + " ist besiegt!");
                palmon1 = getNextPalmon(team1);
                if (palmon1 != null) {
                    System.out.println("Wechsel zu " + BLUE + palmon1.getName() + RESET);
                }
            }

            if (palmon2.getHp() <= 0) {
                System.out.println(RED + palmon2.getName() + RESET + " ist besiegt!");
                palmon2 = getNextPalmon(team2);
                if (palmon2 != null) {
                    System.out.println("Wechsel zu " + RED + palmon2.getName() + RESET);
                }
            }
        }

        if (palmon1 == null) {
            System.out.println("Der Gegner hat gewonnen! Dein Team wurde besiegt.");
        } else if (palmon2 == null) {
            System.out.println("Herzlichen Glückwunsch! Du hast gewonnen!");
        }
    }

    private Palmon getNextPalmon(Team team) {
        return team.getTeamMember().values().stream().filter(p -> p.getHp() > 0).findFirst().orElse(null);
    }

    private void performAttack(Palmon attacker, Palmon defender, boolean isPlayerControlled) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        List<Palmon_move> palmonMoves = palmonMovesMap.get(attacker.getId());

        if (palmonMoves == null || palmonMoves.isEmpty()) {
            System.out.println("Keine Moves vorhanden für " + attacker.getName());
            return;
        }

        // Sortieren der Moves nach Schaden in absteigender Reihenfolge und Auswahl der Top 4
        // Updated code for selecting possible moves based on level

        List<Moves> possibleMoves = palmonMovesMap.get(attacker.getId()).stream()
                .filter(palmonMove -> palmonMove.getLearned_on_level() <= attacker.getLevel())
                .map(palmonMove -> movesMap.get(palmonMove.getMove_id()))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Moves::getDamage).reversed())
                .limit(4)
                .toList();


        if (possibleMoves.isEmpty()) {
            System.out.println(attacker.getName() + " hat keine Moves die er einsetzen kann.");
            return;
        }

        Moves selectedMove;

        if (isPlayerControlled) {
            System.out.println(attacker.getName() + "s verfügbare Moves:");
            for (int i = 0; i < possibleMoves.size(); i++) {
                Moves move = possibleMoves.get(i);
                System.out.println((i + 1) + ". " + move.getName() + " - Schaden: " + move.getDamage() + ", Genauigkeit: " + move.getAccuracy() + ", Level: " + move.getLearned_on_level());
            }
            System.out.println("Wähle eine Attacke:");
            int moveSelection;
            while (true) {
                try {
                    moveSelection = Integer.parseInt(sc.next());
                    if (moveSelection < 1 || moveSelection > possibleMoves.size()) {
                        System.out.println("Falsche Eingabe. Bitte eine gültige Move-Nummer auswählen.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                }
            }
            selectedMove = possibleMoves.get(moveSelection - 1);
        } else {
            selectedMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
        }

        System.out.println(attacker.getName() + " greift " + defender.getName() + " mit " + selectedMove.getName() + " an!");

        if (random.nextInt(100) < selectedMove.getAccuracy()) {
            String attackType = attacker.getTyp1();
            String defenseType = defender.getTyp1();
            String attackType2 = attacker.getTyp2();
            String defenseType2 = defender.getTyp2();

            double factor = 1.0;
            if (effectivityMap.containsKey(attackType) && effectivityMap.get(attackType).containsKey(defenseType)) {
                factor = effectivityMap.get(attackType).get(defenseType);
            }
            if (attackType2 != null && !attackType2.isEmpty()) {
                if (effectivityMap.containsKey(attackType2) && effectivityMap.get(attackType2).containsKey(defenseType)) {
                    factor *= effectivityMap.get(attackType2).get(defenseType);
                }
            }
            if (defenseType2 != null && !defenseType2.isEmpty()) {
                if (effectivityMap.containsKey(attackType) && effectivityMap.get(attackType).containsKey(defenseType2)) {
                    factor *= effectivityMap.get(attackType).get(defenseType2);
                }
                if (attackType2 != null && !attackType2.isEmpty()) {
                    if (effectivityMap.containsKey(attackType2) && effectivityMap.get(attackType2).containsKey(defenseType2)) {
                        factor *= effectivityMap.get(attackType2).get(defenseType2);
                    }
                }
            }

            System.out.println("Faktor: " + factor); // Debug-Ausgabe
            int damage = (int) ((attacker.getAttack() + selectedMove.getDamage() - defender.getDefense()) * factor);

            if (damage < 0) damage = 0;
            defender.setHp(defender.getHp() - damage);
            System.out.println(defender.getName() + " nimmt " + damage + " Schaden. Verbleibende HP: " + defender.getHp());
        } else {
            System.out.println(attacker.getName() + "s Angriff verfehlt!");
        }
    }
}
