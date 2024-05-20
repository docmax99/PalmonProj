import java.util.*;

public class BackLogic {

    private final CSVreader reader;
    private final List<Palmon> palmons;
    private final Map<Integer, List<Moves>> palmonMovesMap;

    public BackLogic() {
        reader = new CSVreader();
        palmons = CSVreader.ladePalmonsAusCsv(reader.getPath_palmon());
        palmonMovesMap = getPalmonMovesMap(CSVreader.ladePalmon_moveAusCsv(reader.getPath_palmon_move()), CSVreader.ladeMovesAusCsv(reader.getPath_moves()));
    }

    private List<Palmon> loadPalmons() {
        return palmons;
    }

    private Team createTeam(int AnzahlDerPalmons, List<Palmon> palmons, String Kriterium) {
        Team team = new Team();
        Scanner sc = new Scanner(System.in);

        switch (Kriterium) {
            case "random":
                Random rand = new Random();
                while (team.getTeamMember().size() < AnzahlDerPalmons) {
                    int randomId = rand.nextInt(palmons.size());
                    addPalmonToTeamById(palmons, team, randomId);
                }
                break;
            case "id":
                System.out.println("Bitte geben Sie " + AnzahlDerPalmons + " IDs der gewünschten Palmons ein: ");
                while (team.getTeamMember().size() < AnzahlDerPalmons) {
                    int eingabeID = getValidId(sc, palmons);
                    addPalmonToTeamById(palmons, team, eingabeID);
                }
                break;
            case "typ":
                System.out.println("Bitte geben Sie den Typ der gewünschten Palmons ein: ");
                String eingabeTyp = sc.next();
                while (team.getTeamMember().size() < AnzahlDerPalmons) {
                    for (Palmon palmon : palmons) {
                        if (palmon.getTyp1().equalsIgnoreCase(eingabeTyp) && !team.getTeamMember().containsKey(palmon.getId())) {
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

    private boolean isValidId(List<Palmon> palmons, int id) {
        for (Palmon palmon : palmons) {
            if (palmon.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private void addPalmonToTeamById(List<Palmon> palmons, Team team, int id) {
        for (Palmon palmon : palmons) {
            if (palmon.getId() == id && !team.getTeamMember().containsKey(palmon.getId())) {
                team.addMember(palmon);
                break;
            }
        }
    }

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
                if (AnzahlPalmonsimEigenenTeam > 1194) {
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

        List<Palmon> palmons = loadPalmons();
        Team MyTeam = createTeam(AnzahlPalmonsimEigenenTeam, palmons, eingabewiesollenPalmonsAusgewähltWerden);
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
                    if (AnzahlPGegnerTeam > 1194) {
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
        Team GegnerTeam = createTeam(AnzahlPGegnerTeam, palmons, "random");
        GegnerTeam.printTeamMember();

        // Kampf starten
        startFight(MyTeam, GegnerTeam);

        // Sieger ermitteln
        if (GegnerTeam.getTeamMember().isEmpty()) {
            System.out.println("Herzlichen Glückwunsch! Du hast gewonnen!");
            MyTeam.printTeamMember();
        } else {
            System.out.println("Der Gegner hat gewonnen! Dein Team wurde besiegt.");
        }
    }

    private Map<Integer, List<Moves>> getPalmonMovesMap(List<Palmon_move> palmon_moves, List<Moves> moves) {
        Map<Integer, List<Moves>> palmonMovesMap = new HashMap<>();

        for (Palmon_move palmon_move : palmon_moves) {
            int palmonId = palmon_move.getPalmon_id();
            Moves move = moves.stream().filter(m -> m.getId().equals(palmon_move.getMove_id())).findFirst().orElse(null);
            if (move != null) {
                palmonMovesMap.computeIfAbsent(palmonId, k -> new ArrayList<>()).add(move);
            }
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
            }

            if (palmon2.getHp() <= 0) {
                System.out.println(RED + palmon2.getName() + RESET + " ist besiegt!");
                palmon2 = getNextPalmon(team2);
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
        List<Moves> moves = palmonMovesMap.get(attacker.getId());

        if (moves == null || moves.isEmpty()) {
            System.out.println("Keine Moves vorhanden für " + attacker.getName());
            return;
        }

        // Sortieren der Moves nach Schaden in absteigender Reihenfolge und Auswahl der Top 4
        List<Moves> topMoves = moves.stream()
                .sorted(Comparator.comparing(Moves::getDamage).reversed()).limit(4)
                .toList();

        Moves selectedMove;

        if (isPlayerControlled) {
            System.out.println(attacker.getName() + "s verfügbare Moves:");
            for (int i = 0; i < topMoves.size(); i++) {
                Moves move = topMoves.get(i);
                System.out.println((i + 1) + ". " + move.getName() + " - Schaden: " + move.getDamage() + ", Genauigkeit: " + move.getAccuracy());
            }
            System.out.println("Wähle eine Attacke:");
            int moveSelection;
            while (true) {
                try {
                    moveSelection = Integer.parseInt(sc.next());
                    if (moveSelection < 1 || moveSelection > topMoves.size()) {
                        System.out.println("Falsche Eingabe. Bitte eine gültige Move-Nummer auswählen.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                }
            }
            selectedMove = topMoves.get(moveSelection - 1);
        } else {
            selectedMove = topMoves.get(random.nextInt(topMoves.size()));
        }

        System.out.println(attacker.getName() + " greift " + defender.getName() + " mit " + selectedMove.getName() + " an!");

        if (random.nextInt(100) < selectedMove.getAccuracy()) {
            int damage = attacker.getAttack() + selectedMove.getDamage() - defender.getDefense();
            if (damage < 0) damage = 0;
            defender.setHp(defender.getHp() - damage);
            System.out.println(defender.getName() + " nimmt " + damage + " Schaden. Verbleibende HP: " + defender.getHp());
        } else {
            System.out.println(attacker.getName() + "s Angriff verfehlt!");
        }
    }
}
