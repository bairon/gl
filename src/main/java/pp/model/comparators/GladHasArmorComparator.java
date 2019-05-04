package pp.model.comparators;

import pp.model.enums.GladType;
import pp.model.xml.CGlad;

import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 3:14
 * To change this template use File | Settings | File Templates.
 */
public class GladHasArmorComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		Boolean armor1 = o1.getArmor() != null;
		Boolean armor2 = o2.getArmor() != null;
		return armor1.compareTo(armor2);
	}
}
