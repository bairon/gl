package pp.model.comparators;

import pp.model.Glad;
import pp.model.IModel;

import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GladLvlComparator implements Comparator<IModel> {
	public int compare(final IModel o1, final IModel o2) {
		return ((Long) ((Glad)o2).getLvl()).compareTo(((Glad)o1).getLvl());
	}
}
