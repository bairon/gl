package pp.model.enums;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public enum TournamentState {
	OPEN("select"),
	STARTED("active"),
	FUTURE("future"),
	COMPLETED("archive");
	private String path;

	TournamentState(final String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
