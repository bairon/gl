package pp;

import org.apache.log4j.Logger;
import pp.model.Btl;
import pp.model.Fighter;
import pp.model.xml.CGlad;
import pp.service.GlRuService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;

/**
 * Created by alsa on 09.08.2016.
 */
public class ChampGuildActivity extends Activity {

    public static final Logger LOGGER = Logger.getLogger(TournamentActivity.class);
    private static final long MIN = 60 * 1000;
    private static final long HOUR = MIN * 60;
    public long counter;

    private StuffManager stuffManager = new StuffManagerImpl(service);
    private Fighter fighter;


    protected ChampGuildActivity(final GlRuService service) {
        super(service);
        this.fighter = new FighterImpl(service, stuffManager);
    }

    public void doSome() {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

            if (counter++ % 5 == 1) {
                service.updateGlads();
                service.updateStuff();
                stuffManager.spendPriest(80);
                stuffManager.spendMasseur(90);
                for (CGlad glad : service.getGlads().values()) {
                    if (glad.getMorale() < 10 && glad.getStamina() >= TournamentActivity.moneyHealStamina(glad) && glad.getStamina() < 100) {
                        service.healMoney(glad);
                    }
                }
                stuffManager.spendDoctor(Collections.<CGlad>emptyList(), true);
                int hh = calendar.get(Calendar.HOUR_OF_DAY);
                int mm = calendar.get(Calendar.MINUTE);
                if (hh == 1 && (mm >= 50 && mm <= 59) && !TournamentActivity.fullHP(service.getGlads().values()) && (service.getStuff().getRstGld() != null && service.getStuff().getRstGld() > 0)) {
                    service.restoreGlads();
                }
            }
            service.champGuild();
            Btl incomingBtl = service.getIncomingBtl();
            if (incomingBtl != null && incomingBtl.getId() != 0L) {
                service.updateGlads();
                fighter.battle(incomingBtl, null);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utils.sleep(11000);
    }
}
