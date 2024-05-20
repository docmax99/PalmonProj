import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team {

    //private List<Palmon> teamMember;
    private HashMap<Integer, Palmon> teamMember;

    public Team() {
        this.teamMember = new HashMap<>();
    }

    public void addMember(Palmon palmon){
        this.teamMember.put(palmon.getId(), palmon);
    }

    public HashMap<Integer, Palmon> getTeamMember(){
        return this.teamMember;
    }

    public void printTeamMember(){
        for (Palmon palmon : teamMember.values()){
            System.out.println(palmon.getName());
        }
    }

    public Palmon getPalmonById(int id){
        return teamMember.get(id);
    }




}
