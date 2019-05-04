package pp;

import pp.model.Guild;
import pp.model.IModel;
import pp.model.Player;
import pp.service.GlRuService;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class OnlineMonitorActivity extends Activity {
	List<Long> guildIds = Arrays.asList(10L, 15L);

	protected OnlineMonitorActivity(final GlRuService service) {
		super(service);
	}

	@Override
	public void doSome() {
		try {
			for (Long guildId : guildIds) {
				service.visitGuild(guildId);
			}
			//service.loadOnline();
			//service.updateOnline();
			//service.saveOnline();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setGuildIds(final List<Long> guildIds) {
		this.guildIds = guildIds;
	}

	public List<Long> getGuildIds() {
		return guildIds;
	}
}
