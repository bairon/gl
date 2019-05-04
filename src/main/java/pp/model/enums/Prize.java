package pp.model.enums;

import javax.swing.text.html.HTMLDocument;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public enum Prize {
	RSTG(4),
	RSTS(5),
	GOLD(2),
	SILVER(3),
	EXP(1),
	INVIT(6), NONE(10);
	private int weight;

	Prize(final int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(final int weight) {
		this.weight = weight;
	}
}
