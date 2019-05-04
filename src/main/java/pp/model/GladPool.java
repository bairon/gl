package pp.model;

import pp.Utils;
import pp.model.comparators.ComparatorProvider;
import pp.model.xml.CGlad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GladPool {
    private boolean botus;
    public long healable = 75;
	private Collection<GladCategory> categories = new ArrayList<GladCategory>();
	private Map<ComparatorProvider, List<CGlad>> gladsByCategories = new HashMap<ComparatorProvider, List<CGlad>>();
    private long chosen;
    private long chosen2;
    private ArrayList<CGlad> glads;


    public GladPool(final long healable, final Collection<CGlad> glads) {
		this.healable = healable;
		initCategories(glads);
	}

	public GladPool(final Collection<CGlad> glads) {
		initHealable(glads);
		initCategories(glads);
	}

    public GladPool(List<CGlad> glads, long chosen) {
        this.chosen = chosen;
        initHealable(glads);
        initCategories(glads);
    }
    public GladPool(List<CGlad> glads, long chosen, long chosen2) {
        this.chosen = chosen;
        this.chosen2 = chosen2;
        initHealable(glads);
        initCategories(glads);
    }

    public GladPool(List<CGlad> gladstogo, boolean botus) {
        this(gladstogo);
        this.botus = botus;
    }

    private void initHealable(Collection<CGlad> glads) {
		long armorale = 0;
		for (CGlad glad : glads) {
			armorale += glad.getArmorale();
		}
		healable = Math.round(10d * armorale / glads.size());

	}

	private void initCategories(Collection<CGlad> glads) {
        this.glads = new ArrayList<CGlad>(glads);
		this.categories = Arrays.asList(GladCategory.values());
		for (ComparatorProvider category : this.categories) {
			List<CGlad> sorted = category.filter(glads);
			gladsByCategories.put(category, sorted);
			Collections.sort(sorted, category.cmpare(Long.valueOf(healable), Long.valueOf(chosen), Long.valueOf(chosen2), Long.valueOf(botus ? 1 : 0)));
			Collections.reverse(sorted);
		}

	}
	public CGlad acquire(final ComparatorProvider category) {
		List<CGlad> glads = gladsByCategories.get(category);
        if (glads == null) {
            glads = category.filter(this.glads);
            gladsByCategories.put(category, glads);
            Collections.sort(glads, category.cmpare(Long.valueOf(healable), Long.valueOf(chosen), Long.valueOf(chosen2), Long.valueOf(botus ? 1 : 0)));
            Collections.reverse(glads);
        }
		return remove(glads.size() > 0 ? glads.get(0) : null);
	}
/*
	public List<CGlad> acquire(final Scheme scheme) {
		List<CGlad> result = new ArrayList<CGlad>();
		for (Slot slot : scheme.getSlots()) {
			result.add(acquire(slot.getCategory()));
		}
		return result;
	}
*/
	private CGlad remove(CGlad g) {
		for (List<CGlad> gladlist : gladsByCategories.values()) {
			if (gladlist.contains(g)) {
				gladlist.remove(g);
			}
		}
		return g;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("GladPool: healable = ").append(healable);
		for (Map.Entry<ComparatorProvider, List<CGlad>> category : gladsByCategories.entrySet()) {
			sb.append("\n").append(category.getKey()).append(":").append(Utils.toString(category.getValue()));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		int count = 130;
		Collection<CGlad> randomGlads = getRandomGlads(count);
		GladPool gp = new GladPool(randomGlads);
		List<CGlad> gladsToGo = SchemeRegistry.horse.iterator().next().fill(gp);
		System.out.println(Utils.toString(gladsToGo));
	}

	private static Collection<CGlad> getRandomGlads(int count) {
		List<CGlad> rglads = new ArrayList<CGlad>();
		for (int i = 0; i < count; ++i) {
			CGlad rg = getRandomGlad();
			rg.setName("g" + i);
			rglads.add(rg);
		}
		return rglads;
	}

	private static CGlad getRandomGlad() {
		CGlad g = new CGlad();
		long stats = 34;
		double av = stats/4;
		g.setLevel(10);
		g.setTypeid(random(1, 11));
		g.setStamina(random(0, 2) == 0 ? 100d : random(0, 100));
		g.setVit(random(Math.round(av-3), Math.round(av + 3))); av = (stats - g.getVit())/3;
		g.setDex(random(Math.round(av - 4), Math.round(av + 4))); av = (stats - g.getVit() - g.getDex())/2;
		g.setAcc(random(Math.round(av-4), Math.round(av + 4))); av = (stats - g.getVit() - g.getDex() - g.getAcc());
		g.setStr(Math.round(av));
		return g;
	}
	private static long random(long from, long to) {
		double r = Math.random();
		return (long) (from + r * (to - from));
	}

    public void setChosen(long chosen) {
        this.chosen = chosen;
    }

    public long getChosen() {
        return chosen;
    }
}
