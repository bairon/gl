package pp;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import pp.exception.NotLoggedInException;
import pp.model.Btl;
import pp.model.Fighter;
import pp.model.TournamentRegistry;
import pp.model.TournamentSetting;
import pp.model.Trnm;
import pp.model.comparators.*;
import pp.model.enums.GladType;
import pp.model.enums.InvitationType;
import pp.model.enums.TournamentState;
import pp.model.enums.TournamentType;
import pp.model.xml.CGlad;
import pp.service.GlRuService;

import java.io.IOException;
import java.util.*;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class TournamentActivity extends Activity {

    public static final Logger LOGGER = Logger.getLogger(TournamentActivity.class);
    private static final long MIN = 60 * 1000;
    private static final long HOUR = MIN * 60;

    private StuffManager stuffManager = new StuffManagerImpl(service);
    private Fighter fighter;
    private boolean wasRecentlyPaused;
    private Calendar closestTrnmTime;


    protected TournamentActivity(final GlRuService service) {
        super(service);
        this.fighter = new FighterImpl(service, stuffManager);
    }

    public static double moneyHealStamina(CGlad glad) {
        return 100 - (65 / glad.getFullhits());
    }

    public void doSome() {

        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            //Utils.soutn("Naideno gladiatorov " + service.getMyGlads().size());
            this.closestTrnmTime = null;
            service.updateGlads();
            service.updateStuff();
            //System.out.println(((FighterImpl) fighter).getCapableGlads());
            stuffManager.spendPriest(50);
            stuffManager.spendMasseur(60);
            for (CGlad glad : service.getGlads().values()) {
                if (glad.getMorale() < 10 && glad.getStamina() >= moneyHealStamina(glad) && glad.getStamina() < 100) {
                    service.healMoney(glad);
                }
            }
            stuffManager.spendDoctor(Collections.<CGlad>emptyList(), true);
            int hh = calendar.get(Calendar.HOUR_OF_DAY);
            int mm = calendar.get(Calendar.MINUTE);
            boolean denyJoining = Settings.getSenate() && (hh > 1 && hh < 21 && hh % 3 == 2 && mm > 30 && mm < 55);
            if (hh == 1 && (mm >= 50 && mm <= 59) && !fullHP(service.getGlads().values()) && (service.getStuff().getRstGld() != null && service.getStuff().getRstGld() > 0)) {
                service.restoreGlads();
            }
            //LOGGER.info("Deny joining " + denyJoining);
            Trnm joined = null;
            //if (hh > 6 && hh < 24 && (hh % 3 == 1 && mm > 45 || hh % 3 == 2 && mm < 36) ) {
            if (Settings.getPrize()) {
                service.visitTournaments(TournamentType.PRIZE, TournamentState.OPEN);
                joined = handle(service.getTournaments(), calendar);
            }
            if (Settings.getSenate() && !denyJoining) {
                service.visitTournaments(TournamentType.SENATOR, TournamentState.OPEN);
                joined = handle(service.getTournaments(), calendar);
            }
            //Utils.sleep(5000);
            if (null == joined && Settings.getImper() && !denyJoining) {
                service.visitTournaments(TournamentType.IMPERATORS, TournamentState.OPEN);
                if (!denyJoining)
                    joined = handle(service.getTournaments(), calendar);
            }
            //Utils.sleep(5000);
            if (null == joined && Settings.getMixed() && !denyJoining) {
                service.visitTournaments(TournamentType.MIXED, TournamentState.OPEN);
                if (!denyJoining)
                    joined = handle(service.getTournaments(), calendar);
            }
            if (null == joined && Settings.getPlebey() && !denyJoining) {
                service.visitTournaments(TournamentType.PLEBEIAN, TournamentState.OPEN);
                if (!denyJoining)
                    joined = handle(service.getTournaments(), calendar);
            }
            if (null == joined && Settings.getNovice() && !denyJoining) {
                service.visitTournaments(TournamentType.NEWBIES, TournamentState.OPEN);
                if (!denyJoining)
                    joined = handle(service.getTournaments(), calendar);
            }
            Btl incomingBtl = service.getIncomingBtl();
            if (incomingBtl != null && incomingBtl.getId() != 0L) {
                fighter.battle(incomingBtl, joined);
            } else {
                Utils.sleep(denyJoining && mm < 55 ? 60000 : 11000);
            }
            if (!wasRecentlyPaused && Settings.getPauseDuration() > 0 && joined == null && getParticipatingTrnm() == null && incomingBtl == null) {
                double pause = Settings.getPauseDuration();

                Utils.soutn("Pausing for " + pause + " minutes");
                Utils.sleep((int) (pause * 60 * 1000));
                wasRecentlyPaused = true;
            }
            if (getParticipatingTrnm() != null || incomingBtl != null) {
                wasRecentlyPaused = false;
            }

        } catch (NotLoggedInException e) {
            Utils.soutn(e.getMessage());
            LOGGER.warn("", e);
            try {
                service.login(Settings.getUser(), Settings.getPassw());
            } catch (IOException e1) {
                LOGGER.error("", e);
            }
        } catch (IOException e) {
            Utils.soutn(e.getMessage());
            LOGGER.error("", e);
        } catch (XmlException e) {
            Utils.soutn(e.getMessage());
            LOGGER.error("", e);
        } catch (Exception e) {
            Utils.soutn(e.getMessage());
            LOGGER.error("", e);
        }

    }

    public static boolean fullHP(Collection<CGlad> glads) {
        for (CGlad glad : glads) {
            if (glad.getStamina() < 100) return false;
        }
        return true;
    }

    private Trnm handle(Map<Long, Trnm> tournaments, Calendar calendar) throws Exception {
        Trnm participating = getParticipatingTrnm();
        if (participating == null) {
            for (Trnm trnm : tournaments.values()) {
                if (trnm.getStart() != null) {
                    Calendar trnmStart = Calendar.getInstance();
                    trnmStart.setTime(trnm.getStart());
                    if (this.closestTrnmTime == null || this.closestTrnmTime.after(trnmStart)) {
                        this.closestTrnmTime = Calendar.getInstance();
                        this.closestTrnmTime.setTime(trnm.getStart());
                    }
                }
            }
            List<Trnm> sortedTrnms = new ArrayList<Trnm>(tournaments.values());
            Collections.sort(sortedTrnms, new TrnmLvlComparator());
            if (!sortedTrnms.isEmpty()) {
                Trnm trnm = findAppropriateTrnm(sortedTrnms, service.getGlads());
                long nowCorrected = calendar.getTime().getTime();// - HOUR;
                if (trnm != null && trnm.getId() != 0) {
                    long joinThreshold = trnm.getStart() == null ? 0 : (trnm.getStart().getTime() - 5 * MIN);
                    if (nowCorrected > joinThreshold) {
                        service.joinTrn(trnm);
                        participating = trnm;
                    }
                }
            }
        } else {
            this.closestTrnmTime = null;
            LOGGER.debug("Participating in " + participating);
            if (participating.getCanLeave() != null && participating.getCanLeave().booleanValue()) {
                TournamentSetting trnmSetting = findTourntmentSetting(participating);
                if (trnmSetting != null && trnmSetting.members > -1 && participating.getMembers() > trnmSetting.members + 1) {
                    service.leaveTrn(participating);
                    return null;
                }
                if (participating.getCanUseTicket() != null && participating.getCanUseTicket().booleanValue() && participating.getMembers() > participating.getMaxMembers() && !participating.isIncluded() &&
                        (Settings.getUSEINVITE() == InvitationType.TRUE || Settings.getUSEINVITE() == InvitationType.SENATE && TournamentType.SENATOR == participating.getType())) {
                    service.useTicket(participating);
                }
            }
            //Utils.sleep(25 * 6 * 1000);
        }
        return participating;
    }

    private TournamentSetting findTourntmentSetting(Trnm trnm) {
        for (TournamentSetting trnmSetting : TournamentRegistry.trnms) {
            if (trnm.getName().contains(trnmSetting.name)) return trnmSetting;
        }
        return null;
    }

    private boolean isFully(Trnm trnm) {
        return trnm.getMembers() >= trnm.getMaxMembers() * (trnm.getLvlTo() == null ? Settings.getJoinon() / 3 : Settings.getJoinon()) || trnm.getType().equals(TournamentType.SENATOR);
    }


    private Trnm findAppropriateTrnm(final Collection<Trnm> trnms, final Map<Long, CGlad> glads) {
        if (trnms.size() == 0) {
            return null;
        }
        List<Trnm> acceptableTrnms = new ArrayList<Trnm>();
        for (Trnm trnm : trnms) {
            boolean nameMatch = nameMatch(trnm, glads);//trnm.getName().contains("Луны") || trnm.getName().contains("Люцифера") || trnm.getName().contains("Фаммы");
            boolean canJoin = trnm.getCanJoin() == null ? false : trnm.getCanJoin();
            //boolean fully = isFully(trnm);
            boolean joinup = trnm.getMembers() == null || trnm.getMaxMembers() == null || trnm.getMaxMembers() == 0
                    || Settings.getJoinup() < 0 || trnm.getMembers() <= Settings.getJoinup();
            TournamentSetting trnmSetting = findTourntmentSetting(trnm);
            boolean overwhelming = trnmSetting != null && trnmSetting.members > -1 && trnm.getMembers() > trnmSetting.members;
            if (trnmSetting != null) {
                trnm.setPriority(trnmSetting.priority);
            }
                if (nameMatch && canJoin && !overwhelming) {
                    acceptableTrnms.add(trnm);
                }
        }
        Collections.sort(acceptableTrnms, new CapableGladsComparator());
        Collections.sort(acceptableTrnms, new TrnmPriorityComparator());
        return acceptableTrnms.size() > 0 ? acceptableTrnms.get(0) : null;
    }

    private boolean nameMatch(Trnm trnm, Map<Long, CGlad> glads) {
        for (TournamentSetting trnmSetting : TournamentRegistry.trnms) {
            if (trnm.getName().contains(trnmSetting.name)) {
                int mainglads = 0;
                int horseglads = 0;
                int totalmain = 0;
                int totalhorse = 0;
                for (CGlad glad : glads.values()) {
                    if (trnm.getLvlTo() == null && glad.getLevel() >= trnmSetting.lvl ||
                            trnm.getLvlTo() != null && glad.getLevel() <= trnm.getLvlTo() && glad.getLevel() >= trnmSetting.lvl && trnmSetting.lvl > 0 ||
                            trnm.getLvlTo() != null && glad.getLevel() == trnm.getLvlTo() && trnmSetting.lvl == 0) {
                        if (glad.getTypeid() < 8) totalmain++;
                        else totalhorse++;
                        if (trnm.getLvlFrom() == null || trnm.getLvlFrom() != null && glad.getLevel() == trnm.getLvlFrom()) {
                            if (glad.getHits() == glad.getFullhits()
                                    || glad.getHits() > 0 && (GladType.Velit.isTypeId(glad.getTypeid()) || GladType.Chariot.isTypeId(glad.getTypeid()) || GladType.Archer.isTypeId(glad.getTypeid())) && Utils.ddMutant(glad)) {
                                if (glad.getTypeid() < 8) mainglads++;
                                else horseglads++;
                            }
                        }
                    }
                }
                trnm.setGladsCapacity(totalmain + totalhorse == 0 ? 0 :  (((double)(mainglads+horseglads)) / (totalmain + totalhorse)));
                int mult = 1;
                if ((service.getStuff().getRstGld() != null && service.getStuff().getRstGld() > 0
                        || stuffManager.canUseBonuses() > 3)) {
                    mult = 2;
                }
                LOGGER.info(trnmSetting.name + "(" + (trnm.getLvlFrom() == null ? 0 : trnm.getLvlFrom()) + "," + trnm.getLvlTo() + ")" + " " + mainglads + " " + horseglads + " mult=" + mult + " cap: " + (int) (trnm.getGladsCapacity() * 100) + "%");
                if (trnmSetting.members > -1 && trnm.getMembers() > trnmSetting.members) return false;
                if (mainglads * mult >= trnmSetting.main && horseglads * mult >= trnmSetting.horse) return true;
            }
        }
        return false;
    }

    private Trnm getParticipatingTrnm() {
        if (service.getTournaments().values().size() == 1) {
            Trnm trnm = service.getTournaments().values().iterator().next();
            if (trnm.getCanJoin() == null || !trnm.getCanJoin().booleanValue()) {
                return trnm;
            }
        }
        return null;
    }
}
