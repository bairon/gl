package pp;

import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.model.Fighter;
import pp.model.Opponent;
import pp.model.Trnm;
import pp.model.enums.BattleType;
import pp.model.xml.CGlad;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Training {
    public static final Logger LOGGER = Logger.getLogger(Morale.class);
    private static GlRuService service;

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("settings.txt"));
        MyHttpClient client = new MyHttpClient();
        client.appendInitialCookie("cookie_lang_3", "rus", props.get("SERVER").toString().trim());
        service = new GlRuServiceImpl(client, new GlRuParser(), props.get("SERVER").toString().trim());
        service.login(props.get("USER").toString(), props.get("PASSW").toString());
        StuffManager stuffManager = new StuffManagerImpl(service);
        Fighter fighter = new FighterImpl(service, stuffManager);
        service.visitMyGladiators();
        while (true) {
            try {
                service.updateGlads();
                Map<Long, CGlad> glads = service.getGlads();
                List<CGlad> capable = getCapableGlads(glads.values());
                Opponent opponent = service.arena(BattleType.DUEL.getId());
                if (service.getBttls().size() > 0) {
                    fighter.battle(service.getBttls().values().iterator().next(), new Trnm());
                } else {
                    if (opponent == null) {
                        if (capable.size() > 0) {
                            int gladLevel = getMaxGladsLevel(capable);
                            service.arena(BattleType.DUEL, 1, gladLevel, gladLevel * 2, 3, "1,2,3,4,5,6,7");
                        }
                    } else {
                        if (opponent.id > 0) {
                            service.arenaAccept();
                        }
                    }
                }
                Utils.sleep(20000);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }

    private static List<CGlad> getCapableGlads(Collection<CGlad> glads) {
        List<CGlad> result = new ArrayList<CGlad>();
        for (CGlad glad : glads) {
            if (glad.getTypeid() < 8 && glad.getHits() > glad.getFullhits() * 0.8) result.add(glad);
        }
        return result;
    }

    private static int getMaxGladsLevel(Collection<CGlad> glads) {
        int max = 0;
        for (CGlad glad : glads) {
            if (glad.getLevel() > max) max = (int) glad.getLevel();
        }
        return max;
    }

}
