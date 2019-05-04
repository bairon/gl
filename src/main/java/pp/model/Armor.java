package pp.model;

public class Armor extends AModel {
    private long level;

    private long typeid;

    private long statusid;

    private long durability;

    private long maxdurability;

    private long damage;

    private long head;

    private long body;

    private long arms;

    private long legs;

    private long morale;

    public long getMorale() {
        return morale;
    }

    public void setMorale(long morale) {
        this.morale = morale;
    }

    public long getLegs() {
        return legs;
    }

    public void setLegs(long legs) {
        this.legs = legs;
    }

    public long getArms() {
        return arms;
    }

    public void setArms(long arms) {
        this.arms = arms;
    }

    public long getBody() {
        return body;
    }

    public void setBody(long body) {
        this.body = body;
    }

    public long getHead() {
        return head;
    }

    public void setHead(long head) {
        this.head = head;
    }

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
    }

    public long getMaxdurability() {
        return maxdurability;
    }

    public void setMaxdurability(long maxdurability) {
        this.maxdurability = maxdurability;
    }

    public long getDurability() {
        return durability;
    }

    public void setDurability(long durability) {
        this.durability = durability;
    }

    public long getStatusid() {
        return statusid;
    }

    public void setStatusid(long statusid) {
        this.statusid = statusid;
    }

    public long getTypeid() {
        return typeid;
    }

    public void setTypeid(long typeid) {
        this.typeid = typeid;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }
}
