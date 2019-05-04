package pp;

import security.provider.MyMD5;
import sun.misc.Unsafe;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 09.12.11
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public class MD5Test {
	static final byte[] HEX_CHAR_TABLE = {
	  (byte)'0', (byte)'1', (byte)'2', (byte)'3',
	  (byte)'4', (byte)'5', (byte)'6', (byte)'7',
	  (byte)'8', (byte)'9', (byte)'a', (byte)'b',
	  (byte)'c', (byte)'d', (byte)'e', (byte)'f'
	};
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int N = 1;
		byte[] message1 = {0, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
		byte[] message2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1};

		//MessageDigest md5 = MessageDigest.getInstance("MD5");
		//byte[] digest1 = md5.digest(message1);
//		int i1 = 0, i2 = 0, i3 = 0, i4 = 0;
		//MyMD5 mymd5 = new MyMD5();
		//mymd5.engineUpdate(message1, 0, message1.length);
		//byte[] digest2 = mymd5.engineDigest();
		//System.out.println(getHexString(digest1));
		//System.out.println(getHexString("90".getBytes()));
		//System.out.println(getHexString("130".getBytes()));
		//System.out.println(getHexString("nikitaonlineru:260:13651312:".getBytes()));
		//System.out.println(getHexString("nikitaonlineru:130:13651312:".getBytes()));
		//long start = System.currentTimeMillis();
		//long end = System.currentTimeMillis();
		//System.out.println("Java builtin MyMD5 digest " + N + " times took " + (end - start) + " ms.");
	}

	private static String mm(String ... m) {
		return m[(int) Math.floor(Math.random() * m.length)];
	}

	public static String getHexString(byte[] raw)
	  throws UnsupportedEncodingException
	{
	  byte[] hex = new byte[2 * raw.length];
	  int index = 0;

	  for (byte b : raw) {
	    int v = b & 0xFF;
	    hex[index++] = HEX_CHAR_TABLE[v >>> 4];
	    hex[index++] = HEX_CHAR_TABLE[v & 0xF];
	  }
	  return new String(hex, "ASCII");
	}
}
