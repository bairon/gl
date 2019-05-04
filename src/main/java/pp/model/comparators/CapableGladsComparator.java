package pp.model.comparators;

import pp.model.Trnm;

public class CapableGladsComparator implements java.util.Comparator<pp.model.Trnm> {
    @Override
    public int compare(Trnm o1, Trnm o2) {
        return Double.compare(o2.getGladsCapacity(), o1.getGladsCapacity());
    }
}
