package pp.model;

import pp.ModelFactory;

public class ApplicationModelFactory implements ModelFactory {
    @Override
    public IModel getNewModel() throws InstantiationException, IllegalAccessException {
        return new Application();
    }

    @Override
    public void merge(IModel from, IModel to) {

    }

    public static ModelFactory getInstance() {
        return new ApplicationModelFactory();
    }
}
