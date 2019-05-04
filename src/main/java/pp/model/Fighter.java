package pp.model;

import noNamespace.Gladiator;
import noNamespace.RosterDocument;
import noNamespace.XmlDocument;
import pp.model.xml.CGlad;
import pp.model.xml.Cxml;

import java.util.List;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public interface Fighter {
	void battle(final Btl btl, Trnm joined) throws Exception;
}
