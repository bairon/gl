package pp.model.comparators;

import pp.model.IModel;

import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class IdComparator implements Comparator<IModel> {
	@Override
	public int compare(final IModel o1, final IModel o2) {
		return Long.valueOf(o1.getId()).compareTo(o2.getId());
	}
}
