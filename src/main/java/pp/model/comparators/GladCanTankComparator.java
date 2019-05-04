package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 1:52
 * To change this template use File | Settings | File Templates.
 */
public class GladCanTankComparator implements Comparator<CGlad> {
	@Override
	public int compare(CGlad o1, CGlad o2) {
		Boolean  canTank1 = o1.getVit() + o1.getDex() > (o1.getAcc() + o1.getStr()) * 1.2;
		Boolean  canTank2 = o2.getVit() + o2.getDex() > (o2.getAcc() + o2.getStr()) * 1.2;
		return canTank1.compareTo(canTank2);
	}
}
