public class Effectivity {
// Klasse Effectivity wird erstellt


    public String getInitial_type() {
        return initial_type;
    }

    public void setInitial_type(String initial_type) {
        this.initial_type = initial_type;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public Integer getDamage_factor() {
        return damage_factor;
    }

    public void setDamage_factor(Integer damage_factor) {
        this.damage_factor = damage_factor;
    }

    private String initial_type;
    private String target_type;
    private Integer damage_factor;

    public Effectivity(String initial_type, String target_type, Integer damage_factor){ // Konstruktor der Klasse Effectivity wird erstellt
        this.initial_type = initial_type;
        this.target_type = target_type;
        this.damage_factor = damage_factor;

    }
}
