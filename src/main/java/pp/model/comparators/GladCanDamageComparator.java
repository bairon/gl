package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

public class GladCanDamageComparator implements Comparator<CGlad> {
    @Override
    public int compare(CGlad o1, CGlad o2) {
        Boolean  canDamage1 = o1.getVit() + o1.getDex() <= (o1.getAcc() + o1.getStr()) * 2;
        Boolean  canDamage2 = o2.getVit() + o2.getDex() <= (o2.getAcc() + o2.getStr()) * 2;
        return canDamage1.compareTo(canDamage2);
    }
}