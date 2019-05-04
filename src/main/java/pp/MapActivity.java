package pp;

import pp.model.City;
import pp.model.IModel;
import pp.model.comparators.IdComparator;
import pp.model.enums.Prize;
import pp.service.GlRuService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class MapActivity extends Activity {
	private List<Long> cities;

	public MapActivity(final GlRuService service, final List<Long> cities) {
		super(service);
		this.cities = cities;
	}

	@Override
	void doSome() {
		for (Long city : cities) {
			try {
				service.visitCity(city);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Map<Long,IModel> citiesMap = service.getCities();
		List<IModel> sorted = new ArrayList<IModel>(citiesMap.values());
        for (IModel iModel : citiesMap.values()) {
            if (iModel == null) continue;
            City c = (City) iModel;
            if (c.getPrize() == null) c.setPrize(Prize.NONE);
        }
        Collections.sort(sorted, new Comparator<IModel>() {
			@Override
			public int compare(final IModel o1, final IModel o2) {
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return -1;
                if (o2 == null) return 1;
				City c1 = (City) o1;
				City c2 = (City) o2;
				return ((Integer) c2.getPrize().getWeight()).compareTo(c1.getPrize().getWeight());
			}
		});

		Collections.reverse(sorted);
		System.out.println("-----------------------------------------------------------------------------------------------------------");
		for (IModel iModel : sorted) {
			City c = (City) iModel;
			System.out.println(c);
		}
		completed = true;


	}
}
