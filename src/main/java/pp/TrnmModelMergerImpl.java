package pp;

import pp.model.Trnm;

import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class TrnmModelMergerImpl implements TrnmModelMerger{
	private Map<Long, Trnm> internal;

	public TrnmModelMergerImpl(final Map<Long, Trnm> internal) {
		this.internal = internal;
	}

	public Map<Long, Trnm> getMergedModel() {
		return internal;
	}

	public Trnm getNewModel() {
		return new Trnm();
	}

	public void merge(final Trnm model) {
		Trnm trnm = internal.get(model.getId());
		internal.put(model.getId(), model);
	}
}
