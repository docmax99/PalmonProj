import java.util.HashMap;

public class Team { // Klasse Team wird erstellt


    private HashMap<Integer, Palmon> teamMember; // HashMap teamMember wird erstellt, die Integer und Palmon enthält

    public Team() {
        this.teamMember = new HashMap<>(); // teamMember wird initialisiert
    }

    public void addMember(Palmon palmon){
        this.teamMember.put(palmon.getId(), palmon); // Palmon wird in teamMember hinzugefügt
    }

    public HashMap<Integer, Palmon> getTeamMember(){
        return this.teamMember; // teamMember wird zurückgegeben
    }

    public void printTeamMember(){
        for (Palmon palmon : teamMember.values()){
            System.out.println(palmon.getName()); // Name des Palmon wird ausgegeben
        }
    }

    public Palmon getPalmonById(int id){
        return teamMember.get(id);
    }




}
