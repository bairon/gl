package pp.model.comparators;

import noNamespace.Gladiator;
import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 19.11.11
 * Time: 9:46
 * To change this template use File | Settings | File Templates.
 */
public class GladHpArmoraleComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		int result = Double.valueOf(o2.getStamina()).compareTo(o1.getStamina());
		if (result == 0) {
			result = Long.valueOf(o2.getArmorale()).compareTo(o1.getArmorale());
		}
		return result;
	}
}
