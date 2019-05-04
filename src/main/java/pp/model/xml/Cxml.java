package pp.model.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 19.11.11
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
@Root(name="xml")
public class Cxml {
	@Attribute(name="Champ", required = true)
	private long champ;
	@Attribute(name="id", required = true)
	private long id;
	@Attribute(name="AwardFee", required = true)
	private long awardfee;
	@ElementList(entry = "gl", required = false, inline = true)
	private List<Cgl> gls = new ArrayList<Cgl>();

	public long getChamp() {
		return champ;
	}

	public void setChamp(long champ) {
		this.champ = champ;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAwardfee() {
		return awardfee;
	}

	public void setAwardfee(long awardfee) {
		this.awardfee = awardfee;
	}

	public List<Cgl> getGls() {
		return gls;
	}

	public void setGls(List<Cgl> gls) {
		this.gls = gls;
	}

	@Override
	public String toString() {
		return "Cxml{" +
				"champ=" + champ +
				", id=" + id +
				", awardfee=" + awardfee +
				", gls=" + gls +
				'}';
	}
}
