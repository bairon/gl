package pp;

import hlp.MyHttpClient;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Online {
	public static final String domain = "s2.gladiators.ru";
	public static void main(String[] args) throws IOException {
		MyHttpClient client = new MyHttpClient();
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("settings.txt"));
            client.appendInitialCookie("cookie_lang_3", "rus", props.get("SERVER").toString().trim());
            GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), props.get("SERVER").toString().trim());
            service.login(props.get("USER").toString(), props.get("PASSW").toString());
            Utils.sleep(1000);
			service.visitGuild(10L);
			service.visitGuild(15L);
			while (true) {
				GuildMonitorActivity gm = new GuildMonitorActivity(service);
				gm.doSome();
				Utils.sleep(60000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
