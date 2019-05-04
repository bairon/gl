package pp;

import noNamespace.Gladiator;

import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class LvlHpGladComparator implements Comparator<Object> {
	@Override
	public int compare(final Object oo1, final Object oo2) {

		Gladiator o1 = (Gladiator)oo1;
		Gladiator o2 = (Gladiator)oo2;

		double o1Weight = (4d * o1.getHits())/o1.getFullHits() + o1.getLevel();
		double o2Weight = (4d * o2.getHits())/o2.getFullHits() + o2.getLevel();
		return ((Double) o2Weight).compareTo(o1Weight);
	}
}
