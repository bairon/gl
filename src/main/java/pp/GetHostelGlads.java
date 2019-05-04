package pp;

import hlp.MyHttpClient;
import pp.model.AModel;
import pp.model.xml.CGlad;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetHostelGlads {
    public static void main(String[] args) {
        MyHttpClient client = new MyHttpClient();
        client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
        GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
        try {
            service.login(Settings.getUser(), Settings.getPassw());
            service.visitMyGladiators();
            Utils.sout("Hostel glads:\n");
            for (AModel hostelGladId : service.getHostelGlads().values()) {
                Utils.sout(hostelGladId.getId() + "\n");
            }
            for (AModel hostelGladId : service.getHostelGlads().values()) {
                service.takeFromHostel(hostelGladId.getId());
                Utils.sleep(500);
            }
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
