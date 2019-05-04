package pp;

import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.model.Application;
import pp.model.Btl;
import pp.model.Challenge;
import pp.model.GladPool;
import pp.model.Opponent;
import pp.model.Position;
import pp.model.Scheme;
import pp.model.SchemeRegistry;
import pp.model.enums.BattleType;
import pp.model.xml.CGlad;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KachThread extends Thread {
    public static final Logger LOGGER = Logger.getLogger(KachThread.class);
    private boolean kach;
    private String place;
    private String battleType;
    private String login;
    private String password;
    private String opponent;
    private int gladlimit;
    private int maxlevel;
    private int totallevel;
    private int delay;
    private String gladfilter;
    private boolean interrupted;

    public KachThread(boolean kach, String place, String battleType, String login, String password, String opponent, int gladlimit, int maxlevel, int totallevel, String gladfilter, int delay) {
        this.kach = kach;
        this.place = place;
        this.battleType = battleType;
        this.login = login;
        this.password = password;
        this.opponent = opponent;
        this.gladlimit = gladlimit;
        this.maxlevel = maxlevel;
        this.totallevel = totallevel;
        this.gladfilter = gladfilter;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            MyHttpClient client = new MyHttpClient();
            client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
            client.appendInitialCookie("cookie_lang_2", "rus", Settings.getServer());
            SchemeRegistry.reload();
            GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
            StuffManager stuffManager = new StuffManagerImpl(service);
            service.login(login, password);
            if (KachGladov.MGT.equals(place))    service.visitGuilds();
            service.visitMyGladiators();
            service.updateStuff();
            if (kach) {
                service.updateGlads();
                service.visitRecovery();
                int maxLevel = Settings.getMaxLevel();
                if (maxLevel == 0) maxLevel = 40;
                while (true) {
                    try {
                        if (interrupted) return;
                        Map<Long, CGlad> glads = service.getGlads();
                        //if (KachGladov.MGT.equals(place)) {
                            service.arena(BattleType.BATTLE.getId());
                        //}
                        if (service.getBttls().values().size() > 0) {
                            Btl btl = service.getBttls().values().iterator().next();
                            btl = service.builder(btl);
                            battleActive(service, stuffManager, btl);
                        } else {
                            if (KachGladov.MGT.equals(place)) {
                                Map<Long, Challenge> challenges = service.getChallenges();
                                for (Challenge challenge : challenges.values()) {
                                    if ((service.getMyGuild() == challenge.attackerGId || service.getMyGuild() == challenge.defenderGId) && challenge.started) {
                                        boolean needTakePosition = true;
                                        Position lastFreePosition = null;
                                        Map<Long, Position> positions = service.getPositions(challenge.getId());
                                        for (Position position : positions.values()) {
                                            if (opponent.equalsIgnoreCase(position.defender)) {
                                                service.attackPosition(challenge.getId(), position.getId());
                                                needTakePosition = false;
                                            }
                                            if (login.equalsIgnoreCase(position.defender))
                                                needTakePosition = false;
                                            if (position.defender == null) lastFreePosition = position;
                                        }
                                        if (needTakePosition && lastFreePosition != null) {
                                            //service.takePosition(challenge.getId(), lastFreePosition.getId());
                                        }
                                    }
                                }
                            } else if (KachGladov.TRANING.equals(place)) {
                                Opponent opp = service.arena(BattleType.BATTLE.getId());
                                if (opp == null || opp.id <= 0) {
                                    service.arena(KachGladov.BATTLE.equalsIgnoreCase(battleType) ? BattleType.BATTLE : BattleType.SURVIVE, gladlimit, maxlevel, totallevel, 40, gladfilter);
                                    if (KachGladov.BOTUS.equalsIgnoreCase(opponent)) {
                                        Utils.sleep(1000);
                                        service.arenaBot(KachGladov.BATTLE.equalsIgnoreCase(battleType) ? BattleType.BATTLE : BattleType.SURVIVE);
                                    }
                                } else {
                                    if (opp.id > 0 && opponent.equalsIgnoreCase(opp.name)) {
                                        service.arenaAccept();
                                    } else if (opp.id > 0) {
                                        service.arenaReject();
                                    }
                                }
                            }
                        }
                        Utils.sleep(100);
                    } catch (Exception e) {
                       LOGGER.error("", e);
                        Utils.sleep(5000);
                    }
                }
            } else {
                while (true) {
                    try {
                        if (interrupted) return;
                        service.updateGlads();
                        service.arena(BattleType.BATTLE.getId());
                        if (service.getBttls().values().size() > 0) {
                            Btl btl = service.getBttls().values().iterator().next();
                            btl = service.builder(btl);
                            battlePassive(service, stuffManager, btl);
                        } else {
                            if (KachGladov.MGT.equals(place)) {
                                Map<Long, Challenge> challenges = service.getChallenges();
                                for (Challenge challenge : challenges.values()) {
                                    if ((service.getMyGuild() == challenge.attackerGId || service.getMyGuild() == challenge.defenderGId) && challenge.started) {
                                        Map<Long, Position> positions = service.getPositions(challenge.getId());
                                        for (Position position : positions.values()) {
                                            if (opponent.equalsIgnoreCase(position.defender)) {
                                                service.attackPosition(challenge.getId(), position.getId());
                                            }
                                        }
                                    }
                                }
                            } else {
                                Map<Long, Application> applications = service.getApplications();
                                for (Application application : applications.values()) {
                                    if (opponent.equalsIgnoreCase(application.userName)) {
                                        service.arenaChallenge(BattleType.BATTLE.getId(), application.getId());
                                    }
                                }
                            }
                        }
                        Utils.sleep(1000);
                    } catch (Exception e) {
                        LOGGER.error("", e);
                        Utils.sleep(5000);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }

    }
    private void battleActive(GlRuService service, StuffManager stuffManager, Btl btl) throws Exception {
        if (btl.getPrepare() != null && (opponent.equalsIgnoreCase(btl.getUser1Login()) || opponent.equalsIgnoreCase(btl.getUser2Login()))) {
            Utils.sleep(1000 * delay);
            service.visitRecovery();
            List<CGlad> gladsToGo = null;
            Scheme scheme = null;
            List<CGlad> allGlads = allowed(service.getGlads().values(), btl);
            if (!SchemeRegistry.kach.isEmpty()) scheme = SchemeRegistry.kach.get(0);
            GladPool gp = new GladPool(new ArrayList<CGlad>(allGlads));
            gladsToGo = scheme.fill(gp);
                if (stuffManager.prepareFast(gladsToGo)) {
                    service.send(scheme.getPlacement(btl.getId(), false));
                }
        }
        Utils.sleep(200);
        service.watchFight(btl.getId());
    }

    private void battlePassive(GlRuService service, StuffManager stuffManager, Btl btl) throws Exception {
        if (btl.getPrepare() != null && (opponent.equalsIgnoreCase(btl.getUser1Login()) || opponent.equalsIgnoreCase(btl.getUser2Login()))) {
            Utils.sleep(1000 * delay + 200);
            SchemeRegistry.reload();
            service.updateGlads();
            service.updateStuff();
            Scheme scheme = null;
            int maxLevel = Settings.getMaxLevel();
            if (maxLevel == 0) maxLevel = 99;
            List<CGlad> gladsToGo = getGeneralCapableTypes(service.getGlads().values(), maxLevel);
            if (getGeneralTypes(service.getGlads().values(), maxLevel).size() > 0 && gladsToGo.size() == 0){// && (stuffManager.canUseBonuses() >= 3 || service.getStuff().getRstGld() != null && service.getStuff().getRstGld() > 0)) {
                service.restoreGlads();
                Utils.sleep(1000);
                gladsToGo = getGeneralCapableTypes(service.getGlads().values(), maxLevel);
            }
            if (gladsToGo.isEmpty()) return;
            if (!SchemeRegistry.sliv.isEmpty()) scheme = SchemeRegistry.sliv.get(0);
            scheme.fill(new GladPool(gladsToGo));
            service.send(scheme.getPlacement(btl.getId(), false));
        }
        Utils.sleep(200);
        service.watchFight(btl.getId());
    }
    private List<CGlad> allowed(Collection<CGlad> glads, Btl btl) {
        ArrayList<CGlad> allowedGlads = new ArrayList<CGlad>();
        for (CGlad gladiator : glads) {
            if (match(gladiator, btl)) {
                allowedGlads.add(gladiator);
            }
        }
        return allowedGlads;
    }
    private boolean match(final CGlad gladiator, final Btl btl) {
        if (!(gladiator.getLevel() <= btl.getMaxLevel())) {
            return false;
        }
        if (!btl.getGladTip().contains(Long.toString(gladiator.getTypeid()))) return false;
        return true;
    }

    private static List<CGlad> getAllTypes(Collection<CGlad> values, int maxLevel) {
        List<CGlad> result = new ArrayList<CGlad>(values.size());
        for (CGlad glad : values) {
            if ( glad.getLevel() <= maxLevel) result.add(glad);
        }
        return result;
    }
    private static List<CGlad> getAllowedGlads(Collection<CGlad> values, int maxLevel) {
        List<CGlad> result = new ArrayList<CGlad>(values.size());
        for (CGlad glad : values) {
            if ( glad.getLevel() <= maxLevel) result.add(glad);
        }
        return result;
    }

    private static List<CGlad> getGeneralTypes(Collection<CGlad> values, int maxLevel) {
        List<CGlad> result = new ArrayList<CGlad>(values.size());
        for (CGlad glad : values) {
            if (glad.getTypeid() < 8 && glad.getLevel() <= maxLevel) result.add(glad);
        }
        return result;
    }

    private static List<CGlad> getGeneralCapableTypes(Collection<CGlad> values, int maxLevel) {
        List<CGlad> result = new ArrayList<CGlad>(values.size());
        for (CGlad glad : values) {
            if (glad.getTypeid() < 8 && glad.getStamina() >= 25 && glad.getLevel() <= maxLevel) result.add(glad);
        }
        return result;
    }

    public void stopThis() {
        interrupted = true;
    }
}
