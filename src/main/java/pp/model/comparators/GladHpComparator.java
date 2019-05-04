package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 1:57
 * To change this template use File | Settings | File Templates.
 */
public class GladHpComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		return Double.valueOf(o1.getStamina()).compareTo(o2.getStamina());
	}
}
