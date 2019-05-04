package pp.model.xml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Armors")
public class CArmors {
	@ElementList(entry = "Armor", required = false, inline = true)
	private List<CArmor> armors;

	public List<CArmor> getArmors() {
		return armors;
	}

	public void setArmors(List<CArmor> armors) {
		this.armors = armors;
	}
}
