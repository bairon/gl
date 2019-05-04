package pp.model;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Player extends AModel {
	private static final long serialVersionUID = 4380567043674586896L;
	private String name;
	private Long vip;
	private Boolean online;
	private String pts;
	private Long lvl;
	private Long onlineMinutes;
	private Long guildId;

	@Override
	public String toString() {
		return name + ((pts == null) ? "" : "(" + pts + ")");
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Long getVip() {
		return vip;
	}

	public void setVip(final Long vip) {
		this.vip = vip;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(final Boolean online) {
		this.online = online;
	}

	public String getPts() {
		return pts;
	}

	public void setPts(final String pts) {
		this.pts = pts;
	}

	public Long getLvl() {
		return lvl;
	}

	public void setLvl(final Long lvl) {
		this.lvl = lvl;
	}

	public Long getOnlineMinutes() {
		return onlineMinutes;
	}

	public void setOnlineMinutes(final Long onlineMinutes) {
		this.onlineMinutes = onlineMinutes;
	}

	public Long getGuildId() {
		return guildId;
	}

	public void setGuildId(final Long guildId) {
		this.guildId = guildId;
	}
}
