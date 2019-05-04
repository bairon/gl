package hlp;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import pp.Settings;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class MyHttpClient extends DefaultHttpClient {
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.0; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0";
	public MyHttpClient(final ClientConnectionManager conman, final HttpParams params) {
		super(conman, params);
		initWithMyValues();
	}

	public MyHttpClient(final ClientConnectionManager conman) {
		super(conman);
		initWithMyValues();
	}

	public MyHttpClient(final HttpParams params) {
		super(params);
		initWithMyValues();
	}

	public MyHttpClient() {
		initWithMyValues();
	}

	private void initWithMyValues() {
        addRequestInterceptor(new RequestAcceptEncoding());
        addResponseInterceptor(new ResponseContentEncoding());
        HttpParams params = new SyncBasicHttpParams();
        HttpProtocolParams.setUserAgent(params, USER_AGENT);
        params.setParameter("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        params.setParameter("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
        params.setParameter("Accept-Encoding", "gzip, deflate, sdch");
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpClientParams.setCookiePolicy(params, CookiePolicy.BROWSER_COMPATIBILITY);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        setParams(params);
        try {
            List<String> cookies = IOUtils.readLines(new FileInputStream("cookie"));
            for (String cookie : cookies) {
                String[] split = cookie.split(";");
                for (String s : split) {
                    String[] namevalue = s.split("=");
                    appendInitialCookie(namevalue[0], namevalue[1], Settings.getServer());
                }
            }
        } catch (IOException e) {
        }
    }
    public void saveCookies() {
        StringBuilder sb = new StringBuilder();
        CookieStore cookieStore = getCookieStore();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        try {
            FileWriter writer = new FileWriter("cookie");
            String cookie = sb.toString();
            IOUtils.write(cookie, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public void appendInitialCookie(String name, String value, String domain) {
		BasicClientCookie cookie = new BasicClientCookie(name, value);
		cookie.setDomain(domain);
		getCookieStore().addCookie(cookie);
	}
    public <T> T execute(
            final HttpHost target,
            final HttpRequest request,
            final ResponseHandler<? extends T> responseHandler,
            final HttpContext context)
            throws IOException, ClientProtocolException {
        if (responseHandler == null) {
            throw new IllegalArgumentException
                    ("Response handler must not be null.");
        }

        HttpResponse response = execute(target, request, context);

        T result;
        try {
            result = responseHandler.handleResponse(response);
        } catch (Throwable t) {
            HttpEntity entity = response.getEntity();
            try {
                EntityUtils.consume(entity);
            } catch (Exception t2) {
                // Log this exception. The original exception is more
                // important and will be thrown to the caller.
                //this.log.warn("Error consuming content after an exception.", t2);
            }

            if (t instanceof Error) {
                throw (Error) t;
            }

            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }

            if (t instanceof IOException) {
                throw (IOException) t;
            }

            throw new UndeclaredThrowableException(t);
        }

        // Handling the response was successful. Ensure that the content has
        // been fully consumed.
        try {
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } catch (Throwable t) {
            //ignore
        }
        return result;
    }
}
