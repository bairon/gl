package pp;

import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.model.*;
import pp.model.Guild;
import pp.model.enums.BtlType;
import pp.model.xml.CGlad;
import pp.model.xml.Cxml;
import pp.service.GlRuService;
import pp.service.GlRuServiceDelegate;
import pp.service.GlRuServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class FighterImpl implements Fighter {
    public static final Logger LOGGER = Logger.getLogger(FighterImpl.class);
    private GlRuService service;
    private StuffManager stuffManager;

    public FighterImpl(GlRuService service, StuffManager stuffManager) {
        this.service = service;
        this.stuffManager = stuffManager;
    }

    public boolean senate(Btl btl) {
        return false;//btl.getTourmentTitle().contains("Большие игры Вертумна");
    }
    @Override
    public void battle(Btl btl, Trnm joined) throws Exception {
        //
        Scheme scheme = null;
        boolean attack = false;
        boolean novel = false;
        boolean botus = false;
        if (btl.getPrepare() != null && btl.getPrepare().booleanValue()) {
            if (Settings.getPlay()) SoundUtils.play();
            if (!Settings.getFight()) return;
            btl = service.builder(btl);
            LOGGER.info("Incoming Battle " + btl);
            if ("Botus".contains(btl.getUser1Login()) || "Botus".contains(btl.getUser2Login())) {
                novel = true;
                botus = true;
            }
            if (Settings.getSliv().contains(btl.getUser1Login()) || Settings.getSliv().contains(btl.getUser2Login())) {
                if (Settings.getSliv().contains(btl.getUser1Login()) || Settings.getSliv().contains(btl.getUser2Login())) {
                    scheme = Scheme.SLIV00100;
                    String slivScheme = Settings.getSlivScheme();
                    String[] attackPowerBlock = slivScheme.split("-");
                    scheme.getSlots()[0].setAttack(Integer.parseInt(attackPowerBlock[0]));
                    scheme.getSlots()[0].setPower(Integer.parseInt(attackPowerBlock[1]));
                    scheme.getSlots()[0].setBlock(Integer.parseInt(attackPowerBlock[2]));
                }
                btl.setLimitGlad(1L);
                btl.setType(BtlType.DUEL);
                List<CGlad> allowedGlads = allowed(service.getGlads().values(), btl, joined, botus);
                scheme.fill(new GladPool(allowedGlads));
                Cxml placement = scheme.getPlacement(btl.getId(), false);
                service.send(placement);
                LOGGER.info(Utils.toString(placement));
                Utils.sleep(3 * 1000);
                service.watchFight(btl.getId());
            } else {
                boolean special = false;
                if (Settings.getSpecial().contains(btl.getUser1Login()) || Settings.getSpecial().contains(btl.getUser2Login())) {
                    special = true;
                } else if (Settings.getGuild1x1() && soguild(btl, idfromurl(btl.getUser1Url()), idfromurl(btl.getUser2Url()))) {
                    if (btl.getLimitGlad() > 1) {
                        btl.setLimitGlad(btl.getLimitGlad() - 1);
                    }
                    btl.setGladTip("2|3|4|5|7");
                    attack = true;
                }
                if (Settings.get1x1().contains(btl.getUser1Login()) || Settings.get1x1().contains(btl.getUser2Login())) {
                    btl.setLimitGlad(1L);
                    btl.setType(BtlType.DUEL);
                    btl.setGladTip("2|3|4|5|7");
                    attack = true;
                }
                List<CGlad> allowedGlads = allowed(service.getGlads().values(), btl, joined, botus);
                Collections.shuffle(allowedGlads);
                if (allowedGlads.size() < btl.getLimitGlad())
                    throw new Exception("We have not enough glads..." + Arrays.deepToString(allowedGlads.toArray()) + btl);
                boolean rightPos = btl.getUser2Login().equals(Settings.getUser());
                int freeSpecs = service.getStuff().getRstSpc() != null ? service.getStuff().getRstSpc().intValue() : 0;
                int useForFight = btl.getLimitGlad() > 0 ? Settings.getUSEBONUSESPERFIGHT() : 0;
                int canUseThisFight = Math.min(useForFight, freeSpecs + stuffManager.canUseBonuses());
                final int canrestorespec = senate(btl) ? canUseThisFight : canUseThisFight; // ToDo remove hardcode
                SchemeVariation sv = getScheme(btl, allowedGlads, !rightPos, canrestorespec, attack, novel, special);
                if (sv == null && special) {
                    sv = getScheme(btl, allowedGlads, !rightPos, canrestorespec, attack, novel, false);
                }
                if (sv == null) {
                    LOGGER.debug("Scheme not found." + btl);
                    LOGGER.info("getRestoreglads() = " + Settings.getRestoreglads());
                    if (Settings.getRestoreglads() >= getCapableGlads()) {
                        service.updateStuff();
                        LOGGER.info("RstGld = " + service.getStuff().getRstGld());
                        LOGGER.info("getSaveTroop = " + Settings.getSaveTroop());
                        if (service.getStuff().getRstGld() != null && service.getStuff().getRstGld() > Settings.getSaveTroop() || stuffManager.canUseBonuses() >= 3) {
                            service.restoreGlads();
                            return;
                        }
                    }
                    if (Settings.getPlay()) {
                        SoundUtils.play();
                    }
                    stuffManager.restore(false);
                    return;
                }
                LOGGER.info(sv.scheme);
                boolean invert = !novel && (rightPos && !sv.scheme.isLeft() && !sv.scheme.isRight() &&  BtlType.BATTLE == btl.getType() && btl.getGladTip().contains("9") || Math.random() < 0.5d && !btl.getGladTip().contains("9") && BtlType.BATTLE == btl.getType());
                GladPool gp = new GladPool(sv.gladstogo, botus);
                List<CGlad> gladsToGo = sv.scheme.fill(gp);

                stuffManager.equipArmor(btl.getMaxLevel(), gladsToGo, Settings.getSilver(), botus);
                LOGGER.debug("Preparing glads...");
                int counter = 0;
                int doublecounter = 0;
                //if (!botus)
                int result;
                while ((result = stuffManager.prepare(gladsToGo, true, true, botus, false)) == 0);
                if (result == -1) {
                    LOGGER.info("getRestoreglads() = " + Settings.getRestoreglads());

                    if (Settings.getRestoreglads() >= getCapableGlads()) {
                        service.updateStuff();
                        LOGGER.info("RstGld = " + service.getStuff().getRstGld());
                        LOGGER.info("getSaveTroop = " + Settings.getSaveTroop());
                        if (service.getStuff().getRstGld() != null && service.getStuff().getRstGld() > Settings.getSaveTroop() || stuffManager.canUseBonuses() >= 3) {
                            service.restoreGlads();
                            result = 1;
                            return;
                        }

                    }
                }
                Cxml placement;
                if (result == -1) {
                    while ((result = stuffManager.prepare(gladsToGo, true, true, botus, false)) == 0);
/*                    scheme = Scheme.SLIV00100;
                    String slivScheme = Settings.getSlivScheme();
                    String[] attackPowerBlock = slivScheme.split("-");
                    scheme.getSlots()[0].setAttack(Integer.parseInt(attackPowerBlock[0]));
                    scheme.getSlots()[0].setPower(Integer.parseInt(attackPowerBlock[1]));
                    scheme.getSlots()[0].setBlock(Integer.parseInt(attackPowerBlock[2]));
                    btl.setLimitGlad(1L);
                    btl.setType(BtlType.DUEL);
                    allowedGlads = allowed(service.getGlads().values(), btl, joined, botus);
                    scheme.fill(new GladPool(allowedGlads));*/
                    LOGGER.info(Arrays.deepToString(gladsToGo.toArray()));
                    placement = sv.scheme.getPlacement(btl.getId(), invert);
                } else {
                    LOGGER.info(Arrays.deepToString(gladsToGo.toArray()));
                    placement = sv.scheme.getPlacement(btl.getId(), invert);
                }
                LOGGER.info("Ready to send, waiting " + Settings.getDelay() + " seconds");
                if (Settings.getDelay() > 0) Utils.sleep(Settings.getDelay() * 1000);
                service.send(placement);
                Utils.sleep(5 *  1000);
                service.watchFight(btl.getId());
            }
        }
        if ((btl.getFight() != null && btl.getFight().booleanValue()) || btl.isTimeOut() || btl.getCheck() != null && btl.getCheck().booleanValue()) {
            String winner = service.watchFight(btl.getId());
            if (!Utils.isBlank(winner)) LOGGER.info("******************** " + winner + "********************");
            service.updateGlads();
            service.updateStuff();
            stuffManager.spendDoctor(Collections.<CGlad>emptyList(), false);
        }

    }


    public int getCapableGlads() throws IOException {
        int result = 0;
        service.updateGlads();
        for (CGlad g : service.getGlads().values()) {
            if (g.getStamina() >= Settings.getCapable() || Utils.ddMutant(g) && g.getHits() > 0) result++;
        }
        LOGGER.info("Capable glads " + result);
        return result;
    }

    private long idfromurl(String url) {
        if (Utils.isBlank(url)) return 0;
        int l = url.lastIndexOf("/");
        String idstr = url.substring(l + 1);
        return Long.parseLong(idstr);
    }

    private List<CGlad> allowed(Collection<CGlad> glads, Btl btl, Trnm joined, boolean botus) {
        ArrayList<CGlad> allowedGlads = new ArrayList<CGlad>();
        for (CGlad gladiator : glads) {
            if (match(gladiator, btl, joined, botus)) {
                allowedGlads.add(gladiator);
            }
        }
        return allowedGlads;
    }

    private Long getUserIdFromUrl(String url) {
        long l = Long.parseLong(url.substring(url.lastIndexOf("/") + 1));
        return l;
    }

    private SchemeVariation getScheme(Btl btl, List<CGlad> glads, boolean left, int canrestorespec, boolean attack, boolean novel, boolean special) throws Exception {
        List<Scheme> schemes = special ? SchemeRegistry.special : SchemeRegistry.main;
        boolean survive = false;
        if (btl.getType() == BtlType.BATTLE) {
            if (!btl.getGladTip().contains("9")) {
                schemes = special ? SchemeRegistry.special_main : SchemeRegistry.main;
            } else {
                schemes = special ? SchemeRegistry.special_horse : SchemeRegistry.horse;
            }
        } else {
            if (btl.getLimitGlad() > 1) {
                schemes = special ? SchemeRegistry.special_survive : SchemeRegistry.survive;
                survive=true;
            }

            else schemes = special ? SchemeRegistry.special_main : SchemeRegistry.main;

        }
        if (novel) {
            if (survive) {
                schemes = SchemeRegistry.botus_survive;
            } else {
                if (!btl.getGladTip().contains("9")) {
                    schemes = SchemeRegistry.botus_main;
                } else {
                    schemes = SchemeRegistry.botus_horse;
                }
            }
        }
        Stuff stuff = service.getStuff();
        final int canheal = (stuff.getMasseur().intValue() / 5) * 10 + (canrestorespec * 100);
        final int canmorale = stuff.getPriest().intValue() / 10 + (canrestorespec * 10);
        final int cancure = stuff.getDoctor().intValue() / 10 + (canrestorespec * 10);
        List<SchemeVariation> variations = new ArrayList<SchemeVariation>();
        for (Scheme scheme : schemes) {
            if (survive && !isForSurvive(scheme)) continue;
            if (scheme.getSlots().length != btl.getLimitGlad()) continue;
            if (scheme.isLeft() && !left || scheme.isRight() && left) continue;
            if (attack && summattack(scheme) < 50) continue;
            if (novel && hasvel(scheme)) continue;
            for (int healable = 0; healable <= 100; healable += 10) {
            //int healable  = 80;
                GladPool gp = new GladPool(glads);
                gp.healable = healable;
                if (scheme.getSlots().length == btl.getLimitGlad()) {
                    List<CGlad> gladsToGo = scheme.fill(gp);
                    stuffManager.equipArmor(btl.getMaxLevel(), gladsToGo, Settings.getSilver(), novel);
                    if (gladsToGo.size() == btl.getLimitGlad()) {//&& stuffManager.canPrepare(gladsToGo, canheal, canmorale, cancure)) {
                        SchemeVariation sv = new SchemeVariation(scheme, gladsToGo, healable, stuffManager.needHeal(gladsToGo, true, service.getStuff().getDoctor()), stuffManager.needMorale(gladsToGo, true), stuffManager.needCure(gladsToGo));
                        variations.add(sv);
                    }
                }
            }
        }
        Collections.shuffle(variations);
        Collections.sort(variations, new Comparator<SchemeVariation>() {
            @Override
            public int compare(SchemeVariation o1, SchemeVariation o2) {
                int result = 0;
                if (result == 0) result = o1.compareTo(o2);
                return result;
            }
        });
        if (variations == null || variations.size() == 0) {
            return null;
        }
        Set<SchemeVariation> firstBest = new HashSet<SchemeVariation>();
        for (SchemeVariation variation : variations) {
            firstBest.add(variation);
        }
        LOGGER.debug("Found " + firstBest.size() + " best variants.");
        Set<SchemeVariation> firstBestSame = bestSame(firstBest);

        if (firstBestSame.size() > 1) {
            return random(firstBestSame);
        } else if (firstBestSame.size() > 0) {
            return firstBestSame.iterator().next();
        } else return null;
    }

    private boolean isForSurvive(Scheme scheme) {
        for (Slot slot : scheme.getSlots()) {
            if (slot.getX() != 1) return false;
        }
        return true;
    }

    private Set<SchemeVariation> bestSame(Set<SchemeVariation> firstBest) {
        int value = Integer.MAX_VALUE;
        HashSet<SchemeVariation> result = new HashSet<SchemeVariation>();
        for (SchemeVariation sv : firstBest) {
            if (Integer.valueOf(sv.needheal + sv.needmorale + sv.needcure * 10) < value) {
                value = Integer.valueOf(sv.needheal + sv.needmorale + sv.needcure * 10);
            }
        }
        for (SchemeVariation sv : firstBest) {
            if (Integer.valueOf(sv.needheal + sv.needmorale + sv.needcure * 10) <= value)
                result.add(sv);
        }
        return result;
    }

    private boolean hasvel(Scheme scheme) {
        for (Slot slot : scheme.getSlots()) {
            if (slot.getCategory() == GladCategory.VEL) return true;
        }
        return false;
    }

    private int summattack(Scheme scheme) {
        int attack = 0;
        Slot[] slots = scheme.getSlots();
        for (Slot slot : slots) {
            attack += slot.getAttack();
        }
        return attack / slots.length;
    }

    private SchemeVariation random(Set<SchemeVariation> schemas) {
        LOGGER.info("Choosing from " + schemas.size() + " variants:\n" + Arrays.deepToString(schemas.toArray()));
        // according schema.priority
        Object[] scmarr = schemas.toArray();
        int[] F = new int[scmarr.length];
        int prev = 0;
        for (int i = 0; i < scmarr.length; ++i) {
            F[i] = prev + ((SchemeVariation) scmarr[i]).scheme.getPriority();
            prev = F[i];
        }
        double r = Math.random() * F[F.length - 1];
        for (int i = 0; i < F.length; ++i) {
            if (r <= F[i]) return (SchemeVariation) scmarr[i];
        }
        return null;
    }

    private Object mm(Object... m) {
        return m[(int) Math.floor(Math.random() * m.length)];
    }


    private boolean match(final CGlad gladiator, final Btl btl, Trnm trnm, boolean botus) {

        TournamentSetting setting = null;
        for (TournamentSetting trnmSetting : TournamentRegistry.trnms) {
            //if (trnm != null && !Utils.isBlank(trnm.getName()) && trnm.getName().contains(trnmSetting.name)) {
            if (btl.getTourmentTitle() != null && btl.getTourmentTitle().contains(trnmSetting.name)) {
                setting = trnmSetting;
            }
        }
        //LOGGER.info("Setting " + setting == null ? "not found" : setting);
        if (botus) {
            if (setting == null) {
                setting = new TournamentSetting(btl.getTourmentTitle(), 0, 0, 0,0, 0);
            }
            setting.lvl = btl.getMaxLevel() == null ? 50 : (int) (btl.getMaxLevel() - 3);
        }
        LOGGER.debug("Glad: " + gladiator + " Setting: " + setting + " Tournament: " + trnm);
        if (btl.getMaxLevel() == null || btl.getMaxLevel() == 0) {
            if (gladiator.getLevel() >= (setting == null ? 50 : setting.lvl) && btl.getGladTip().contains(Long.toString(gladiator.getTypeid()))) {
                return true;
            } else {
                LOGGER.debug("Ne prohodit po lvly");
                return false;
            }
        }
        if (!(gladiator.getLevel() >= (setting == null || setting.lvl == 0 ? btl.getMaxLevel() : setting.lvl) && (gladiator.getLevel() <= btl.getMaxLevel()))) {
            LOGGER.debug("Ne prohodit po lvly");
            return false;
        }
        if (!btl.getGladTip().contains(Long.toString(gladiator.getTypeid()))) {
            LOGGER.debug("Ne prohodit po tipy");
            return false;
        }
        return true;
    }

    private double getAvMorale(List<CGlad> gladsToGo) {
        long morale = 0;
        for (CGlad cGlad : gladsToGo) {
            morale += cGlad.getArmorale();
        }
        return 1d * morale / gladsToGo.size();
    }

    private double getMinMorale(List<CGlad> gladsToGo) {
        long min = 10;
        for (CGlad cGlad : gladsToGo) {
            if (cGlad.getArmorale() < min) {
                min = cGlad.getArmorale();
            }
        }
        return min;
    }

    public static void main(String[] args) throws Exception {
        MyHttpClient client = new MyHttpClient();
        client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
        GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
        service.login(Settings.getUser(), Settings.getPassw());
        service.updateGlads();
        service.updateStuff();
        GlRuService serviceDelegate = new GlRuServiceDelegate(service);
        StuffManager stuffStub = new StuffManagerStub(new StuffManagerImpl(service));
        Fighter fighter = new FighterImpl(service, stuffStub);
        fighter.battle(getFakeBtl(), new Trnm());
    }

    private static Btl getFakeBtl() {
        Btl btl = new Btl();
        btl.setId(11008l);
        btl.setPrepare(true);
        btl.setLimitGlad(7L);
        btl.setMaxLevel(0L);
        btl.setType(BtlType.BATTLE);
        btl.setGladTip("1|2|3|4|5|6|7|8|9|10");
        btl.setUser1Login("");
        btl.setUser2Login("");
        return btl;
    }

    public boolean soguild(Btl btl, final long id1, final long id2) {
        if (btl.getOppGuild() != 0 && service.getMyGuild() == btl.getOppGuild()) return true;
        else {
            boolean g1 = false, g2 = false;
            for (Guild guild : service.getGuilds().values()) {
                for (Player player : guild.getPlayers().values()) {
                    g1 |= player.getId() == id1;
                    g2 |= player.getId() == id2;
                }
            }
            return g1 && g2;
        }
    }
}
