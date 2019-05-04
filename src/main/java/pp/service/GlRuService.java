package pp.service;

import noNamespace.Horses;
import noNamespace.ResponseDocument;
import noNamespace.RosterDocument;
import noNamespace.XmlDocument;
import org.apache.xmlbeans.XmlException;
import pp.BetPlacer;
import pp.GlRuParser;
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
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface GlRuService {
	void visitMyOffice() throws IOException;
	void visitMyGladiators() throws IOException;
	Map<Long, Glad> getMyGlads();

	void visitTraining();
	void visitRecovery() throws IOException;
	public void visitArmory() throws IOException;
	void visitTournaments(final TournamentType type, final TournamentState state) throws IOException;
	void visitProfile();
	void visitMap();
	void visitGuilds() throws IOException;
	String visitGuild(final long guildId) throws IOException;
	Map<Long, Guild> getGuilds();
	void login(String user, String pss) throws IOException;
	void updateSenates(final Date tillDate) throws IOException;
	void saveSenates();
	void loadSenates();
	Map<Long, Trnm> getSenates();

	void visitCity(Long city) throws IOException;
	Map<Long, IModel> getCities();

	void checkTavern() throws IOException;
	Map<Long, IModel> getMerceneries();

	void checkSlaves() throws IOException;
	Map<Long, IModel> getSlaves();

	void mail(final String login, final String subject, final String body) throws IOException;
	long getFreeSlots();

	void buy(Glad bestRecruit) throws Exception;

	void sell(Glad glad) throws Exception;

	void hippoLogin() throws Exception;
	Horses hippoGetBets() throws Exception;
	ResponseDocument.Response hippoSetBet(final long horse, final long money) throws Exception;

	HippoTour hippoTour(final BetPlacer betPlacer) throws Exception;

	void loadHippoTours();
	void saveHippoTours();
	HashMap<Long, HippoTour> getHippoTours();
	public Map<Long, Trnm> getTournaments();
	GlRuParser getParser();

	boolean heal(final CGlad g, final double goal) throws IOException;
    boolean healexperiment(final CGlad g) throws IOException;
	boolean morale(final CGlad g, long maxvalue) throws IOException;
	boolean cure(final CGlad g) throws IOException;
	Btl getIncomingBtl();
	public void joinTrn(final Trnm trnm) throws IOException;

	public void leaveTrn(final Trnm trnm) throws IOException;

	void useTicket(Trnm trnm) throws IOException;

	public Btl builder(final Btl id) throws IOException;
	public void send(final Cxml placement) throws Exception;
	public Stuff getStuff();

	public String watchFight(final long id) throws IOException;

	void restoreSpecs() throws IOException;
	public void restoreGlads() throws IOException;
	public Map<Long, Btl> getBttls();
	public Map<Long, CGlad> getGlads();
	public void updateGlads() throws IOException;

	void updateStuff() throws IOException;

	Map<Long, AModel> getDues(int pages) throws IOException;

	void deleteDue(Long due) throws IOException;

    int getMyGuild();
    int getDualGuild();

    void arena(BattleType type, int gladlimit, int maxlevel, int totallevel, int timeout, String gladfilter) throws IOException;

    Opponent arena(int id) throws IOException;
    void arenaCancel() throws IOException;
    void arenaExam() throws IOException;
    void arenaAccept() throws IOException;
    void arenaReject() throws IOException;
    void arenaChallenge(int typeid, long id) throws IOException;
    Map<Long, Application> getApplications();

    void healMoney(CGlad glad) throws IOException;
    CRoster getRoster() throws Exception;
    void arenaBot(BattleType type) throws IOException;

    double getBonuses();
    long getDinaries();
    Map<Long, AModel> getHostelGlads();
    Map<Long, Challenge> getChallenges() throws IOException;

    Map<Long, Position> getPositions(Long id) throws IOException;

    void attackPosition(Long id, Long pid) throws IOException;

    void takePosition(Long id, Long pid)throws IOException;

    void takeArmor(Long id, int type, int level) throws IOException;

    void buyArmor(Armor armor) throws IOException;

    void dropArmor(CArmor armor) throws IOException;
    void updateFreeArmors(Collection<AModel> goldFreeArmors, Collection<AModel> silverFreeArmors) throws IOException;

    void updateaArmorsShop(Collection<Armor> armory) throws IOException;

    void takeFromHostel(long hostelGladId) throws IOException;

    void applySpecs() throws IOException;

    void champGuild() throws IOException;
}
