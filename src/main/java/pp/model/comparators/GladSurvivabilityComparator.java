package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 1:56
 * To change this template use File | Settings | File Templates.
 */
public class GladSurvivabilityComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		Long survive1 = o1.getVit() + o1.getDex();
		Long survive2 = o2.getVit() + o2.getDex();
		return survive1.compareTo(survive2);
	}
}
