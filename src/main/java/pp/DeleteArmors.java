package pp;

import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.model.xml.CArmor;
import pp.model.xml.CGlad;
import pp.model.xml.CRoster;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by alsa on 02.12.2014.
 */
public class DeleteArmors {
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
            int index = 0;
                CRoster roster = service.getRoster();
                for (CArmor armor : roster.getArmors().getArmors()) {
                    //if (index > 0) return;

                    if (armor.getMorale() < 5 && armor.getLevel() < 5) {
                        service.dropArmor(armor);
                        index++;
                        Utils.sleep(100);
                    }
                }
            Utils.soutn(index + " бронек удалено.");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
