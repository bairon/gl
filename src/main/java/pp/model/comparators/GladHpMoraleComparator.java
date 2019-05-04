package pp.model.comparators;

import pp.model.Glad;
import pp.model.enums.GladType;
import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 19.11.11
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class GladHpMoraleComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		int result = Double.valueOf(o2.getStamina()).compareTo(o1.getStamina());
		if (result == 0) {
			result = Long.valueOf((o2.getMorale() + o2.getArmorale() >= 10) ? 10 : o2.getMorale() + o2.getArmorale())
					.compareTo((o1.getMorale() + o1.getArmorale() >= 10) ? 10 : o1.getMorale() + o1.getArmorale());
		}
		if (result == 0) {
			if (o1.getTypeid() == GladType.Velit.getId()) {
				return 1;
			}
			if (o2.getTypeid() == GladType.Velit.getId()) {
				return -1;
			}
			if (o1.getTypeid() == GladType.Secutor.getId()) {
				return -1;
			}
			if (o2.getTypeid() == GladType.Secutor.getId()) {
				return 1;
			}
		}
		return result;
	}
}
