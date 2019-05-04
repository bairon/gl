package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 04.12.11
 * Time: 8:10
 * To change this template use File | Settings | File Templates.
 */
public class GladExpComparator implements Comparator<CGlad> {
    public boolean reversed;

    public GladExpComparator(boolean reversed) {
        this.reversed = reversed;
    }

    @Override
	public int compare(CGlad o1, CGlad o2) {
        if (reversed) {
            return Double.valueOf(o2.getExp()).compareTo(o1.getExp());
        }

        return Double.valueOf(o1.getExp()).compareTo(o2.getExp());
	}
}
