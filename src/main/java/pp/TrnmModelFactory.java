package pp;

import pp.model.IModel;
import pp.model.Trnm;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class TrnmModelFactory implements ModelFactory {
	private static TrnmModelFactory instance = new TrnmModelFactory();

	public IModel getNewModel() {
		return new Trnm();
	}

	public void merge(final IModel source, final IModel destination) {
		final Trnm src = (Trnm) source;
		final Trnm dst = (Trnm) destination;
		//ToDo implement if needed
	}
	public static final TrnmModelFactory getInstance() {
		return instance;
	}
}
