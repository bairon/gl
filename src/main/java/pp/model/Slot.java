package pp.model;

import org.apache.log4j.Logger;
import pp.Utils;
import pp.model.comparators.ComparatorProvider;
import pp.model.enums.GladType;
import pp.model.xml.CGlad;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alsa
 * Date: 08.12.11
 * Time: 3:24
 * To change this template use File | Settings | File Templates.
 */
public class Slot {
    public static final Logger LOGGER = Logger.getLogger(Slot.class);
    private ComparatorProvider category;
    private int attack;
    private int power;
    private int block;
    private int x;
    private int y;
    private CGlad glad;

    public Slot(ComparatorProvider category, int attack, int power, int block, int x, int y) {
        this.category = category;
        this.attack = attack;
        this.power = power;
        this.block = block;
        this.x = x;
        this.y = y;
    }

    public ComparatorProvider getCategory() {
        return category;
    }

    public int getAttack() {
        return attack;
    }

    public int getPower() {
        return power;
    }

    public int getBlock() {
        return block;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Slot create(GladCategory cat, int attack, int power, int block, int x, int y) {
        return new Slot(cat, attack, power, block, x, y);
    }

    public void invert() {
        y = 6 - y;
    }

    public void setGlad(CGlad glad) {
        this.glad = glad;
    }

    public CGlad getGlad() {
        return glad;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setBlock(int block) {
        this.block = block;
    }


    public static Slot create(String slotString, Slot prev) {
        try {
            int place = 0;
            ComparatorProvider category = null;
            int attack = 0;
            int power = 0;
            int block = 0;
            String[] tokens = slotString.split("-");
            if (tokens.length == 5) {
                place = Integer.parseInt(tokens[0].trim());
                category = GladCategory.valueOfExtended(tokens[1].trim());
                attack = Integer.parseInt(tokens[2].trim());
                power = Integer.parseInt(tokens[3].trim());
                block = Integer.parseInt(tokens[4].trim());
            } else if (tokens.length == 4) {
                if (prev != null) {
                    place = prev.x * 10 + prev.y;
                    category = GladCategory.valueOfExtended(tokens[0].trim());
                    attack = Integer.parseInt(tokens[1].trim());
                    power = Integer.parseInt(tokens[2].trim());
                    block = Integer.parseInt(tokens[3].trim());
                } else {
                    LOGGER.error("Wrong slot " + slotString);
                    Utils.soutn("Wrong slot " + slotString);
                }
            } else if (tokens.length == 3) {
                if (prev != null) {
                    place = prev.x * 10 + prev.y;
                    category = prev.category;
                    attack = Integer.parseInt(tokens[0].trim());
                    power = Integer.parseInt(tokens[1].trim());
                    block = Integer.parseInt(tokens[2].trim());
                } else {
                    LOGGER.error("Wrong slot " + slotString);
                    Utils.soutn("Wrong slot " + slotString);
                }
            } else {
                Utils.soutn("Wrong slot format: " + slotString + " Format is xy-TYPE-attack-power-block");
                throw new Exception("Wrong slot format: " + slotString + " Format is xy-TYPE-attack-power-block");
            }
            int x = place / 10;
            int y = place % 10;
            if (x < 1 || x > 5 || y < 1 || y > 5) throw new Exception("Wrong place " + place);
            return new Slot(category, attack, power, block, x, y);

        } catch (Throwable e) {
            LOGGER.error("Wrong slot " + slotString, e);
            Utils.soutn("Wrong slot " + slotString);
            return null;
        }
    }

    @Override
    public String toString() {
        return "" + Integer.toString(x) + Integer.toString(y) + "-" +
                (glad == null ? "" : (GladType.byID(glad.getTypeid()).getName() + "-" +
                glad.getName()) + "-") +
                attack + "-" +
                power + "-" +
                block;
    }

}
