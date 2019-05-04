package pp;

import pp.model.xml.CGlad;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 30.11.11
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public interface StuffManager {

	boolean restore(boolean needcure) throws Exception;
	void spendMasseur() throws IOException;
    void spendMasseur(int limit) throws IOException;
	void spendDoctor(List<CGlad> gladsToGo, boolean cureMutant) throws IOException;
	void spendPriest(int limit) throws Exception;

    /**
     *
     * @param gladsToGo
     * @param real
     * @param healrets
     * @param woodOnly
     * @return -1 not possible, 0 continue, 1 ready
     * @throws Exception
     */
    int prepare(List<CGlad> gladsToGo, boolean real, boolean healrets, boolean woodOnly, boolean forceHeal) throws Exception;
	void equipArmor(Long maxLevel, List<CGlad> glads, final boolean silverOn10Morale, final boolean woodOnly);
    int needHeal(List<CGlad> gladsToGo, final boolean healrets, final long doctor);
    int needMorale(List<CGlad> gladsToGo, final boolean moralerets);
    int needCure(List<CGlad> gladsToGo);
    int canUseBonuses();

    boolean canPrepare(List<CGlad> gladsToGo) throws Exception;

    boolean canPrepare(List<CGlad> gladsToGo, int canheal, int canmorale, int cancure);

    boolean prepareFast(List<CGlad> gladsToGo) throws Exception;
}
