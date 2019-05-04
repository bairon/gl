package pp;

import pp.model.Glad;

import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface GladModelMerger {
	Glad getNewModel();
	void merge(Glad model);
	Map<Long, Glad> getMergedModel();
}
