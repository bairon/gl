package pp.model.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 19.11.11
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
@Root(name="gl")
public class Cgl {
	@Element(name = "ID", required = true)
	private long id;
	@Element(name = "Attack", required = true)
	private long attack;
	@Element(name = "Block", required = true)
	private long block;
	@Element(name = "Power", required = true)
	private long power;
	@Element(name = "Courage", required = true)
	private String courage;
	@Element(name = "x", required = true)
	private long x;
	@Element(name = "y", required = true)
	private long y;
	@Element(name = "ArmorID", required = true)
	private String armorid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAttack() {
		return attack;
	}

	public void setAttack(long attack) {
		this.attack = attack;
	}

	public long getBlock() {
		return block;
	}

	public void setBlock(long block) {
		this.block = block;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public String getCourage() {
		return courage;
	}

	public void setCourage(String courage) {
		this.courage = courage;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public String getArmorid() {
		return armorid;
	}

	public void setArmorid(String armorid) {
		this.armorid = armorid;
	}
}
