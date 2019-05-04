package pp;

import pp.model.Btl;
import pp.model.IModel;
import pp.model.enums.BtlType;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class BtlModelFactory implements ModelFactory {
	@Override
	public IModel getNewModel() {
		return new Btl();
	}

	@Override
	public void merge(final IModel source, final IModel destination) {
		Btl src = (Btl) source;
		Btl dst = (Btl) destination;
		if (src.getPrepare() != null) dst.setPrepare(src.getPrepare());
		if (src.getCheck() != null) dst.setCheck(src.getCheck());
		if (src.getBack() != null) dst.setBack(src.getBack());
		if (src.getFight() != null) dst.setFight(src.getFight());
		if (src.getVars() != null) dst.setVars(src.getVars());
		if (src.getType() != null) dst.setType(src.getType());
		if (src.getLimitGlad() != null) dst.setLimitGlad(src.getLimitGlad());
		if (src.getLimitSkl() != null) dst.setLimitSkl(src.getLimitSkl());
		if (src.getMaxLevel() != null) dst.setMaxLevel(src.getMaxLevel());
		if (src.getXml() != null) dst.setXml(src.getXml());
		if (src.getTimeLeft() != null) dst.setTimeLeft(src.getTimeLeft());
		if (src.getGladTip() != null) dst.setGladTip(src.getGladTip());
		if (src.getFvid() != null) dst.setFvid(src.getFvid());
		if (src.getUser1Login() != null) dst.setUser1Login(src.getUser1Login());
		if (src.getUser2Login() != null) dst.setUser2Login(src.getUser2Login());
		if (src.getTourmentTitle() != null) dst.setTourmentTitle(src.getTourmentTitle());
		if (src.getChamp() != null) dst.setChamp(src.getChamp());
		if (src.getMinAwardFee() != null) dst.setMinAwardFee(src.getMinAwardFee());
		if (src.getTut() != null) dst.setTut(src.getTut());
		if (src.getUser1Url() != null) dst.setUser1Url(src.getUser1Url());
		if (src.getUser2Url() != null) dst.setUser2Url(src.getUser2Url());
		if (src.getArmorLevels() != null) dst.setArmorLevels(src.getArmorLevels());
		if (src.getUrl() != null) dst.setUrl(src.getUrl());
	}
}
