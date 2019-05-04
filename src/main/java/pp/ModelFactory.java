package pp;

import pp.model.IModel;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface ModelFactory {
	IModel getNewModel() throws InstantiationException, IllegalAccessException;
	void merge(final IModel from, final IModel to);
}
