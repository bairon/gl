package pp.model.comparators;

import pp.model.xml.CArmor;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 19.11.11
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public class ArmorsMoraleComparator implements Comparator<CArmor> {
	private long gladMorale;
	private boolean silverOn10Morale;

	public ArmorsMoraleComparator(long gladMorale, boolean silverOn10Morale) {
		this.gladMorale = gladMorale;
		this.silverOn10Morale = silverOn10Morale;
	}

	@Override
	public int compare(CArmor o1, CArmor o2) {
		long tr = 10 - gladMorale;
		Long m1 = o1.getMorale();
		Long m2 = o2.getMorale();
		Long q1 = o1.getArms() + o1.getBody() + o1.getHead() + o1.getLegs() + o1.getDamage()*3;
		Long q2 = o2.getArms() + o2.getBody() + o2.getHead() + o2.getLegs() + o2.getDamage()*3;
		int result = 0;
		if (m1 < tr && m2 < tr) {
			result =  m2.compareTo(m1);
			if (result == 0) {
				result = q2.compareTo(q1);
			}
		}
		else if (m1 < tr && m2 >= tr) result = 1;
		else if (m2 < tr && m1 >= tr) result = -1;
		else if (m1 >= tr && m2 >= tr) {
			if (silverOn10Morale)
				result = Boolean.valueOf(m2 > 0).compareTo(Boolean.valueOf(m1 > 0));
			if (result == 0) {
				result = m1.compareTo(m2);
				if (result == 0) {
					result = q2.compareTo(q1);
				}
			}
		}
		return result;
	}
}
