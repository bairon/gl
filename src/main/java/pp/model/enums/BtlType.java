package pp.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public enum BtlType {
	DUEL(2, new String[]{"11"}),
	BATTLE(1, new String[]{"33", "32", "34", "31", "35", "23", "24", "22", "21", "25", "13", "12", "14", "11", "15", "43", "42", "44", "41", "45", "53", "54", "52", "51", "55"}),
	SURVIVAL(4, new String[]{"11", "12", "13", "14", "15"}),
	;

	private long id;
	private String[] possiblePositions;

	BtlType(final long id, final String[] possiblePositions) {
		this.id = id;
		this.possiblePositions = possiblePositions;
	}

	private static final Map<Long, BtlType> byID = new HashMap<Long, BtlType>();
	static {
		BtlType[] values = BtlType.values();
		for (BtlType value : values) {
			byID.put(value.getId(), value);
		}
	}

	public static BtlType byID(final long id) {
		return byID.get(id);
	}

	public long getId() {
		return id;
	}

	public String[] getPossiblePositions() {
		return possiblePositions;
	}
}
