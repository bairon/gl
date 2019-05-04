package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 04.12.11
 * Time: 8:57
 * To change this template use File | Settings | File Templates.
 */
public class GladInjuryComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		return Double.valueOf(o2.getStamina()).compareTo(o1.getStamina());
	}
}
