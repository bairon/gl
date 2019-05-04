package pp;

import pp.model.Glad;
import pp.model.IModel;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GladModelFactory implements ModelFactory {
	private static final GladModelFactory INSTANCE = new GladModelFactory();
	public static final GladModelFactory getInstance() {
		return INSTANCE;
	}
	public IModel getNewModel() {
		return new Glad();
	}

	public void merge(final IModel source, final IModel destination) {
		final Glad src = (Glad) source;
		final Glad dst = (Glad) destination;
		if (src.getOwnerID() != null) dst.setOwnerID(src.getOwnerID());
		if (src.getName() != null) dst.setName(src.getName());
		if (src.getType() != null) dst.setType(src.getType());
		if (src.getStatus() != null) dst.setStatus(src.getStatus());
		if (src.getHealth() != null) dst.setHealth(src.getHealth());
		if (src.getMaxHealth() != null) dst.setMaxHealth(src.getMaxHealth());
		if (src.getMorale() != null) dst.setMorale(src.getMorale());
		if (src.getInjury() != null) dst.setInjury(src.getInjury());
		if (src.getAge() != null) dst.setAge(src.getAge());
		if (src.getTalent() != null) dst.setTalent(src.getTalent());
		if (src.getExp() != null) dst.setExp(src.getExp());
		if (src.getExpToLvl() != null) dst.setExpToLvl(src.getExpToLvl());
		if (src.getLvl() != null) dst.setLvl(src.getLvl());
		if (src.getLvlUp() != null) dst.setLvlUp(src.getLvlUp());
		if (src.getVitality() != null) dst.setVitality(src.getVitality());
		if (src.getDexterity() != null) dst.setDexterity(src.getDexterity());
		if (src.getAccuracy() != null) dst.setAccuracy(src.getAccuracy());
		if (src.getStrength() != null) dst.setStrength(src.getStrength());
		if (src.getNation() != null) dst.setNation(src.getNation());
		if (src.getHeight() != null) dst.setHeight(src.getHeight());
		if (src.getWeight() != null) dst.setWeight(src.getWeight());
		if (src.getPrice() != null) dst.setPrice(src.getPrice());
		if (src.getRetirement() != null) dst.setDexterity(src.getDexterity());
		if (src.getVictories() != null) dst.setVictories(src.getVictories());
		if (src.getDefeat() != null) dst.setDefeat(src.getDefeat());
		if (src.getHpPercent() != null) dst.setHpPercent(src.getHpPercent());
	}
}
