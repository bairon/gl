package pp.model.comparators;

import pp.model.enums.GladType;
import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by alsa on 02.07.2016.
 */
public class SlivComparator implements Comparator<CGlad> {
    @Override
    public int compare(CGlad o1, CGlad o2) {
        if (o1.getStamina() <= 0 || o1.getTypeid() == GladType.Velit.getId()) return 1;
        if (o2.getStamina() <= 0 || o2.getTypeid() == GladType.Velit.getId()) return -1;
        if ((o1.getStamina() <= 0 || o1.getTypeid() == GladType.Velit.getId()) && (o2.getStamina() <= 0 || o2.getTypeid() == GladType.Velit.getId())) return 0;
        return Double.compare(o1.getStamina(), o2.getStamina());
    }
}
