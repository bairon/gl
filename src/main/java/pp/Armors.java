package pp;

import com.sun.deploy.panel.JavaPanel;
import hlp.MyHttpClient;
import org.apache.log4j.Logger;
import pp.component.JBorderPanel;
import pp.model.AModel;
import pp.model.Armor;
import pp.model.enums.GladType;
import pp.model.xml.CArmor;
import pp.model.xml.CArmors;
import pp.model.xml.CRoster;
import pp.service.GlRuService;
import pp.service.GlRuServiceImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class Armors extends JFrame implements ActionListener {
    public static final String ARMORY = "Оружейная";
    public static final String YOU_HAVE = "У Вас %d золотых и %d серебряных доспехов";
    public static final String TAKE = "Забрать доспехи";
    public static final String BUY = "Купить доспехи";
    public static final String [] levels = new String[]{"1-9", "10-19", "20-29", "30-39", "40+"};
    public static final String [] types = new String[]{"Ретиарий", "Секутор", "Мурмиллон", "Димахер", "Фракиец", "Велит", "Гопломах", "Саггитарий", "Эквит", "Эсседарий"};

    public static final Logger LOGGER = Logger.getLogger(Tournaments.class);
    public static final String[] qualities = new String [] {"Золотых", "Серебряных"};
    public static final String[] qualities2 = new String [] {"+0", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10"};

    public static final JTextField takeQuantity = new JTextField("1   ");
    public static final JComboBox takeQualities = new JComboBox(qualities);
    public static final JComboBox takeTypes = new JComboBox(types);
    public static final JComboBox takeLevels = new JComboBox(levels);

    public static final JComboBox buyQualityFrom = new JComboBox(qualities2);
    public static final JComboBox buyQualityTo = new JComboBox(qualities2);
    public static final JComboBox buyLevels = new JComboBox(levels);
    public static final JLabel infoLabel = new JLabel();
    public static Collection<AModel> goldFreeArmors = new ArrayList<AModel>();
    public static Collection<AModel> silverFreeArmors = new ArrayList<AModel>();

    public static Collection<Armor> armory = new ArrayList<Armor>();

    public static GlRuService service;

    static {
        buyQualityFrom.setSelectedIndex(1);
        buyQualityTo.setSelectedIndex(10);
    }

    public Armors() throws IOException {
        int goldCount = 5;
        int silverCount = 6;
        setTitle(ARMORY);
        setSize(200, 600);
        move(300, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        Container content = getContentPane();
        BoxLayout layout1 = new BoxLayout(content, BoxLayout.Y_AXIS);
        content.setLayout(layout1);

        JPanel infoPanelContent = new JPanel();
        infoLabel.setText(String.format(YOU_HAVE, goldFreeArmors.size(), silverFreeArmors.size()));

        infoPanelContent.add(infoLabel);
        JPanel infoPanel = new JBorderPanel(ARMORY, null, infoPanelContent, BorderFactory.createLineBorder(Color.black));
        content.add(infoPanel);

        JPanel takePanelContent = new JPanel();
        JPanel takePanel = new JBorderPanel(TAKE, null, takePanelContent, BorderFactory.createLineBorder(Color.black));
        takePanelContent.setLayout(new BoxLayout(takePanelContent, BoxLayout.X_AXIS));
        takePanelContent.add(takeTypes);
        takePanelContent.add(takeLevels);
        takePanelContent.add(takeQuantity);
        takePanelContent.add(takeQualities);
        JButton takeButton = new JButton(TAKE);
        takeButton.setActionCommand("take");
        takeButton.addActionListener(this);
        takePanelContent.add(takeButton);
        //
        content.add(takePanel);

        JPanel buyArmorsContent = new JPanel();
        JPanel buyArmorsPanel = new JBorderPanel(BUY, null, buyArmorsContent, BorderFactory.createLineBorder(Color.black));
        buyArmorsContent.setLayout(new BoxLayout(buyArmorsContent, BoxLayout.X_AXIS));
        buyArmorsContent.add(buyLevels);
        buyArmorsContent.add(buyQualityFrom);
        buyArmorsContent.add(buyQualityTo);
        JButton buyButton = new JButton(BUY);
        buyButton.setActionCommand("buy");
        buyButton.addActionListener(this);
        buyArmorsContent.add(buyButton);
        //
        content.add(buyArmorsPanel);

        pack();
    }


    public static void main(String[] args) throws IOException {
        MyHttpClient client = new MyHttpClient();
        client.appendInitialCookie("cookie_lang_3", "rus", Settings.getServer());
        client.appendInitialCookie("cookie_lang_2", "rus", Settings.getServer());
        service = new GlRuServiceImpl(client, new GlRuParser(), Settings.getServer());
        service.login(Settings.getUser(), Settings.getPassw());
        updateArmors();
        try {
            CRoster roster = service.getRoster();
            CArmors armors = roster.getArmors();
            List<CArmor> armorList = armors.getArmors();
            for (int level = 1; level < 6; ++level) {
                LOGGER.debug("Level " + level);
                for (GladType gladType : GladType.values()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(gladType.name()).append(": ");
                    int simple = 0;
                    for (CArmor armor : armorList) {
                        if (armor.getLevel() == level && armor.getTypeid() == gladType.getId()) {
                            if (armor.getMorale() > 0) sb.append(armor.getMorale()).append(",");
                            else simple++;
                        }
                    }
                    sb.append(" + " + simple);
                    LOGGER.info(sb.toString());
                }
                LOGGER.info("\n");
            }
            //System.in.read();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        new Armors();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("take".equals(e.getActionCommand())) {
            int option = JOptionPane.showConfirmDialog(null, "Хотите забрать " + takeQuantity.getText() + " " + takeQualities.getSelectedItem() + " доспехов?");
            if (option == 0) {
                int qty = getSafeInteger(takeQuantity);
                if (qty > 0) {
                    Collection<AModel> takeIdsFrom = null;
                    if (takeQualities.getSelectedItem().equals(qualities[0]))
                        takeIdsFrom =  goldFreeArmors;
                    if (takeQualities.getSelectedItem().equals(qualities[1]))
                        takeIdsFrom =  silverFreeArmors;
                    int taken = 0;
                    for (AModel model : takeIdsFrom) {
                        if (taken == qty) break;
                        int type = takeTypes.getSelectedIndex() + 1;
                        int level = takeLevels.getSelectedIndex() + 1;
                        try {
                            service.takeArmor(model.getId(), type,  level);
                            taken++;
                        } catch (IOException e1) {
                            LOGGER.error("Failed to take armor id " + model.getId());
                        }

                    }
                    JOptionPane.showMessageDialog(takeQuantity, "" + taken + " доспехов было взято");
                    try {
                        updateArmors();
                        infoLabel.setText(String.format(YOU_HAVE, goldFreeArmors.size(), silverFreeArmors.size()));
                    } catch (IOException e1) {
                        LOGGER.error(e1);
                    }
                }
            }
        } else
        if ("buy".equals(e.getActionCommand())) {
            int option = JOptionPane.showConfirmDialog(null, "Хотите купить доспехи " + buyQualityFrom.getSelectedItem() +" -- " + buyQualityTo.getSelectedItem() + "до уровня " + buyLevels.getSelectedItem() + " включительно?");
            if (option == 0) {
                int bought = 0;
                for (Armor armor : armory) {
                    if (armor.getLevel() == buyLevels.getSelectedIndex() + 1
                            && armor.getMorale() <= buyQualityTo.getSelectedIndex()
                            && armor.getMorale() >= buyQualityFrom.getSelectedIndex()) {
                        try {
                            service.buyArmor(armor);
                            bought++;
                        } catch (IOException e1) {
                            LOGGER.error("Failed to buy armor with id " + armor.getId());
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "" + bought + " доспехов было куплено");
                try {
                    updateArmors();
                } catch (IOException e1) {
                    LOGGER.error(e1);
                }
            }
        }
    }

    private static void updateArmors() throws IOException {
        service.updateFreeArmors(goldFreeArmors, silverFreeArmors);
        service.updateaArmorsShop(armory);
    }

    private int getSafeInteger(JTextField takeQuantity) {
        String qty = takeQuantity.getText().trim();
        try {
            return Integer.parseInt(qty);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, qty + " неверный параметр, введите целое число");
            return 0;
        }
    }
}
