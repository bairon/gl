package pp.model;

import pp.ModelFactory;

public class ArmorModelFactory implements ModelFactory {
    @Override
    public IModel getNewModel() throws InstantiationException, IllegalAccessException {
        return new Armor();
    }

    @Override
    public void merge(IModel from, IModel to) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    public static final ArmorModelFactory getInstance() {
        return new ArmorModelFactory();
    }
}
