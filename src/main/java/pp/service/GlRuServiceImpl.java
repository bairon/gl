package pp.service;

import noNamespace.Horse;
import noNamespace.Horses;
import noNamespace.ResponseDocument;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import pp.AModelFactory;
import pp.BetPlacer;
import pp.BonusHistory;
import pp.BtlModelFactory;
import pp.GlRuParser;
import pp.GladModelFactory;
import pp.MergingStrategy;
import hlp.MyHttpClient;
import pp.ParserMarker;
import pp.Settings;
import pp.StuffModelFactory;
import pp.TrnmModelFactory;
import pp.Utils;
import pp.exception.NotLoggedInException;
import pp.model.AModel;
import pp.model.Application;
import pp.model.ApplicationModelFactory;
import pp.model.Armor;
import pp.model.ArmorModelFactory;
import pp.model.Btl;
import pp.model.Challenge;
import pp.model.ChallengeModelFactory;
import pp.model.City;
import pp.model.Glad;
import pp.model.Guild;
import pp.model.HippoHorse;
import pp.model.HippoTour;
import pp.model.IModel;
import pp.model.ModelFactoryImpl;
import pp.model.Opponent;
import pp.model.Player;
import pp.model.Position;
import pp.model.PositionModelFactory;
import pp.model.Stuff;
import pp.model.Trnm;
import pp.model.comparators.ArmorsMoraleComparator;
import pp.model.enums.BattleType;
import pp.model.enums.GladType;
import pp.model.enums.Prize;
import pp.model.enums.TournamentState;
import pp.model.enums.TournamentType;
import pp.model.xml.CArmor;
import pp.model.xml.CGlad;
import pp.model.xml.CRoster;
import pp.model.xml.Cxml;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Exception;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GlRuServiceImpl implements GlRuService {
    public static final long UID = 20040933L;
    public static final Logger LOGGER = Logger.getLogger(GlRuServiceImpl.class);
    public static final String MY_OFFICE_URI = "";
    public static final String MY_GLADS = "/xml/gladiators/";
    public static final String TRAIN = "/xml/gladiators/train.php";
    public static final String RECOVERY = "/xml/gladiators/recovery.php";
    public static final String ARMORY = "/xml/gladiators/armor.php";
    public static final String TRNMS = "/xml/arena/tournaments.php&typeid=9";
    public static final String TRNMS_JOINED = "/xml/arena/tournaments.php";
    public static final String BUILDER = "/builder/?id=";
    public static final String ROSTER = "/builder/get_xml_roster.php";
    public static final String SEND = "/builder/send.php";
    public static final String FIGHT = "/xml/arena/fight.php?id=";
    public static final String HEAL = "/xml/gladiators/recovery.php?type=gladiators/recovery&act=select";
    public static final String HEAL_MONEY = "/xml/gladiators/info.php?act=recovery_glad&rpage=1&id=";
    public static final String TRN_JOIN = "/xml/arena/tournaments.php?act=join&step=1&id=";
    public static final String TRN_LEAVE = "/xml/arena/tournaments.php?act=cancel&step=1&id=";
    private static final String TRNM_USE_TICKET = "/xml/arena/tournaments.php?act=useticket&id=";
    public static final String MAP = "/xml/politics/map.php";
    public static final String GUILDS = "/xml/politics/guilds.php";
    public static final String GUILD = "/guilds/";
    public static final String SENATES = "/xml/arena/tournaments.php?type=arena/tournaments&act=archive&typeid=3&return=1&page=";
    public static final String senatesSer = "D:/Projects/gl/a.ser";
    public static final String hippoSer = "D:/Projects/gl/hippo.ser";
    public static final String TRNM_INFO = "/xml/arena/tournaments.php?id=";
    public static final String TAVERN = "/xml/city/tavern.php";
    public static final String SLAVES = "/xml/city/slaves.php";
    public static final String MAIL = "/xml/residence/mail.php?type=residence/mail&act=write";
    public static final String TOURNAMENTS_URL_BASE = "/xml/arena/tournaments.php";
    public static final String DUES = "/guild/index.php?type=finances/dues_history&page=";
    public static final String DELETE_DUE = "/guild/index.php?act=delete&step=1&type=finances/dues_history&id=";
    public static Map<Long, String> armorlevels = parseArmorlevels("1:1:9;2:10:19;3:20:29;4:30:39;5:40:100;");
    public static final String ARENA_ACT = "/xml/arena/arena.php?type=arena/arena&act=";
    public static final String ARENA = "/xml/arena/arena.php?typeid=";
    public static final String ARENA_CANCEL = "/xml/arena/arena.php?type=arena/arena&act=my";
    public static final String ARENA_ACCEPT = "/xml/arena/arena.php?approve=1";
    public static final String ARENA_REJECT = "/xml/arena/arena.php?cancel=1&typeid=1";
    public static final String ARENA_CHALLENGE = "/xml/arena/arena.php?act=battles&step=1";//&typeid=1&id=12464572";
    public static final String ARENA_BOT = "/xml/arena/arena.php?typeid=";
    private static final String CHALLENGES = "/xml/arena/challenges.php";
    private static final String CHALLENGE = "/xml/arena/challenges.php?id=";
    private static final String POSITION_ATTACK = "/xml/arena/challenges.php?act=attack&step=1";
    private static final String POSITION_TAKE = "/xml/arena/challenges.php?act=place&step=1";
    private static final String TAKE_ARMOR = "/xml/gladiators/armor.php?type=gladiators/armor&act=getarmor";
    private static final String BUY_ARMOR = "/xml/gladiators/armor.php?type=gladiators/armor&act=buy";
    private static final String DROP_ARMOR = "/xml/gladiators/armor.php?type=gladiators/armor&act=drop";
    private static final String MY_ARMORS = "/xml/gladiators/armor.php?act=my";
    private static final String CHAMP_GUILD = "/xml/arena/gd_champs.php";

    public static final DecimalFormat df = new DecimalFormat("#.##");
    public static final int DOUBLE_DECIMAL_PRECISION = 2;
    public String hostprefix;

    // =========== My office =================

    // ============ MY GLADS =================
    private Map<Long, Glad> myGlads = new HashMap<Long, Glad>();
    private static final String TAKE_FROM_HOSTEL = "/xml/gladiators/index.php?act=takefromhotel&gladiator=";

    public Map<Long, AModel> getHostelGlads() {
        return hostelGlads;
    }

    private Map<Long, AModel> hostelGlads = new HashMap<Long, AModel>();
    private Map<Long, CGlad> glads = new HashMap<Long, CGlad>();

    // ============= STUFF =====================
    private Map<Long, Stuff> stuff = new HashMap<Long, Stuff>();
    // ============ MAP =======================
    private City current;
    private Map<Long, IModel> cities = new HashMap<Long, IModel>();

    // ================= GUILDS ===================
    private Map<Long, Guild> guilds = new HashMap<Long, Guild>();

    protected static final ResponseHandler<String> responseHandler = new BasicResponseHandler();
    private MyHttpClient client;
    private GlRuParser parser;

    // ================= SENATES ==================
    private Map<Long, Trnm> senates = new HashMap<Long, Trnm>();
    private static final String CITY = "/xml/politics/map.php?act=city&id=";

    // ================= SLAVES =====================
    private Map<Long, IModel> slaves = new HashMap<Long, IModel>();

    // ================= TAVERN =====================
    private Map<Long, IModel> merceneries = new HashMap<Long, IModel>();
    private long freeSlots;

    // ================ Hippodrom ======================
    private HashMap<Long, HippoTour> hippoTours = new HashMap<Long, HippoTour>();

    // ================= Tournaments =======================
    private Map<Long, Trnm> tournaments = new HashMap<Long, Trnm>();

    private Map<Long, Btl> bttls = new HashMap<Long, Btl>();
    private static final boolean USE_STUBS = false;
    public static final String BUILDER_STUB = "D:\\Projects\\gl\\response-samples\\builder-new.html";
    public static final String RECOVERY_STUB = "D:\\Projects\\gl\\response-samples\\recovery.html";
    private static final int EXECUTE_DELAY = 2500;

    private static int[] expTable = new int[]{120, 260, 420, 610, 840, 1110, 1430, 1810, 2250, 2770, 3390, 4120, 4980, 5990, 7190, 8600, 10270,
            12240, 14560, 17300, 20530, 24340, 28840, 34150, 40420, 47810, 56540, 66840, 78990, 93330, 110250, 130210, 153770, 181570, 214370
            , 253070, 298740, 352630, 416220, 491260};
    public int myguild;
    public int dualguild;
    private Map<Long, Application> applications = new HashMap<Long, Application>();
    private double bonuses;
    private long denaries;
    private Map<Long, Challenge> challenges = new HashMap<Long, Challenge>();
    private Map<Long, Position> positions = new HashMap<Long, Position>();
    private long serverClientDateDifference = 0;
    private String captchaUrl = null;
    private static final boolean IGNORE_CAPTCHA = false;
    private byte[] captcha = null;

    public GlRuServiceImpl(final MyHttpClient client, final GlRuParser parser, final String hostname) {
        this.parser = parser;
        this.client = client;
        this.hostprefix = "http://" + hostname;
    }

    private String execute(HttpUriRequest request, List<BasicNameValuePair> formparams) throws IOException {
        String response = null;
        try {
            if (formparams != null) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
                ((HttpPost) request).setEntity(entity);
            }
            LOGGER.debug("Sending " + request.getURI() + " formparams: " + (formparams == null ? "no" : Arrays.deepToString(formparams.toArray())));
            Utils.soutn("Sending " + request.getURI() + " formparams: " + (formparams == null ? "no" : Arrays.deepToString(formparams.toArray())));
            long clientTIme = System.currentTimeMillis();
            response = client.execute(request, responseHandler);
            long serverTime = Utils.dateToTimestamp(Utils.between(response, "new Date(", ")"));
            if (serverTime > 0) {
                serverClientDateDifference = serverTime - clientTIme;
            }
            //LOGGER.debug(serverClientDateDifference);
        } catch (ClientProtocolException e) {
            Utils.soutn(e.getMessage());
            if ("Found".equals(e.getMessage())) return "";
        } catch (IOException e) {
            Utils.soutn(e.getMessage());
        }
        Utils.sleep(EXECUTE_DELAY);
        checkLoggedIn(response);
        return response;
    }

    protected void checkBattle(String page) {
        if (Utils.isBlank(page)) return;
        ParserMarker[] markers = new ParserMarker[]{
                ParserMarker.BTL_START,
                ParserMarker.BTL_OPP_GUILD,
                ParserMarker.BTL_FIGHT_ID,
                ParserMarker.BTL_CHECK,
                ParserMarker.BTL_FIGHT_DONE_ID,
                ParserMarker.BTL_FIGHT,
                ParserMarker.BTL_BUILDER_ID,
                ParserMarker.BTL_BUILDER_ID2,
                ParserMarker.BTL_PREPARE,
                ParserMarker.BTL_PREPARE2,
                ParserMarker.BTL_BACK,
                ParserMarker.BTL_TIMEOUT,
                ParserMarker.BTL_END};
        parser.parsePage(page, markers, new BtlModelFactory(), MergingStrategy.REPLACE, bttls, true);
        //LOGGER.info("..");
    }

    private String execute(HttpUriRequest request) throws IOException {
        return execute(request, null);
    }

    private void checkLoggedIn(final String response) {
        //if (response == null) return;
        if (response != null && response.contains("Вход в игру")) {
            captchaUrl = response.contains("Код на картинке: <br><img src=\"") ? Utils.between(response, "Код на картинке: <br><img src=\"", "\"") : null;
            try {
                URLConnection urlConnection = new URL(this.hostprefix + captchaUrl).openConnection();
                captcha = IOUtils.toByteArray(urlConnection.getInputStream());
            } catch (IOException e) {
                LOGGER.error(e);
            }
            throw new NotLoggedInException(response.contains("Неправильный код"), response.contains("Пользователь с таким логином не найден"), response.contains("Неправильный пароль"));
        }
        if (response == null) return;
        try {
            String dinariiStr = Utils.between(response, "'Финансы'>", "</a>");
            dinariiStr = dinariiStr.replace(".", "");
            if (!Utils.isBlank(dinariiStr))
                this.denaries = Long.parseLong(dinariiStr);
        } catch (NumberFormatException e) {
            LOGGER.error("Dinaries parse fail", e);
        }
        try {
            String bonusesStr = Utils.between(response, "сенаторские)'>", "+");
            if (!Utils.isBlank(bonusesStr)) {
                if (bonusesStr.length() > 5) bonusesStr = bonusesStr.substring(0, 5);
                this.bonuses = Double.parseDouble(bonusesStr);
            }
        } catch (NumberFormatException e) {
            String bonusesStr = Utils.between(response, "сенаторские)'>", "<");
            if (!Utils.isBlank(bonusesStr)) {
                if (bonusesStr.length() > 5) bonusesStr = bonusesStr.substring(0, 5);
                this.bonuses = Double.parseDouble(bonusesStr);
            }
        }

    }

    @Override
    public Btl getIncomingBtl() {
        if (bttls.size() > 0) {
            return bttls.values().iterator().next();
        } else return null;
    }

    @Override
    public void visitMyOffice() throws IOException {
        HttpGet myOfficeGet = new HttpGet(this.hostprefix + MY_OFFICE_URI);
        String response = execute(myOfficeGet);
/*
        int trnmstart = response.indexOf("Вы заявлены в турнир");
		int trnmend = response.indexOf("Мой кабинет");
		if (trnmstart != -1 && trnmend != -1) {
			String trnmHtml = response.substring(trnmstart, trnmend);
			int idstart = trnmHtml.indexOf("/xml/arena/tournaments.php?id=");
			int idend = trnmHtml.indexOf("\"", idstart);
			long id = Long.parseLong(trnmHtml.substring(idstart, idend));
			participatingTournament = id;
		} else {
			participatingTournament = 0;
		}
*/
    }


    @Override
    public void visitMyGladiators() throws IOException {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpGet myGladsRequest = new HttpGet(this.hostprefix + MY_GLADS);
        String response = execute(myGladsRequest);
        ParserMarker[] markers = new ParserMarker[]{ParserMarker.GL_TYPE, ParserMarker.GL_ID, ParserMarker.GL_NAME, ParserMarker.GL_LVL, ParserMarker.GL_STATUS,
                ParserMarker.GL_HP, ParserMarker.GL_MAX_HP, ParserMarker.GL_AGE, ParserMarker.GL_TAL, ParserMarker.GL_VIT, ParserMarker.GL_DEX, ParserMarker.GL_ACC,
                ParserMarker.GL_STR, ParserMarker.GL_PRICE};
        parser.parsePage(response, markers, new GladModelFactory(), MergingStrategy.MERGE, myGlads, true);
        markers = new ParserMarker[]{ParserMarker.TAKE_FROM_HOSTEL};
        parser.parsePage(response, markers, new AModelFactory(), MergingStrategy.REPLACE, hostelGlads, false);
        int guildstart = response.indexOf("/guilds/");
        if (guildstart > 0) {
            int guildend = response.indexOf('>', guildstart + "/guilds/".length());
            if (guildend > 0) {
                String guild = response.substring(guildstart + "/guilds/".length(), guildend);
                try {
                    this.myguild = Integer.parseInt(guild);
                } catch (NumberFormatException e) {
                    System.out.println("No guild");
                }
            }
        }
        if (this.myguild > 0) {
            String guildresponse = visitGuild(this.myguild);
            if (guildresponse == null) return;
            int dualguildstart = guildresponse.indexOf("Рекрут-гильдия");
            if (dualguildstart <= 0)
                dualguildstart = guildresponse.indexOf("Родительская гильдия");
            if (dualguildstart > 0) {
                dualguildstart = guildresponse.indexOf("/guilds/", dualguildstart);
                if (dualguildstart > 0) {
                    int dualguildend = guildresponse.indexOf('>', dualguildstart);
                    if (dualguildend > 0) {
                        String dualguild = guildresponse.substring(dualguildstart + "/guilds/".length(), dualguildend);
                        this.dualguild = Utils.findNumber(dualguild).intValue();
                        visitGuild(this.dualguild);
                    }
                }
            }
        }

    }

    @Override
    public void visitTraining() {
    }

    @Override
    public void visitRecovery() throws IOException {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        HttpGet getRecovery = new HttpGet(this.hostprefix + RECOVERY);
        String responseBody = USE_STUBS ? readFileAsString(RECOVERY_STUB) : execute(getRecovery);
        for (Glad glad : myGlads.values()) {
            glad.setHealth(null);
        }
        ParserMarker[] markers = new ParserMarker[]{ParserMarker.GL_TYPE, ParserMarker.GL_ID,
                ParserMarker.GL_INJURED, ParserMarker.GL_HP_PERCENT,
                ParserMarker.GL_PREDMORALE, ParserMarker.GL_MORALE,
                ParserMarker.GL_PREDINJURY, ParserMarker.GL_INJURY, ParserMarker.GL_END};
        parser.parsePage(responseBody, markers, new GladModelFactory(), MergingStrategy.MERGE, myGlads, true);
        parseStuff(responseBody);
        for (Glad glad : myGlads.values()) {
            for (CGlad cGlad : glads.values()) {
                if (glad.getId().equals(cGlad.getId())) {
                    if (glad.getInjury() > 0) {
                        glad.setHealth(0L);
                        glad.setHpPercent(-glad.getInjury() * 40);
                    }
                    cGlad.setStamina(glad.getHpPercent());
                    cGlad.setMorale(glad.getMorale());
                }
            }
        }
    }

    public void visitArmory() throws IOException {
        execute(new HttpGet(ARMORY));
    }

    private void parseStuff(String responseBody) {
        ParserMarker[] stuffMarkers = new ParserMarker[]{
                ParserMarker.STF_SINGLE,
                ParserMarker.STF_MASSEUR_MRK,
                ParserMarker.STF_MASSEUR,
                ParserMarker.STF_PRIEST_MRK,
                ParserMarker.STF_PRIEST,
                ParserMarker.STF_DOCTOR_MRK,
                ParserMarker.STF_DOCTOR,
                ParserMarker.STF_RST_GLD,
                ParserMarker.STF_RST_SPC
        };
        parser.parsePage(responseBody, stuffMarkers, new StuffModelFactory(), MergingStrategy.REPLACE, stuff, true);
    }

    @Override
    public void visitTournaments(final TournamentType type, final TournamentState state) throws IOException {
        tournaments.clear();
        HttpGet tournamentsGet = new HttpGet(this.hostprefix + TOURNAMENTS_URL_BASE + "?typeid=" + type.getPath() + "&act=" + state.getPath());
        String response = execute(tournamentsGet);
        //response = FileUtils.readFileToString(new File("D:/Projects/gl/response"));
        ParserMarker[] markers = new ParserMarker[]{ParserMarker.TRN_ID, ParserMarker.TRNM_NAME, ParserMarker.TRN_DATE, ParserMarker.TRN_LVL_TO2, ParserMarker.TRN_LVL_FROM, ParserMarker.TRN_LVL_TO, ParserMarker.TRN_PRIZE,
                ParserMarker.TRN_MEMBERS, ParserMarker.TRN_MEMBERS_MAX, ParserMarker.TRN_CAN_JOIN, ParserMarker.TRN_CAN_LEAVE, ParserMarker.TRN_CAN_USE_TICKET, ParserMarker.TRN_END_MARKER};
        parser.parsePage(response, markers, new TrnmModelFactory(), MergingStrategy.REPLACE, tournaments, true);
        checkBattle(response);
        if (tournaments.isEmpty()) {
            String startidmarker = "document.location.href = '/xml/arena/tournaments.php?id=";
            int startid = response.indexOf(startidmarker);
            int endid = response.indexOf("'", startid + startidmarker.length());
            if (startid != -1 && endid != -1) {
                long id = Long.parseLong(response.substring(startid + startidmarker.length(), endid));
                String partTrnmPage = execute(new HttpGet(this.hostprefix + TRNM_INFO + id));
                ParserMarker[] markers2 = new ParserMarker[]{ParserMarker.TRN_ID, ParserMarker.TRNM_PRE_NAME, ParserMarker.TRNM_POST_NAME, ParserMarker.TRN_DATE, ParserMarker.TRN_LVL_FROM, ParserMarker.TRN_LVL_TO, ParserMarker.TRN_PRIZE,
                        ParserMarker.TRN_MEMBERS, ParserMarker.TRN_MEMBERS_MAX, ParserMarker.TRN_CAN_JOIN, ParserMarker.TRN_CAN_LEAVE, ParserMarker.TRN_CAN_USE_TICKET};
                parser.parsePage(partTrnmPage, markers2, new TrnmModelFactory(), MergingStrategy.REPLACE, tournaments, true);
                int from = 0;
                int invitations = 0;
                while ((from = partTrnmPage.indexOf("invite.gif", from + 1)) != -1) {
                    invitations++;
                }
                Trnm parsedTrnm = tournaments.values().iterator().next();
                parsedTrnm.setInvitations(invitations);
                parsedTrnm.setIncluded(isIncluded(Settings.getUser(), partTrnmPage));
                if (partTrnmPage.contains("300%"))
                    parsedTrnm.setType(TournamentType.SENATOR);
                checkBattle(partTrnmPage);
            }
        } else {
            for (Trnm trnm : tournaments.values()) {
                trnm.setType(type);
            }
        }
    }

    private boolean isIncluded(String user, String partTrnmPage) {
        String startParticipantsString = "<b>Участников:</b>";
        String endParticipantsString = "</td></tr>";
        String participantsString = Utils.between(partTrnmPage, startParticipantsString, endParticipantsString);
        String[] participantsStrings = participantsString.split(",");
        for (String string : participantsStrings) {
            if (string.contains(user) && string.contains("<b>")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visitProfile() {
    }

    @Override
    public void visitMap() {

    }

    @Override
    public void visitGuilds() throws IOException {
        int page = 1;
        boolean finish = false;
        while (!finish) {
            HttpGet guildsRequest = new HttpGet(this.hostprefix + GUILDS + "?page=" + page);
            String response = execute(guildsRequest);
            int start = response.indexOf("<h1>Рейтинг гильдий</h1>");
            final ParserMarker[] markers = new ParserMarker[]{ParserMarker.GLDS_ID, ParserMarker.GLDS_NAME};
            int was = guilds.size();
            parser.parsePage(response.substring(start), markers, new ModelFactoryImpl(Guild.class), MergingStrategy.ADD, guilds, true);
            finish = was == guilds.size();
            page++;
        }

    }

    @Override
    public String visitGuild(final long guildId) throws IOException {
        HttpGet visitGuildRequest = new HttpGet(this.hostprefix + GUILD + Long.toString(guildId));
        String response = execute(visitGuildRequest);
        if (response == null) return response;
        HashMap<Long, Player> players = new HashMap<Long, Player>();
        int st = response.indexOf("Состав гильдии");
        int en = response.indexOf("Награды", st);
        if (en < 0) en = response.length();
        String guildRoster = response.substring(st, en);
        final ParserMarker[] markers = new ParserMarker[]{ParserMarker.PL_MARKER, ParserMarker.PL_VIP, ParserMarker.PL_ID, ParserMarker.PL_NAME, ParserMarker.PL_LVL};
        parser.parsePage(guildRoster, markers, new ModelFactoryImpl(Player.class), MergingStrategy.REPLACE, players, false);
        int start = response.indexOf("Список игроков в онлайне");
        int end = response.indexOf("Финансы", start);
        final ParserMarker[] onlineMarkers = new ParserMarker[]{ParserMarker.PL_ID, ParserMarker.PL_NAME, ParserMarker.PL_ONLINE};
        String part = response.substring(start, end);
        parser.parsePage(part, onlineMarkers, new ModelFactoryImpl(Player.class), MergingStrategy.MERGE, players, false);
        Guild guild = (Guild) guilds.get(guildId);
        if (guild == null) {
            guild = new Guild(guildId);
            guilds.put(guildId, guild);
        }
        guild.setPlayers(players);
        return response;
    }

    @Override
    public void login(String user, String pss) throws IOException {
        try {
            try {
                HttpGet myGladsRequest = new HttpGet(this.hostprefix + MY_GLADS);
                String response = execute(myGladsRequest);
                checkLoggedIn(response);
                return;
            } catch (NotLoggedInException e) {

            }
            HttpPost post = new HttpPost(this.hostprefix + MY_OFFICE_URI);
            List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
            formparams.add(new BasicNameValuePair("step", "1"));
            formparams.add(new BasicNameValuePair("login", "1"));
            formparams.add(new BasicNameValuePair("url", ""));
            formparams.add(new BasicNameValuePair("auth_pass", pss));
            if (captchaUrl != null && !IGNORE_CAPTCHA) {
                String cpt = (String) JOptionPane.showInputDialog(null, "Enter captcha", "Captcha", JOptionPane.PLAIN_MESSAGE, new ImageIcon(captcha), null, "");
                formparams.add(new BasicNameValuePair("need_captcha", "1"));
                formparams.add(new BasicNameValuePair("Code", cpt));
            }
            formparams.add(new BasicNameValuePair("auth_name", user));
            formparams.add(new BasicNameValuePair("auth_domain", ""));
            formparams.add(new BasicNameValuePair("auth_pass1", ""));
            formparams.add(new BasicNameValuePair("auth_remember", "on"));
            String responseBody = null;
            while (responseBody == null) {
                try {
                    if (captchaUrl != null && !IGNORE_CAPTCHA) {
                        String cpt = (String) JOptionPane.showInputDialog(null, "Enter captcha", "Captcha", JOptionPane.PLAIN_MESSAGE, new ImageIcon(captcha), null, "");
                        formparams.add(new BasicNameValuePair("need_captcha", "1"));
                        formparams.add(new BasicNameValuePair("Code", cpt));
                    }
                    responseBody = execute(post, formparams);
                } catch (NotLoggedInException nle) {
                    nle.printStackTrace();
                }
            }
            LOGGER.info("Login successfull");
            client.saveCookies();
        } catch (IOException e) {
        }
    }

    @Override
    public void updateSenates(final Date tillDate) throws IOException {

        int page = 1;
        boolean enoughPages = false;
        while (!enoughPages) {
            LOGGER.debug("page " + page);
            HttpGet senatesPage = new HttpGet(this.hostprefix + SENATES + (page++));
            String response = execute(senatesPage);
            ParserMarker[] markers = new ParserMarker[]{ParserMarker.TRN_ID, ParserMarker.TRN_DATE, ParserMarker.TRN_COMPLETE, ParserMarker.TRN_WINNER};
            Map<Long, IModel> trnmsOnPage = new HashMap<Long, IModel>();
            int start = response.indexOf("<h3>Сенаторские турниры</h3>");
            parser.parsePage(response.substring(start), markers, TrnmModelFactory.getInstance(), MergingStrategy.REPLACE, trnmsOnPage, false);
            if (trnmsOnPage.size() == 0) {
                enoughPages = true;
            }
            for (IModel iModel : trnmsOnPage.values()) {
                Trnm trnm = (Trnm) iModel;
                if (senates.get(trnm.getId()) != null) {
                    enoughPages = true;
                } else {
                    if (trnm.getStart().compareTo(tillDate) < 0) {
                        enoughPages = true;
                    } else {
                        if (trnm.getCompleted() == null ? false : trnm.getCompleted()) {
                            HttpGet senate = new HttpGet(this.hostprefix + TRNM_INFO + trnm.getId());
                            String senateResponse = execute(senate);
                            int membersStart = senateResponse.indexOf("Участники:");
                            int membersEnd = senateResponse.indexOf("</td>", membersStart);
                            String membersPart = senateResponse.substring(membersStart, membersEnd);
                            ParserMarker[] memberMarkers = new ParserMarker[]{ParserMarker.TRN_MEMBER_GUILD, ParserMarker.TRN_MEMBER, ParserMarker.TRN_MEMBER_NAME};
                            Map<Long, Player> senateMembers = new HashMap<Long, Player>();
                            parser.parsePage(membersPart, memberMarkers, new ModelFactoryImpl(Player.class), MergingStrategy.REPLACE, senateMembers, false);
                            trnm.setPlayers(senateMembers);
                            int finalStart = senateResponse.indexOf("финал");

                            for (int i = 0; i < 13; ++i) {
                                finalStart = senateResponse.indexOf("/users/", finalStart + 1);
                            }
                            int finalEnd = finalStart;
                            for (int i = 0; i < 2; ++i) {
                                finalEnd = senateResponse.indexOf("/users/", finalEnd + 1);
                            }
                            String finalString = senateResponse.substring(finalStart - 1, finalEnd);

                            Map<Long, Player> finalMembers = new HashMap<Long, Player>();
                            ParserMarker[] finalMarkers = new ParserMarker[]{ParserMarker.TRN_FINAL};
                            parser.parsePage(finalString, finalMarkers, new ModelFactoryImpl(Player.class), MergingStrategy.REPLACE, finalMembers, false);
                            for (Player player : finalMembers.values()) {
                                Long pid = player.getId();
                                Long winner = trnm.getWinner();
                                if (!pid.equals(winner)) {
                                    trnm.setFinalist(player.getId());
                                }
                            }
                            senates.put(trnm.getId(), trnm);

                        }
                    }

                }
            }
        }
    }

    private long getGuild(final long id) {
        for (IModel iModel : guilds.values()) {
            Guild guild = (Guild) iModel;
            if (guild.getPlayers().get(id) != null) {
                return guild.getId();
            }
        }
        return 0;
    }

    @Override
    public void saveSenates() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(senatesSer)));
            out.writeObject(senates);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void loadSenates() {
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(senatesSer)));
            senates = (HashMap<Long, Trnm>) in.readObject();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (senates == null) {
            senates = new HashMap<Long, Trnm>();
        }
    }

    @Override
    public void visitCity(final Long city) throws IOException {
        HttpGet cityRequest = new HttpGet(this.hostprefix + CITY + Long.toString(city));
        String response = execute(cityRequest);
        if (response.contains("Вы перемещаетесь")) return;
        City c = (City) cities.get(city);
        if (c == null) {
            c = new City();
            c.setId(city);
            cities.put(city, c);
        }
        int p = response.indexOf("Описание города");
        p = response.indexOf("Город", p);
        p = response.indexOf("value=\"", p);
        int p2 = response.indexOf("\"", p + "value=\"".length());
        String name = response.substring(p + "value=\"".length(), p2);
        c.setName(name);
        if (response.contains("восстановление всего отряда")) {
            c.setPrize(Prize.RSTG);
        }
        if (response.contains("серебряный доспех")) {
            c.setPrize(Prize.SILVER);
        }
        if (response.contains("бонус опыта")) {
            c.setPrize(Prize.EXP);
        }
        if (response.contains("золотой доспех")) {
            c.setPrize(Prize.GOLD);
        }
        if (response.contains("восстановление специалистов")) {
            c.setPrize(Prize.RSTS);
        }
        if (response.contains("приглашение")) {
            c.setPrize(Prize.INVIT);
        }
        if (!response.contains("Нет гильдий претендующих на этот город")) {
            int pretendentsStart = response.indexOf("Претенденты на город");
            pretendentsStart = response.indexOf("Очки", pretendentsStart);
            int pretendentsEnd = response.indexOf("</table>", pretendentsStart);
            String pretendents = response.substring(pretendentsStart, pretendentsEnd);
            ParserMarker[] markers = new ParserMarker[]{ParserMarker.MAP_GUILD_ID, ParserMarker.MAP_GUILD_PTS};
            Map<Long, IModel> pretendentGuilds = new HashMap<Long, IModel>();
            parser.parsePage(pretendents, markers, new ModelFactoryImpl(Guild.class), MergingStrategy.REPLACE, pretendentGuilds, false);
            c.setPretendents(pretendentGuilds);
        }
        if (!response.contains("В городе нет игроков вашей гильдии")) {
            int playersStart = response.indexOf("Игрок");
            int playersEnd = response.indexOf("</table>", playersStart);
            String players = response.substring(playersStart, playersEnd);
            ParserMarker[] markers = new ParserMarker[]{ParserMarker.PL_ID, ParserMarker.PL_NAME, ParserMarker.PL_PTS};
            Map<Long, IModel> cityPlayers = new HashMap<Long, IModel>();
            parser.parsePage(players, markers, new ModelFactoryImpl(Player.class), MergingStrategy.REPLACE, cityPlayers, false);
            c.setPlayers(cityPlayers);
        }

    }

    @Override
    public void checkTavern() throws IOException {
        HttpGet tavernRequest = new HttpGet(this.hostprefix + TAVERN);
        String response = execute(tavernRequest);
        ParserMarker[] markers = new ParserMarker[]{ParserMarker.GL_TYPE, ParserMarker.RCR_ID, ParserMarker.GL_NAME, ParserMarker.GL_LVL,
                ParserMarker.GL_AGE, ParserMarker.RCR_TAL, ParserMarker.GL_VIT, ParserMarker.GL_DEX, ParserMarker.GL_ACC,
                ParserMarker.GL_STR, ParserMarker.GL_PRICE};
        parser.parsePage(response, markers, GladModelFactory.getInstance(), MergingStrategy.REPLACE, merceneries, true);
    }

    @Override
    public void checkSlaves() throws IOException {
        HttpGet slavesRequest = new HttpGet(this.hostprefix + SLAVES);
        String response = execute(slavesRequest);
        ParserMarker[] markers = new ParserMarker[]{ParserMarker.GL_TYPE, ParserMarker.RCR_ID, ParserMarker.GL_NAME, ParserMarker.GL_LVL,
                ParserMarker.GL_AGE, ParserMarker.RCR_TAL, ParserMarker.GL_VIT, ParserMarker.GL_DEX, ParserMarker.GL_ACC,
                ParserMarker.GL_STR, ParserMarker.GL_PRICE};
        parser.parsePage(response, markers, GladModelFactory.getInstance(), MergingStrategy.REPLACE, slaves, true);
        String startMarker = "У Вас осталось свободных слотов:";
        int start = response.indexOf(startMarker);
        String endMarker = "<";
        int end = response.indexOf(endMarker, start);
        if (start < end && start > 0) {
            String freeslots = response.substring(start + startMarker.length(), end);
            freeSlots = Long.parseLong(freeslots.trim());
        }
    }

    @Override
    public void mail(final String login, final String subject, final String body) throws IOException {
        HttpPost post = new HttpPost(this.hostprefix + MAIL);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type ", "residence/mail"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/residence/mail.php?act=write"));
        formparams.add(new BasicNameValuePair("act", "write"));
        formparams.add(new BasicNameValuePair("Login", login));
        formparams.add(new BasicNameValuePair("Subject", subject));
        formparams.add(new BasicNameValuePair("Body", body));
        formparams.add(new BasicNameValuePair("oldact", "write"));
        String responseBody = execute(post, formparams);

    }

    @Override
    public void buy(final Glad bestRecruit) throws Exception {
        LOGGER.info("buying.." + bestRecruit);
    }

    @Override
    public void sell(final Glad glad) throws Exception {
        LOGGER.info("selling..." + glad);
    }

    @Override
    public void loadHippoTours() {
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(hippoSer)));
            hippoTours = (HashMap<Long, HippoTour>) in.readObject();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (hippoTours == null) {
            hippoTours = new HashMap<Long, HippoTour>();
        }

    }

    @Override
    public void saveHippoTours() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(hippoSer)));
            out.writeObject(hippoTours);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void hippoLogin() throws Exception {
        HttpPost post = new HttpPost(this.hostprefix + "/php/hippo_api.php");
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("php_session_id", "cc7143f7926039a5f71c2bec69db3e2a"));
        formparams.add(new BasicNameValuePair("method", "login"));
        String responseBody = execute(post, formparams);
        ResponseDocument responseDocument = ResponseDocument.Factory.parse(responseBody);
        checkHippoResponse(responseDocument);
    }

    private void checkHippoResponse(final ResponseDocument responseDocument) throws Exception {
        String error = responseDocument.getResponse().getError();
        if (!Utils.isBlank(error)) {
            throw new Exception("Hippo response:" + error);
        }
    }

    @Override
    public Horses hippoGetBets() throws Exception {
        HttpPost post = new HttpPost(this.hostprefix + "/php/hippo_api.php");
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("php_session_id", "cc7143f7926039a5f71c2bec69db3e2a"));
        formparams.add(new BasicNameValuePair("method", "getBets"));
        String responseBody = execute(post, formparams);
        ResponseDocument responseDocument = ResponseDocument.Factory.parse(responseBody);
        checkHippoResponse(responseDocument);
        return responseDocument.getResponse().getHorses();

    }

    @Override
    public ResponseDocument.Response hippoSetBet(final long horse, final long money) throws Exception {
        HttpPost post = new HttpPost(this.hostprefix + "/php/hippo_api.php");
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("php_session_id", "cc7143f7926039a5f71c2bec69db3e2a"));
        formparams.add(new BasicNameValuePair("method", "setBet"));
        formparams.add(new BasicNameValuePair("horse", Long.toString(horse)));
        formparams.add(new BasicNameValuePair("money", Long.toString(money)));
        String responseBody = execute(post, formparams);
        ResponseDocument responseDocument = ResponseDocument.Factory.parse(responseBody);
        checkHippoResponse(responseDocument);
        return responseDocument.getResponse();
    }

    @Override
    public HippoTour hippoTour(final BetPlacer betPlacer) throws Exception {
        Horses horses = hippoGetBets();
        HippoHorse[] hh = parseHorses(horses);
        int horse = betPlacer.horse(hh);
        int bet = betPlacer.bet(hh);
        ResponseDocument.Response response = hippoSetBet(horse, bet);
        HippoTour ht = new HippoTour();
        ht.setHorses(hh);
        ht.setWinner(response.getWinner());
        ht.setHorse(horse);
        ht.setBet((long) bet);
        ht.setPot(Utils.isBlank(response.getMoney()) ? 0 : Long.parseLong(response.getMoney()));
        ht.setTimestamp(System.currentTimeMillis());
        hippoTours.put(ht.getTimestamp(), ht);
        return ht;
    }

    private static HippoHorse[] parseHorses(final Horses horses) {
        HippoHorse[] hh = new HippoHorse[horses.getHorseArray().length];
        int i = 0;
        for (Horse horse : horses.getHorseArray()) {
            HippoHorse h = new HippoHorse();
            h.setName(horse.getName());
            h.setFactor(horse.getFactor());
            h.setWin(horse.getWin());
            hh[i++] = h;
        }
        return hh;
    }

    public Map<Long, IModel> getSlaves() {
        return slaves;
    }

    public Map<Long, IModel> getMerceneries() {
        return merceneries;
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(final MyHttpClient client) {
        this.client = client;
    }

    public Map<Long, Guild> getGuilds() {
        return guilds;
    }

    @Override
    public Map<Long, Trnm> getSenates() {
        return this.senates;
    }

    public Map<Long, IModel> getCities() {
        return cities;
    }

    public void setCities(final Map<Long, IModel> cities) {
        this.cities = cities;
    }

    public Map<Long, Glad> getMyGlads() {
        return myGlads;
    }

    @Override
    public long getFreeSlots() {
        return freeSlots;
    }

    public HashMap<Long, HippoTour> getHippoTours() {
        return hippoTours;
    }

    public Map<Long, Trnm> getTournaments() {
        return tournaments;
    }

    public GlRuParser getParser() {
        return parser;
    }

    @Override
    public boolean heal(final CGlad g, final double goal) throws IOException {
        //http://s2.gladiators.ru/xml/gladiators/info.php?act=recovery_glad&id=1575389&rpage=1
        boolean money = Settings.getMoneyheal() > 0;

        Utils.soutn("Heal " + GladType.byID(g.getTypeid()).getName() + " - " + g.getName() + " up to  " + goal);
        HttpPost post = new HttpPost(this.hostprefix + HEAL);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
        formparams.add(new BasicNameValuePair("act", "select"));
        formparams.add(new BasicNameValuePair("numrows", Long.toString(glads.size())));
        Stuff stuff = getStuff();
        Long newMasseur = stuff.getMasseur();
        if (newMasseur < 5) return false;
        for (Map.Entry<Long, CGlad> gladEntry : glads.entrySet()) {
            CGlad glad = gladEntry.getValue();
            if (glad.getId() == g.getId()) {
                if (glad.getStamina() < 0) return false;
                double currentPercentH = glad.getStamina();
                //if (currentPercentH == 100) return true;
                Long currentMasseur = stuff.getMasseur();
                double percentCanHeal = currentMasseur.doubleValue() * 2;
                double oneHitPercent = 100d / glad.getFullhits();
                if (oneHitPercent > 0.2d) oneHitPercent = 0.2d;
                if (!(money && goal > 99d)) oneHitPercent = 0;
                double newPercentH = currentPercentH + percentCanHeal >= (goal - oneHitPercent) ? (goal - oneHitPercent) : currentPercentH + percentCanHeal;
                double masseurUsage = (double) (newPercentH - currentPercentH) / 2;
                newMasseur = currentMasseur - (long) Math.floor(masseurUsage) - 1;
                newMasseur = newMasseur < 0 ? 0 : newMasseur;
                if (percentCanHeal > 0 && masseurUsage > 0) {
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(newPercentH)));
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));
                }
            } else {
                if (glad.getStamina() >= 0) {
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(glad.getStamina())));
                }
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));
            }
        }
        formparams.add(new BasicNameValuePair("Health", newMasseur.toString()));
        if (stuff.getPriest() != null)
            formparams.add(new BasicNameValuePair("Morale", stuff.getPriest().toString()));
        if (stuff.getDoctor() != null)
            formparams.add(new BasicNameValuePair("Injury", stuff.getDoctor().toString()));
        formparams.add(new BasicNameValuePair("oldact", "select"));
        execute(post, formparams);
        updateStuff();
        updateGlads();
        CGlad updatedGlad = getGlads().get(g.getId());
        //System.out.println("After heal glad hp " + updatedGlad.getHits() + ", stamina " + updatedGlad.getStamina());
        if (updatedGlad.getStamina() < 100d && updatedGlad.getFullhits() - updatedGlad.getHits() <= 1 && money) {
            healMoney(updatedGlad);
            updateGlads();
        }
        return Double.valueOf(goal > 99 ? 99.99d : 9.9d).compareTo(glads.get(g.getId()).getStamina()) < 0;
    }

    @Override
    public void healMoney(CGlad glad) throws IOException {
        if (Settings.getMoneyheal() > 0) {
            Utils.sout("Heal for money " + glad + " hp " + glad.getHits() + ", stamina " + glad.getStamina() + "\n");
            execute(new HttpGet(this.hostprefix + HEAL_MONEY + glad.getId()));
        }
    }

    @Override
    public boolean healexperiment(final CGlad g) throws IOException {
        //1575389
        LOGGER.debug("Use masseur on " + g);
        HttpPost post = new HttpPost(this.hostprefix + HEAL);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
        formparams.add(new BasicNameValuePair("act", "select"));
        formparams.add(new BasicNameValuePair("numrows", Long.toString(glads.size())));
        Stuff stuff = getStuff();
        Long newMasseur = stuff.getMasseur();
        if (newMasseur < 5) return false;
        for (Map.Entry<Long, CGlad> gladEntry : glads.entrySet()) {
            CGlad glad = gladEntry.getValue();
            if (glad.getId() == g.getId()) {
                if (glad.getStamina() < 0) return false;
                double currentPercentH = glad.getStamina();
                //if (currentPercentH == 100) return true;
                double oneHitPercent = 2.99;
                Long currentMasseur = stuff.getMasseur();
                double percentCanHeal = oneHitPercent;//currentMasseur.doubleValue() * 2;
                double newPercentH = currentPercentH + oneHitPercent;
                double masseurUsage = (double) (newPercentH - currentPercentH) / 2;
                newMasseur = currentMasseur - 1;// - (long) Math.floor(masseurUsage);
                newMasseur = newMasseur < 0 ? 0 : newMasseur;
                if (percentCanHeal > 0 && masseurUsage > 0) {
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(newPercentH)));
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));
                }
            } else {
                if (glad.getStamina() >= 0) {
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(glad.getStamina())));
                }
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));
            }
        }
        formparams.add(new BasicNameValuePair("Health", newMasseur.toString()));
        formparams.add(new BasicNameValuePair("Morale", stuff.getPriest().toString()));
        formparams.add(new BasicNameValuePair("Injury", stuff.getDoctor().toString()));
        formparams.add(new BasicNameValuePair("oldact", "select"));
        execute(post, formparams);
        //visitRecovery();
        updateStuff();
        updateGlads();
        LOGGER.debug("Glad hp " + getGlads().get(g.getId()).getHits());
        return Double.valueOf(99.9).compareTo(glads.get(g.getId()).getStamina()) < 0;
    }

    private double calcInj(double stamina) {
        if (stamina < 0) return -stamina / 40;
        else return 0;
    }

    @Override
    public void applySpecs() throws IOException {
        Stuff stuff = this.getStuff();
        HttpPost post = new HttpPost(this.hostprefix + HEAL);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
        formparams.add(new BasicNameValuePair("act", "select"));
        formparams.add(new BasicNameValuePair("numrows", Long.toString(glads.size())));

        for (CGlad glad : glads.values()) {
            if (glad.getStamina() >= 0) {
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(glad.getStamina())));
            }
            formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
            formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));

        }
        formparams.add(new BasicNameValuePair("Health", stuff.getMasseur().toString()));
        formparams.add(new BasicNameValuePair("Morale", stuff.getPriest().toString()));
        formparams.add(new BasicNameValuePair("Injury", stuff.getDoctor().toString()));
        formparams.add(new BasicNameValuePair("oldact", "select"));
        execute(post, formparams);
    }

    @Override
    public boolean morale(CGlad g, long maxvalue) throws IOException {
        Utils.soutn("Morale " + GladType.byID(g.getTypeid()).getName() + " - " + g.getName() + " up to  " + maxvalue);

        if (maxvalue > 10) maxvalue = 10;
        HttpPost post = new HttpPost(this.hostprefix + HEAL);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
        formparams.add(new BasicNameValuePair("act", "select"));
        formparams.add(new BasicNameValuePair("numrows", Long.toString(glads.size())));
        Stuff stuff = getStuff();
        Long newPriest = stuff.getPriest();
        for (Map.Entry<Long, CGlad> gladEntry : glads.entrySet()) {
            CGlad glad = gladEntry.getValue();
            if (glad.getId() == g.getId()) {
                long currentMorale = glad.getMorale();
                Long currentPriest = stuff.getPriest();
                long canMorale = currentPriest / 10;
                long newMorale = currentMorale + canMorale;
                newMorale = newMorale > maxvalue ? maxvalue : newMorale;
                long priestUsage = (newMorale - currentMorale) * 10;
                newPriest = currentPriest - priestUsage;
                newPriest = newPriest < 0 ? 0 : newPriest;
                if (canMorale > 0 && priestUsage > 0) {
                    if (glad.getStamina() >= 0) {
                        formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(glad.getStamina())));
                    }
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(newMorale)));
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));
                }
            } else {
                if (glad.getStamina() >= 0) {
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(glad.getStamina())));
                }
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));

            }
        }
        formparams.add(new BasicNameValuePair("Health", stuff.getMasseur().toString()));
        formparams.add(new BasicNameValuePair("Morale", newPriest.toString()));
        formparams.add(new BasicNameValuePair("Injury", stuff.getDoctor().toString()));
        formparams.add(new BasicNameValuePair("oldact", "select"));
        execute(post, formparams);
        //visitRecovery();
        updateGlads();
        updateStuff();
        return Long.valueOf(maxvalue).equals(glads.get(g.getId()).getMorale());
    }

    @Override
    public boolean cure(CGlad g) throws IOException {
        Utils.soutn("Cure " + GladType.byID(g.getTypeid()).getName() + " - " + g.getName());
        HttpPost post = new HttpPost(this.hostprefix + HEAL);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
        formparams.add(new BasicNameValuePair("act", "select"));
        formparams.add(new BasicNameValuePair("numrows", Long.toString(glads.size())));
        Stuff stuff = getStuff();
        Long newDoctor = stuff.getDoctor();
        for (Map.Entry<Long, CGlad> gladEntry : glads.entrySet()) {
            CGlad glad = gladEntry.getValue();
            if (glad.getId() == g.getId()) {
                double currentInjury = calcInj(glad.getStamina());
                Long currentDoctor = stuff.getDoctor();
                double canCure = currentDoctor.doubleValue() / 10d;
                long newInjury = (long) Math.ceil(currentInjury - canCure);
                if (canCure < 1 && newInjury > 0) {
                    return false;
                }
                newInjury = newInjury <= 0 ? 0 : newInjury;
                double doctorUsage = (currentInjury - newInjury) * 10;
                newDoctor = currentDoctor - (long) Math.floor(doctorUsage);
                newDoctor = newDoctor < 0 ? 0 : newDoctor;
                if (canCure > 0 && doctorUsage > 0) {
                    if (glad.getStamina() >= 0) {
                        formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(glad.getStamina())));
                    }
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", Long.toString(newInjury)));
                }
            } else {
                if (glad.getStamina() >= 0) {
                    formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", format(glad.getStamina())));
                }
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
                formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", format(calcInj(glad.getStamina()))));

            }
        }
        formparams.add(new BasicNameValuePair("Health", stuff.getMasseur().toString()));
        formparams.add(new BasicNameValuePair("Morale", stuff.getPriest().toString()));
        formparams.add(new BasicNameValuePair("Injury", newDoctor.toString()));
        formparams.add(new BasicNameValuePair("oldact", "select"));
        String responseBody = execute(post, formparams);
        //visitRecovery();
        updateGlads();
        updateStuff();
        return glads.get(g.getId()).getStamina() >= 0;
    }

    public void joinTrn(final Trnm trnm) throws IOException {
        LOGGER.info("Joining " + trnm);
        execute(new HttpGet(this.hostprefix + TRN_JOIN + Long.toString(trnm.getId())));
    }

    public void leaveTrn(final Trnm trnm) throws IOException {
        LOGGER.info("Leaving " + trnm);
        execute(new HttpGet(this.hostprefix + TRN_LEAVE + Long.toString(trnm.getId())));
    }

    @Override
    public void useTicket(Trnm trnm) throws IOException {
        LOGGER.info("Check Using invitation on tournament " + trnm);
        execute(new HttpGet(this.hostprefix + TRNM_USE_TICKET + Long.toString(trnm.getId())));
    }

    public Btl builder(final Btl btl) throws IOException {
        String builder = USE_STUBS ? readFileAsString(BUILDER_STUB) : execute(new HttpGet(this.hostprefix + BUILDER + Long.toString(btl.getId()) + "&champ=0"));
        ParserMarker[] markers = new ParserMarker[]{
                ParserMarker.BLD_FLASHVARS,
                ParserMarker.BLD_TYPE_ID,
                ParserMarker.BLD_TYPE_ID2,
                ParserMarker.BLD_LIMIT_GLAD,
                ParserMarker.BLD_LIMIT_GLAD2,
                ParserMarker.BLD_LIMIT_SKL,
                ParserMarker.BLD_LIMIT_SKL2,
                ParserMarker.BLD_MAX_LVL,
                ParserMarker.BLD_MAX_LVL2,
                ParserMarker.BLD_XML,
                ParserMarker.BLD_XML2,
                ParserMarker.BLD_TIME_LEFT,
                ParserMarker.BLD_TIME_LEFT2,
                ParserMarker.BLD_GLAD_TIP,
                ParserMarker.BLD_GLAD_TIP2,
                ParserMarker.BLD_ID,
                ParserMarker.BLD_ID2,
                ParserMarker.BLD_USER1_LOGIN,
                ParserMarker.BLD_USER1_LOGIN2,
                ParserMarker.BLD_USER2_LOGIN,
                ParserMarker.BLD_USER2_LOGIN2,
                ParserMarker.BLD_TOURMENT_TITLE,
                ParserMarker.BLD_TOURMENT_TITLE2,
                ParserMarker.BLD_CHAMP,
                ParserMarker.BLD_CHAMP2,
                ParserMarker.BLD_MIN_AWARD_FEE,
                ParserMarker.BLD_MIN_AWARD_FEE2,
                ParserMarker.BLD_TUT,
                ParserMarker.BLD_TUT2,
                ParserMarker.BLD_USER1_URL,
                ParserMarker.BLD_USER1_URL2,
                ParserMarker.BLD_USER2_URL,
                ParserMarker.BLD_USER2_URL2,
                ParserMarker.BLD_ARMOR_LEVELS,
                ParserMarker.BLD_ARMOR_LEVELS2,
                ParserMarker.BLD_URL,
                ParserMarker.BLD_URL2,
                ParserMarker.BLD_ORDER
        };
        parser.parsePage(builder, markers, new BtlModelFactory(), MergingStrategy.MERGE, bttls, false);
        return getIncomingBtl();
    }

    private Map<Long, CGlad> getFromRoster() throws Exception {
        Map<Long, CGlad> result = new HashMap<Long, CGlad>();
        CRoster cRoster = getRoster();
        for (CGlad cGlad : cRoster.getGlads()) {
            for (CArmor cArmor : cRoster.getArmors().getArmors()) {
                if (match(cArmor, cGlad, 0)) {
                    cGlad.getArmors().add(cArmor);
                }
            }
            cGlad.setAge(Utils.countSilver(cGlad));
/*
			if (cGlad.getArmors().isEmpty()) {
				for (CArmor cArmor : cRoster.getArmors().getArmors()) {
					if (match(cArmor, cGlad, 0)) {
						cGlad.getArmors().add(cArmor);
					}
				}
			}
			if (cGlad.getArmors().isEmpty()) {
				for (CArmor cArmor : cRoster.getArmors().getArmors()) {
					if (match(cArmor, cGlad, 20)) {
						cGlad.getArmors().add(cArmor);
					}
				}
			}
			if (cGlad.getArmors().isEmpty()) {
				for (CArmor cArmor : cRoster.getArmors().getArmors()) {
					if (match(cArmor, cGlad, 0)) {
						cGlad.getArmors().add(cArmor);
					}
				}
			}
			if (cGlad.getArmors().isEmpty()) {
				for (CArmor cArmor : cRoster.getArmors().getArmors()) {
					if (match(cArmor, cGlad, 0)) {
						cGlad.getArmors().add(cArmor);
					}
				}
			}
*/
            Collections.sort(cGlad.getArmors(), new ArmorsMoraleComparator(cGlad.getMorale(), false));
            //if (cGlad.getArmors().isEmpty()) {
            //	LOGGER.warn("!!!!!!! No armor for glad of type " + GladType.byID(cGlad.getTypeid()) + " lvl " + cGlad.getLevel());
            //}
            result.put(cGlad.getId(), cGlad);
        }
        return result;
    }

    private static Map<Long, String> parseArmorlevels(final String armorLevels) {
        Map<Long, String> result = new HashMap<Long, String>();
        for (String armLvl : armorLevels.split(";")) {
            if (armLvl != null && armLvl.trim().length() > 0) {
                String[] lvls = armLvl.split(":");
                result.put(Long.parseLong(lvls[0]), lvls[1] + ":" + lvls[2]);
            }
        }
        return result;
    }

    private boolean match(final CArmor armor, final CGlad glad, int add) {
        if (armor.getTypeid() != glad.getTypeid()) return false;
        String allowedLvl = armorlevels.get(armor.getLevel());
        String[] minmaxLvl = allowedLvl.split(":");
        if (glad.getLevel() > Long.parseLong(minmaxLvl[1]) || glad.getLevel() + add < Long.parseLong(minmaxLvl[0]))
            return false;
        return true;
    }

    public void send(final Cxml placement) throws Exception {
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("xml", Utils.toString(placement)));
        //LOGGER.info(Utils.toString(placement));
        String responseBody = execute(new HttpPost(this.hostprefix + SEND), formparams);
    }

    public Stuff getStuff() {
        return stuff.values().iterator().next();
    }

    public String watchFight(final long id) throws IOException {
        String response = execute(new HttpGet(this.hostprefix + FIGHT + Long.toString(id)));
        String winner = parser.parseFight(response);
        return winner;
    }

    @Override
    public void restoreSpecs() throws IOException {
        visitRecovery();
        Stuff stuff = getStuff();
        LOGGER.debug("Restoring specialists " + stuff);
        System.out.println("Restoring specialists " + stuff);
        if (stuff.getMasseur() >= 5 && stuff.getDoctor() >= 10) {
            //System.out.println("Try restoring " + stuff);
            return;
        }
        if (stuff.getRstSpc() == null && getBonuses() >= 1) BonusHistory.use();
        HttpPost post = new HttpPost(this.hostprefix + RECOVERY);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("act", "restore"));
        String responseBody = execute(post, formparams);
        stuff.setDoctor(100L);
        stuff.setPriest(100L);
        stuff.setMasseur(100L);
        //updateStuff();
    }

    @Override
    public void restoreGlads() throws IOException {

        LOGGER.debug("Restoring gladiators " + Arrays.deepToString(glads.values().toArray()));
        if (getStuff().getRstGld() == null && getBonuses() >= 3) BonusHistory.use(3);
        HttpPost post = new HttpPost(this.hostprefix + RECOVERY);
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("act", "restore_all"));
        String responseBody = execute(post, formparams);
        Utils.sleep(3000);
        updateGlads();
    }

    public Map<Long, Btl> getBttls() {
        return bttls;
    }

    @Override
    public Map<Long, CGlad> getGlads() {
        return this.glads;
    }

    @Override
    public void updateGlads() throws IOException {
        try {
            this.glads = getFromRoster();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public void updateStuff() throws IOException {
        HttpGet recovery = new HttpGet(this.hostprefix + RECOVERY);
        String responseBody = execute(recovery);
        parseStuff(responseBody);
        checkBattle(responseBody);
    }

    @Override
    public Map<Long, AModel> getDues(int pages) throws IOException {
        Map<Long, AModel> result = new HashMap<Long, AModel>();
        for (int page = 1; page <= pages; page++) {
            String dues = execute(new HttpGet(DUES + Integer.toString(page)));
            ParserMarker[] markers = new ParserMarker[]{ParserMarker.DUE_ID};
            parser.parsePage(dues, markers, new AModelFactory(), MergingStrategy.REPLACE, result, false);
        }
        return result;
    }

    @Override
    public void deleteDue(Long due) throws IOException {
        execute(new HttpGet(this.hostprefix + DELETE_DUE + due.toString()));
    }

    public static void main(String[] args) throws Exception {
        String source = "<table border=0 width=100% bordercolor=#918567 bgcolor=#918567 cellpadding=4 cellspacing=1><tr bgcolor=\"#FAEECF\"><td rowspan=3 bgcolor=#E7DBBB valign=top><a href=/xml/arena/tournaments.php?id=1415901><img src=\"/images/ft_tournament_types/small/23784.jpg\" border=0></a></td><td height=10px  bgcolor=#E7DBBB><b><a href=tournaments.php?id=1415901>Малый турнир Пегаса-CCCLXXIV</a> // 02.11.2013 17:40 </td><tr bgcolor=\"#FAEECF\"><td valign=top height=50px width=100%><i>Уровень гладиаторов до 10</i><br><br><img src=/images/icons/finance.gif width=17 height=17 align=absmiddle> <b>Призовой фонд:</b> 7000 денариев<br><img src=/images/icons/time.gif width=15 height=15 align=absmiddle> <b>Длительность:</b> до 29 минут<br><img src=/images/types/2.gif width=15 height=15 align=absmiddle> <b>Опыт:</b> в 2.5 раза выше<br><img src=/images/icons/profile.gif width=15 height=15 align=absmiddle> <b>Участников:</b> 14 из 8: <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><a href=/guilds/86/><img src=/images/gd_guilds/small/86.jpg border=0 align=absmiddle></a> <a href=/users/20638910>Cefer M</a> [11]</b></a>, <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><a href=/guilds/27/><img src=/images/gd_guilds/small/27.jpg border=0 align=absmiddle></a> <a href=/users/20096971>farrtaun</a> [14]</b></a>, <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><a href=/guilds/135/><img src=/images/gd_guilds/small/135.jpg border=0 align=absmiddle></a> <a href=/users/20628156>kalachnik-72</a> [12]</b></a>, <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><a href=/guilds/91/><img src=/images/gd_guilds/small/91.jpg border=0 align=absmiddle></a> <a href=/users/20219296>deportament</a> [11]</b></a>, <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><img src=/images/vip/4.gif title=VIP-4 border=0 align=absmiddle> <a href=/guilds/10/><img src=/images/gd_guilds/small/10.jpg border=0 align=absmiddle></a> <a href=/users/20749630>Solomon XIII</a> [9]</b></a>, <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><a href=/guilds/194/><img src=/images/gd_guilds/small/194.jpg border=0 align=absmiddle></a> <a href=/users/20603651>9marat9</a> [10]</b></a>, <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><img src=/images/vip/2.gif title=VIP-2 border=0 align=absmiddle> <a href=/guilds/123/><img src=/images/gd_guilds/small/123.jpg border=0 align=absmiddle></a> <a href=/users/20107590>NidL86</a> [11]</b></a>, <b><img width=13px src=/images/invite.gif align=absmiddle title='приглашение в турнир' border=0><a href=/guilds/33/><img src=/images/gd_guilds/small/33.jpg border=0 align=absmiddle></a> <a href=/users/20424718>bimon83</a> [11]</b></a>, <img src=/images/vip/4.gif title=VIP-4 border=0 align=absmiddle> <a href=/guilds/68/><img src=/images/gd_guilds/small/68.jpg border=0 align=absmiddle></a> <a href=/users/20304004>серкас</a> [15]</b></a>, <img src=/images/vip/3.gif title=VIP-3 border=0 align=absmiddle> <a href=/guilds/92/><img src=/images/gd_guilds/small/92.jpg border=0 align=absmiddle></a> <a href=/users/20331846>Ksu Flakk</a> [12]</b></a>, <img src=/images/vip/3.gif title=VIP-3 border=0 align=absmiddle> <a href=/guilds/160/><img src=/images/gd_guilds/small/160.jpg border=0 align=absmiddle></a> <a href=/users/20594211>Потрошитель 88</a> [13]</b></a>, <img src=/images/vip/2.gif title=VIP-2 border=0 align=absmiddle> <a href=/guilds/16/><img src=/images/gd_guilds/small/16.jpg border=0 align=absmiddle></a> <a href=/users/20198549>Бутырка</a> [12]</b></a>, <a href=/guilds/108/><img src=/images/gd_guilds/small/108.jpg border=0 align=absmiddle></a> <a href=/users/20168848>Птенчик</a> [12]</b></a>, <a href=/guilds/5/><img src=/images/gd_guilds/small/5.jpg border=0 align=absmiddle></a> <a href=/users/20053447>Novokras</a> [13]</b></a></td></tr><tr bgcolor=\"#FAEECF\"><td height=10px  bgcolor=#E7DBBB><a href=tournaments.php?id=1415901&act=join&step=1 ><b>[присоединиться]</b></a></td></tr></table><br><script>\n" +
                "function GetTournamentFights(tournament,tour,user,pair,pair1,place1)\n";
    }

    private static int getMaxLvl(CGlad glad) {
        for (int i = 0; i < expTable.length; ++i) {
            if (expTable[i] > glad.getExp()) return i;
        }
        return 50;
    }

    public static String format(final double d) {
        double pM = Math.pow(10, DOUBLE_DECIMAL_PRECISION);
        return df.format(Math.round(d * pM) / pM).replace(",", ".");
    }

    private static String readFileAsString(String filePath) {
        try {
            File file = new File(filePath);

            String content = FileUtils.readFileToString(file);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getMyGuild() {
        return myguild;
    }

    public int getDualGuild() {
        return dualguild;
    }

    @Override
    public void arena(BattleType type, int gladlimit, int maxlevel, int totallevel, int timeout, String gladfilter) throws IOException {
        HttpPost post = new HttpPost(this.hostprefix + ARENA_ACT + type.getAct());
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "arena/arena"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/arena/arena.php?typeid=" + type.getId() + "&do=1"));
        if (type == BattleType.DUEL) formparams.add(new BasicNameValuePair("act", "" + type.getAct()));
        formparams.add(new BasicNameValuePair("id", "" + type.getId()));
        formparams.add(new BasicNameValuePair("typeid", "" + type.getId()));
        formparams.add(new BasicNameValuePair("do", "1"));
        formparams.add(new BasicNameValuePair("Timeout", "" + timeout));
        if (gladfilter.contains("1")) formparams.add(new BasicNameValuePair("glad1", "on"));
        if (gladfilter.contains("2")) formparams.add(new BasicNameValuePair("glad2", "on"));
        if (gladfilter.contains("3")) formparams.add(new BasicNameValuePair("glad3", "on"));
        if (gladfilter.contains("4")) formparams.add(new BasicNameValuePair("glad4", "on"));
        if (gladfilter.contains("5")) formparams.add(new BasicNameValuePair("glad5", "on"));
        if (gladfilter.contains("6")) formparams.add(new BasicNameValuePair("glad6", "on"));
        if (gladfilter.contains("7")) formparams.add(new BasicNameValuePair("glad7", "on"));
        if (type != BattleType.DUEL) formparams.add(new BasicNameValuePair("LimitGlad", "" + gladlimit));
        formparams.add(new BasicNameValuePair("MaxLevel", "" + maxlevel));
        if (type != BattleType.DUEL) formparams.add(new BasicNameValuePair("LimitSkl", "" + totallevel));
        formparams.add(new BasicNameValuePair("oldact", type.getAct()));
        execute(post, formparams);
    }

    @Override
    public void arenaExam() throws IOException {
        BattleType type = BattleType.EXAM;
        HttpPost post = new HttpPost(this.hostprefix + ARENA_ACT + type.getAct());
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "arena/arena"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/arena/arena.php?typeid=" + type.getId()));
        if (type == BattleType.DUEL) formparams.add(new BasicNameValuePair("act", "" + type.getAct()));
        formparams.add(new BasicNameValuePair("id", "" + type.getId()));
        formparams.add(new BasicNameValuePair("typeid", "" + type.getId()));
        formparams.add(new BasicNameValuePair("do", ""));
        formparams.add(new BasicNameValuePair("BattleType", "аттестационный бой"));
        formparams.add(new BasicNameValuePair("GladTypes", "все основные типы"));
        formparams.add(new BasicNameValuePair("LimitGlad", "7"));
        formparams.add(new BasicNameValuePair("MaxLevel", "20"));
        formparams.add(new BasicNameValuePair("LimitSkl", "175"));
        formparams.add(new BasicNameValuePair("BonusPrice", "1 бонуса"));
        formparams.add(new BasicNameValuePair("oldact", type.getAct()));
        execute(post, formparams);
    }

    @Override
    public Opponent arena(int id) throws IOException {
        HttpGet get = new HttpGet(this.hostprefix + ARENA + id);
        Opponent result = new Opponent();
        String response = execute(get);
        int challengedStart = response.indexOf("Вас вызвал");
        if (challengedStart > 0) {
            int opponentIdStart = response.indexOf("/users/", challengedStart);
            if (opponentIdStart > 0) {
                int opponentIdEnd = response.indexOf(">", opponentIdStart + "/users/".length());
                if (opponentIdEnd > 0) {
                    String opponentIdString = response.substring(opponentIdStart + "/users/".length(), opponentIdEnd);
                    int opponentId = Utils.findNumber(opponentIdString).intValue();
                    result.id = opponentId;
                    int opponentNameStart = opponentIdEnd + ">".length();
                    int opponentNameEnd = response.indexOf("<", opponentNameStart);
                    String opponentNameString = response.substring(opponentNameStart, opponentNameEnd);
                    result.name = opponentNameString;
                } else {
                    LOGGER.warn("Challenged, but opponent not found.");
                }
            } else {
                LOGGER.warn("Challenged, but opponent not found.");
            }
        }
        this.applications.clear();
        int appsStart = response.indexOf("Ищут соперника");
        if (appsStart > 0) {
            appsStart = response.indexOf("<table", appsStart);
            int appsEnd = response.indexOf("/table", appsStart + "<table".length());
            if (appsEnd > 0) {
                ParserMarker[] markers = new ParserMarker[]{ParserMarker.APP_ID, ParserMarker.APP_USER_ID};//, ParserMarker.APP_TIME, ParserMarker.APP_TYPES, ParserMarker.APP_GLAD_LIMIT, ParserMarker.APP_GLAD_LEVEL, ParserMarker.APP_TOTAL_LEVEL};
                parser.parsePage(response.substring(appsStart, appsEnd), markers, ApplicationModelFactory.getInstance(), MergingStrategy.REPLACE, this.applications, true);
            }
        }
        if (!response.contains("Отказаться"))
            result = null;
        checkBattle(response);
        return result;
    }

    public void arenaBot(BattleType type) throws IOException {
        HttpPost post = new HttpPost(this.hostprefix + ARENA_BOT + type.getId());
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("act", "bot"));
        execute(post, formparams);
        BonusHistory.use(1);
    }

    @Override
    public double getBonuses() {
        return this.bonuses;
    }

    @Override
    public long getDinaries() {
        return this.denaries;
    }

    @Override
    public void arenaCancel() throws IOException {
        HttpPost post = new HttpPost(this.hostprefix + ARENA_CANCEL);
        /*       step	1
                type	arena/arena
                firstpage	/xml/arena/arena.php?type=arena/arena&act=battlesapp&id=1&form_ok=1
                act	my
                id	1
                id	1
                do
                    typeid	1
                Timeout	10
                LimitGlad	7
                LimitSkl	120
                MaxLevel	20
                RecordID	12463042
                oldact	my
        */
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "arena/arena"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/arena/arena.php"));
        formparams.add(new BasicNameValuePair("act", "my"));
        formparams.add(new BasicNameValuePair("id", "" + 1));
        formparams.add(new BasicNameValuePair("typeid", "" + 1));
        formparams.add(new BasicNameValuePair("do", ""));
        formparams.add(new BasicNameValuePair("Timeout", ""));
        formparams.add(new BasicNameValuePair("LimitGlad", ""));
        formparams.add(new BasicNameValuePair("MaxLevel", ""));
        formparams.add(new BasicNameValuePair("LimitSkl", ""));
        formparams.add(new BasicNameValuePair("RecordID", ""));
        formparams.add(new BasicNameValuePair("oldact", "my"));
        execute(post, formparams);

    }

    @Override
    public void arenaAccept() throws IOException {
        HttpGet get = new HttpGet(this.hostprefix + ARENA_ACCEPT);
        execute(get);
    }

    @Override
    public void arenaReject() throws IOException {
        HttpGet get = new HttpGet(this.hostprefix + ARENA_REJECT);
        execute(get);
    }

    public void arenaChallenge(int typeid, long id) throws IOException {
        HttpGet get = new HttpGet(this.hostprefix + ARENA_CHALLENGE + "&typeid=" + typeid + "&id=" + id);
        execute(get);
    }

    @Override
    public Map<Long, Application> getApplications() {
        return this.applications;
    }

    public CRoster getRoster() throws Exception {
        String roster = execute(new HttpGet(this.hostprefix + ROSTER));
        FileOutputStream fileOutputStream = new FileOutputStream("roster.xml");
        //IOUtils.write(roster, fileOutputStream);
        CRoster cRoster = new Persister(new AnnotationStrategy()).read(CRoster.class, roster);
        return cRoster;
    }

    public Map<Long, Challenge> getChallenges() throws IOException {
        String challengesResponse = execute(new HttpGet(this.hostprefix + CHALLENGES));
        ParserMarker[] challengesMarkers = new ParserMarker[]{ParserMarker.ATTACKING, ParserMarker.DEFENDING, ParserMarker.CHALLENGE_STARTED, ParserMarker.CHALLENGE_ID};
        parser.parsePage(challengesResponse, challengesMarkers, ChallengeModelFactory.getInstance(), MergingStrategy.REPLACE, challenges, false);
        return challenges;
    }

    @Override
    public Map<Long, Position> getPositions(Long id) throws IOException {
        String challengeResponse = execute(new HttpGet(this.hostprefix + CHALLENGE + id));
        int start = challengeResponse.indexOf("Разрешены");
        int end = challengeResponse.indexOf("</table>", start) + "</table>".length();
        ParserMarker[] challengesMarkers = new ParserMarker[]{ParserMarker.TR_START, ParserMarker.POSITION_DEFENDING_USER, ParserMarker.POSITION_ID, ParserMarker.POSITION_TAKE, ParserMarker.POSITION_ATTACK};
        parser.parsePage(challengeResponse.substring(start, end), challengesMarkers, PositionModelFactory.getInstance(), MergingStrategy.REPLACE, positions, false);
        return positions;
    }

    @Override
    public void attackPosition(Long id, Long pid) throws IOException {
        execute(new HttpGet(this.hostprefix + POSITION_ATTACK + "&id=" + id + "&pid=" + pid));
    }

    @Override
    public void takePosition(Long id, Long pid) throws IOException {
        execute(new HttpGet(this.hostprefix + POSITION_TAKE + "&id=" + id + "&pid=" + pid));
    }

    @Override
    public void takeArmor(Long id, int type, int level) throws IOException {
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/armor"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/armor.php?act=my"));
        formparams.add(new BasicNameValuePair("act", "getarmor"));
        formparams.add(new BasicNameValuePair("ArmorID", Long.toString(id)));
        formparams.add(new BasicNameValuePair("TypeID", Integer.toString(type)));
        formparams.add(new BasicNameValuePair("Level", Integer.toString(level)));
        formparams.add(new BasicNameValuePair("oldact", "getarmor"));
        execute(new HttpPost(this.hostprefix + TAKE_ARMOR), formparams);
    }

    @Override
    public void buyArmor(Armor armor) throws IOException {
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/armor"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/armor.php?id=" + armor.getId() + "&act=buy"));
        formparams.add(new BasicNameValuePair("act", "buy"));
        formparams.add(new BasicNameValuePair("id", Long.toString(armor.getId())));
        formparams.add(new BasicNameValuePair("level", Long.toString(armor.getLevel())));
        formparams.add(new BasicNameValuePair("oldact", "buy"));
        execute(new HttpPost(this.hostprefix + BUY_ARMOR), formparams);
    }

    @Override
    public void dropArmor(CArmor armor) throws IOException {
        Utils.soutn("Удаление брони " + GladType.byID(armor.getTypeid()).getName() + " " + (armor.getLevel() - 1) * 10 + " - " + (armor.getLevel() * 10 + 9) + " мораль " + armor.getMorale());
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        formparams.add(new BasicNameValuePair("step", "1"));
        formparams.add(new BasicNameValuePair("type", "gladiators/armor"));
        formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/armor.php?id=" + armor.getId() + "&act=drop"));
        formparams.add(new BasicNameValuePair("act", "drop"));
        formparams.add(new BasicNameValuePair("id", Long.toString(armor.getId())));
        formparams.add(new BasicNameValuePair("Drop", ""));
        formparams.add(new BasicNameValuePair("oldact", "drop"));
        execute(new HttpPost(this.hostprefix + DROP_ARMOR), formparams);
    }


    @Override
    public void updateFreeArmors(Collection<AModel> goldFreeArmors, Collection<AModel> silverFreeArmors) throws IOException {
        String myArmors = execute(new HttpGet(this.hostprefix + MY_ARMORS));
        ParserMarker[] goldArmorsMarkers = new ParserMarker[]{ParserMarker.CAN_TAKE_GOLD_ARMOR, ParserMarker.ARMOR_ID};
        ParserMarker[] silverArmorsMarkers = new ParserMarker[]{ParserMarker.CAN_TAKE_SILVER_ARMOR, ParserMarker.ARMOR_ID};
        Map<Long, AModel> goldArmors = new HashMap<Long, AModel>();
        Map<Long, AModel> silverArmors = new HashMap<Long, AModel>();
        parser.parsePage(myArmors, goldArmorsMarkers, AModelFactory.getInstance(), MergingStrategy.REPLACE, goldArmors, false);
        parser.parsePage(myArmors, silverArmorsMarkers, AModelFactory.getInstance(), MergingStrategy.REPLACE, silverArmors, false);
        goldFreeArmors.clear();
        silverFreeArmors.clear();
        goldFreeArmors.addAll(goldArmors.values());
        silverFreeArmors.addAll(silverArmors.values());
    }

    @Override
    public void updateaArmorsShop(Collection<Armor> armory) throws IOException {
        armory.clear();
        for (int i = 1; i <= 5; ++i) {
            String response = execute(new HttpGet(this.hostprefix + ARMORY + "?level=" + i));
            String shopArmors = response.substring(0, response.indexOf("Вооружение гладиаторов продается комплектами"));
            ParserMarker[] markers = new ParserMarker[]{ParserMarker.COMPLECT, ParserMarker.COMPLECT_ID, ParserMarker.COMPLECT_MORALE};
            Map<Long, Armor> sar = new HashMap<Long, Armor>();
            parser.parsePage(shopArmors, markers, ArmorModelFactory.getInstance(), MergingStrategy.REPLACE, sar, false);
            for (Armor armor : sar.values()) {
                armor.setLevel(i);
            }
            armory.addAll(sar.values());
        }

    }

    public long getServerClientDateDifference() {
        return serverClientDateDifference;
    }

    @Override
    public void takeFromHostel(long hostelGladId) throws IOException {
        execute(new HttpGet(this.hostprefix + TAKE_FROM_HOSTEL + hostelGladId + "&step=1"));
    }

    public void champGuild() throws IOException {
        String page = execute(new HttpGet(this.hostprefix + CHAMP_GUILD + "?act=requests"));
        checkBattle(page);
    }
}
