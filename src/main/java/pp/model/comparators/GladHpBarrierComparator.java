package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 0:33
 * To change this template use File | Settings | File Templates.
 */
public class GladHpBarrierComparator implements Comparator<CGlad> {
	private double barrier;

	public GladHpBarrierComparator(double barrier) {
		this.barrier = barrier;
	}

	@Override
	public int compare(CGlad o1, CGlad o2) {
		Boolean b1 = o1.getStamina() >= barrier;
		Boolean b2 = o2.getStamina() >= barrier;
		return b1.compareTo(b2);
	}
}

