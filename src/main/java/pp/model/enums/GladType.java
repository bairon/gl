package pp.model.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public enum GladType {
	Retiarius(1, "Retiarius", 100, 100, 100),
	Secutor(2, "Secutor", 100, 50, 0),
	Murmillon(3, "Murmillo", 0,  100, 0),
	Dimachaerus(4, "Dimachaerus", 0, 25, 0),
	Thraex(5, "Thraex", 0, 50, 0),
	Velit(6, "Velit", 100, 50, 0),
	Hoplomachus(7, "Hoplomachus", 0, 50, 100),
	Archer(8, "Archer", 100, 0, 0),
	Horseman(9, "Horseman", 100, 50, 0),
	Chariot(10, "Chariot", 100, 50, 0);

	private static final Map<String, GladType> byName = new HashMap<String, GladType>();
	private static final Map<Long, GladType> byID = new HashMap<Long, GladType>();

	static {
		GladType[] values = GladType.values();
		for (GladType value : values) {
			byName.put(value.getName(), value);
			byID.put(value.getId(), value);
		}
	}
	private long id;
	private String name;

	private int attackmin;
	private int powermin;
	private int blockmin;
	private int attackmax;
	private int powermax;
	private int blockmax;

	GladType(long id, String name, int attackmin, int powermin, int blockmin) {
		this.id = id;
		this.name = name;
		this.attackmin = attackmin;
		this.powermin = powermin;
		this.blockmin = blockmin;
		this.attackmax = attackmin;
		this.powermax = powermin;
		this.blockmax = blockmin;
	}

	public static GladType byName(final String name) {
		return byName.get(name);
	}
	public static GladType byID(final long id) {
		return byID.get(id);
	}
    public boolean isTypeId(final long id) {
        return this.id == id;
    }

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAttackmin() {
		return attackmin;
	}

	public int getPowermin() {
		return powermin;
	}

	public int getBlockmin() {
		return blockmin;
	}

	public int getAttackmax() {
		return attackmax;
	}

	public int getPowermax() {
		return powermax;
	}

	public int getBlockmax() {
		return blockmax;
	}

    public static Collection<GladType> mainTypes() {
        ArrayList<GladType> mainTypes = new ArrayList<GladType>();
        for (GladType gladType : values()) {
            if (gladType.getId() <= 7) mainTypes.add(gladType);
        }
        return mainTypes;
    }
}
