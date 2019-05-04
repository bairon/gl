package pp;

import pp.model.IModel;
import pp.model.Stuff;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class StuffModelFactory implements ModelFactory {
	@Override
	public IModel getNewModel() {
		return new Stuff();
	}

	@Override
	public void merge(final IModel source, final IModel destination) {
	}
}
