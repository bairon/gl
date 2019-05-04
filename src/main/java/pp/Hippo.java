package pp;

import hlp.MyHttpClient;
import pp.model.HippoHorse;
import pp.model.HippoTour;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Hippo {
	public static final String domain = "s2.gladiators.ru";
	public static BetPlacer factorBetPlacer = new BetPlacer() {
		@Override
		public int horse(final HippoHorse[] horses) {
			int factorNumber = 0;
			double [] factors = new double[horses.length];
			for (int i = 0; i < horses.length; ++i) {
				factors[i] = horses[i].getFactor();
			}
			Arrays.sort(factors);
			for (int i = 0; i < horses.length; ++i) {
				if (factors[factorNumber] == horses[i].getFactor()) {
					return i;
				}
			}
			return 0;
		}

		@Override
		public int bet(final HippoHorse[] horses) {
			return 5000;
		}
	};
    public static BetPlacer luckBetPlacer = new BetPlacer() {
        @Override
        public int horse(HippoHorse[] horses) {
            return horses[3].getFactor() == 9 || horses[3].getFactor() == 18 ? 3 : 0;
        }

        @Override
        public int bet(HippoHorse[] horses) {
            return horses[3].getFactor() == 9 || horses[3].getFactor() == 18 ? 50000 : 100;
        }
    };
	public static void main(String[] args) {
        MyHttpClient client = new MyHttpClient();
        client.appendInitialCookie("cookie_lang_3", "rus", domain);
        GlRuService service = null;
        try {
            Properties props = new Properties();
            service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
            service.login(Settings.getUser(), Settings.getPassw());
            Utils.sleep(1000);
            service.hippoLogin();
            Utils.sleep(1000);
            service.loadHippoTours();
            for (int i = 0; i < 17; ++i) {
                HippoTour ht = service.hippoTour(luckBetPlacer);
                Utils.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.saveHippoTours();
        }


    }

}
