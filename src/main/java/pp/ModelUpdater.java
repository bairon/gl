package pp;

import pp.model.IModel;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface  ModelUpdater<T extends IModel> {
	boolean update(T model, String text);
}
