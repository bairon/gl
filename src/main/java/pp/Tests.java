package pp;

import hlp.MyHttpClient;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import pp.exception.NotLoggedInException;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Tests {
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"));
    }
    public static final String SERVER_DATE_FORMAT = "MMM, dd yyyy HH:mm:ss";//Nov, 03 2011 21:27:29
    public static final SimpleDateFormat serverDateFormat = new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.ENGLISH);
    public static void main(String[] args) throws ParseException {
        TimeZone eet = TimeZone.getTimeZone("EET");
        TimeZone.setDefault(eet);
        long t1 = System.currentTimeMillis();
        long t2 = serverDateFormat.parse("Apr, 13 2013 04:17:00").getTime();
        Date d2 = new Date(t2);
        long d = t2 - t1;
    }
}
