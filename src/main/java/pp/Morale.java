package pp;

import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.model.Application;
import pp.model.Btl;
import pp.model.Opponent;
import pp.model.enums.BattleType;
import pp.model.enums.GladType;
import pp.model.xml.CGlad;
import pp.model.xml.Cgl;
import pp.model.xml.Cxml;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Morale {
    public static final Logger LOGGER = Logger.getLogger(Morale.class);

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            usage();
        }
        String action = args[0];
        if (!action.equalsIgnoreCase("active") || !action.equalsIgnoreCase("passive")) {
            usage();
        }
        String name = args[1];
        LOGGER.debug("Starting morale " + action + " with " + name);
        MyHttpClient client = new MyHttpClient();
        client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
        GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
        service.login(Settings.getUser(), Settings.getPassw());
        service.visitMyGladiators();
        if (action.equalsIgnoreCase("active")) {
            while (true) {
                service.updateGlads();
                Map<Long, CGlad> glads = service.getGlads();
                int needmorale = calcNeedMorale(glads);
                if (needmorale > 0) {
                    LOGGER.info("Need morale " + needmorale);
                    Opponent opponent = service.arena(BattleType.BATTLE.getId());
                    if (service.getBttls().values().size() > 0) {
                        battleActive(service, service.getBttls().values().iterator().next(), glads);
                    } else {
                        if (opponent == null) {
                            int maxlevel = getMaxLevel(glads);
                            if (maxlevel < 20) maxlevel = 20;
                            service.arena(BattleType.BATTLE, 7, maxlevel, (maxlevel * 8 / 10) * 10, 10, "1,2,3,4,5,6,7");
                        } else {
                            if (opponent.id > 0 && name.equals(opponent.name)) {
                                service.arenaAccept();
                            } else if (opponent.id > 0) {
                                service.arenaReject();
                            }
                        }
                    }
                } else {
                    LOGGER.info("Morale full.");
                    return;
                }
                Utils.sleep(5000);
            }
        } else if (action.equalsIgnoreCase("passive")) {
            while (true) {
                service.updateGlads();
                Map<Long, CGlad> glads = service.getGlads();
                    service.arena(BattleType.BATTLE.getId());
                    if (service.getBttls().values().size() > 0) {
                        battlePassive(service, service.getBttls().values().iterator().next(), glads);
                    } else {
                        Map<Long, Application> applications = service.getApplications();
                        for (Application application : applications.values()) {
                            if (name.equals(application.userName)) {
                                service.arenaChallenge(BattleType.BATTLE.getId(), application.getId());
                            }
                        }
                    }
                Utils.sleep(5000);
            }
        } else {
            usage();
        }
    }

    private static void battlePassive(GlRuService service, Btl btl, Map<Long, CGlad> glads) throws Exception {
        if (btl.getPrepare() != null && btl.getPrepare().booleanValue()) {
            List<CGlad> gladsToGo = getGeneralTypes(glads.values());
            Collections.sort(gladsToGo, new Comparator<CGlad>() {
                @Override
                public int compare(CGlad o1, CGlad o2) {
                    if (o1.getTypeid() == GladType.Velit.getId()) return 1;
                    if (o2.getTypeid() == GladType.Velit.getId()) return -1;
                    return Long.valueOf(o1.getMorale()).compareTo(o2.getMorale());
                }
            });
            gladsToGo = gladsToGo.size() > 1 ? gladsToGo.subList(0, 1) : gladsToGo;
            Cxml cxml = new Cxml();
            cxml.setId(btl.getId());
            int index = 0;
            List<Cgl> gls = new ArrayList<Cgl>();
            for (CGlad glad : gladsToGo) {
                Cgl gl = new Cgl();
                gl.setId(glad.getId());
                gl.setAttack(0);
                gl.setPower(0);
                gl.setBlock(0);
                gl.setCourage("0");
                gl.setX(getX(index));
                gl.setY(getY(index));
                gl.setArmorid("");
                index++;
                gls.add(gl);
            }
            cxml.setGls(gls);
            service.send(cxml);
            Utils.sleep(5000);
            service.watchFight(btl.getId());
        }
        if ((btl.getFight() != null && btl.getFight().booleanValue()) || btl.isTimeOut() || btl.getCheck() != null && btl.getCheck().booleanValue()) {
            String winner = service.watchFight(btl.getId());
            if (!Utils.isBlank(winner)) LOGGER.info("******************** " + winner + "********************");
        }
    }

    private static void battleActive(GlRuService service, Btl btl, Map<Long, CGlad> glads) throws Exception {
        if (btl.getPrepare() != null && btl.getPrepare().booleanValue()) {
            List<CGlad> gladsToGo = getGeneralTypes(glads.values());
            if (gladsToGo.size() == 0) {
                LOGGER.warn("Not enough glads");
                return;
            }
            Collections.sort(gladsToGo, new Comparator<CGlad>() {
                @Override
                public int compare(CGlad o1, CGlad o2) {
                    return Long.valueOf(o1.getMorale()).compareTo(o2.getMorale());
                }
            });
            gladsToGo = gladsToGo.size() > 7 ? gladsToGo.subList(0, 7) : gladsToGo;
            Collections.sort(gladsToGo, new Comparator<CGlad>() {
                @Override
                public int compare(CGlad o1, CGlad o2) {
                    if (o1.getTypeid() == GladType.Velit.getId()) return 1;
                    if (o2.getTypeid() == GladType.Velit.getId()) return -1;
                    return 0;
                }
            });
            Cxml cxml = new Cxml();
            cxml.setId(btl.getId());
            int index = 0;
            List<Cgl> gls = new ArrayList<Cgl>();
            for (CGlad glad : gladsToGo) {
                Cgl gl = new Cgl();
                gl.setId(glad.getId());
                gl.setAttack(glad.getTypeid() == GladType.Velit.getId() ? 0 : (40 + index * 5) > 100 ? 100 : (40 + index * 5));
                gl.setPower(index > 0 ? 0 : 0);
                gl.setBlock(index > 0 ? 100 : 100);
                gl.setCourage("50");
                gl.setX(getX(index));
                gl.setY(getY(index));
                gl.setArmorid("");
                index++;
                gls.add(gl);
            }
            cxml.setGls(gls);
            service.send(cxml);
            Utils.sleep(5000);
            service.watchFight(btl.getId());
        }
        if ((btl.getFight() != null && btl.getFight().booleanValue()) || btl.isTimeOut() || btl.getCheck() != null && btl.getCheck().booleanValue()) {
            String winner = service.watchFight(btl.getId());
            if (!Utils.isBlank(winner)) LOGGER.info("******************** " + winner + "********************");
        }
    }

    private static List<CGlad> getGeneralTypes(Collection<CGlad> values) {
        List<CGlad> result = new ArrayList<CGlad>(values.size());
        for (CGlad glad : values) {
            if (glad.getTypeid() < 8 && glad.getHits() >= 1) result.add(glad);
        }
        return result;
    }

    private static long getX(int index) {
        switch (index) {
            case 0: return 4;
            case 1: return 3;
            case 2: return 3;
            case 3: return 2;
            case 4: return 2;
            case 5: return 1;
            case 6: return 1;
            default:return 1;
        }
    }
    private static long getY(int index) {
        switch (index) {
            case 0: return 3;
            case 1: return 2;
            case 2: return 4;
            case 3: return 1;
            case 4: return 5;
            case 5: return 1;
            case 6: return 5;
            default:return 3;
        }
    }

    private static int getMaxLevel(Map<Long, CGlad> glads) {
        int result = 0;
        for (CGlad glad : glads.values()) {
            if (result < glad.getLevel()) result = (int) glad.getLevel();
        }
        return result;
    }

    private static int calcNeedMorale(Map<Long, CGlad> glads) {
        int result = 0;
        for (CGlad glad : glads.values()) {
            if (glad.getTypeid() < 8 && glad.getHits() > 0) result += 10 - glad.getMorale();
        }
        return result;
    }

    public static void usage() {
        LOGGER.info("Usage: morale active/passive \"nickname\"");
        return;
    }
}
