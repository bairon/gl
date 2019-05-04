package pp;

import pp.model.AModel;
import pp.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 01.12.11
 * Time: 6:20
 * To change this template use File | Settings | File Templates.
 */
public class AModelFactory implements ModelFactory {
    public static ModelFactory getInstance() {
        return new AModelFactory();
    }

    @Override
	public IModel getNewModel() throws InstantiationException, IllegalAccessException {
		return new AModel();
	}

	@Override
	public void merge(IModel from, IModel to) {

	}

}
