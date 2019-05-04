package pp.model;

public class TournamentSetting {
    public String name;
    public int main;
    public int horse;
    public int lvl;
    public int priority;
    public int members;

    public TournamentSetting(String name, int main, int horse, int lvl, int priority, int members) {
        this.name = name;
        this.main = main;
        this.horse = horse;
        this.lvl = lvl;
        this.priority = priority;
        this.members = members;
    }

    @Override
    public String toString() {
        return name +
                "," + main +
                "," + horse +
                "," + lvl +
                "," + priority +
                "," + members +

                '}';
    }
}
