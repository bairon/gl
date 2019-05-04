package pp.model;

import pp.model.enums.TournamentType;

import java.util.Date;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Trnm extends AModel {
	private static final long serialVersionUID = -2145241235118445212L;
	private String name;
	private Date start;
	private Long lvlFrom;
	private Long lvlTo;
	private Long prize;
	private Long length;
	private Long members;
	private Long maxMembers;
	private Boolean canJoin;
	private Boolean canLeave;
	private Boolean completed;
	private Long finalist;
	private Long winner;
	private String winnerName;
	private Map<Long, Player> players;
	private Boolean canUseTicket;
	private TournamentType type;
	private Boolean allTypes;
	private long invitations;

    private boolean included;
	private double gladsCapacity;
	private int priority;

	public boolean isIncluded() {
        return included;
    }


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	public Long getLvlFrom() {
		return lvlFrom;
	}

	public void setLvlFrom(final Long lvlFrom) {
		this.lvlFrom = lvlFrom;
	}

	public Long getLvlTo() {
		return lvlTo;
	}

	public void setLvlTo(final Long lvlTo) {
		this.lvlTo = lvlTo;
	}

	public Long getPrize() {
		return prize;
	}

	public void setPrize(final Long prize) {
		this.prize = prize;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(final Long length) {
		this.length = length;
	}

	public Long getMembers() {
		return members;
	}

	public void setMembers(final Long members) {
		this.members = members;
	}

	public Long getMaxMembers() {
		return maxMembers;
	}

	public void setMaxMembers(final Long maxMembers) {
		this.maxMembers = maxMembers;
	}

	public Boolean getCanJoin() {
		return canJoin;
	}

	public void setCanJoin(final Boolean canJoin) {
		this.canJoin = canJoin;
	}

	public Boolean getCanLeave() {
		return canLeave;
	}

	public void setCanLeave(final Boolean canLeave) {
		this.canLeave = canLeave;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(final Boolean completed) {
		this.completed = completed;
	}

	public Long getFinalist() {
		return finalist;
	}

	public void setFinalist(final Long finalist) {
		this.finalist = finalist;
	}

	public Long getWinner() {
		return winner;
	}

	public void setWinner(final Long winner) {
		this.winner = winner;
	}

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(final String winnerName) {
		this.winnerName = winnerName;
	}

	public Map<Long, Player> getPlayers() {
		return players;
	}

	public void setPlayers(final Map<Long, Player> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "Trnm{" +
				"id='" + getId() + '\'' +
				", name=" + name +
				", start=" + start +
				", lvlFrom=" + lvlFrom +
				", lvlTo=" + lvlTo +
				", members=" + members +
				", maxMembers=" + maxMembers +
				", canJoin=" + canJoin +
				", canLeave=" + canLeave +
				", canUseTicket=" + canUseTicket +
				", type=" + type +
				", invitations=" + invitations +
				'}';
	}

	public void setCanUseTicket(Boolean canUseTicket) {
		this.canUseTicket = canUseTicket;
	}

	public Boolean getCanUseTicket() {
		return canUseTicket;
	}

	public TournamentType getType() {
		return type;
	}

	public void setType(TournamentType type) {
		this.type = type;
	}

	public Boolean getAllTypes() {
		return allTypes;
	}

	public void setAllTypes(Boolean allTypes) {
		this.allTypes = allTypes;
	}

	public long getInvitations() {
		return invitations;
	}

	public void setInvitations(long invitations) {
		this.invitations = invitations;
	}

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public void setGladsCapacity(double gladsCapacity) {
        this.gladsCapacity = gladsCapacity;
    }

    public double getGladsCapacity() {
        return gladsCapacity;
    }

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
