package pp;

import pp.model.Glad;
import pp.model.IModel;
import pp.model.comparators.GladValueComparator;
import pp.service.GlRuService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class RecruitsActivity extends Activity {
	public RecruitsActivity(final GlRuService service) {
		super(service);
	}

	@Override
	void doSome() {
		try {
			List<IModel> recruits = new ArrayList<IModel>();
			service.getMerceneries().clear();
			service.getSlaves().clear();
			service.checkTavern();
			service.checkSlaves();
			recruits.addAll(service.getMerceneries().values());
			recruits.addAll(service.getSlaves().values());
			Collections.sort(recruits, new GladValueComparator());
			//Utils.print(recruits);
			System.out.println("Slots" + service.getFreeSlots());
			System.out.println("Best recruit:");
			Glad bestRecruit = (Glad) recruits.get(0);
			System.out.println(bestRecruit);
			System.out.println("----------------------------------------------------------");
			service.visitMyGladiators();
			Map<Long, Glad> myGlads = service.getMyGlads();
			List<IModel> sortedGlads = new ArrayList<IModel>();
			sortedGlads.addAll(myGlads.values());
			Collections.sort(sortedGlads, new GladValueComparator());
			Collections.reverse(sortedGlads);
			Glad lastGlad = (Glad) sortedGlads.get(0);
			if (service.getFreeSlots() > 0) {
				if (bestRecruit.getValue() < 0) {
					System.out.println("Buying...");
					try {
						service.buy(bestRecruit);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
				for (IModel iModel : sortedGlads) {
					Glad glad = (Glad) iModel;
					System.out.println("Worst glad:" + glad);
					if (bestRecruit.getValue() < glad.getValue()) {
						try {
							System.out.println("Replacing...");
							service.sell(glad);
							service.buy(bestRecruit);
							break;
						} catch (Exception e) {
							System.out.println("Problems with sell / buy" + e.getMessage());
							e.printStackTrace();
						}
					} else {
						break;
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
