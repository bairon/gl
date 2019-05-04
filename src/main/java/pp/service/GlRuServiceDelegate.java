package pp.service;

import noNamespace.Horses;
import noNamespace.ResponseDocument;
import org.apache.log4j.Logger;
import pp.BetPlacer;
import pp.GlRuParser;
import pp.Utils;
import pp.model.AModel;
import pp.model.Application;
import pp.model.Armor;
import pp.model.Btl;
import pp.model.Challenge;
import pp.model.Glad;
import pp.model.Guild;
import pp.model.HippoTour;
import pp.model.IModel;
import pp.model.Opponent;
import pp.model.Position;
import pp.model.Stuff;
import pp.model.Trnm;
import pp.model.enums.BattleType;
import pp.model.enums.TournamentState;
import pp.model.enums.TournamentType;
import pp.model.xml.CArmor;
import pp.model.xml.CGlad;
import pp.model.xml.CRoster;
import pp.model.xml.Cxml;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GlRuServiceDelegate implements GlRuService {
	public static final Logger LOGGER = Logger.getLogger(GlRuServiceDelegate.class);
	private GlRuService delegate;

	public GlRuServiceDelegate(GlRuService delegate) {
		this.delegate = delegate;
	}

	@Override
	public void visitMyOffice() throws IOException {
		delegate.visitMyOffice();
	}

	@Override
	public void visitMyGladiators() throws IOException {
		delegate.visitMyGladiators();
	}

	@Override
	public Map<Long, Glad> getMyGlads() {
		return delegate.getMyGlads();
	}

	@Override
	public void visitTraining() {
		delegate.visitTraining();
	}

	@Override
	public void visitRecovery() throws IOException {
		delegate.visitRecovery();
	}

	@Override
	public void visitArmory() throws IOException {
		delegate.visitArmory();
	}

	@Override
	public void visitTournaments(TournamentType type, TournamentState state) throws IOException {
		delegate.visitTournaments(type, state);
	}

	@Override
	public void visitProfile() {
		delegate.visitProfile();
	}

	@Override
	public void visitMap() {
		delegate.visitMap();
	}

	@Override
	public void visitGuilds() throws IOException {
		delegate.visitGuilds();
	}

	@Override
	public String visitGuild(long guildId) throws IOException {
		delegate.visitGuild(guildId);
        return "";
	}

	@Override
	public Map<Long, Guild> getGuilds() {
		return delegate.getGuilds();
	}

	@Override
	public void login(String user, String pss) throws IOException {
		delegate.login(user, pss);
	}

	@Override
	public void updateSenates(Date tillDate) throws IOException {
		delegate.updateSenates(tillDate);
	}

	@Override
	public void saveSenates() {
		delegate.saveSenates();
	}

	@Override
	public void loadSenates() {
		delegate.loadSenates();
	}

	@Override
	public Map<Long, Trnm> getSenates() {
		return delegate.getSenates();
	}

	@Override
	public void visitCity(Long city) throws IOException {
		delegate.visitCity(city);
	}

	@Override
	public Map<Long, IModel> getCities() {
		return delegate.getCities();
	}

	@Override
	public void checkTavern() throws IOException {
		delegate.checkTavern();
	}

	@Override
	public Map<Long, IModel> getMerceneries() {
		return delegate.getMerceneries();
	}

	@Override
	public void checkSlaves() throws IOException {
		delegate.checkSlaves();
	}

	@Override
	public Map<Long, IModel> getSlaves() {
		return delegate.getSlaves();
	}

	@Override
	public void mail(String login, String subject, String body) throws IOException {
		delegate.mail(login, subject, body);
	}

	@Override
	public long getFreeSlots() {
		return delegate.getFreeSlots();
	}

	@Override
	public void buy(Glad bestRecruit) throws Exception {
		delegate.buy(bestRecruit);
	}

	@Override
	public void sell(Glad glad) throws Exception {
		delegate.sell(glad);
	}

	@Override
	public void hippoLogin() throws Exception {
		delegate.hippoLogin();
	}

	@Override
	public Horses hippoGetBets() throws Exception {
		return delegate.hippoGetBets();
	}

	@Override
	public ResponseDocument.Response hippoSetBet(long horse, long money) throws Exception {
		return delegate.hippoSetBet(horse, money);
	}

	@Override
	public HippoTour hippoTour(BetPlacer betPlacer) throws Exception {
		return delegate.hippoTour(betPlacer);
	}

	@Override
	public void loadHippoTours() {
		delegate.loadHippoTours();
	}

	@Override
	public void saveHippoTours() {
		delegate.saveHippoTours();
	}

	@Override
	public HashMap<Long, HippoTour> getHippoTours() {
		return delegate.getHippoTours();
	}

	@Override
	public Map<Long, Trnm> getTournaments() {
		return delegate.getTournaments();
	}

	@Override
	public GlRuParser getParser() {
		return delegate.getParser();
	}

    @Override
    public boolean heal(CGlad g, double goal) throws IOException {
        return delegate.heal(g, goal);
    }

    @Override
    public boolean healexperiment(CGlad g) throws IOException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public boolean morale(CGlad g, long maxvalue) throws IOException {
		return delegate.morale(g, maxvalue);
	}

	@Override
	public boolean cure(CGlad g) throws IOException {
		return delegate.cure(g);
	}

	@Override
	public Btl getIncomingBtl() {
		return delegate.getIncomingBtl();
	}

	@Override
	public void joinTrn(Trnm trnm) throws IOException {
		delegate.joinTrn(trnm);
	}

	@Override
	public void leaveTrn(Trnm trnm) throws IOException {
		delegate.leaveTrn(trnm);
	}

	@Override
	public void useTicket(Trnm trnm) throws IOException {
		delegate.useTicket(trnm);
	}

	@Override
	public Btl builder(Btl btl) throws IOException {
		return btl;
	}

	@Override
	public void send(Cxml placement) throws Exception {
		LOGGER.info("sending " + Utils.toString(placement));
	}

	@Override
	public Stuff getStuff() {
		return delegate.getStuff();
	}

	@Override
	public String watchFight(long id) throws IOException {
		return delegate.watchFight(id);
	}

	@Override
	public void restoreSpecs() throws IOException {
		delegate.restoreSpecs();
	}

	@Override
	public void restoreGlads() throws IOException {
		delegate.restoreGlads();
	}

	@Override
	public Map<Long, Btl> getBttls() {
		return delegate.getBttls();
	}

	@Override
	public Map<Long, CGlad> getGlads() {
		return delegate.getGlads();
	}

	@Override
	public void updateGlads() throws IOException {
		delegate.updateGlads();
	}

	@Override
	public void updateStuff() throws IOException {
		delegate.updateStuff();
	}

	@Override
	public Map<Long, AModel> getDues(int pages) throws IOException {
		return delegate.getDues(pages);
	}

	@Override
	public void deleteDue(Long due) throws IOException {
		delegate.deleteDue(due);
	}

    @Override
    public int getMyGuild() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getDualGuild() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void arena(BattleType type, int gladlimit, int maxlevel, int totallevel, int timeout, String gladfilter) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Opponent arena(int id) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void arenaCancel() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void arenaExam() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void arenaAccept() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void arenaReject() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void arenaChallenge(int typeid, long id) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<Long, Application> getApplications() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void healMoney(CGlad glad) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CRoster getRoster() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void arenaBot(BattleType type) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getBonuses() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getDinaries() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<Long, AModel> getHostelGlads() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<Long, Challenge> getChallenges() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<Long, Position> getPositions(Long id) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void attackPosition(Long id, Long pid) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void takePosition(Long id, Long pid) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void takeArmor(Long id, int type, int level) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateFreeArmors(Collection<AModel> goldFreeArmors, Collection<AModel> silverFreeArmors) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateaArmorsShop(Collection<Armor> armory) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void takeFromHostel(long hostelGladId) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void applySpecs() throws IOException {

    }

    @Override
    public void champGuild() throws IOException {

    }

    @Override
    public void buyArmor(Armor armor) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dropArmor(CArmor armor) throws IOException {

    }
}
