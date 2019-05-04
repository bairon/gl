package pp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Guild extends AModel {
	private String name;
	private Map<Long, Player> players;
	private Long points;

	public Guild() {
	}

	public Guild(final long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Map<Long, Player> getPlayers() {
		return players;
	}

	public void setPlayers(final Map<Long, Player> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		List<Player> online = null;
		if (players != null) {
			online = new ArrayList<Player>();
			for (IModel iModel : players.values()) {
				Player p = (Player) iModel;
				if (p.getOnline() && p.getVip() > 0) {
					online.add(p);
				}
			}

		}
		return "{" + name + (online == null ? "" : Arrays.deepToString(online.toArray())) + '}';
	}
	public int getOnlineSenatorCount() {
		List<Player> online = null;
		if (players != null) {
			online = new ArrayList<Player>();
			for (IModel iModel : players.values()) {
				Player p = (Player) iModel;
				if (p.getOnline() && p.getVip() > 0) {
					online.add(p);
				}
			}

		}
		return online.size();
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(final Long points) {
		this.points = points;
	}
}
