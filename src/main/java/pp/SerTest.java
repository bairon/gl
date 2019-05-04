package pp;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alexander.sokolovsky.a@gmail.com
 */
public class SerTest {
	public static void main(String[] args) {

		try {
			DateFormat sdf = SimpleDateFormat.getInstance();
			Date date = sdf.parse("12.08.2011 17:17");
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Map<Long, String> ms = new HashMap<Long, String>();
		ms.put(1L, "str");
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream("D:/a.ser")));
			out.writeObject(ms);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ObjectInputStream in = null;

		try {
			in = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream("D:/a.ser")));
			ms = (HashMap<Long, String>) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(ms.get(1L));
	}
}
