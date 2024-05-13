import java.io.IOException;
import java.util.*;

public class Gameplay {


    public static void main(String[] args) {





        // Eingabe der Anzahl der Palmons
      System.out.println("Bitte geben Sie die Anzahl der Palmons ein: ");
      Scanner sc = new Scanner(System.in);
      int eingabeAnzahlderPalmons = sc.nextInt();
      int AnzahlDerPalmons = eingabeAnzahlderPalmons;
      System.out.println("Anzahl der Palmons: " + AnzahlDerPalmons);
      System.out.println("Wollen Sie die Palmons nach id, Typ oder random auswählen?");
      String eingabewiesollenPalmonsAusgewähltWerden = sc.next();

      if (eingabewiesollenPalmonsAusgewähltWerden.equals("random")){
          generiereRandomTeam();
          generiereRandomTeam();
      } else if (eingabewiesollenPalmonsAusgewähltWerden.equals("id")) {
        nachIDbestimmen();
      }


      // Eingabe des Gewünschten Pokemons
   // int gesuchtesPalmon = 12; // Hier Entscheide ich welche Palmons ich auslesen will
   // SenderEmpfänger.setZahl(gesuchtesPalmon);



    }

    public static void generiereRandomTeam(){
      List<Palmon> palmons = CSVreader.ladePalmonsAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv");
      List<Palmon> team = new ArrayList<>();

      while (team.size()<5){ // die 5 alss Variable ersetzen
        int randomId = (int) (Math.random() * 1000);

        for (Palmon palmon : palmons){
          if (palmon.getId() == randomId && !team.contains(palmon)){
            team.add(palmon);
            break;
          }
        }
      }

      for (Palmon palmon : team){
        System.out.println(palmon.getName());
      }
    }

    public static void nachIDbestimmen(){
      List<Palmon> palmons = CSVreader.ladePalmonsAusCsv("/Users/louis/IdeaProjects/Palmon/src/CSVinput/palmon.csv");
        List<Palmon> team = new ArrayList<>();
      System.out.println("Bitte geben Sie die ID der Palmons ein: ");
      Scanner sc = new Scanner(System.in);
      while (team.size()<5){
        int eingabeID  = sc.nextInt();
        for (Palmon palmon : palmons){
          if (palmon.getId() == eingabeID && !team.contains(palmon)){
            team.add(palmon);
            break;
          }
        }
      }
      for (Palmon palmon : team){
        System.out.println(palmon.getName());
      }

    }




}