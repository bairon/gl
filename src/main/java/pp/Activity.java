package pp;

import pp.service.GlRuService;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public abstract class Activity {
	protected GlRuService service;
	protected boolean completed = false;
	protected Activity(final GlRuService service) {
		this.service = service;
	}

	boolean isCompleted() {
		return completed;
	}
	abstract void doSome();
}
