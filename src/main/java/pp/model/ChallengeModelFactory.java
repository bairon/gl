package pp.model;

import pp.ModelFactory;

public class ChallengeModelFactory implements ModelFactory {
    @Override
    public IModel getNewModel() throws InstantiationException, IllegalAccessException {
        return new Challenge();
    }

    @Override
    public void merge(IModel from, IModel to) {
    }
    public static ChallengeModelFactory getInstance() {
        return new ChallengeModelFactory();
    }
}
