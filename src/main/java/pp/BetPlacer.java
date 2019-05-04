package pp;

import pp.model.HippoHorse;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface BetPlacer {
	int horse(final HippoHorse[] horses);
	int bet(final HippoHorse[] horses);
}
