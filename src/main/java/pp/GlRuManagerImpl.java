package pp;

import hlp.MyHttpClient;
import noNamespace.RosterDocument;
import noNamespace.XmlDocument;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.xmlbeans.XmlException;
import pp.exception.NotLoggedInException;
import pp.model.Glad;
import pp.model.IModel;
import pp.model.Stuff;
import pp.model.comparators.GladLvlComparator;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GlRuManagerImpl implements GlRuManager, Runnable {
	public static final long UID = 20040933L;
	public static final String MY_GLADS = "http://s2.gladiators.ru/xml/gladiators/";
	public static final String TRAIN = "http://s2.gladiators.ru/xml/gladiators/train.php";
	public static final String RECOVERY = "http://s2.gladiators.ru/xml/gladiators/recovery.php";
	public static final String TRNMS = "http://s2.gladiators.ru/xml/arena/tournaments.php?return=1&typeid=9";
	public static final String TRNMS_JOINED = "http://s2.gladiators.ru/xml/arena/tournaments.php";
	public static final String BUILDER = "http://s2.gladiators.ru/builder/?id=";
	public static final String ROSTER = "http://s2.gladiators.ru/builder/get_xml_roster.php?id=" + Long.toString(UID);
	public static final String SEND = "http://s2.gladiators.ru/builder/send.php";
	public static final String FIGHT = "http://s2.gladiators.ru/xml/arena/fight.php?id=";
	public static final String HEAL = "http://s2.gladiators.ru/xml/gladiators/recovery.php?type=gladiators/recovery&act=select";
	public static final String TRN_JOIN = "http://s2.gladiators.ru/xml/arena/tournaments.php?act=join&step=1&id=";
	public static final String TRN_LEAVE = "http://s2.gladiators.ru/xml/arena/tournaments.php?act=cancel&step=1&id=";

	private Thread t;
	private boolean pause;
	private Set<StrategySetting> settings;
	private Activity current;
	private MyHttpClient client;
	private final Map<Long, IModel> glads = new HashMap<Long, IModel>();
	private final Map<Long, IModel> trnms = new HashMap<Long, IModel>();
	private final Map<Long, IModel> bttls = new HashMap<Long, IModel>();
	private final Map<Long, IModel> stuff = new HashMap<Long, IModel>();
	private static final GlRuParser parser = new GlRuParser();
	public static final boolean USE_STUBS = false;
	public static final String TRNM_STUB = "D:\\Projects\\gl\\response-samples\\tournaments-go-to-fight.html";
	public static final String BUILDER_STUB = "D:\\Projects\\gl\\response-samples\\builder.html";
	public static final String ROSTER_STUB = "D:\\Projects\\gl\\response-samples\\roster.xml";
	public static final String FIGHT_STUB = "D:\\Projects\\gl\\response-samples\\fight.html";
	public static final String JOIN_STUB = "D:\\Projects\\gl\\response-samples\\fight.html";
	public static final String LEAVE_STUB = "D:\\Projects\\gl\\response-samples\\fight.html";
	public static final String RECOVERY_STUB = "D:\\Projects\\gl\\response-samples\\recovery.html";
	private GlRuService service;
	private boolean firstTime = true;
	private int switcher = -1;
	public GlRuManagerImpl(final MyHttpClient client) {
		this.client = client;
		service = new GlRuServiceImpl(client, parser, "s2.gladiators.ru");
	}

	public void updateAll() throws IOException {
		updateGlads();
		updateTrain();
		String page = updateRecovery();
		updateStuff(page);
		updateTrnms();
	}

	public void updateGlads() throws IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		HttpGet getGlads = new HttpGet(MY_GLADS);
		String responseBody = client.execute(getGlads, responseHandler);
		parser.parseMyGlads(responseBody, glads);
	}

	public void updateTrain() throws IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet getTrain = new HttpGet(TRAIN);
		String responseBody = client.execute(getTrain, responseHandler);
		parser.parseTrain(responseBody, glads);

	}

	public String updateRecovery() throws IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet getRecovery = new HttpGet(RECOVERY);
		String responseBody = USE_STUBS ? readFileAsString(RECOVERY_STUB) : client.execute(getRecovery, responseHandler);
		parser.parseRecovery(responseBody, glads);
		return responseBody;
	}

	public String updateTrnms() throws IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet getTrnm = new HttpGet(TRNMS);
		String responseBody = USE_STUBS ? readFileAsString(TRNM_STUB) :
				client.execute(getTrnm, responseHandler);
		//System.out.println(responseBody);
		parser.parseTrnmnts(responseBody, trnms);
		return responseBody;
	}
	public void updateStuff(String page) {
		parser.parseStuff(page, stuff);
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

	public void builder(final long id) throws IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet builder = new HttpGet(BUILDER + Long.toString(id));
		String responseBody = USE_STUBS ? readFileAsString(BUILDER_STUB) :
				client.execute(builder, responseHandler);
		parser.parseBuilder(responseBody, bttls);

	}
	public RosterDocument getRoster() throws IOException, XmlException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet getRoster = new HttpGet(ROSTER);
		String responseBody = USE_STUBS ? readFileAsString(ROSTER_STUB) :
				client.execute(getRoster, responseHandler);
		//responseBody = responseBody.substring(responseBody.indexOf("<roster>"));
		//responseBody = responseBody.replace("<roster>", "<roster xmlns=\"http://pp\">");
		//System.out.println(responseBody);
		RosterDocument rosterDocument = RosterDocument.Factory.parse(responseBody);
		return rosterDocument;
	}

	public void send(final XmlDocument placement) throws IOException {
		HttpPost post = new HttpPost(SEND);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("xml", placement.toString()));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = USE_STUBS ? "STUB SENT" : client.execute(post, responseHandler);
		System.out.println("Sent POST with " + placement);
		System.out.println("Response contains " + responseBody);
	}
	public void joinTrn(final long id) throws IOException {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! JOIN TOURNAMENT " + id + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet join = new HttpGet(TRN_JOIN + Long.toString(id));
		String responseBody = USE_STUBS ? readFileAsString(JOIN_STUB) :
				client.execute(join, responseHandler);
	}

	public void leaveTrn(final long id) throws IOException {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! LEAVE TOURNAMENT " + id + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet leave = new HttpGet(TRN_LEAVE + Long.toString(id));
		String responseBody = USE_STUBS ? readFileAsString(LEAVE_STUB) :
				client.execute(leave, responseHandler);
	}
	public String watchFight(final long id) throws IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet fight = new HttpGet(FIGHT + Long.toString(id));
		String responseBody = USE_STUBS ? readFileAsString(FIGHT_STUB) :
				client.execute(fight, responseHandler);
		String winner = parser.parseFight(responseBody);
		return winner;
	}

	public void start() {
		t = new Thread(this);
		t.start();
	}

	public void stop() {
		t.interrupt();
	}

	public void pause() {
		pause = true;
	}

	public void addSetting(final StrategySetting setting) {
		settings.add(setting);
	}

	public void removeSetting(final StrategySetting setting) {
		settings.remove(setting);
	}

	public void run() {
		try {
			service.visitMyOffice();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotLoggedInException nle) {
			try {
                Properties props = new Properties();
                props.load(new FileInputStream("settings.txt"));
                service.login(props.get("USER").toString(), props.get("PASSW").toString());
            } catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			while (true) {
				if (pause) {
					t.sleep(1000);
				} else {
					if (current != null && !current.isCompleted()) {
						current.doSome();
						t.sleep(15000);
					} else {
						t.sleep(1000);
						current = findActivity();
						//t.sleep(10000);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Activity findActivity() {
		return new RecruitsActivity(service);

/*
		switcher++;
		switcher%=3;
		switch (switcher) {
		case 0:
			return new GuildMonitorActivity(service);
		case 1:
			return new TournamentReporterActivity(service);
		case 2:
			return new MapActivity(service, Arrays.asList(161L, 160L, 162L, 78L, 77L, 75L, 76L));//, 73L, 74L, 64L, 72L, 71L, 69L, 70L, 65L, 66L, 68L, 67L, 168L));
		default:  new MapActivity(service, Arrays.asList(161L, 160L, 162L, 78L, 77L, 75L, 76L));//, 73L, 74L, 64L, 72L, 71L, 69L, 70L, 65L, 66L, 68L, 67L, 168L));
		}
		return null;
*/

	}

	public Map<Long, IModel> getGlads() {
		return glads;
	}

	public Map<Long, IModel> getTrnms() {
		return trnms;
	}

	public Map<Long, IModel> getBttls() {
		return bttls;
	}

	public void heal(final long maxLevel) {
		try {
			String page = updateRecovery();
			updateStuff(page);
			Stuff stuff = (Stuff) this.stuff.values().iterator().next();
			if (stuff == null || stuff.getMasseur() == 0) return;
			double allHitsPercent = getSummHitsPercent();
			double maxHitPercent = getMaxHitPercent();
			if (maxHitPercent < 20d && stuff.getRstGld() > 0) {
				restoreGld();
				updateAll();
			}
			if (stuff.getMasseur() < 10 && stuff.getPriest() < 10 && stuff.getDoctor() < 10) {
				restoreSpc();
				updateAll();
			}
			ArrayList<IModel> sortedGlads = new ArrayList<IModel>(glads.values());
			Collections.sort(sortedGlads, new GladLvlComparator());
			for (IModel model : sortedGlads) {
				Glad glad = (Glad) model;
				if (stuff.getMasseur() > 0 && glad.getLvl() <= maxLevel && (double)glad.getHealth() > ((double)glad.getMaxHealth()) / 2d && glad.getHealth() < glad.getMaxHealth()) {

/*
					try {
						healGlad(glad.getId());
					} catch (IOException e) {
						e.printStackTrace();
					}
					Utils.sleep(1500);
					page = updateRecovery();
					updateStuff(page);
*/

				}
				if (stuff.getPriest() > 0 && glad.getLvl() <= maxLevel && (glad.getMorale() < 0 || (glad.getMorale() >= 8 && glad.getMorale() < 10))) {
/*
					try {
						upMorale(glad.getId());
					} catch (IOException e) {
						e.printStackTrace();
					}
					Utils.sleep(1500);
					page = updateRecovery();
					updateStuff(page);
*/
				}
				if (stuff.getDoctor() > 0 && glad.getLvl() <= maxLevel && glad.getInjury() > 0 && glad.getInjury() <=3) {

/*
					try {
						upInjury(glad.getId());
					} catch (IOException e) {
						e.printStackTrace();
					}
					Utils.sleep(1500);
					page = updateRecovery();
					updateStuff(page);
*/

				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void upInjury(final long id) throws IOException {
		Stuff stuff = (Stuff) this.stuff.values().iterator().next();
		ArrayList<IModel> sortedGlads = new ArrayList<IModel>(glads.values());
		Collections.sort(sortedGlads, new GladLvlComparator());
		HttpPost post = new HttpPost(HEAL);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("step", "1"));
		formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
		formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
		formparams.add(new BasicNameValuePair("act", "select"));
		formparams.add(new BasicNameValuePair("numrows", Integer.toString(glads.size())));
		double maxDelta = ((double)stuff.getDoctor()) / 10d;
		if (maxDelta < 0.5) return;
		for (IModel model : sortedGlads) {
			Glad glad = (Glad) model;
			Double upInjury = (glad.getInjury() > 0 && glad.getInjury() <= 3) ? ((glad.getInjury() - maxDelta >= 0) ? glad.getInjury() - maxDelta : 0)  : glad.getInjury();
			int currentPercentH = (int)((100d * glad.getHealth()) / glad.getMaxHealth());
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", Integer.toString(currentPercentH)));
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
			Double inj = glad.getInjury();
			if (glad.getId() == id) {
				 inj = upInjury;
			}
			String injVal = Double.toString(inj);
			if (inj < 0.01) injVal = "0";

			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", injVal));
		}
		String masseur = Long.toString(stuff.getMasseur());
		formparams.add(new BasicNameValuePair("Health", masseur));
		String morale = Long.toString(stuff.getPriest());
		formparams.add(new BasicNameValuePair("Morale", morale));
		String injury = Long.toString(stuff.getDoctor());
		formparams.add(new BasicNameValuePair("Injury", "0"));
		formparams.add(new BasicNameValuePair("oldact", "select"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		System.out.println("Sending UP INJ with " + Arrays.deepToString(formparams.toArray()));
		String responseBody = USE_STUBS ? "STUB SENT" : client.execute(post, responseHandler);
		System.out.println("Response contains " + responseBody);

	}

	private void upMorale(final long id) throws IOException {
		Stuff stuff = (Stuff) this.stuff.values().iterator().next();
		ArrayList<IModel> sortedGlads = new ArrayList<IModel>(glads.values());
		Collections.sort(sortedGlads, new GladLvlComparator());
		double maxDelta = ((double)stuff.getPriest()) / 10d;
		if (maxDelta < 1) return;
		HttpPost post = new HttpPost(HEAL);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("step", "1"));
		formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
		formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
		formparams.add(new BasicNameValuePair("act", "select"));
		formparams.add(new BasicNameValuePair("numrows", Integer.toString(glads.size())));
		for (IModel model : sortedGlads) {
			Glad glad = (Glad) model;
			int currentPercentH = (int)((100d * glad.getHealth()) / glad.getMaxHealth());
			long UPmorale = glad.getMorale() < 0 ? (glad.getMorale() + maxDelta >= 0 ? 0 : (int)(glad.getMorale() + maxDelta)) : (glad.getMorale() >= 8 ? (glad.getMorale() + maxDelta >= 10 ? 10 :
					(int)(glad.getMorale() + maxDelta))	: glad.getMorale());
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", Integer.toString(currentPercentH)));
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getId() == id ? UPmorale : glad.getMorale())));
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", Double.toString(glad.getInjury())));
		}
		String masseur = Long.toString(stuff.getMasseur());
		formparams.add(new BasicNameValuePair("Health", masseur));
		String morale = Long.toString(stuff.getPriest());
		formparams.add(new BasicNameValuePair("Morale", morale));
		String injury = Long.toString(stuff.getDoctor());
		formparams.add(new BasicNameValuePair("Injury", injury));
		formparams.add(new BasicNameValuePair("oldact", "select"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		System.out.println("Sending UP MORALE with " + Arrays.deepToString(formparams.toArray()));
		String responseBody = USE_STUBS ? "STUB SENT" : client.execute(post, responseHandler);
		System.out.println("Response contains " + responseBody);
	}

	private void healGlad(final long id) throws IOException {
		Stuff stuff = (Stuff) this.stuff.values().iterator().next();
		ArrayList<IModel> sortedGlads = new ArrayList<IModel>(glads.values());
		Collections.sort(sortedGlads, new GladLvlComparator());
		double maxDelta = ((double)stuff.getMasseur()) / 5d;
		if (maxDelta <= 1) return;
		HttpPost post = new HttpPost(HEAL);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("step", "1"));
		formparams.add(new BasicNameValuePair("type", "gladiators/recovery"));
		formparams.add(new BasicNameValuePair("firstpage", "/xml/gladiators/recovery.php?"));
		formparams.add(new BasicNameValuePair("act", "select"));
		formparams.add(new BasicNameValuePair("numrows", Integer.toString(glads.size())));
		for (IModel model : sortedGlads) {
			Glad glad = (Glad) model;
			int currentPercentH = (int)((100d * glad.getHealth()) / glad.getMaxHealth());
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "H", glad.getId() == id ? ((glad.getHealth() + 10 * maxDelta >= 100) ?  "100" : Integer.toString((int)(glad.getHealth()
					+ 10 * maxDelta))) : Integer.toString(currentPercentH)));
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "M", Long.toString(glad.getMorale())));
			formparams.add(new BasicNameValuePair(Long.toString(glad.getId()) + "I", Double.toString(glad.getInjury())));
		}
		String masseur = Long.toString(stuff.getMasseur());
		formparams.add(new BasicNameValuePair("Health", "0"));
		String morale = Long.toString(stuff.getPriest());
		formparams.add(new BasicNameValuePair("Morale", morale));
		String injury = Long.toString(stuff.getDoctor());
		formparams.add(new BasicNameValuePair("Injury", injury));
		formparams.add(new BasicNameValuePair("oldact", "select"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		System.out.println("Sending HEAL GLAD with " + Arrays.deepToString(formparams.toArray()));
		String responseBody = USE_STUBS ? "STUB SENT" : client.execute(post, responseHandler);
		System.out.println("Response contains " + responseBody);
	}

	private void restoreGld() throws IOException {

		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!11 Restoring gladiators !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111111111");
/*
		HttpPost post = new HttpPost(HEAL);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("act", "trestore_all"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		System.out.println("Sending RESTORE GLAD with " + Arrays.deepToString(formparams.toArray()));
		String responseBody = USE_STUBS ? "STUB SENT" : client.execute(post, responseHandler);
		System.out.println("Response contains " + responseBody);
*/


	}
	private void restoreSpc() throws IOException {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!11 Restoring specialists !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111111111");
/*
		HttpPost post = new HttpPost(HEAL);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("act", "restore"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		System.out.println("Sending RESTORE SPEC with " + Arrays.deepToString(formparams.toArray()));
		String responseBody = USE_STUBS ? "STUB SENT" : client.execute(post, responseHandler);
		System.out.println("Response contains " + responseBody);
*/

	}

	private double getMaxHitPercent() {
		double maxHPerc = 0;
		for (IModel iModel : glads.values()) {
			Glad glad = (Glad) iModel;
			double currentPercentH = ((100d * glad.getHealth()) / glad.getMaxHealth());
			if (currentPercentH > maxHPerc) {
				maxHPerc = currentPercentH;
			}
		}
		return maxHPerc;
	}

	private double getSummHitsPercent() {
		double summ = 0;
		for (IModel iModel : glads.values()) {
			Glad glad = (Glad) iModel;
			double currentPercentH = ((100d * glad.getHealth()) / glad.getMaxHealth());
				summ += currentPercentH;
		}
		return summ;
	}
	public long getJoinedTrnm() throws IOException {
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet fight = new HttpGet(TRNMS_JOINED);
		String responseBody = USE_STUBS ? readFileAsString(JOIN_STUB) :
				client.execute(fight, responseHandler);
		return parser.parseJoinedTrnm(responseBody);
	}
}
