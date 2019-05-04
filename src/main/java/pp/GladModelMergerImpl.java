package pp;

import pp.model.Glad;

import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GladModelMergerImpl implements GladModelMerger {
	private Map<Long, Glad> internal;

	public GladModelMergerImpl(final Map<Long, Glad> internal) {
		this.internal = internal;
	}

	public Glad getNewModel() {
		return new Glad();
	}

	public void merge(final Glad model) {
		Glad glad = internal.get(model.getId());
		if (glad == null) {
			internal.put(model.getId(), model);
		} else {
			copy(model, glad);
		}
	}

	/**
	 * 	private long gladID;
	private long ownerID;

	private String name;
	private GladType type;
	private StatusType status;
	private long health;
	private long maxHealth;
	private long morale;
	private long injury;
	private long age;
	private long talent;
	private long exp;
	private long expToLvl;
	private long lvl;
	private boolean lvlUp;
	private long vitality;
	private long dexterity;
	private long accuracy;
	private long strength;
	private Nation nation;
	private long height;
	private long weight;
	private double price;
	private long retirement;
	private long victories;
	private long defeat;

	 * @param source
	 * @param destination
	 */
	private void copy(final Glad source, final Glad destination) {
		if (source.getOwnerID() != 0) destination.setOwnerID(source.getOwnerID());
		if (source.getName() != null) {
			destination.setName(source.getName());
		}
		if (source.getType() != null) destination.setType(source.getType());
		if (source.getStatus() != null) destination.setStatus(source.getStatus());
		if (source.getHealth() != null) destination.setHealth(source.getHealth());
		if (source.getMaxHealth() != 0) destination.setMaxHealth(source.getMaxHealth());
		if (source.getMorale() != null) destination.setMorale(source.getMorale());
		if (source.getInjury() != null) destination.setInjury(source.getInjury());
		if (source.getAge() != 0) destination.setAge(source.getAge());
		if (source.getTalent() != 0) destination.setTalent(source.getTalent());
		if (source.getExp() != 0) destination.setExp(source.getExp());
		if (source.getExpToLvl() != 0) destination.setExpToLvl(source.getExpToLvl());
		if (source.getLvl() != 0) destination.setLvl(source.getLvl());
		if (source.getLvlUp() != null) destination.setLvlUp(source.getLvlUp());
		if (source.getVitality() != 0) destination.setVitality(source.getVitality());
		if (source.getDexterity() != 0) destination.setDexterity(source.getDexterity());
		if (source.getAccuracy() != 0) destination.setAccuracy(source.getAccuracy());
		if (source.getStrength() != 0) destination.setStrength(source.getStrength());
		if (source.getNation() != null) destination.setNation(source.getNation());
		if (source.getHeight() != 0) destination.setHeight(source.getHeight());
		if (source.getWeight() != 0) destination.setWeight(source.getWeight());
		if (source.getPrice() != 0) destination.setPrice(source.getPrice());
		if (source.getRetirement() != null) destination.setDexterity(source.getDexterity());
		if (source.getVictories() != null) destination.setVictories(source.getVictories());
		if (source.getDefeat() != null) destination.setDefeat(source.getDefeat());
	}

	public Map<Long, Glad> getMergedModel() {
		return internal;
	}
}
