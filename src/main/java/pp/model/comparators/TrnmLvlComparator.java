package pp.model.comparators;

import pp.model.Trnm;

import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class TrnmLvlComparator implements Comparator<Trnm> {
	@Override
	public int compare(final Trnm o1, final Trnm o2) {
        if (o1.getLvlTo() == null && !(o2.getLvlTo() == null)) return -1;
        if (o2.getLvlTo() == null && !(o1.getLvlTo() == null)) return 1;
		return o1.getStart().compareTo(o2.getStart());
	}
}
