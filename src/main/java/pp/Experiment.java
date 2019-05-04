package pp;

import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.model.enums.BattleType;
import pp.model.xml.CGlad;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

public class Experiment {
    public static final Logger LOGGER = Logger.getLogger(Tournaments.class);

    public static void main(String[] args) throws IOException {
        LOGGER.debug(TimeZone.getDefault());
        LOGGER.debug(DateFormat.getInstance().format(new Date()));
        //Utils.sleep(10000);
        MyHttpClient client = new MyHttpClient();
        //client.appendInitialCookie("PHPSESSID", Constants.PHPSESSID, Constants.DOMAIN);
        client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
        GlRuService service = new GlRuServiceImpl(client, new GlRuParser(),Settings.getServer());

        try {
            service.login(Settings.getUser(), Settings.getPassw());
            while (true) {
                System.out.println("Enter id: ");
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferRead.readLine();
                long id = Long.parseLong(s);
                service.updateGlads();
                service.updateStuff();
                Long rstGld = service.getStuff().getRstGld();
                Utils.sleep(1000);
                CGlad g = service.getGlads().get(id);
                service.heal(g, 100d);
                System.out.println("Done.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
