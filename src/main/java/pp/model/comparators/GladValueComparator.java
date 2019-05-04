package pp.model.comparators;

import pp.model.Glad;
import pp.model.IModel;

import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GladValueComparator implements Comparator<IModel> {
	@Override
	public int compare(final IModel o1, final IModel o2) {
		Glad g1 = (Glad) o1;
		Glad g2 = (Glad) o2;
		return Long.valueOf(g1.getValue()).compareTo(g2.getValue());
	}

}
