package pp.model.comparators;

import pp.model.xml.CArmor;
import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GladArmorCountComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		int a1 = 0, a2 = 0;
		for (CArmor armor : o1.getArmors()) {
			if (armor.getMorale() > 0) a1++;
		}
		for (CArmor armor : o2.getArmors()) {
			if (armor.getMorale() > 0) a2++;
		}
		return Integer.valueOf(a1).compareTo(a2);
	}
}
