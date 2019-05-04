package pp;

import hlp.MyHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class F1 {
    public static final String RASKACHKA = "1 2 3 4";
	public static void main(String[] args) throws Exception {
        String [] stats10 = RASKACHKA.split(" ");
        int [] intstats10 = new int[stats10.length];
        for (int i = 0; i < stats10.length; ++i) {
            intstats10[i] = Integer.parseInt(stats10[i]);
        }
        int summ10 = 0;
        for (int intstat : intstats10) {
            summ10 += intstat;
        }
        int summ15 = 34 + 15;
        int summ20 = summ15 + 15;
        int summ25 = summ20 + 15;
        int summ30 = summ25 + 15;

        int [] stats15 = calcStats(intstats10, summ15);
        int [] stats20 = calcStats(intstats10, summ20);
        int [] stats25 = calcStats(intstats10, summ25);
        int [] stats30 = calcStats(intstats10, summ30);
        print(intstats10);
        print(stats15);
        print(stats20);
        print(stats25);
        print(stats30);
    }
    public static int[] calcStats(int [] template, int summ) {
        int[] result = new int[template.length];
        int templatesumm = 0;
        for (int intstat : template) {
            templatesumm += intstat;
        }
        for (int i = 0; i < template.length; ++i) {
            result[i] = (int) Math.round((double) summ * template[i] / templatesumm);
        }
        int realsumm = 0;
        for (int intstat : result) {
            realsumm += intstat;
        }
        result[3] += summ - realsumm;
        return result;
    }

    public static void print(int [] array) {
        for (int i : array) {
            System.out.print(" " + i);
        }
        System.out.print("\n");
    }
}
