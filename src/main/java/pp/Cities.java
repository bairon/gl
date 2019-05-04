package pp;

import hlp.MyHttpClient;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Cities {
	public static final String domain = "s2.gladiators.ru";
	public static void main(String[] args) {
		MyHttpClient client = new MyHttpClient();
		//client.appendInitialCookie("PHPSESSID", "66bc7c257e046e05519add1b7b1382f3", domain);
		client.appendInitialCookie("cookie_lang_3", "rus", domain);
		try {
            Properties props = new Properties();
            props.load(new FileInputStream("settings.txt"));
            GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), props.get("SERVER").toString().trim());
            service.login(props.get("USER").toString(), props.get("PASSW").toString());
            Utils.sleep(1000);
			service.visitGuild(10L);
			//service.visitGuild(15L);

			while (true) {
				MapActivity map = new MapActivity(service, cities());// Arrays.asList(161L, 160L, 162L, 78L, 77L, 75L, 76L, 73L, 74L, 64L, 72L, 71L, 69L, 70L, 65L, 66L, 68L, 67L, 168L));
				//MapActivity map = new MapActivity(service, Arrays.asList(161L, 160L, 162L, 78L, 77L, 75L, 76L));
				map.doSome();
				Utils.sleep(60000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

    private static List<Long> cities() {
        ArrayList<Long> cities = new ArrayList<Long>();
        for (long i = 1; i < 171;  ++i)
            cities.add(i);
        return cities;
    }

}
