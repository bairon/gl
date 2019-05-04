package pp.model;

import pp.ModelFactory;

public class PositionModelFactory implements ModelFactory {
    @Override
    public IModel getNewModel() throws InstantiationException, IllegalAccessException {
        return new Position();
    }

    @Override
    public void merge(IModel from, IModel to) {
    }
    public static PositionModelFactory getInstance() {
        return new PositionModelFactory();
    }
}
