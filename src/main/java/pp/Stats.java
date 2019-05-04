package pp;

import hlp.MyHttpClient;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Stats {
	public static final String domain = "s2.gladiators.ru";
	public static void main(String[] args) throws IOException {
		MyHttpClient client = new MyHttpClient();
        try {
			String inTur = "";
            Properties props = new Properties();
            props.load(new FileReader("settings.txt"));
            client.appendInitialCookie("cookie_lang_3", "rus", props.get("SERVER").toString().trim());
            GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), props.get("SERVER").toString().trim());

            service.login(props.get("USER").toString(), props.get("PASSW").toString());
            Utils.sleep(1000);
			//service.visitGuild(10L);
			//service.visitGuild(15L);
			//service.visitGuild(5L);
			//service.visitGuild(6L);
			//service.visitGuild(3L);
			//service.visitGuild(12L);
			//service.visitGuild(2L);
			//service.visitGuild(7L);
			//service.visitGuild(18L);
			//service.visitGuild(16L);
			//service.visitGuild(17L);
			//service.visitGuild(20L);
			//service.visitGuild(11L);
			//service.visitGuild(9L);
			//service.visitGuild(21L);
			//service.visitGuild(19L);
			//service.visitGuild(22L);
			while (true) {
				TournamentReporterActivity trnm = new TournamentReporterActivity(service, inTur);
				trnm.doSome();
				Utils.sleep(60000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
