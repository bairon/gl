package pp.model.enums;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public enum TournamentType {
	NEWBIES("2"),
	PLEBEIAN("1"),
	IMPERATORS("9"),
	SENATOR("3"),
	MIXED("13"),
	GUILD("4"),
	COMMERCIAL("5"),
    PRIZE("7");
	private String path;

	TournamentType(final String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
