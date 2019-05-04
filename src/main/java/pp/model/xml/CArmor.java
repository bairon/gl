package pp.model.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Armor")
public class CArmor {
	@Attribute(name = "ID", required = false)
	private long id;

	@Element(name = "Level", required = false)
	private long level;

	@Element(name = "TypeID", required = false)
	private long typeid;

	@Element(name = "StatusID", required = false)
	private long statusid;

	@Element(name = "Durability", required = false)
	private long durability;

	@Element(name = "MaxDurability", required = false)
	private long maxdurability;

	@Element(name = "Damage", required = false)
	private long damage;

	@Element(name = "Head", required = false)
	private long head;

	@Element(name = "Body", required = false)
	private long body;

	@Element(name = "Arms", required = false)
	private long arms;

	@Element(name = "Legs", required = false)
	private long legs;

	@Element(name = "Morale", required = false)
	private long morale;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public long getTypeid() {
		return typeid;
	}

	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}

	public long getStatusid() {
		return statusid;
	}

	public void setStatusid(long statusid) {
		this.statusid = statusid;
	}

	public long getDurability() {
		return durability;
	}

	public void setDurability(long durability) {
		this.durability = durability;
	}

	public long getMaxdurability() {
		return maxdurability;
	}

	public void setMaxdurability(long maxdurability) {
		this.maxdurability = maxdurability;
	}

	public long getDamage() {
		return damage;
	}

	public void setDamage(long damage) {
		this.damage = damage;
	}

	public long getHead() {
		return head;
	}

	public void setHead(long head) {
		this.head = head;
	}

	public long getBody() {
		return body;
	}

	public void setBody(long body) {
		this.body = body;
	}

	public long getArms() {
		return arms;
	}

	public void setArms(long arms) {
		this.arms = arms;
	}

	public long getLegs() {
		return legs;
	}

	public void setLegs(long legs) {
		this.legs = legs;
	}

	public long getMorale() {
		return morale;
	}

	public void setMorale(long morale) {
		this.morale = morale;
	}

	@Override
	public String toString() {
		return "+" + morale;
		//"CArmor{" +
				//"morale=" + morale +
				//", id=" + id +
				//'}';
	}
}
