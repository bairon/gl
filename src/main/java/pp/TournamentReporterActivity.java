package pp;

import pp.model.Guild;
import pp.model.IModel;
import pp.model.Player;
import pp.model.PlayerStat;
import pp.model.Trnm;
import pp.service.GlRuService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class TournamentReporterActivity extends Activity {
	public static final Set<Long> ourPlayers = new HashSet<Long>();//Arrays.asList(20053151L, 20012078L, 20057691L, 20093619L, 20080818L, 20089804L, 20002581L, 20035700L, 20005291L, 20076239L,
	// 20002805L, 			20011096L  			));
	private boolean completed = false;
	private String inTur;
	public TournamentReporterActivity(final GlRuService service, final String inTur) {
		super(service);
		this.inTur = inTur;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}
	private Set<Long> nOurPlayers = new HashSet<Long>();
	@Override
	public void doSome() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, 9, 19, 18, 1, 1);

		try {
			//service.loadSenates();
			service.updateSenates(calendar.getTime());
			//service.saveSenates();
			long last = 0;
			long tours = 0;
			calendar.set(2011, 9, 5, 0, 0, 1);
			Map<Long, PlayerStat> stats = new HashMap<Long, PlayerStat>();
			for (Trnm trnm : service.getSenates().values()) {
				//if (trnm.getStart().before(calendar.getTime())) continue;
				if (trnm.getStart().getTime() > last) {
					last = trnm.getStart().getTime();
				}
				if (sliti(trnm)) {
					tours++;
					Map<Long, Player> players = trnm.getPlayers();
					for (IModel iModel : players.values()) {
						Player player = (Player) iModel;

						long id = player.getId();
						PlayerStat stat = stats.get(id);
						String name = player.getName();
						if (Utils.isBlank(inTur) || inTur.contains(name)) {
							if (stat == null) {
								stat = new PlayerStat();
								stat.setId(id);
								stat.setName(name);
								stats.put(id, stat);
							}
							if (trnm.getWinner().equals(id)) {
								stat.setWins((stat.getWins() == null ? 0 : stat.getWins()) + 1);
							} else if (trnm.getFinalist().equals(id)) {
								stat.setFinals((stat.getFinals() == null ? 0 : stat.getFinals()) + 1);
							}
							stat.setParticipates((stat.getParticipates() == null ? 0L : stat.getParticipates()) + 1L);

						}
					}
				}
			}
			List<PlayerStat> sorted = new ArrayList<PlayerStat>(stats.values());
			Collections.sort(sorted, new Comparator<PlayerStat>() {
				@Override
				public int compare(final PlayerStat o1, final PlayerStat o2) {
					return ((Double) o2.getRatio()).compareTo(o1.getRatio());
					//return ((Long) o2.getParticipates()).compareTo(o1.getParticipates());
				}
			});
			double ratio = 0;
			System.out.println("------------------------------------------------------------------------------------");
			for (PlayerStat playerStat : sorted) {
				System.out.println(playerStat);
				ratio+=playerStat.getRatio();
			}
			System.out.println("Av. ratio:" + ratio / (double) sorted.size());
			System.out.println("Tours: " + tours);
			System.out.println(Arrays.toString(nOurPlayers.toArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		completed = true;
	}


	private boolean sliti(final Trnm trnm) {
		Map<Long, Player> players = trnm.getPlayers();
		final Long winner = trnm.getWinner();
		final Long finalist = trnm.getFinalist();
		return ourGuild(((Player)players.get(winner)).getGuildId()) && ourGuild(((Player)players.get(finalist)).getGuildId());
/*
		List<Long> notOur = new ArrayList<Long>();
		long winner = trnm.getWinner();
		Map<Long, IModel> players = trnm.getPlayers();
		long finalist = trnm.getFinalist();
		if (ourGuild(((Player)players.get(winner)).getGuildId()) && ourGuild(((Player)players.get(finalist)).getGuildId())) {
			int notOurPlayers = 0;
			for (Long id : players.keySet()) {
				if (ourPlayer(id) == null) {
					notOurPlayers++;
					notOur.add(id);
				}
			}
			if (notOurPlayers <= 2) {
				nOurPlayers.addAll(notOur);
			}
			if (notOurPlayers <= 0) {
				return true;
			}
		}
		return false;
*/
	}
	private static final boolean ourGuild(Long guildid) {
		return true;//guildid == 10L || guildid == 15L;
	}
}
