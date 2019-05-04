package pp.model;

import java.io.Serializable;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class HippoHorse implements Serializable {
	private static final long serialVersionUID = 4388967098774586896L;
	private String name;
	private double factor;
	private long win;
	private boolean winner;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Double getFactor() {
		return factor;
	}

	public void setFactor(final Double factor) {
		this.factor = factor;
	}

	public Long getWin() {
		return win;
	}

	public void setWin(final Long win) {
		this.win = win;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(final Boolean winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "" + factor;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final HippoHorse that = (HippoHorse) o;

		if (Double.compare(that.factor, factor) != 0) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final long temp = factor != +0.0d ? Double.doubleToLongBits(factor) : 0L;
		return (int) (temp ^ (temp >>> 32));
	}
}
