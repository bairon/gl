package pp;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.simpleframework.xml.core.Persister;
import pp.model.Glad;
import pp.model.IModel;
import pp.model.enums.GladType;
import pp.model.xml.CArmor;
import pp.model.xml.CGlad;
import pp.model.xml.Cxml;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class Utils {
    public static final Logger LOGGER = Logger.getLogger(Utils.class);
    public static final String SERVER_DATE_FORMAT = "MMM, dd yyyy HH:mm:ss";//Nov, 03 2011 21:27:29
    public static final SimpleDateFormat serverDateFormat = new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH);
    public static void print(final Map<Long, IModel> model) {
		for (IModel iModel : model.values()) {
			System.out.println(iModel);
		}
		System.out.println("---------------------------------------------------------------------------------------");
	}

	public static void sleep(final int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void print(final Collection<? extends IModel> sorted) {
		for (IModel iModel : sorted) {
			System.out.println(iModel);
		}
		System.out.println("---------------------------------------------------------------------------------------");

	}
	public static String toString(final Collection c) {
		StringBuilder sb = new StringBuilder();
		for (Object o : c) {
			sb.append("\n").append(o);
		}
		return sb.toString();

	}
	public static String toString(final Cxml cxml) throws Exception {
		StringWriter writer = new StringWriter();
		new Persister().write(cxml, writer);
		return writer.toString();

	}
	public static boolean isBlank(String s) {
		return s == null || "".equals(s);
	}
    public static void sout(Object o) {
        try {
            //System.out.write(o.toString().getBytes("Cp866"));
            System.out.write(o.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    public static String between(final String source, final String starttoken, final String endtoken) {
        int start = source.indexOf(starttoken);
        int end = source.indexOf(endtoken, start + starttoken.length());
        if (start > 0 && end > start)
            return source.substring(start + starttoken.length(), end);
        else return "";
    }
    public static int countSilver(CGlad g) {
        int result = 0;
        for (CArmor armor : g.getArmors()) {
            if (armor.getMorale() > 0) result++;
        }
        return result;
    }
    public static boolean ddMutant(CGlad glad) {
        return (glad.getVit() + glad.getDex()) * 3 < (glad.getAcc() + glad.getStr())
                && ( GladType.Chariot.isTypeId(glad.getTypeid())
                || GladType.Velit.isTypeId(glad.getTypeid())
                || GladType.Archer.isTypeId(glad.getTypeid()));
    }

    public static long dateToTimestamp(String serverdatestr) {
        if (serverdatestr.contains("'")) {
            serverdatestr = between(serverdatestr, "'", "'").trim();
        }
        try {
            return serverDateFormat.parse(serverdatestr).getTime();
        } catch (ParseException e) {
            //LOGGER.error("date parse error " + serverdatestr);
            return 0;
        }
    }
    public static Long findNumber(String text) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return Long.parseLong(m.group());
        }
        return null;
    }

    public static void soutn(String s) {
        sout(s + "\n");
    }
    public static int hppercent(List<CGlad> gladsToGo) {
        double stm = 0;
        int cnt = 0;
        for (CGlad glad : gladsToGo) {
            if (!Utils.ddMutant(glad)) {
                stm+=glad.getStamina();
                cnt++;
            }
        }
        return (int)(stm / cnt);
    }

}
