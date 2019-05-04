package pp.model;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class PlayerStat extends AModel {
	private String name;
	private Long participates;
	private Long finals;
	private Long wins;
	private Double ratio;


	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Long getParticipates() {
		return participates;
	}

	public void setParticipates(final Long participates) {
		this.participates = participates;
		updateRatio();
	}

	public Long getFinals() {
		return finals;
	}

	public void setFinals(final Long finals) {
		this.finals = finals;
		updateRatio();
	}

	public Long getWins() {
		return wins;
	}

	public void setWins(final Long wins) {
		this.wins = wins;
		updateRatio();
	}

	public Double getRatio() {
		return ratio;
	}

	private void setRatio(final Double ratio) {
		this.ratio = ratio;
	}

	private void updateRatio() {
		this.ratio = (double)(long)(participates == null ? 0 : participates) - (wins == null ? 0L : wins) * 6 - (finals == null ? 0 : finals) * 2;
	}
	@Override
	public String toString() {
		return name + " " + (participates == null ? 0 : participates) + " " + (wins == null ? 0 : wins) + " " + (finals == null ? 0 : finals) + " " + "(" + (ratio.intValue()) + ")";
	}
}
