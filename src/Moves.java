public class Moves {
// Klasse Moves wird erstellt
    private Integer id;
    private String Name;
    private Integer Damage;
    private Integer Max_usages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getDamage() {
        return Damage;
    }

    public void setDamage(Integer damage) {
        Damage = damage;
    }

    public Integer getMax_usages() {
        return Max_usages;
    }

    public void setMax_usages(Integer max_usages) {
        Max_usages = max_usages;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
    public Integer getLearned_on_level() {
        return learned_on_level;
    }

    public void setLearned_on_level(Integer learned_on_level) {
        this.learned_on_level = learned_on_level;
    }

    private Integer accuracy;
    private String Type;
    private Integer learned_on_level;

    public Moves(Integer _id, String _Name, Integer _Damage, Integer max_usages, Integer _accuracy, String _Type){ // Konstruktor der Klasse Moves wird erstellt
        this.id = _id;
        this.Name = _Name;
        this.Damage = _Damage;
        this.Max_usages = max_usages;
        this.accuracy = _accuracy;
        this.Type = _Type;
    }

}