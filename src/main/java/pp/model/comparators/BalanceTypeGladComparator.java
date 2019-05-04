package pp.model.comparators;

import pp.Utils;
import pp.model.xml.CGlad;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alsa on 27.06.2016.
 */
public class BalanceTypeGladComparator implements Comparator<CGlad> {
    public static final int [] gladTypePriority = new int [] {9, 0, 2, 1, 7, 3, 4, 6, 5, 10, 8};
    private List<CGlad> glads;
    private final Map<Long, Integer> typemap;

    public BalanceTypeGladComparator(List<CGlad> glads) {
        this.glads = glads;
        this.typemap = typemap(this.glads);
    }

    private Map<Long, Integer> typemap(List<CGlad> glads) {
        HashMap<Long, Integer> types = new HashMap<Long, Integer>();
        for (CGlad glad : glads) {
            if (glad.getStamina() >= 99 || Utils.ddMutant(glad)) {
                if (types.get(glad.getTypeid()) == null) {
                    types.put(glad.getTypeid(), 1);
                } else {
                    types.put(glad.getTypeid(), types.get(glad.getTypeid()) + 1);
                }
            }
        }
        return types;

    }

    @Override
    public int compare(CGlad o1, CGlad o2) {
        Integer o1types = typemap.get(o1.getTypeid());
        Integer o2types = typemap.get(o2.getTypeid());
        if (o1types == null && o2types == null) return 0;
        if (o1types == null) return -1;
        if (o2types == null) return 1;
        int res = o1types.compareTo(o2types);
        if (res == 0) {
            res = Integer.compare(gladTypePriority[((int) o1.getTypeid())], gladTypePriority[((int) o2.getTypeid())]);
        }
        return res;
    }
}
