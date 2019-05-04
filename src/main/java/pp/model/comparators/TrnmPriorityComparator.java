package pp.model.comparators;

import pp.model.Trnm;

public class TrnmPriorityComparator implements java.util.Comparator<Trnm> {

    @Override
    public int compare(Trnm o1, Trnm o2) {
        return Integer.compare(o2.getPriority(), o1.getPriority());
    }
}
