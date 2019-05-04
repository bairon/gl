package pp;

import hlp.MyHttpClient;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import pp.exception.NotLoggedInException;
import pp.model.Scheme;
import pp.model.SchemeRegistry;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * Created by alsa on 09.08.2016.
 */
public class ChampGuild {
    public static final Logger LOGGER = Logger.getLogger(ChampGuild.class);

    public static void main(String[] args) throws IOException {
        List<Scheme> schemes = SchemeRegistry.main;
        MyHttpClient client = new MyHttpClient();
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        String ip = Settings.getProxyAddress();
        int port = Settings.getProxyPort();
        if (!Utils.isBlank(ip)) {
            HttpHost proxy = new HttpHost(ip.trim(), port);
            ConnRouteParams.setDefaultProxy(params, proxy);
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
        client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
        client.appendInitialCookie("cookie_lang_2", "rus", Settings.getServer());
        GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
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
            ChampGuildActivity trnm = new ChampGuildActivity(service);

            while (true) {
                trnm.doSome();
                Utils.sleep(10000);
            }
    }
}
