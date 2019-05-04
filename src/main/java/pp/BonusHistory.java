package pp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.SortedSet;
import java.util.TreeSet;

public class BonusHistory {
    public static SortedSet<Long> history;
    static {
        restore();
    }

    public static void restore() {
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(new FileInputStream("bonushistory"));
            history = (SortedSet<Long>) is.readObject();
        } catch (IOException e) {
            history = new TreeSet<Long>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("bonushistory"));
            os.writeObject(history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void use() {
        use(1);
    }
    public static int used(long period) {
        return history.tailSet(System.currentTimeMillis() - period).size();
    }

    public static void use(int i) {
        long now = System.currentTimeMillis();
        for (int k = 0; k < i; ++k) {
            history.add(now + k);
        }
        save();
    }
}
