package pp;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import pp.model.City;
import pp.model.IModel;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class CityModelFactory implements ModelFactory {
	private static final CityModelFactory INSTANCE = new CityModelFactory();

	@Override
	public IModel getNewModel() {
		return new City();
	}

	@Override
	public void merge(final IModel source, final IModel destination) {
	}

	public static CityModelFactory getInstance() {
		return INSTANCE;
	}
}
