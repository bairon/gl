package pp.model;

import pp.model.enums.GladType;
import pp.model.enums.Nation;
import pp.model.enums.StatusType;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Glad extends AModel {
	private Long ownerID;

	private String name;
	private GladType type;
	private StatusType status;
	private Long health;
	private Long maxHealth;
	private Long morale;
	private Double injury;
	private Long age;
	private Long talent;
	private Long exp;
	private Long expToLvl;
	private Long lvl;
	private Boolean lvlUp;
	private Long vitality;
	private Long dexterity;
	private Long accuracy;
	private Long strength;
	private Nation nation;
	private Long height;
	private Long weight;
	private Double price;
	private Long retirement;
	private Long victories;
	private Long defeat;
	private Double hpPercent;
	private Long armorMorale;
	private Long armorId;

	public Long getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(final Long ownerID) {
		this.ownerID = ownerID;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public GladType getType() {
		return type;
	}

	public void setType(final GladType type) {
		this.type = type;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(final StatusType status) {
		this.status = status;
	}

	public Long getHealth() {
		return health;
	}

	public void setHealth(final Long health) {
		this.health = health;
	}

	public Long getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(final Long maxHealth) {
		this.maxHealth = maxHealth;
	}

	public Long getMorale() {
		return morale;
	}

	public void setMorale(final Long morale) {
		this.morale = morale;
	}

	public Double getInjury() {
		return injury;
	}

	public void setInjury(final Double injury) {
		this.injury = injury;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(final Long age) {
		this.age = age;
	}

	public Long getTalent() {
		return talent;
	}

	public void setTalent(final Long talent) {
		this.talent = talent;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(final Long exp) {
		this.exp = exp;
	}

	public Long getExpToLvl() {
		return expToLvl;
	}

	public void setExpToLvl(final Long expToLvl) {
		this.expToLvl = expToLvl;
	}

	public Long getLvl() {
		return lvl;
	}

	public void setLvl(final Long lvl) {
		this.lvl = lvl;
	}

	public Boolean getLvlUp() {
		return lvlUp;
	}

	public void setLvlUp(final Boolean lvlUp) {
		this.lvlUp = lvlUp;
	}

	public Long getVitality() {
		return vitality;
	}

	public void setVitality(final Long vitality) {
		this.vitality = vitality;
	}

	public Long getDexterity() {
		return dexterity;
	}

	public void setDexterity(final Long dexterity) {
		this.dexterity = dexterity;
	}

	public Long getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(final Long accuracy) {
		this.accuracy = accuracy;
	}

	public Long getStrength() {
		return strength;
	}

	public void setStrength(final Long strength) {
		this.strength = strength;
	}

	public Nation getNation() {
		return nation;
	}

	public void setNation(final Nation nation) {
		this.nation = nation;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(final Long height) {
		this.height = height;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(final Long weight) {
		this.weight = weight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public Long getRetirement() {
		return retirement;
	}

	public void setRetirement(final Long retirement) {
		this.retirement = retirement;
	}

	public Long getVictories() {
		return victories;
	}

	public void setVictories(final Long victories) {
		this.victories = victories;
	}

	public Long getDefeat() {
		return defeat;
	}

	public void setDefeat(final Long defeat) {
		this.defeat = defeat;
	}

	public Double getHpPercent() {
		return hpPercent;
	}

	public void setHpPercent(Double hpPercent) {
		this.hpPercent = hpPercent;
	}

	@Override
	public String toString() {
		return "Glad{" +
				"ownerID=" + ownerID +
				", name='" + name + '\'' +
				", type=" + type +
				", status=" + status +
				", hpPercent=" + hpPercent +
				", health=" + health +
				", maxHealth=" + maxHealth +
				", morale=" + morale +
				", injury=" + injury +
				", age=" + age +
				", talent=" + talent +
				", exp=" + exp +
				", expToLvl=" + expToLvl +
				", lvl=" + lvl +
				", lvlUp=" + lvlUp +
				", vitality=" + vitality +
				", dexterity=" + dexterity +
				", accuracy=" + accuracy +
				", strength=" + strength +
				", nation=" + nation +
				", height=" + height +
				", weight=" + weight +
				", price=" + price +
				", retirement=" + retirement +
				", victories=" + victories +
				", defeat=" + defeat +
				'}';
	}

	public long getValue() {
		return age - talent * 3;
	}

	public Long getArmorMorale() {
		return armorMorale;
	}

	public void setArmorMorale(Long armorMorale) {
		this.armorMorale = armorMorale;
	}

	public Long getArmorId() {
		return armorId;
	}

	public void setArmorId(Long armorId) {
		this.armorId = armorId;
	}
}
