package pp;

import hlp.MyHttpClient;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import pp.exception.NotLoggedInException;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.IOException;

public class GiftOfPandorra {
    public static final Logger LOGGER = Logger.getLogger(Tournaments.class);
    public static void main(String[] args) {
        MyHttpClient client = new MyHttpClient();
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        String ip = Settings.getProxyAddress();
        int port = Settings.getProxyPort();
        client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
        GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
        try {
            while (true) {
                try {
                    service.login(Settings.getUser(), Settings.getPassw());
                    break;
                } catch (NotLoggedInException nle) {
                    LOGGER.error("", nle);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                Utils.sleep(1000);
            }
            service.visitMyGladiators();
            TournamentActivity trnm = new TournamentActivity(service);

            while (true) {
                trnm.doSome();
                Utils.sleep(10000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

