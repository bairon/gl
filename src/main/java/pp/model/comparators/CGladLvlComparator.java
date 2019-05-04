package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

public class CGladLvlComparator implements Comparator<CGlad> {
    @Override
    public int compare(CGlad o1, CGlad o2) {
        return Long.valueOf(o1.getLevel()).compareTo(o2.getLevel());
    }
}
