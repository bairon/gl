package pp.model;

import pp.model.enums.Prize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class City extends AModel{
	private Map<Long, IModel> pretendents = new HashMap<Long, IModel>();
	private Prize prize;
	private Map<Long, IModel> players = new HashMap<Long, IModel>();
	private String name;
	private static final long myguild = 10L;

	public Map<Long, IModel> getPretendents() {
		return pretendents;
	}

	public void setPretendents(final Map<Long, IModel> pretendents) {
		this.pretendents = pretendents;
	}

	public Prize getPrize() {
		return prize;
	}

	public void setPrize(final Prize prize) {
		this.prize = prize;
	}

	public Map<Long, IModel> getPlayers() {
		return players;
	}

	public void setPlayers(final Map<Long, IModel> players) {
		this.players = players;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return safeNull(name) + " - " + safeNull(prize) + " - " + getScore() + " - " + safeNull(players.values());
	}

    private Object safeNull(Object o) {
        if (o == null) return "null";
        return o;
    }

    public String getScore() {
        if (pretendents == null) return "";
		long ourscore = 0;
		long maxscore = 0;
		for (IModel iModel : pretendents.values()) {
			Guild g = (Guild) iModel;
			if (g.getId() == myguild) {
				ourscore = g.getPoints();
			} else {
			if (g.getPoints() > maxscore) {
				maxscore = g.getPoints();
			}
			}
		}
		if (pretendents.values().size() == 1 && ourscore > 0)
			maxscore = 0;
		return Long.toString(ourscore) + "/" + Long.toString(maxscore);
	}
}
