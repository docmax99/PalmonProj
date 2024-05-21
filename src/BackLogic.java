import java.util.*;
import java.util.stream.Collectors;

public class BackLogic {

    // Klassenvariablen für CSVreader und verschiedene Maps und Listen
    private final CSVreader reader;
    private final List<Palmon> palmons;
    private final Map<Integer, List<Palmon_move>> palmonMovesMap;
    private final Map<Integer, Moves> movesMap;
    private final Map<String, Map<String, Double>> effectivityMap;

    // Konstruktor der Klasse BackLogic (Das Gehirn des Spiels)
    public BackLogic() {
        reader = new CSVreader(); // Initialisierung des CSVreaders
        palmons = CSVreader.ladePalmonsAusCsv(reader.getPath_palmon()); // Laden der Palmons aus der CSV-Datei
        palmonMovesMap = getPalmonMovesMap(CSVreader.ladePalmon_moveAusCsv(reader.getPath_palmon_move())); // Laden der Palmon-Moves aus der CSV-Datei und Erstellen der Map
        movesMap = CSVreader.ladeMovesAusCsv(reader.getPath_moves()).stream().collect(Collectors.toMap(Moves::getId, move -> move)); // Laden der Moves aus der CSV-Datei und Erstellen der Map
        effectivityMap = loadEffectivity(); // Laden der Effizienz-Daten
    }

    // Methode zum Laden der Palmons (Unsere "Figuren" im Spiel)
    private List<Palmon> loadPalmons() {
        return palmons;
    }

    // Methode zum Laden der Effizienz-Daten (Die Effektivität der Moves gegenüber den verschiedenen Typen)
    private Map<String, Map<String, Double>> loadEffectivity() {
        List<Effectivity> effectivities = CSVreader.ladeEffectivityAusCsv(reader.getPath_effectivity());
        Map<String, Map<String, Double>> effectivityMap = new HashMap<>();

        for (Effectivity effectivity : effectivities) {
            effectivityMap.computeIfAbsent(effectivity.getTarget_type(), k -> new HashMap<>())
                    .put(effectivity.getInitial_type(), effectivity.getDamage_factor() / 100.0);
        }
        return effectivityMap;
    }

    // Methode zum Erstellen eines Teams
    private Team createTeam(int AnzahlDerPalmons, List<Palmon> palmons, String Kriterium, int minLevel, int maxLevel) {
        Team team = new Team();
        Scanner sc = new Scanner(System.in);

        switch (Kriterium) { // Auswahl des Kriteriums zur Auswahl der Palmons
            case "random": // Zufällige Auswahl der Palmons
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
            case "id": // Auswahl der Palmons anhand der ID
                System.out.println("Bitte geben Sie " + AnzahlDerPalmons + " IDs der gewünschten Palmons ein: ");
                while (team.getTeamMember().size() < AnzahlDerPalmons) {
                    int eingabeID = getValidId(sc, palmons);
                    Palmon palmon = palmons.stream().filter(p -> p.getId() == eingabeID).findFirst().orElse(null);
                    if (palmon != null && !team.getTeamMember().containsKey(palmon.getId())) {
                        setRandomLevel(palmon, minLevel, maxLevel);
                        team.addMember(palmon);
                    }
                }
                break;
            case "typ": // Auswahl der Palmons anhand des Typs
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
            default: // Im Falle einer falschen Eingabe wird das hier ausgeben
                System.out.println("Falsche Eingabe");
                break;
        }

        return team; // Rückgabe des Teams
    }

    // Methode zur Überprüfung und Eingabe eines gültigen Typs
    private String getValidTyp(Scanner scanner, List<Palmon> palmons) { // Hier wird überprüft ob der Typ existiert und ob er gültig ist
        String eingabeTyp;
        while (true) {
            try {
                eingabeTyp = scanner.next().toLowerCase();
                if (Arrays.asList("bug", "dark", "dragon", "electric", "fairy", "fighting", "fire", "flying", "ghost", "grass", "ground", "ice", "normal", "poison", "psychic", "rock", "steel", "water").contains(eingabeTyp)) {
                    break;
                } else {
                    System.out.println("Ungültiger Typ eingegeben. Bitte versuchen Sie es erneut.");
                }

            } catch (NumberFormatException e) { // Im Falle von Ungültigen Eingaben wird das hier ausgegeben
                System.out.println("Ungültige Eingabe. Bitte geben Sie einen gültigen Typ ein.");
            }
        }
        return eingabeTyp;
    }

