package pp.model.enums;

public enum BattleType {
    BATTLE(1, "battlesapp"),
    DUEL(2, "duelsapp"),
    SURVIVE(4, "survivalapp"),
    EXAM(5, "exam");
    private int id;
    private String act;

    private BattleType(int id, String act) {
        this.id = id;
        this.act = act;
    }

    public int getId() {
        return id;
    }

    public String getAct() {
        return act;
    }
}
