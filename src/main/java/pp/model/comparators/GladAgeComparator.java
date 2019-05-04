package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Comparator;

/**
 * Created by alsa on 25.06.2016.
 */
public class GladAgeComparator  implements Comparator<CGlad> {
    @Override
    public int compare(CGlad o1, CGlad o2) {
        return Long.valueOf(o1.getAge()).compareTo(o2.getAge());
    }
}
