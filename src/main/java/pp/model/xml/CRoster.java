package pp.model.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "roster")
public class CRoster {

	@Element(name="Armors", required = true)
	private CArmors armors;

	@ElementList(entry = "Gladiator", required = false, inline = true)
	private List<CGlad> glads;

	public CArmors getArmors() {
		return armors;
	}

	public void setArmors(CArmors armors) {
		this.armors = armors;
	}

	public List<CGlad> getGlads() {
		return glads;
	}

	public void setGlads(List<CGlad> glads) {
		this.glads = glads;
	}
}
