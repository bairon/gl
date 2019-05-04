package pp.model;

import pp.ModelFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class ModelFactoryImpl implements ModelFactory {
	private Class modelClass;

	public ModelFactoryImpl(final Class modelClass) {
		this.modelClass = modelClass;
	}

	@Override
	public IModel getNewModel() throws InstantiationException, IllegalAccessException {
		return (IModel) modelClass.newInstance();
	}

	@Override
	public void merge(final IModel from, final IModel to) {
		if (modelClass.isInstance(from) && modelClass.isInstance(to)) {
			Method[] methods = to.getClass().getMethods();
			for (Method toMethod : to.getClass().getMethods()){
				try {
					String toMethodName = toMethod.getName();
					if (toMethodName.startsWith("set")) {
						String toFieldName = toMethodName.substring(3);
						Method toFieldMethodGet = to.getClass().getMethod("get" + toFieldName);
						Object toValue = toFieldMethodGet.invoke(to);
						if (toValue == null) {
							Method fromFieldMethodGet = from.getClass().getMethod("get" + toFieldName);
							Object fromValue = fromFieldMethodGet.invoke(from);
							if (fromValue != null) {
								toMethod.invoke(to, fromValue);
							}
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
