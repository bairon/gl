package pp.model.comparators;

import pp.model.enums.GladType;
import pp.model.xml.CGlad;

import java.util.Collection;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 1:39
 * To change this template use File | Settings | File Templates.
 */
public class GladTypeComparator implements Comparator<CGlad> {
	private Collection<GladType> allowedTypes;

	public GladTypeComparator(Collection<GladType> allowedTypes) {
		this.allowedTypes = allowedTypes;
	}

	@Override
	public int compare(CGlad o1, CGlad o2) {
		return Boolean.valueOf(allowedTypes.contains(GladType.byID(o2.getTypeid()))).compareTo(allowedTypes.contains(GladType.byID(o1.getTypeid())));
	}
}
