public class Palmon {



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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getTyp1() {
        return Typ1;
    }

    public void setTyp1(String typ1) {
        Typ1 = typ1;
    }

    public String getTyp2() {
        return Typ2;
    }

    public void setTyp2(String typ2) {
        Typ2 = typ2;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private Integer id;
    private String Name;
    private Integer height;
    private Integer weight;
    private String Typ1;
    private String Typ2;
    private Integer hp;
    private Integer attack;
    private Integer defense;
    private Integer speed;
    private Integer level;

    public Palmon(Integer _id, String _name, Integer _height, Integer _weight, String _Typ1, String _Typ2, Integer _hp, Integer _attack, Integer _defense, Integer _speed){

        this.id = _id;
        this.Name = _name;
        this.height = _height;
        this.weight = _weight;
        this.Typ1 = _Typ1;
        this.Typ2 = _Typ2;
        this.hp = _hp;
        this.attack = _attack;
        this.defense = _defense;
        this.speed = _speed;

    }



}