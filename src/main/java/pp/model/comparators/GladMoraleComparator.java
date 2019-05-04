package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 07.12.11
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public class GladMoraleComparator implements Comparator<CGlad> {

    public GladMoraleComparator(boolean reverse) {
        this.reverse = reverse;
    }

    private boolean reverse;
	@Override
	public int compare(CGlad o1,CGlad o2) {
        if (reverse) return Long.valueOf(o2.getMorale()).compareTo(o1.getMorale());
		return Long.valueOf(o1.getArmorale()).compareTo(o2.getArmorale());
	}
}
