package pp;

/**
 * This is posting commands to the http client and takes decisions
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface GlRuManager {
	static final String SETTING_PARTICIPATE_PRACTICE = "";
	void start();
	void stop();
	void pause();
	void addSetting(final StrategySetting setting);
	void removeSetting(final StrategySetting setting);
}
