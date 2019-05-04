package pp.model.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import pp.model.enums.GladType;
import pp.model.xml.CArmor;

import java.util.ArrayList;
import java.util.List;

@Root(name = "Gladiator")
public class CGlad {
	@Attribute(name = "ID", required = false)
	private long id;

	@Element(name = "Name", required = false)
	private String name;

	@Element(name = "Level", required = false)
	private long level;

	@Element(name = "Exp", required = false)
	private double exp;

	@Element(name = "TypeID", required = false)
	private long typeid;

	@Element(name = "Age", required = false)
	private long age;

	@Element(name = "Stamina", required = false)
	private double stamina;

	@Element(name = "Morale", required = false)
	private long morale;

	@Element(name = "Acc", required = false)
	private long acc;

	@Element(name = "Str", required = false)
	private long str;

	@Element(name = "Dex", required = false)
	private long dex;

	@Element(name = "Vit", required = false)
	private long vit;

	@Element(name = "Hits", required = false)
	private long hits;

	@Element(name = "FullHits", required = false)
	private long fullhits;

	private List<CArmor> armors = new ArrayList<CArmor>();
	private CArmor armor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public double getExp() {
		return exp;
	}

	public void setExp(double exp) {
		this.exp = exp;
	}

	public long getTypeid() {
		return typeid;
	}

	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public double getStamina() {
		return stamina;
	}

	public void setStamina(double stamina) {
		this.stamina = stamina;
	}

	public long getMorale() {
		return morale;
	}

	public void setMorale(long morale) {
		this.morale = morale;
	}

	public long getAcc() {
		return acc;
	}

	public void setAcc(long acc) {
		this.acc = acc;
	}

	public long getStr() {
		return str;
	}

	public void setStr(long str) {
		this.str = str;
	}

	public long getDex() {
		return dex;
	}

	public void setDex(long dex) {
		this.dex = dex;
	}

	public long getVit() {
		return vit;
	}

	public void setVit(long vit) {
		this.vit = vit;
	}

	public long getHits() {
		return hits;
	}

	public void setHits(long hits) {
		this.hits = hits;
	}

	public long getFullhits() {
		return fullhits;
	}

	public void setFullhits(long fullhits) {
		this.fullhits = fullhits;
	}

	public List<CArmor> getArmors() {
		return armors;
	}

	public CArmor getArmor() {
		return armor;
	}

	public void setArmor(CArmor armor) {
		this.armor = armor;
	}
	public long getArmorale() {
		return armor == null ? morale : ((morale + armor.getMorale()) >= 10 ? 10 : (morale + armor.getMorale()));
	}
	public String getArmorId() {
		return armor == null ? "" : Long.toString(armor.getId());
	}

/*
	@Override
	public String toString() {
		return "CGlad{" +
				"id=" + id +
				", name='" + name + '\'' +
				", typeid=" + typeid +
				", stamina=" + stamina +
				", morale=" + morale +
				", armor=" + armor +
				'}';
	}
*/

	@Override
	public String toString() {
		return "" +
				GladType.byID(typeid).toString().substring(0, 3).toUpperCase() +
				//", " + getArmorale() +
				//", " + name +
				//", " + stamina +
				" " + vit +
				" " + dex +
				" " + acc +
				" " + str +
				" morale " + morale +
				"" + (armors.size() > 0 ? armors.get(0) : "") +
				"";
	}
}
