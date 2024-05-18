public class Palmon_move {

    public Integer getPalmon_id() {
        return palmon_id;
    }

    public void setPalmon_id(Integer palmon_id) {
        this.palmon_id = palmon_id;
    }

    public Integer getMove_id() {
        return move_id;
    }

    public void setMove_id(Integer move_id) {
        this.move_id = move_id;
    }

    public Integer getLearned_on_level() {
        return learned_on_level;
    }

    public void setLearned_on_level(Integer learned_on_level) {
        this.learned_on_level = learned_on_level;
    }

    private Integer palmon_id;
    private Integer move_id;
    private Integer learned_on_level;


    public Palmon_move(Integer palmon_id, Integer move_id, Integer learned_on_level){
        this.palmon_id = palmon_id;
        this.move_id = move_id;
        this.learned_on_level = learned_on_level;
    }

}
