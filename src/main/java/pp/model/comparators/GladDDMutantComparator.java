package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GladDDMutantComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		Boolean  DDMutant1 = 1.2d * (o1.getVit() + o1.getDex()) < o1.getAcc() + o1.getStr();
		Boolean  DDMutant2 = 1.2d * (o2.getVit() + o2.getDex()) < o2.getAcc() + o2.getStr();
		return DDMutant1.compareTo(DDMutant2);
	}
}
