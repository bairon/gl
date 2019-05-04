package pp.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public enum StatusType {

	SLAVE(1, "Slave"),
	MERCENARY(2, "Mercenary"),
	RUDIARY(3, "Rudiary");

	private long id;
	private String name;

	StatusType(final long id, final String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	private static final Map<String, StatusType> byName = new HashMap<String, StatusType>();
	private static final Map<Long, StatusType> byID = new HashMap<Long, StatusType>();

	static {
		StatusType[] values = StatusType.values();
		for (StatusType value : values) {
			byName.put(value.getName(), value);
			byID.put(value.getId(), value);
		}
	}
	public static StatusType byId(long id) {
		return byID.get(id);
	}
	public static StatusType byName(long id) {
		return byName.get(id);
	}

}
