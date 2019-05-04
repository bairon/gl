package pp;

import hlp.MyHttpClient;
import pp.model.HippoHorse;
import pp.model.HippoTour;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class HippoAnalyze {
	public static final String domain = "s2.gladiators.ru";
	public static void main(String[] args) {
		MyHttpClient client = new MyHttpClient();
		client.appendInitialCookie("cookie_lang_3", "rus", domain);
		try {
			int granularity = 1700;
            GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), "s2.gladiators.ru");
            service.loadHippoTours();
			double pot = 0;
			int cnty = 0;
			int totcnty = 0;
			List<HippoTour> hippoTours = new ArrayList<HippoTour>(service.getHippoTours().values());
			for (HippoTour hippoTour : hippoTours) {
				for (HippoHorse hippoHorse : hippoTour.getHorses()) {
					if (hippoHorse.getFactor() > 17d) {
						totcnty++;

					}
				}
				Double factor = hippoTour.getHorses()[hippoTour.getWinner()].getFactor();
				if (factor > 17d) {
					pot += factor;
					cnty++;
				}
			}
			System.out.println(pot);
			System.out.println(cnty);
			System.out.println(totcnty);
			Collections.sort(hippoTours, new Comparator<HippoTour>() {
				@Override
				public int compare(final HippoTour o1, final HippoTour o2) {
					return Long.valueOf(o1.getTimestamp()).compareTo(o2.getTimestamp());
				}
			});
			sortFactorsAscending(hippoTours);
			double [] pots = new double[4];
			int [] wins = new int[4];
			int counter = 0;
			pot = 0;
			for (HippoTour hippoTour : hippoTours) {
				//System.out.println(Arrays.toString(hippoTour.simpleform()));
				counter++;
				pots[hippoTour.getWinner()] += hippoTour.getHorses()[hippoTour.getWinner()].getFactor();
				wins[hippoTour.getWinner()] += 1;
				if (counter == granularity) {
					//System.out.println(counter + " tours.");
					System.out.println(Arrays.toString(pots));
					//System.out.println(Arrays.toString(wins));
					pots[0] = pots[1] = pots[2] = pots[3] = 0d;
					counter = 0;
				}
				pot+= (hippoTour.getWinner() == randomWinner()) ? hippoTour.getHorses()[hippoTour.getWinner()].getFactor() : 0d;
			}
			if (counter > 0) {
				System.out.println(counter + " tours.");
				System.out.println(Arrays.toString(pots));
				//System.out.println(Arrays.toString(wins));
				pots[0] = pots[1] = pots[2] = pots[3] = 0d;
				wins[0] = wins[1] = wins[2] = wins[3] = 0;
				counter = 0;
			}
			double balance = 0d;
			for (HippoTour hippoTour : hippoTours) {
				HippoHorse[] horses = hippoTour.getHorses();
				//System.out.println(horses[0].getFactor() + " - " + horses[1].getFactor() + " - " + horses[2].getFactor() + " - " + horses[3].getFactor() + " - " + hippoTour.getWinner());
				//balance += horses[hippoTour.getWinner()].getFactor() - 4;
				//System.out.println(horses[hippoTour.getWinner()].getFactor() - 4);
			}
			Map<HippoTour, Integer> density = new HashMap<HippoTour, Integer>(hippoTours.size());
			for (HippoTour hippoTour : hippoTours) {
				Integer dens = density.get(hippoTour);
				if (dens == null) {
					dens = 0;
				}
				dens = dens + 1;
				density.put(hippoTour, dens);
			}
			for (HippoTour hippoTour : density.keySet()) {
				System.out.print(hippoTour);
				System.out.println(" " + density.get(hippoTour));
			}
			//System.out.println("Balance:" + balance);
			System.out.println("Random pot:" + pot);
			System.out.println("Tours: " + hippoTours.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}


	}

	private static void sortFactorsAscending(final List<HippoTour> hippoTours) {
		for (HippoTour hippoTour : hippoTours) {
			hippoTour.getHorses()[hippoTour.getWinner()].setWinner(true);
			Arrays.sort(hippoTour.getHorses(), new Comparator() {
				@Override
				public int compare(final Object o1, final Object o2) {
					HippoHorse h1 = (HippoHorse) o1;
					HippoHorse h2 = (HippoHorse) o2;
					return Double.valueOf(h1.getFactor()).compareTo(h2.getFactor());
				}
			});
			for(int i = 0; i < 4; ++i) {
				if (hippoTour.getHorses()[i].getWinner()) {
					hippoTour.setWinner(i);
				}

			}
		}


	}
	private static int randomWinner(double ... k) {

		double r = Math.random();
		if (r < 0.25) return 0;
		if (r < 0.5) return 1;
		if (r < 0.75) return 2;
		return 3;
	}


}
