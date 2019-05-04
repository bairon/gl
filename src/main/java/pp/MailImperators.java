package pp;

import hlp.MyHttpClient;
import pp.model.Guild;
import pp.model.IModel;
import pp.model.Player;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class MailImperators {
	public static final String domain = "s2.gladiators.ru";
	public static final Set<Long> excluded = new HashSet<Long>(Arrays.asList(20040828L, 20011096L, 20002581L, 20080818L, 20013466L));
	public static final String message = "Друзья, поскольку происходит полный ахтунг с проведением сенатов, постановляю.\n" +
			"Сбор в сенаты проводить в строго определенные часы:\n" +
			"09:00, 12:00, 15:00, 18:00, 21:00, 22:00, 23:00, 00:00 по Москве.\n" +
			"Явка очень желательна хотя бы в 12:00 и начиная с 18:00\n" +
			"ОГРОМНАЯ ПРОСЬБА БЫТЬ НЕ В ТУРЕ в эти часы И НЕ ОТВЛЕКАТЬСЯ от чата с 00 до 05 минут, дабы остальные вас не ждали, тратя впустую драгоценное время.\n" +
			"Спасибо.";

	public static void main(String[] args) {
		MyHttpClient client = new MyHttpClient();
		client.appendInitialCookie("PHPSESSID", "66bc7c257e046e05519add1b7b1382f3", domain);
		client.appendInitialCookie("cookie_lang_3", "rus", domain);
		try {
            Properties props = new Properties();
            props.load(new FileInputStream("settings.txt"));
            GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), props.get("SERVER").toString().trim());
            service.login(props.get("USER").toString(), props.get("PASSW").toString());
            Utils.sleep(1000);
			//service.mail("alsa", "ЧАСЫ ПРОВЕДЕНИЯ СЕНАТОВ", message);
			while (true) {
				service.visitGuild(10L);
				service.visitGuild(15L);
				Map<Long, Guild> guilds = service.getGuilds();
				System.out.println("-----------------------------------------------------");
				List<Player> senators = new ArrayList<Player>();
				List<Player> imperators = new ArrayList<Player>();
				for (Long guildId : guilds.keySet()) {
					Guild guild = (Guild) guilds.get(guildId);
					for (IModel iModel : guild.getPlayers().values()) {
						Player p = (Player) iModel;
						if ((p.getOnline() == null ? false : p.getOnline()) && (p.getVip() == null ? 0 : p.getVip()) > 0 && !excluded.contains(p.getId())) {
							senators.add(p);
						}
						if (p.getOnline() == null ? false : p.getOnline() && (p.getLvl() == null ? 0 : p.getLvl()) > 8 && !excluded.contains(p.getId())) {
							imperators.add(p);
						}
					}

				}
				System.out.print("Imperators:" + imperators.size());
				System.out.println(Arrays.deepToString(imperators.toArray()));
				if (imperators.size() >= 2) {
					System.out.println("Emailing...");
					for (Player imperator : imperators) {
						try {
							//service.mail(senator.getName(), "ЧАСЫ ПРОВЕДЕНИЯ СЕНАТОВ", message);
							service.mail(imperator.getName(), "Друзья, зайдите в чат гильдии", "");//, "Заходим в чат и готовимся в импер! Нас " + imperators.size() + Arrays.deepToString(imperators
									//.toArray()));
						} catch (IOException e) {
						}
						Utils.sleep(100);
					}
					break;
				}
				Utils.sleep(10000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
