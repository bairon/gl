package pp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class HippoTour implements Serializable {
	private static final long serialVersionUID = 4380567098774586896L;

	private HippoHorse horses[] = new HippoHorse[4];
	int winner;
	int horse;
	long bet;
	long pot;
	long timestamp;

	public HippoHorse[] getHorses() {
		return horses;
	}

	public void setHorses(final HippoHorse[] horses) {
		this.horses = horses;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(final int winner) {
		this.winner = winner;
	}

	public int getHorse() {
		return horse;
	}

	public void setHorse(final int horse) {
		this.horse = horse;
	}

	public long getBet() {
		return bet;
	}

	public void setBet(final long bet) {
		this.bet = bet;
	}

	public long getPot() {
		return pot;
	}

	public void setPot(final long pot) {
		this.pot = pot;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	public double[] simpleform() {
		HippoHorse[] horses = getHorses().clone();
		int winner = getWinner();
		getHorses()[(int)getWinner()].setWinner(true);
		Arrays.sort(horses, new Comparator() {
			@Override
			public int compare(final Object o1, final Object o2) {
				HippoHorse h1 = (HippoHorse) o1;
				HippoHorse h2 = (HippoHorse) o2;
				return Double.valueOf(h1.getFactor()).compareTo(h2.getFactor());
			}
		});
		for (int i = 0; i < 4; ++i) {
			if (horses[i].getWinner()) {
				winner = i;
			}
		}
		return new double[]{horses[0].getFactor(), horses[1].getFactor(), horses[2].getFactor(), horses[3].getFactor(), (double) winner};
	}

	@Override
	public String toString() {
		return "HippoTour{" +
				"horses=" + (horses == null ? null : Arrays.asList(horses)) +
				", winner=" + winner +
				", horse=" + horse +
				", bet=" + bet +
				", pot=" + pot +
				", timestamp=" + timestamp +
				'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final HippoTour hippoTour = (HippoTour) o;
		double[] f1 = simpleform();
		double[] f2 = hippoTour.simpleform();
		if (!Arrays.equals(f1, f2)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		double[] f1 = simpleform();
		int result = Arrays.hashCode(f1);
		return result;
	}
}
