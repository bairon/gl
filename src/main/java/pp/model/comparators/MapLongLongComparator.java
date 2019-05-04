package pp.model.comparators;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 20.11.11
 * Time: 9:51
 * To change this template use File | Settings | File Templates.
 */
public class MapLongLongComparator implements Comparator<Long> {
	private Map<Long, Long> map;
	@Override
	public int compare(Long o1, Long o2) {
		return map.get(o2).compareTo(map.get(o1));
	}

	public MapLongLongComparator(Map<Long, Long> map) {
		this.map = map;
	}
}
