package pp;

import org.apache.log4j.Logger;
import pp.model.Stuff;
import pp.model.comparators.*;
import pp.model.enums.GladType;
import pp.model.xml.CArmor;
import pp.model.xml.CGlad;
import pp.service.GlRuService;

import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 30.11.11
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public class StuffManagerImpl implements StuffManager {
	public static final Logger LOGGER = Logger.getLogger(FighterImpl.class);
	private GlRuService service;

	public StuffManagerImpl(GlRuService service) {
		this.service = service;
	}

    @Override
    public boolean canPrepare(List<CGlad> gladsToGo) throws Exception {
        return prepare(gladsToGo, false, true, false, false) >= 0;
    }

    @Override
    public boolean canPrepare(List<CGlad> gladsToGo, int canheal, int canmorale, int cancure) {
        return needHeal(gladsToGo, true, cancure) <= canheal && needCure(gladsToGo) <= cancure;
    }

    public boolean prepareFast(List<CGlad> gladsToGo)throws Exception {
        long heal;
        long cure;
        Stuff stuff = service.getStuff();
        Map<Long, CGlad> glads = service.getGlads();
        boolean first = true;
        while ((heal = needHeal(gladsToGo, true, stuff.getDoctor())) > 0 && (stuff.getMasseur() >= 5 || stuff.getSpecs() > 0 || canUseBonuses() > 0)
            | (cure = needCure(gladsToGo)) > 0 && (stuff.getDoctor() >= 10 || stuff.getSpecs() > 0 || canUseBonuses() > 0)) {
            if (!first) {
               service.visitRecovery();
                stuff = service.getStuff();
            }
            first = false;
            LOGGER.info("Need heal " + heal + " cure " + cure);
            System.out.println("Need heal " + heal + " cure " + cure);
            if (cure > 0) {
                if (stuff.getDoctor() < 10 && (stuff.getSpecs() > 0 || canUseBonuses() > 0)) {// && service.getStuff().getRstSpc() > 0)) {
                    spendMasseurFast(gladsToGo);
                    spendPriestFast(0);
                    service.applySpecs();
                    service.restoreSpecs();
                }
                for (CGlad glad : gladsToGo) {
                    double injury = calcInj(glad.getStamina());
                    long doctor = stuff.getDoctor();
                    double cured = 0;
                    if (doctor >= Math.ceil(injury) * 10) {
                        cured = injury;
                    } else {
                        if (doctor >= 10 && injury >= 1) {
                            cured = Math.min(doctor / 10 , (long) injury);
                        } else if (injury * 10 < doctor) {
                            cured = injury;
                        }
                    }
                    if (cured > 0) {
                        glad.setStamina(glad.getStamina() + cured * 40);
                        stuff.setDoctor((long) (doctor - cured * 10));
                    }
                }
                service.applySpecs();
            }
            if ((heal = needHeal(gladsToGo, true, stuff.getDoctor())) > 0) {
                if (stuff.getMasseur() < 5 && (stuff.getSpecs() > 0 || canUseBonuses() > 0)) {// && service.getStuff().getRstSpc() > 0)) {
                    spendDoctorFast(gladsToGo, true);
                    spendPriestFast(0);
                    service.applySpecs();
                    service.restoreSpecs();
                }
                for (CGlad glad : gladsToGo) {
                    double goal = Utils.ddMutant(glad) ? Settings.getHealUpTo() : 100d;
                    if (glad.getStamina() >= 0 && glad.getStamina() < goal) {
                        double toHeal = Math.min(stuff.getMasseur() * 2, goal - glad.getStamina());
                        if (stuff.getMasseur() < 5) toHeal = 0;
                        glad.setStamina(glad.getStamina() + toHeal);
                        stuff.setMasseur((long) ((stuff.getMasseur() * 2 - toHeal) / 2));
                    }
                }
                service.applySpecs();
            }
        }
        return !(needHeal(gladsToGo, true, stuff.getDoctor()) > 0  || needCure(gladsToGo) > 0);
    }

    private void spendPriestFast(int i) {
        Stuff stuff = service.getStuff();
        for (CGlad glad : service.getGlads().values()) {
            long priest = stuff.getPriest();
            long moraled = Math.min(priest / 10, 10 - glad.getMorale());
            if (moraled > 0) {
                stuff.setPriest(priest - moraled * 10);
                glad.setMorale(glad.getMorale() + moraled);
            }
        }

    }

    public boolean restore(boolean needcure) throws Exception {
		service.updateStuff();
		service.updateGlads();
		Stuff stuff = service.getStuff();
		int capable = 0;
		Collection<CGlad> fromRoster = service.getGlads().values();
		for (CGlad cGlad : fromRoster) {
			if (cGlad.getStamina() >= Settings.getCapable() || cGlad.getHits() > 0 && (GladType.Velit.isTypeId(cGlad.getTypeid()) || GladType.Chariot.isTypeId(cGlad.getTypeid()) || GladType.Archer.isTypeId(cGlad.getTypeid()))) capable++;
		}
		if (stuff.getMasseur() < 5 && (stuff.getPriest() < 10 || fullmorale(fromRoster)) && (service.getStuff().getRstSpc() != null && service.getStuff().getRstSpc() > Settings.getSavespec() || service.getStuff().getRstSpc() == null && canUseBonuses() > 0)){// && service.getStuff().getRstSpc() > 0)) {
            spendDoctor(Collections.<CGlad>emptyList(), true);
            spendPriest(0);
			service.restoreSpecs();
			return true;
		}
        if (needcure && (service.getStuff().getRstSpc() != null && service.getStuff().getRstSpc() > Settings.getSavespec() || service.getStuff().getRstSpc() == null && canUseBonuses() > 0)) {
            spendMasseur();
            spendPriest(0);
            service.restoreSpecs();
        }
		return false;
	}

    private boolean fullmorale(Collection<CGlad> fromRoster) {
        for (CGlad cGlad : fromRoster) {
            if (cGlad.getMorale() < 10) return false;
        }
        return true;
    }

    @Override
	public int prepare(List<CGlad> gladsToGo, boolean real, boolean healrets, boolean woodOnly, boolean forceHeal) throws Exception {

        if (!real) return 1;
		LOGGER.info("Preparing battle.. ");
        if (Utils.hppercent(gladsToGo) > 60 && woodOnly && gladsToGo.size() > 2) return 1;

		LOGGER.info(Arrays.deepToString(gladsToGo.toArray()));
		if (Settings.getArmor() ) equipArmor(0L, gladsToGo, Settings.getSilver(), woodOnly);
        service.updateStuff();
		boolean canheal = service.getStuff().getMasseur() >= 5;
		boolean canmorale = service.getStuff().getPriest() >= 10;
		boolean cancure = service.getStuff().getDoctor() >= 10;
		int needheal = needHeal(gladsToGo, healrets, service.getStuff().getDoctor());
		int needmorale = healrets ? needMorale(gladsToGo, healrets) : Settings.getMinmorale();
		int needcure = needCure(gladsToGo);
        int mm = minMorale(gladsToGo);
        int specs = (int) Math.min(service.getStuff().getRstSpc() == null ? 0 : service.getStuff().getRstSpc() , Settings.getUSEBONUSESPERFIGHT());
		LOGGER.info("Need " + (needheal > 0 ? "heal " + needheal : " ") + (needmorale > 0 ? " morale " + needmorale : "") + (needcure > 0 ? " cure " + needcure : ""));
		if (needheal > 2 * (service.getStuff().getMasseur() + specs * 100) ||
            needmorale * 10 > service.getStuff().getPriest() + specs * 100 ||
            needcure * 10 >  service.getStuff().getDoctor()  + specs * 100) {
		    if (!forceHeal) {
                return -1;
            }
		}
		if (cancure && needcure > 0)
			for (CGlad glad : gladsToGo)
				if (glad.getStamina() < 0 && cancure && needCure(Arrays.asList(glad)) > 0)
					cancure = service.cure(glad);
		if (canheal && needheal > 0)
			for (CGlad glad : gladsToGo)
				if (glad.getStamina() < 100d && glad.getStamina() >= 0 && canheal && needHeal(Arrays.asList(glad), healrets, service.getStuff().getDoctor()) > 0) {
                    double goal = (service.getStuff().getDoctor() > 20 && !Utils.ddMutant(glad) && !healrets || Utils.ddMutant(glad) || glad.getTypeid() == GladType.Retiarius.getId() && !healrets) ? Settings.getHealUpTo() : 100d;// ? 100d/glad.getFullhits() : 100d;
					canheal = service.heal(glad, goal);
                }

		if (canmorale && needmorale > 0)
			for (CGlad glad : gladsToGo)
				if (glad.getArmorale() < 10 && canmorale && needMorale(Arrays.asList(glad), healrets) > 0)
					canmorale = service.morale(glad, 10);

		updateGlads(gladsToGo);
        equipArmor(0L, gladsToGo, Settings.getSilver(), woodOnly);

		canheal = service.getStuff().getMasseur() >= 5;
		canmorale = service.getStuff().getPriest() >= 10;
		cancure = service.getStuff().getDoctor() >= 10;

		needheal = needHeal(gladsToGo, healrets, service.getStuff().getDoctor());
		needmorale = healrets ? needMorale(gladsToGo, healrets) : Settings.getMinmorale();
        mm = minMorale(gladsToGo);
		needcure = needCure(gladsToGo);

		if (needheal > 0 && !canheal) {
			spendPriest(0);
			spendDoctor(Collections.<CGlad>emptyList(), false);
		}
		if (needmorale > 0 && !canmorale && (canUseBonuses() > 0 || service.getStuff().getRstSpc() != null)) {
			spendMasseur();
			spendDoctor(Collections.<CGlad>emptyList(), false);
		}
		if (needcure > 0 && !cancure) {
			spendMasseur();
			spendPriest(0);
		}
		if (needheal > 0 || needcure > 0 || mm < Settings.getMinmorale()) {
            boolean restored = restore(needcure > 0 && !cancure);
            if (!restored) return -1;
        }
        //if (!healrets && !canheal && service.getStuff().getRstSpc() == null && canUseBonuses() <= 0) return true;
		return !(needheal > 0 || needcure > 0 || mm < Settings.getMinmorale()) ? 1 : 0;
	}

    public int canUseBonuses() {
        return Math.min((int) service.getBonuses(),  Settings.getUsebonuses() - BonusHistory.used(24 * 60 * 60 * 1000));
    }

    private int minMorale(List<CGlad> gladsToGo) {
        long minmorale = 10;
        for (CGlad cGlad : gladsToGo) {
            if (cGlad.getArmorale() < minmorale) minmorale = cGlad.getArmorale();
        }
        return (int) minmorale;
    }

    public int needHeal(List<CGlad> gladsToGo, boolean healrets, long doctor) {
		int needheal = 0;
		for (CGlad g : gladsToGo) {
            double goal = doctor > 20 && !Utils.ddMutant(g) && !healrets || Utils.ddMutant(g) || (g.getTypeid() == GladType.Retiarius.getId() && !healrets) ? Settings.getHealUpTo() : 100d;//100d/g.getFullhits() : 100d;
            if (g.getStamina() < 0) needheal += goal;
            else if (g.getStamina() < goal) needheal += goal - g.getStamina();
		}
		return needheal;
	}


	public int needMorale(List<CGlad> gladsToGo, boolean moralerets) {
		int needmorale = 0;
		for (CGlad cGlad : gladsToGo) {
			if (cGlad.getArmorale() < Settings.getMinmorale() && !(GladType.Velit.isTypeId(cGlad.getTypeid()) || (GladType.Retiarius.isTypeId(cGlad.getTypeid()) && !moralerets))) needmorale += (Settings.getMinmorale() - cGlad.getArmorale());
            else if (cGlad.getArmorale() < Settings.getMinmorale()) needmorale += Settings.getMinmorale() - cGlad.getArmorale();
		}
		return needmorale;
	}

	public int needCure(List<CGlad> gladsToGo) {
		int needCure = 0;
		for (CGlad cGlad : gladsToGo) {
			if (cGlad.getStamina() < 0) needCure += Math.ceil(((double) -cGlad.getStamina()) / 40);
		}
		return needCure;
	}

	private void updateGlads(List<CGlad> gladsToGo) throws IOException {
		for (CGlad glad : gladsToGo) {
			glad.setStamina(service.getGlads().get(glad.getId()).getStamina());
			glad.setMorale(service.getGlads().get(glad.getId()).getMorale());
		}
	}

	public void equipArmor(Long maxLevel, List<CGlad> glads, final boolean silverOn10Morale, final boolean woodOnly) {
        Set<CArmor> usedArmors = new HashSet<CArmor>();
		for (CGlad g : glads) {
            g.setArmor(null);
            if (g.getTypeid() == GladType.Retiarius.getId() && !silverOn10Morale) continue;
			Collections.sort(g.getArmors(), new ArmorsMoraleComparator(g.getMorale(), silverOn10Morale));
            for (CArmor cArmor : g.getArmors()) {
                boolean woodCheck = !woodOnly || woodOnly && cArmor.getMorale() == 0 || maxLevel == null || maxLevel == 0 || g.getLevel() < maxLevel;
                if (!usedArmors.contains(cArmor) && woodCheck) {
                    g.setArmor(cArmor);
                    usedArmors.add(cArmor);
                    break;
                }
            }
		}
	}

	public void spendMasseur() throws IOException {
		boolean canheal = service.getStuff().getMasseur() >= 5;
		List<CGlad> sorted = new ArrayList<CGlad>(service.getGlads().values());
		Collections.sort(sorted, new GladHpArmoraleComparator());
		for (CGlad glad : sorted) {
			if (glad.getStamina() >= 0 && canheal && glad.getStamina() < 100d) {
				canheal = service.heal(glad, 100d);
			}
		}

	}

	public void spendDoctorFast(final List<CGlad> gladsToGo, boolean cureMutant) throws IOException {
        Stuff stuff = service.getStuff();
		List<CGlad> sorted = new ArrayList<CGlad>(service.getGlads().values());
		Collections.sort(sorted, new Comparator<CGlad>() {
            @Override
            public int compare(CGlad o1, CGlad o2) {
                if (gladsToGo.contains(o1) && !gladsToGo.contains(o2)) return -1;
                if (gladsToGo.contains(o2) && !gladsToGo.contains(o1)) return 1;
                if (o1.getTypeid() == GladType.Horseman.getId() && o2.getTypeid() != GladType.Horseman.getId()) return -1;
                if (o2.getTypeid() == GladType.Horseman.getId() && o1.getTypeid() != GladType.Horseman.getId()) return 1;
                return Double.valueOf(o2.getStamina()).compareTo(o1.getStamina());
            }
        });
		for (CGlad glad : sorted) {
            double injury = calcInj(glad.getStamina());
            long doctor = stuff.getDoctor();
            double cured = 0;
            if (doctor >= Math.ceil(injury) * 10) {
                cured = injury;
            } else  {
                if (doctor >= 10 && injury >= 1) {
                    cured = Math.min(doctor / 10 , (long) injury);
                } else if (injury * 10 < doctor) {
                    cured = injury;
                }
            }
            if (cured > 0) {
                glad.setStamina(glad.getStamina() + cured * 40);
                stuff.setDoctor((long) (doctor - cured * 10));
            }
		}
	}
    public void spendMasseurFast(final List<CGlad> gladsToGo) {
        Stuff stuff = service.getStuff();
        List<CGlad> sorted = new ArrayList<CGlad>(service.getGlads().values());
        Collections.sort(sorted, new Comparator<CGlad>() {
            @Override
            public int compare(CGlad o1, CGlad o2) {
                if (gladsToGo.contains(o1) && !gladsToGo.contains(o2)) return -1;
                if (gladsToGo.contains(o2) && !gladsToGo.contains(o1)) return 1;
                if (o1.getTypeid() == GladType.Horseman.getId() && o2.getTypeid() != GladType.Horseman.getId()) return -1;
                if (o2.getTypeid() == GladType.Horseman.getId() && o1.getTypeid() != GladType.Horseman.getId()) return 1;
                return Double.valueOf(o2.getStamina()).compareTo(o1.getStamina());
            }
        });
        for (CGlad glad : gladsToGo) {
            double goal = Utils.ddMutant(glad) ? Settings.getHealUpTo() : 100d;
            if (glad.getStamina() >= 0 && glad.getStamina() < goal) {
                double toHeal = Math.min(stuff.getMasseur() * 2, goal - glad.getStamina());
                if (stuff.getMasseur() < 5) toHeal = 0;
                glad.setStamina(glad.getStamina() + toHeal);
                stuff.setMasseur((long) ((stuff.getMasseur() * 2 - toHeal)/2));
            }
        }

        for (CGlad glad : sorted) {
            double goal = 100d;
            if (glad.getStamina() >= 0 && glad.getStamina() < goal) {
                double toHeal = Math.min(stuff.getMasseur() * 2, goal - glad.getStamina());
                if (stuff.getMasseur() < 5) toHeal = 0;
                glad.setStamina(glad.getStamina() + toHeal);
                stuff.setMasseur((long) ((stuff.getMasseur() * 2 - toHeal)/2));
            }
        }

    }
    private double calcInj(double stamina) {
        if (stamina < 0) return -stamina / 40;
        else return 0;
    }
	public void spendDoctor(List<CGlad> gladsToGo, boolean cureMutant) throws IOException {
		boolean cancure = service.getStuff().getDoctor() >= 10;
		List<CGlad> sorted = new ArrayList<CGlad>(service.getGlads().values());
        Collections.sort(sorted, new GladHpArmoraleComparator());
        Collections.sort(sorted, new GladTypeComparator(Arrays.asList(GladType.Horseman)));

        for (CGlad glad : gladsToGo) {
			if (glad.getStamina() < 0) {
				cancure = service.cure(glad);
			}
		}
		for (CGlad glad : sorted) {
			if (glad.getStamina() < 0 && cancure && (!Utils.ddMutant(glad) || (cureMutant && glad.getStamina() > -100))) {
				cancure = service.cure(glad);
			}
		}

	}

    @Override
    public void spendPriest(int limit) throws IOException {
        if (service.getStuff().getPriest() <= limit) return;
        List<CGlad> sorted = new ArrayList<CGlad>(service.getGlads().values());
        Collections.sort(sorted, new GladHpArmoraleComparator());
        Collections.sort(sorted, new GladTypeComparator(Arrays.asList(GladType.Horseman)));
        for (CGlad glad : sorted) {
            int canmorale = (service.getStuff().getPriest().intValue() - limit) / 10 ;
            if (canmorale < 0) canmorale = 0;
            if (glad.getMorale() < 8 && canmorale > 0) {
                int newmorale = (int) (glad.getMorale() + canmorale);
                if (newmorale > 8) newmorale = 8;
                service.morale(glad, newmorale);
            }
        }
        int canmorale = (service.getStuff().getPriest().intValue() - limit) / 10 ;
        if (canmorale < 0) canmorale = 0;
        if (canmorale > 0) {
            for (CGlad glad : sorted) {
                canmorale = (service.getStuff().getPriest().intValue() - limit) / 10 ;
                if (canmorale < 0) canmorale = 0;
                if (glad.getArmorale() < 10 && canmorale > 0) {
                    int newmorale = (int) (glad.getMorale() + canmorale);
                    if (newmorale > 10) newmorale = 10;
                    service.morale(glad, newmorale);
                }
            }
        }
    }



    public void spendMasseur(int limit) throws IOException {
		List<CGlad> sorted = new ArrayList<CGlad>(service.getGlads().values());
        Collections.sort(sorted, new GladExpComparator(false));
        Collections.sort(sorted, new BalanceTypeGladComparator(new ArrayList<CGlad>(service.getGlads().values())));
        Collections.sort(sorted, new GladTypeComparator(Arrays.asList(GladType.Horseman)));

        if (service.getStuff().getMasseur() <= limit) return;
		for (CGlad glad : sorted) {
            long canheal = service.getStuff().getMasseur();
            long canspend = canheal - limit;
			if (glad.getStamina() >= 0 && glad.getStamina() <= 90 && canheal >= 5 && canspend > 0) {
			    if (canspend < 5) canspend = 5;
			    long canrestore = canspend * 2;
			    double finalStamina = glad.getStamina() + canrestore;
			    if (finalStamina > 100) finalStamina = 100;
				service.heal(glad, finalStamina);
                break;
			}
		}
	}
}
