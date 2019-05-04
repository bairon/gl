package pp.model;

import pp.model.enums.BtlType;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Btl extends AModel {
	private Boolean prepare;
	private Boolean check;
	private Boolean back;
	private Boolean fight;
	private String vars;
	private BtlType type;
	private Long limitGlad;
	private Long limitSkl;
	private Long maxLevel;
	private String xml;
	private Long timeLeft;
	private String gladTip;
	private Long fvid;
	private String user1Login;
	private String user2Login;
	private String tourmentTitle;
	private Long champ;
	private Long minAwardFee;
	private String tut;
	private String user1Url;
	private String user2Url;
	private String armorLevels;
	private String url;
	private boolean timeOut;
    private long oppGuild;

    public Boolean getPrepare() {
		return prepare;
	}

	public void setPrepare(final Boolean prepare) {
		this.prepare = prepare;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(final Boolean check) {
		this.check = check;
	}

	public Boolean getBack() {
		return back;
	}

	public void setBack(final Boolean back) {
		this.back = back;
	}

	public Boolean getFight() {
		return fight;
	}

	public void setFight(final Boolean fight) {
		this.fight = fight;
	}

	public String getVars() {
		return vars;
	}

	public void setVars(final String vars) {
		this.vars = vars;
	}

	public BtlType getType() {
		return type;
	}

	public void setType(final BtlType type) {
		this.type = type;
	}

	public Long getLimitGlad() {
		return limitGlad;
	}

	public void setLimitGlad(final Long limitGlad) {
		this.limitGlad = limitGlad;
	}

	public Long getLimitSkl() {
		return limitSkl;
	}

	public void setLimitSkl(final Long limitSkl) {
		this.limitSkl = limitSkl;
	}

	public Long getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(final Long maxLevel) {
		this.maxLevel = maxLevel;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(final String xml) {
		this.xml = xml;
	}

	public Long getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(final Long timeLeft) {
		this.timeLeft = timeLeft;
	}

	public String getGladTip() {
		return gladTip;
	}

	public void setGladTip(final String gladTip) {
		this.gladTip = gladTip;
	}

	public Long getFvid() {
		return fvid;
	}

	public void setFvid(final Long fvid) {
		this.fvid = fvid;
	}

	public String getUser1Login() {
		return user1Login;
	}

	public void setUser1Login(final String user1Login) {
		this.user1Login = user1Login;
	}

	public String getUser2Login() {
		return user2Login;
	}

	public void setUser2Login(final String user2Login) {
		this.user2Login = user2Login;
	}

	public String getTourmentTitle() {
		return tourmentTitle;
	}

	public void setTourmentTitle(final String tourmentTitle) {
		this.tourmentTitle = tourmentTitle;
	}

	public Long getChamp() {
		return champ;
	}

	public void setChamp(final Long champ) {
		this.champ = champ;
	}

	public Long getMinAwardFee() {
		return minAwardFee;
	}

	public void setMinAwardFee(final Long minAwardFee) {
		this.minAwardFee = minAwardFee;
	}

	public String getTut() {
		return tut;
	}

	public void setTut(final String tut) {
		this.tut = tut;
	}

	public String getUser1Url() {
		return user1Url;
	}

	public void setUser1Url(final String user1Url) {
		this.user1Url = user1Url;
	}

	public String getUser2Url() {
		return user2Url;
	}

	public void setUser2Url(final String user2Url) {
		this.user2Url = user2Url;
	}

	public String getArmorLevels() {
		return armorLevels;
	}

	public void setArmorLevels(final String armorLevels) {
		this.armorLevels = armorLevels;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Btl{" +
				"id=" + id +
				", prepare=" + prepare +
				", check=" + check +
				", back=" + back +
				", fight=" + fight +
				", type=" + type +
				", limitGlad=" + limitGlad +
				", limitSkl=" + limitSkl +
				", maxLevel=" + maxLevel +
				", user1Login='" + user1Login + '\'' +
				", user2Login='" + user2Login + '\'' +
				", tourmentTitle='" + tourmentTitle + '\'' +
				'}';
	}

	public void setTimeOut(boolean timeOut) {
		this.timeOut = timeOut;
	}

	public boolean isTimeOut() {
		return timeOut;
	}

    public void setOppGuild(long oppGuild) {
        this.oppGuild = oppGuild;
    }

    public long getOppGuild() {
        return oppGuild;
    }
}
