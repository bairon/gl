package pp;

import pp.model.Guild;
import pp.model.IModel;
import pp.model.Player;
import pp.model.comparators.IdComparator;
import pp.service.GlRuService;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class GuildMonitorActivity extends Activity {
	List<Long> guildIds = Arrays.asList(10L, 15L);

	protected GuildMonitorActivity(final GlRuService service) {
		super(service);
	}

	@Override
	public void doSome() {
		try {
/*
			service.visitGuilds();
			Map<Long,IModel> guilds = service.getGuilds();
			List<IModel> guildList = new ArrayList<IModel>(guilds.values());
			Collections.sort(guildList, new IdComparator());
			Utils.print(guildList);
*/
			for (Long guildId : guildIds) {
				service.visitGuild(guildId);

			}
			Map<Long, Guild> guilds = service.getGuilds();
			System.out.println("-----------------------------------------------------");
			List<Player> senators = new ArrayList<Player>();
			List<Player> imperators = new ArrayList<Player>();
			List<Player> total = new ArrayList<Player>();
			for (Long guildId : guildIds) {
				Guild guild = (Guild) guilds.get(guildId);
				for (IModel iModel : guild.getPlayers().values()) {
					Player p = (Player) iModel;
					if (p.getOnline() == null ? false : p.getOnline() && p.getVip() == null ? false : p.getVip() > 0) {
						senators.add(p);
					}
					if (p.getOnline() == null ? false : p.getOnline() && p.getLvl() == null ? false : p.getLvl() > 8) {
						imperators.add(p);
					}
					if (p.getOnline() == null ? false : p.getOnline()) {
						total.add(p);
					}
				}

			}
			DateFormat df = DateFormat.getInstance();
			System.out.println(df.format(new Date()) + " " + senators.size() + "/" + imperators.size());
			System.out.print("Senators:" + senators.size());
			System.out.println(Arrays.deepToString(senators.toArray()));
			System.out.print("Imperators:" + imperators.size());
			System.out.println(Arrays.deepToString(imperators.toArray()));
			System.out.print("Total:" + total.size());
			System.out.println(Arrays.deepToString(total.toArray()));
			completed = true;
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void setGuildIds(final List<Long> guildIds) {
		this.guildIds = guildIds;
	}

	public List<Long> getGuildIds() {
		return guildIds;
	}
}
