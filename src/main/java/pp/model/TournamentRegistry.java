package pp.model;

import pp.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TournamentRegistry {
    public static final List<TournamentSetting> trnms = new ArrayList<TournamentSetting>();
    static {
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() throws IOException {
        trnms.clear();
        String line;
        BufferedReader r = new BufferedReader(new FileReader("tournaments.txt"));
        while((line = r.readLine()) != null) {
            if (Utils.isBlank(line)) continue;
            if (line.trim().startsWith("#")) continue;
            String[] parts = line.trim().split(",");
            String name = null;
            int main = 0;
            int horse = 0;
            int lvl = 0;
            int priority = 0;
            int members = -1;

            if (parts.length > 0)
                name = parts[0].replaceAll("_", " ");
            if (parts.length > 1)
                main = Integer.parseInt(parts[1]);
            if (parts.length > 2)
                horse = Integer.parseInt(parts[2]);
            if (parts.length > 3)
                lvl = Integer.parseInt(parts[3]);
            if (parts.length > 4)
                lvl = Integer.parseInt(parts[4]);
            if (parts.length > 5)
                members = Integer.parseInt(parts[5]);

            trnms.add(new TournamentSetting(name, main, horse, lvl, priority, members));
        }
    }
}
