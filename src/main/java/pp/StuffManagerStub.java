package pp;

import pp.model.xml.CGlad;

import java.io.IOException;
import java.util.List;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class StuffManagerStub implements StuffManager {
	StuffManager delegate;

	public StuffManagerStub(StuffManagerImpl stuffManager) {
		this.delegate = stuffManager;
	}

    @Override
    public boolean restore(boolean needcure) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void spendMasseur() throws IOException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

    @Override
    public void spendMasseur(int limit) throws IOException {

    }

    @Override
	public void spendDoctor(List<CGlad> gladsToGo, boolean cureMutant) throws IOException {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void spendPriest(int limit) throws Exception {
		//To change body of implemented methods use File | Settings | File Templates.
	}

    @Override
    public int prepare(List<CGlad> gladsToGo, boolean real, boolean healrets, boolean woodOnly, boolean forceHeal) throws Exception {
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
    }



	@Override
	public void equipArmor(Long maxLevel, List<CGlad> glads, boolean silverOn10Morale, boolean silverVsBotus) {
		delegate.equipArmor(0L, glads, silverOn10Morale, silverVsBotus);
	}

    @Override
    public int needHeal(List<CGlad> gladsToGo, boolean healrets, long doctor) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int needMorale(List<CGlad> gladsToGo, boolean moralerets) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public int needCure(List<CGlad> gladsToGo) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int canUseBonuses() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean canPrepare(List<CGlad> gladsToGo) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean canPrepare(List<CGlad> gladsToGo, int canheal, int canmorale, int cancure) {
        return delegate.canPrepare(gladsToGo, canheal, canmorale, cancure);
    }

    @Override
    public boolean prepareFast(List<CGlad> gladsToGo) throws Exception {
        return false;
    }
}
