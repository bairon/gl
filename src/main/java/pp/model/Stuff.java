package pp.model;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Stuff extends AModel {
	private Long masseur;
	private Long priest;
	private Long doctor;
	private Long rstGld;
	private Long rstSpc;

	public Long getMasseur() {
		return masseur;
	}

	public void setMasseur(final Long masseur) {
		this.masseur = masseur;
	}

	public Long getPriest() {
		return priest;
	}

	public void setPriest(final Long priest) {
		this.priest = priest;
	}

	public Long getDoctor() {
		return doctor;
	}

	public void setDoctor(final Long doctor) {
		this.doctor = doctor;
	}

	public Long getRstGld() {
		return rstGld;
	}

	public void setRstGld(final Long rstGld) {
		this.rstGld = rstGld;
	}

	public Long getRstSpc() {
		return rstSpc;
	}

	public void setRstSpc(final Long rstSpc) {
		this.rstSpc = rstSpc;
	}

	@Override
	public String toString() {
		return "Stuff{" +
				"masseur=" + masseur +
				", priest=" + priest +
				", doctor=" + doctor +
				'}';
	}

    public void renew() {

    }

    public long getSpecs() {
        return rstSpc == null ? 0 : rstSpc;
    }
}
