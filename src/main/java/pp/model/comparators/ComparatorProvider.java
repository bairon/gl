package pp.model.comparators;

import pp.model.xml.CGlad;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 1:35
 * To change this template use File | Settings | File Templates.
 */
public interface ComparatorProvider<T> {
	Comparator<T> cmpare(Long... args);

    List<CGlad> filter(Collection<CGlad> glads);
}
