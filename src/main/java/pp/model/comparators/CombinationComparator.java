package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 07.12.11
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class CombinationComparator implements Comparator<CGlad> {
	private Comparator<CGlad>[] comparators;

	public CombinationComparator(Comparator<CGlad> ... comparators) {
		this.comparators = comparators;
	}

	@Override
	public int compare(CGlad o1, CGlad o2) {
		int result = 0;
		for (Comparator<CGlad> comparator : comparators) {
			result = comparator.compare(o1, o2);
			if (result != 0) break;
		}
		return result;
	}
}
