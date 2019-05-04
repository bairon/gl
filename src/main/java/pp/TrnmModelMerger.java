package pp;

import pp.model.Glad;
import pp.model.Trnm;

import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface TrnmModelMerger {
	Trnm getNewModel();
	void merge(Trnm model);
	Map<Long, Trnm> getMergedModel();

}
