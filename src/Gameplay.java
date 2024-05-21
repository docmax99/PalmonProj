
public class Gameplay {


    public static void main(String[] args) throws InterruptedException { // die InterruptedException wird geworfen, wenn ein Thread unterbrochen wird

        BackLogic backLogic = new BackLogic(); // Instanz der Klasse BackLogic wird erstellt
        backLogic.startAbfragen(); // Methode startAbfragen() wird aufgerufen

        // Mehr ist hier nicht da, da die Methode startAbfragen() alle weiteren Methoden aufruft
    }

}