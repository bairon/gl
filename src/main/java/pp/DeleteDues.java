package pp;

import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.model.AModel;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class DeleteDues {
	public static final Logger LOGGER = Logger.getLogger(DeleteDues.class);
	public static final String domain = "s2.gladiators.ru";
	public static void main(String[] args) {
		MyHttpClient client = new MyHttpClient();
		client.appendInitialCookie("PHPSESSID", "51ac12d66182805ed95a7662004c0213", domain);
		client.appendInitialCookie("cookie_lang_3", "rus", domain);
		GlRuService service = new GlRuServiceImpl(client, new GlRuParser(), "s2.gladiators.ru");
		try {
			Map<Long,AModel> dues = service.getDues(10);
			LOGGER.info(Integer.toString(dues.size()) + " to delete.");
			for (Long due : dues.keySet()) {
				service.deleteDue(due);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