    // Methode zur Überprüfung und Eingabe einer gültigen ID
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
            } catch (NumberFormatException e) { // Im Fall das eine ID eingegbene wird die nicht existiert wird das hier ausgegeben
                System.out.println("Ungültige Eingabe. Bitte eine numerische ID eingeben.");
            }
        }
        return eingabeID;
    }

    // Methode zum Setzen eines zufälligen Levels
    private void setRandomLevel(Palmon palmon, int minLevel, int maxLevel) { // In unseren Spiel legt der User die Ober und Untergrenze des Levels fest Aber die Level der Palmons werden zufällig generiert. // Hier werden die Level dann auch noch zugeordent
        Random random = new Random();
        int level = random.nextInt((maxLevel - minLevel) + 1) + minLevel;
        palmon.setLevel(level);
        System.out.println("Palmon ID " + palmon.getId() + " wurde auf Level " + level + " gesetzt."); // Debug-Ausgabe
    }

    // Methode zur Überprüfung, ob die ID gültig ist
    private boolean isValidId(List<Palmon> palmons, int id) {
        for (Palmon palmon : palmons) {
            if (palmon.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Methode zur Anzeige eines Ladebalkens
    private void Lodingbaar() throws InterruptedException {
        final String RESET = "\u001B[0m"; // Setzt die Farbe zurück
        final String RED = "\u001B[31m"; // Setzte die Farbe auf Rot
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
        System.out.println("\nLadevorgang abgeschlossen!"); // Ladevorgang abgeschlossen
    }

    // Methode zum Starten des Abfrageprozesses
    public void startAbfragen() throws InterruptedException {

        Lodingbaar(); // Ladebalken anzeigen

        System.out.println("Willkommen bei Palmon! \n" + // Wilkommens Nachricht beim Starten des Spiels
                "In diesem Spiel kannst du dein eigenes Team aus Palmons zusammenstellen und gegen andere Teams kämpfen. \n" +
                "Jedes Palmon hat eigene Werte wie Angriff, Verteidigung, Level und Geschwindigkeit. \n" +
                "Wähle weise, um die besten Chancen auf den Sieg zu haben! \n");

        Scanner sc = new Scanner(System.in);
        int AnzahlPalmonsimEigenenTeam; // Hier werden die Anzahl an Teammitgliedern festgelegt für das User Team
        while (true) {
            System.out.println("Aus wie vielen Palmons soll dein Team bestehen: ");
            try {
                AnzahlPalmonsimEigenenTeam = Integer.parseInt(sc.next());
                if (AnzahlPalmonsimEigenenTeam > 1092 || AnzahlPalmonsimEigenenTeam < 1) {
                    System.out.println("Leider haben wir nicht genügend Palmons für so ein gewaltiges Event :-( ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) { // Sicherheit für Ungülltige Eingaben
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
            }
        }

        System.out.println("Wollen Sie die Palmons nach id, Typ oder random auswählen?");
        String eingabewiesollenPalmonsAusgewähltWerden; // Hier wird festgelegt wie die Palmons ausgewählt werden Nur für das User Team
        while (true) {
            eingabewiesollenPalmonsAusgewähltWerden = sc.next().toLowerCase();
            if (eingabewiesollenPalmonsAusgewähltWerden.equals("id") || eingabewiesollenPalmonsAusgewähltWerden.equals("typ") || eingabewiesollenPalmonsAusgewähltWerden.equals("random")) { // Schutz vor falscher Eingaben
                break;
            } else { // Falls eingabe in valide ist wird das hier ausgegeben
                System.out.println("Falsche Eingabe. Bitte wählen Sie zwischen id, Typ oder random.");
            }
        }

        int minLevel; // Hier wird das Minimale Level festgelegt
        while (true) {
            System.out.println("Geben Sie das minimale Level ein: ");
            try {
                minLevel = Integer.parseInt(sc.next());
                if (minLevel < 1 || minLevel > 99) { // Kontrollstruktur das der User denn Min wert nicht unter 1 und nicht über 99 legen kann
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen 1 und 99 eingeben."); // Ausgabe bei flascher eingabe
                } else {
                    break;
                }
            } catch (NumberFormatException e) { // Sicherheit für Ungültige Eingaben
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
            }
        }

        int maxLevel; // Hier wird das Maximale Level fest
        while (true) {
            System.out.println("Geben Sie das maximale Level ein: ");
            try {
                maxLevel = Integer.parseInt(sc.next());
                if (maxLevel < minLevel || maxLevel > 100) { // Kontrollstruktur das der User denn Max wert nicht unter dem Min wert und nicht über 100 legen kann
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen dem MiniLevel und 100 eingeben."); // Ausgabe bei flascher eingabe
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte geben sie eine Zahl ein: "); // Sicherheit für Ungültige Eingaben
            }
        }

        List<Palmon> palmons = loadPalmons(); // Palmons werden geladen
        Team MyTeam = createTeam(AnzahlPalmonsimEigenenTeam, palmons, eingabewiesollenPalmonsAusgewähltWerden, minLevel, maxLevel); // Erstellung des User Teams
        MyTeam.printTeamMember(); // Teammitglieder des Usere Teams werden hier ausgegeben

        System.out.println("Wie soll das Gegner Team generiert werden, random oder selbstgewählt?");
        String eingabewieGegnerTeamGeneriertWerden;
        while (true) {
            eingabewieGegnerTeamGeneriertWerden = sc.next().toLowerCase(); // Wie wird das Gegner Team generiert
            if (eingabewieGegnerTeamGeneriertWerden.equals("random") || eingabewieGegnerTeamGeneriertWerden.equals("selbstgewählt")) {
                break;
            } else { // Sicherung vor falscher Eingaben
                System.out.println("Falsche Eingabe. Bitte wählen Sie zwischen random oder selbstgewählt.");
            }
        }

        int AnzahlPGegnerTeam; // Hier wird die Anzahl der Palmons im Gegner Team festgelegt
        if (eingabewieGegnerTeamGeneriertWerden.equals("random")) { // Wenn random dann wird das hier ausgeführt
            AnzahlPGegnerTeam = new Random().nextInt(1092) + 1; // Es werden zufällig Palmons ausgewählt zwischen 1 und 1092
        } else {
            while (true) {
                System.out.println("Wie viele Palmons soll das Gegnerteam haben?");
                try {
                    AnzahlPGegnerTeam = Integer.parseInt(sc.next());
                    if (AnzahlPGegnerTeam > 1092 || AnzahlPGegnerTeam < 1) { // Sicherheit vor falscher Eingabe// zu viele Palmons oder zu wenige Palmons werden hier Ausgeschlossen
                        System.out.println("Leider haben wir nicht genügend Palmons für so ein gewaltiges Event :-( ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) { // Die 100.000 Kontrollstruktur :-)
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                }
            }
        }

        System.out.println("Anzahl der Palmons im Gegner Team: " + AnzahlPGegnerTeam); // Ausgabe der Anzahl der Palmons im Gegner Team
        Team GegnerTeam = createTeam(AnzahlPGegnerTeam, palmons, "random", minLevel, maxLevel); // In dieser Zeile wird das Gegner Team erstellt und mit zufälligen Palmons gefüllt
        GegnerTeam.printTeamMember(); // Teammitglieder des Gegnerteams ausdrucken

        // Kampf starten
        startFight(MyTeam, GegnerTeam); // Hier geht der Spaß los! Der Kampf wird gestartet

        // Sieger ermitteln
        if (Playerwin == true) { // Wenn der Spieler gewinnt wird das hier ausgegeben
            MyTeam.printTeamMember();
        } else if (Playerwin == false) { // Wenn der Spieler verliert wird das hier ausgegeben // Mit einer Motivierenden Nachricht
            GegnerTeam.printTeamMember();
            System.out.println("Hey King, mach dir keine Sorgen! Du wirst das nächste mal sicher gewinnen! Kopf Hoch :-)");
        }
    }

    // Methode zum Erstellen der Palmon-Moves-Map
    private Map<Integer, List<Palmon_move>> getPalmonMovesMap(List<Palmon_move> palmon_moves) {
        Map<Integer, List<Palmon_move>> palmonMovesMap = new HashMap<>();

        for (Palmon_move palmonMove : palmon_moves) { // Hier wird über die Palmon-Moves iteriert und die Palmon-Moves in die Map eingefügt
            int palmonId = palmonMove.getPalmon_id();
            palmonMovesMap.computeIfAbsent(palmonId, k -> new ArrayList<>()).add(palmonMove);
        }

        return palmonMovesMap;
    }

    boolean Playerwin; // Variable zur Bestimmung des einzig wahren Siegers

    // Methode zum Starten des Epischen Kampfes zwischen den zwei Teams Mensch gegen Roboter :-)
    public void startFight(Team team1, Team team2) {
        Palmon palmon1 = getNextPalmon(team1);
        Palmon palmon2 = getNextPalmon(team2);
        final String RESET = "\u001B[0m"; // Setzt die Farbe zurück
        final String RED = "\u001B[31m"; // Setzte die Farbe auf Rot Gegner
        final String BLUE = "\u001B[34m"; // Setzte die Farbe auf Blau Spieler

        while (palmon1 != null && palmon2 != null) {
            while (palmon1.getHp() > 0 && palmon2.getHp() > 0) {
                System.out.println("\n-------------------------------------"); // Die Linien dienen zu Visualiesierung des Kampfes // besseren überblick
                System.out.println("KAMPF: " + BLUE + palmon1.getName() + RESET + " (HP: " + palmon1.getHp() + ") vs. " + RED + palmon2.getName() + RESET + " (HP: " + palmon2.getHp() + ")"); // Stats
                System.out.println("-------------------------------------");

                if (palmon1.getSpeed() > palmon2.getSpeed()) { // Hier wird überprüft welcher Palmon schneller ist und wer zuerst angreift
                    performAttack(palmon1, palmon2, true); // Der Spieler greift an
                    if (palmon2.getHp() > 0) { // Wenn der Gegner noch lebt greift er an
                        performAttack(palmon2, palmon1, false); // Der Gegner greift an
                    }
                } else { // Wenn der Gegner schneller ist greift er zuerst an
                    performAttack(palmon2, palmon1, false);
                    if (palmon1.getHp() > 0) {
                        performAttack(palmon1, palmon2, true);
                    }
                }
            }

            if (palmon1.getHp() <= 0) { // Wenn der Spieler besiegt ist wird das hier ausgegeben
                System.out.println(BLUE + palmon1.getName() + RESET + " ist besiegt!");
                palmon1 = getNextPalmon(team1); // Der nächste Palmon wird ausgewählt
                if (palmon1 != null) { // Wenn der nächste Palmon existiert wird das hier ausgegeben
                    System.out.println("Wechsel zu " + BLUE + palmon1.getName() + RESET);
                }
            }

            if (palmon2.getHp() <= 0) { // Wenn der Gegner besiegt ist wird das hier ausgegeben
                System.out.println(RED + palmon2.getName() + RESET + " ist besiegt!");
                palmon2 = getNextPalmon(team2); // Der nächste Palmon wird ausgewählt
                if (palmon2 != null) {// Wenn der nächste Palmon existiert wird das hier ausgegeben
                    System.out.println("Wechsel zu " + RED + palmon2.getName() + RESET); // Der nächste Palmon wird ausgewählt
                }
            }
        }

        if (palmon1 == null) { // Wenn der Spieler besiegt ist wird das hier ausgegeben
            System.out.println("Der Gegner hat gewonnen! Dein Team wurde besiegt.");
            Playerwin = false; // Hier wird unseren boolean auf false gesetzt um zu übermitteln das der Gegner  Gewonnen hat
        } else if (palmon2 == null) {
            System.out.println("Herzlichen Glückwunsch! Du hast gewonnen!");
            Playerwin = true; // Hier wird unseren boolean auf true gesetzt um zu übermitteln das der Spieler Gewonnen hat
        }
    }

    // Methode zur Auswahl des nächsten Palmons im Team
    private Palmon getNextPalmon(Team team) { // Hier wird der nächste Palmon ausgewählt
        return team.getTeamMember().values().stream().filter(p -> p.getHp() > 0).findFirst().orElse(null); // In dieser Zeile wird das erste Palmon mit HP > 0 im Team zurückgegeben, sonst null!
    }

    // Methode zur Durchführung eines Angriffs
    private void performAttack(Palmon attacker, Palmon defender, boolean isPlayerControlled) { // Hier wird der Angriff durchgeführt
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        List<Palmon_move> palmonMoves = palmonMovesMap.get(attacker.getId()); // Hier wird die Liste der Palmon-Moves für den Angreifer abgerufen

        if (palmonMoves == null || palmonMoves.isEmpty()) { // Wenn keine Moves vorhanden sind wird das hier ausgegeben
            System.out.println("Keine Moves vorhanden für " + attacker.getName());
            return;
        }

        // Sortieren der Moves nach Schaden in absteigender Reihenfolge und Auswahl der Top 4
        List<Moves> possibleMoves = palmonMovesMap.get(attacker.getId()).stream()
                .filter(palmonMove -> palmonMove.getLearned_on_level() <= attacker.getLevel())
                .map(palmonMove -> movesMap.get(palmonMove.getMove_id()))
                .filter(Objects::nonNull).sorted(Comparator.comparing(Moves::getDamage).reversed()).limit(4).toList();
        //Filtern der Moves nach Level und Sortieren nach Schaden in absteigender Reihenfolge und Auswahl der Top 4

        if (possibleMoves.isEmpty()) { // Was passiert wenn keine Moves vorhanden sind
            System.out.println(attacker.getName() + " hat keine Moves die er einsetzen kann.");
            return;
        }

        Moves selectedMove;

        if (isPlayerControlled) { // Wenn der User an der Reihe ist (User Gesteuert!!!)
            System.out.println(attacker.getName() + "s verfügbare Moves:"); // Hier werden die verfügbaren Moves für den User ausgegeben
            for (int i = 0; i < possibleMoves.size(); i++) {
                Moves move = possibleMoves.get(i);
                System.out.println((i + 1) + ". " + move.getName() + " - Schaden: " + move.getDamage() + ", Genauigkeit: " + move.getAccuracy()); // Anzeige der Moves
            }
            System.out.println("Wähle eine Attacke:");
            int moveSelection;
            while (true) {
                try {
                    moveSelection = Integer.parseInt(sc.next());
                    if (moveSelection < 1 || moveSelection > possibleMoves.size()) { // Sicherheit vor falscher Eingabe
                        System.out.println("Falsche Eingabe. Bitte eine gültige Move-Nummer auswählen.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                }
            }
            selectedMove = possibleMoves.get(moveSelection - 1); // Der Move wird ausgewählt
        } else {
            selectedMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
        }

        System.out.println(attacker.getName() + " greift " + defender.getName() + " mit " + selectedMove.getName() + " an!"); // HIer wird angezeigt was passiert (Wer greift wenn mit was an :-) )

        if (random.nextInt(100) < selectedMove.getAccuracy()) {
            String attackType = attacker.getTyp1(); // In dieser Zeile wird der Typ1 des Angreifers abgerufen
            String defenseType = defender.getTyp1(); // In dieser Zeile wird der Typ1 des Verteidigers abgerufen
            String attackType2 = attacker.getTyp2(); // In dieser Zeile wird der Typ2 des Angreifers abgerufen
            String defenseType2 = defender.getTyp2();// In dieser Zeile wird der Typ2 des Verteidigers abgerufen

            double factor = 1.0; // Faktor für die Berechnung des Schadens
            if (effectivityMap.containsKey(attackType) && effectivityMap.get(attackType).containsKey(defenseType)) { // Hier wird überprüft ob der Angriffstyp effektiv gegen den Verteidigungstyp ist
                factor = effectivityMap.get(attackType).get(defenseType); // Hier wird der Faktor für den Schaden berechnet
            }
            if (attackType2 != null && !attackType2.isEmpty()) {
                if (effectivityMap.containsKey(attackType2) && effectivityMap.get(attackType2).containsKey(defenseType)) { // Hier wird überprüft ob der 2. Angriffstyp effektiv gegen den Verteidigungstyp ist
                    factor *= effectivityMap.get(attackType2).get(defenseType); // Hier wird der Faktor für den Schaden berechnet
                }
            }
            if (defenseType2 != null && !defenseType2.isEmpty()) {
                if (effectivityMap.containsKey(attackType) && effectivityMap.get(attackType).containsKey(defenseType2)) { // Hier wird überprüft ob der Angriffstyp effektiv gegen den 2. Verteidigungstyp ist
                    factor *= effectivityMap.get(attackType).get(defenseType2); // Hier wird der Faktor für den Schaden berechnet
                }
                if (attackType2 != null && !attackType2.isEmpty()) {
                    if (effectivityMap.containsKey(attackType2) && effectivityMap.get(attackType2).containsKey(defenseType2)) {// Hier wird überprüft ob der 2. Angriffstyp effektiv gegen den 2. Verteidigungstyp ist
                        factor *= effectivityMap.get(attackType2).get(defenseType2);// Hier wird der Faktor für den Schaden berechnet
                    }
                }
            }

            System.out.println("Faktor: " + factor); // Debug-Ausgabe
            int damage = (int) ((attacker.getAttack() + selectedMove.getDamage() - defender.getDefense()) * factor); // Hier wird der entgültige Schaden berechnet

            if (damage < 0) damage = 0; // Schutz vor negativem Schaden
            defender.setHp(defender.getHp() - damage); // Hier wird der Schaden vom Verteidiger abgezogen
            System.out.println(defender.getName() + " nimmt " + damage + " Schaden. Verbleibende HP: " + defender.getHp()); // Hier wird der Schaden unsern Spieler Präsentiert
        } else {
            System.out.println(attacker.getName() + "s Angriff verfehlt!"); // Wenn das glück nicht auf unserer Seite ist wird das hier ausgegeben
        }
    }
} // Geschaft :-)
