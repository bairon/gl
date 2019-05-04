package pp;

import hlp.MyHttpClient;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Main {
	public static final String domain = "s2.gladiators.ru";
	public static void main(String[] args) {
		//System.out.println(Character.isUpperCase('1'));
		MyHttpClient client = new MyHttpClient();
		client.appendInitialCookie("PHPSESSID", "66bc7c257e046e05519add1b7b1382f3", domain);
		client.appendInitialCookie("cookie_lang_3", "rus", domain);
		GlRuManager manager = new GlRuManagerImpl(client);
		manager.start();
	}
}
