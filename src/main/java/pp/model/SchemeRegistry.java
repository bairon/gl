package pp.model;

import org.apache.log4j.Logger;
import pp.MultiplicationIterator;
import pp.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchemeRegistry {
    public static final Logger LOGGER = Logger.getLogger(SchemeRegistry.class);
    public static final List<Scheme> main = new ArrayList<Scheme>();
    public static final List<Scheme> special = new ArrayList<Scheme>();
    public static final List<Scheme> special_main = new ArrayList<Scheme>();
    public static final List<Scheme> special_survive = new ArrayList<Scheme>();
    public static final List<Scheme> special_horse = new ArrayList<Scheme>();
    public static final List<Scheme> horse = new ArrayList<Scheme>();
    public static final List<Scheme> survive = new ArrayList<Scheme>();
    public static final List<Scheme> kach = new ArrayList<Scheme>();
    public static final List<Scheme> sliv = new ArrayList<Scheme>();
    public static final List<Scheme> botus_main = new ArrayList<Scheme>();
    public static final List<Scheme> botus_survive = new ArrayList<Scheme>();
    public static final List<Scheme> botus_horse = new ArrayList<Scheme>();
    private static final String PRIORITY_KEY = "priority";
    private static final CharSequence FIXED_KEY = "fixed";
    private static final CharSequence LEFT_KEY = "left";
    private static final CharSequence RIGHT_KEY = "right";

    static {
     reload();
    }


    public static void reload() {
        long start = System.currentTimeMillis();
        try {
            loadMain();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadSpecial();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadSpecialMain();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadSpecialSurvive();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadSpecialHorse();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadHorse();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadSurvive();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadKach();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadSliv();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadBotusMain();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadBotusSurvive();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        try {
            loadBotusHorse();
        } catch (IOException e) {
            //LOGGER.error("", e);
        }
        long stop = System.currentTimeMillis();

    }
    public static void loadMain() throws IOException {
        init(main, new BufferedReader(new FileReader("settings-main.txt")));
    }
    public static void loadSpecial() throws IOException {
        init(special, new BufferedReader(new FileReader("settings-special.txt")));
    }
    public static void loadSpecialMain() throws IOException {
        init(special_main, new BufferedReader(new FileReader("special-main.txt")));
    }
    public static void loadSpecialSurvive() throws IOException {
        init(special_survive, new BufferedReader(new FileReader("special-survive.txt")));
    }
    public static void loadSpecialHorse() throws IOException {
        init(special_horse, new BufferedReader(new FileReader("special-horse.txt")));
    }
    public static void loadHorse() throws IOException {
        init(horse, new BufferedReader(new FileReader("settings-horse.txt")));
    }
    public static void loadSurvive() throws IOException {
        init(survive, new BufferedReader(new FileReader("settings-survive.txt")));
    }
    public static void loadKach() throws IOException {
        init(kach, new BufferedReader(new FileReader("kach-scheme.txt")));
    }
    public static void loadSliv() throws IOException {
        init(sliv, new BufferedReader(new FileReader("sliv-scheme.txt")));
    }
    public static void loadBotusMain() throws IOException {
        init(botus_main, new BufferedReader(new FileReader("botus-main.txt")));
    }
    public static void loadBotusSurvive() throws IOException {
        init(botus_survive, new BufferedReader(new FileReader("botus-survive.txt")));
    }
    public static void loadBotusHorse() throws IOException {
        init(botus_horse, new BufferedReader(new FileReader("botus-horse.txt")));
    }

    public static void init(List<Scheme> schemas, BufferedReader r) throws IOException {
        schemas.clear();
        String line;
        String parameterLine = null;
        List<String> slotLines = new ArrayList<String>();
        int groupid = 0;
        while ((line = r.readLine()) != null) {
            if (Utils.isBlank(line.trim())) continue;
            if (startSchema(line)) {
                List<Scheme> newSchemas = parseSchemas(parameterLine, slotLines, groupid++);
                schemas.addAll(newSchemas);
                parameterLine = line;
                slotLines.clear();
            } else {
                slotLines.add(line);
            }
        }
        List<Scheme> newSchemas = parseSchemas(parameterLine, slotLines, groupid++);
        schemas.addAll(newSchemas);
    }

    private static List<Scheme> parseSchemas(String parameterLine, List<String> slotLines, int group) {
        boolean fixed = parameterLine != null && parameterLine.contains(FIXED_KEY);
        ArrayList<Scheme> result = new ArrayList<Scheme>();
        if (slotLines.size() > 0) {
        List<Slot> [] listSlots = new List[slotLines.size()];
        int maxindex = 0;
        for (int i = 0; i < slotLines.size(); ++i) {
            listSlots[i] = new ArrayList<Slot>();
            List<Slot> listSlot = listSlots[i];
            String[] slotStrings = slotLines.get(i).split(",");
            for (String slotString : slotStrings) {
                listSlot.add(Slot.create(slotString, (listSlot.size() > 0 ? listSlot.get(listSlot.size() - 1) : null)));
            }
            if (maxindex < listSlot.size()) maxindex = listSlot.size();
        }
        if (fixed) {
        for (int i = 0; i < maxindex; ++i) {
            List<Slot> slots = new ArrayList<Slot>();
            for (List<Slot> listSlot : listSlots) {
                slots.add(listSlot.get(i < listSlot.size() ? i : listSlot.size() - 1));
            }
            Scheme s = createScheme(parameterLine, slots);
            s.setGroup(group);
            result.add(s);
        }
        } else {
            int [][] source = new int[listSlots.length][];
            for (int i = 0; i < listSlots.length; ++i) {
                source[i] = new int[listSlots[i].size()];
                for (int j = 0; j < listSlots[i].size(); ++j) {
                    source[i][j] = j;
                }
            }
            MultiplicationIterator iterator = new MultiplicationIterator(source);
            while (iterator.hasNext()) {
                int [] current = iterator.next();
                List<Slot> slots = new ArrayList<Slot>();
                for (int i = 0; i < listSlots.length; ++i) {
                    slots.add(listSlots[i].get(current[i]));
                }
                Scheme s = createScheme(parameterLine, slots);
                s.setGroup(group);
                result.add(s);
            }
        }
        }
        return result;
    }

    private static Scheme createScheme(String line, List<Slot> slots) {
        Slot [] slotarray = new Slot[slots.size()];
        int index = 0;
        for (Slot slot : slots) {
            slotarray[index++] = slot;
        }
        Scheme s = new Scheme(slotarray);
        line = line.trim().substring(1, line.length() - 1);
        String[] params = line.split(",");
        for (String param : params) {
            String[] keyvalue = param.split("=");
            String key = keyvalue[0].trim();
            if (keyvalue.length == 2) {
                // key value param -  priority=5
                if (PRIORITY_KEY.equals(key))
                    s.setPriority(Integer.parseInt(keyvalue[1].trim()));
            } else {
                if (LEFT_KEY.equals(key))
                    s.setLeft(true);
                if (RIGHT_KEY.equals(key))
                    s.setRight(true);
                // single param  NOVEL,NORET
            }
        }
        return s;
    }

    private static boolean startSchema(String line) {
        return line.contains("[") && line.contains("]");
    }
}
