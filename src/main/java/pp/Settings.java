package pp;

import org.apache.log4j.Logger;
import pp.model.SchemeRegistry;
import pp.model.enums.InvitationType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

public class Settings {
    public static final Logger LOGGER = Logger.getLogger(Settings.class);

    private static Properties props;

    static {
        try {
            init();
        } catch (IOException e) {
            Logger.getLogger(Settings.class).error("", e);
        }
    }

    private static boolean USEINVITATIONS;

    public static void init() throws IOException {
        props = new Properties();
        props.load(new InputStreamReader(new FileInputStream("settings.txt")));
    }

    private static String get(String key) {
        if (props == null) {
            LOGGER.warn("Settings loading FAILED!");
            return null;
        }
        String value = props.getProperty(key);
        if (value == null) {
            //LOGGER.warn("Value " + key + " NOT FOUND!");
            return null;
        }
        return value;
    }

    public static String getUser() {
        return get("USER");
    }
    public static String getPassw() {
        return get("PASSW");
    }
    public static String getServer() {
        return get("SERVER");
    }
    public static boolean getPrize() {
        String prize = get("PRIZE");
        return Utils.isBlank(prize) ? false : Boolean.parseBoolean(prize);
    }
    public static boolean getSenate() {
        String senate = get("SENATE");
        return Utils.isBlank(senate) ? false : Boolean.parseBoolean(senate);
    }
    public static boolean getImper() {
        return Boolean.parseBoolean(get("IMPER"));
    }
    public static boolean getMixed() {
        return Boolean.parseBoolean(get("MIXED"));
    }
    public static boolean getPlebey() {
        return Boolean.parseBoolean(get("PLEBEY"));
    }
    public static boolean getNovice() {
        return Boolean.parseBoolean(get("NOVICE"));
    }
    public static int getUsebonuses() {
        return Integer.parseInt(get("USEBONUSES"));
    }
    public static int getMinmorale() {
        String minmorale = get("MINMORALE");
        return Utils.isBlank(minmorale) ? 0 : Integer.parseInt(minmorale);
    }
    public static boolean getSilver() {
        String silver = get("SILVER");
        return Utils.isBlank(silver) ? false : Boolean.parseBoolean(silver);
    }
    public static double getCapable() {
        String capable = get("CAPABLE");
        return Utils.isBlank(capable) ? 0d : Double.parseDouble(capable);
    }
    public static int getRestoreglads() {
        String restoreglads = get("RESTOREGLADS");
        return Utils.isBlank(restoreglads) ? 0 : Integer.parseInt(restoreglads);
    }
    public static double getJoinon() {
        String joinon = get("JOINON");
        return Utils.isBlank(joinon) ? 0d : Double.parseDouble(joinon);
    }
    public static boolean getGuild1x1() {
        String guild1x1 = get("GUILD1x1");
        return Utils.isBlank(guild1x1) ? false : Boolean.parseBoolean(guild1x1);
    }
    public static int getMoneyheal() {
        String moneyheal = get("MONEYHEAL");
        return Utils.isBlank(moneyheal) ? 0 : Integer.parseInt(moneyheal);
    }

    public static boolean getPlay() {
        String playsound = get("PLAYSOUND");
        return Utils.isBlank(playsound) ? false : Boolean.parseBoolean(playsound);
    }

    public static boolean getFight() {
        String fight = get("FIGHT");
        return Utils.isBlank(fight) ? false : Boolean.parseBoolean(fight);
    }

    public static int getSavespec() {
        String savespec = get("SAVESPEC");
        return Utils.isBlank(savespec) ? 0 : Integer.parseInt(savespec);
    }
    public static int getSaveTroop() {
        String savetroop = get("SAVETROOP");
        return Utils.isBlank(savetroop) ? 0 : Integer.parseInt(savetroop);
    }

    public static String getSliv() {
        return get("SLIV");
    }
    public static String getSlivScheme() {
        return get("SLIVSCHEMA");
    }

    public static String getOpponent() {
        return get("OPPONENT");
    }
    public static int getMaxLevel() {
        String maxlevel = get("MAXLEVEL");
        return Utils.isBlank(maxlevel) ? 0 : Integer.parseInt(maxlevel);
    }

    public static boolean getMGT() {
        String mgt = get("MGT");
        return Utils.isBlank(mgt) ? false : Boolean.parseBoolean(mgt);
    }

    public static boolean getKachHorse() {
        String kach_horse = get("KACH_HORSE");
        return Utils.isBlank(kach_horse) ? false : Boolean.parseBoolean(kach_horse);
    }
    public static int getHealUpTo() {
        String healUpTO = get("HEALUPTO");
        return Utils.isBlank(healUpTO) ? 10 : Integer.parseInt(healUpTO);
    }

    public static int getUSEBONUSESPERFIGHT() {
        String usebonusesperfight = get("USEBONUSESPERFIGHT");
        return Utils.isBlank(usebonusesperfight) ? 1 : Integer.parseInt(usebonusesperfight);

    }

    public static String getProxyAddress() {
        return get("PROXY_ADDRESS");
    }
    public static int getProxyPort() {
        String proxy_port = get("PROXY_PORT");
        return Utils.isBlank(proxy_port) ? 80 : Integer.parseInt(proxy_port);
    }

    public static int getDelay() {
        String delay = get("DELAY");
        return Utils.isBlank(delay) ? 1 : Integer.parseInt(delay);
    }

    public static InvitationType getUSEINVITE() {
        String useInvitations = get("USEINVITE");
        if (useInvitations == null) useInvitations = "true";
        return InvitationType.valueOf(useInvitations.toUpperCase());
    }

    public static boolean getArmor() {
        String armor = get("ARMOR");
        return Utils.isBlank(armor) ? false : Boolean.parseBoolean(armor);
    }

    public static String get1x1() {
        return get("1x1");
    }

    public static String getSpecial() {
        String special = get("SPECIAL");
        return special == null ? "" : special;
    }

    public static long getJoinup() {
        String joinup = get("JOINUP");
        return Utils.isBlank(joinup) ? -1 : Long.parseLong(joinup);

    }

    public static double getPauseDuration() {
        String pauseDuration = get("PAUSEDURATION");
        return Utils.isBlank(pauseDuration) ? 0 : Double.parseDouble(pauseDuration);
    }
    public static int getPauseFrom() {
        String pauseFrom = get("PAUSEFROM");
        return Utils.isBlank(pauseFrom) ? 0 : Integer.parseInt(pauseFrom);
    }
}
