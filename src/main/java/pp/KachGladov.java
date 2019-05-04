package pp;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class KachGladov extends JFrame  implements ActionListener {
    public static final Logger LOGGER = Logger.getLogger(KachGladov.class);
    public static final String KACH_GLADOV = "Кач Гладов";
    public static final String SLIV_GLADOV = "Слив Гладов";
    public static final String LOGIN = "логин";
    public static final String PASSWORD = "пароль";
    public static final String OPPONENT = "оппонент";
    public static final String GLADLIMIT = "количество гладиаторов";
    public static final String MAXLEVEL = "максимальный уровень";
    public static final String TOTALLEVEL = "суммарный уровень";
    public static final String GLADFILTER = "типы гладиаторов";
    public static final String KACH = "КАЧ";
    public static final String SLIV = "СЛИВ";
    public static final String STOP = "СТОП";
    public static final String TRANING = "Тренировка";
    public static final String MGT = "МГТ";
    public static final String BOTUS = "BOTUS";
    public static final String START_STOP_ACTION = "START_STOP_ACTION";
    public static final String PURPOSE_ACTION = "PURPOSE_ACTION";
    public static final String DEFAULT_FILTER = "2,3,4,5";
    public static final String BATTLE = "Сражение";
    public static final String SURVIVE = "Выживание";
    public JComboBox purpose = new JComboBox(getPurposeItems());
    public JComboBox place = new JComboBox(getPlaceItems());
    public JComboBox btltype = new JComboBox(getBtlTypeItems());
    public JTextField login = new JTextField();
    public JTextField password = new JTextField();
    public JTextField opponent = new JTextField(BOTUS);
    public JTextField gladlimit = new JTextField("7");
    public JTextField maxlevel = new JTextField("7");
    public JTextField totallevel = new JTextField("21");
    public JTextField gladfilter = new JTextField(DEFAULT_FILTER);
    public JButton startStop = new JButton(KACH);
    private boolean started;
    KachThread thread;
    public static int delta;

    public KachGladov(){
        //JFrame jf=new JFrame();
        setTitle(KACH_GLADOV);
        setSize(200, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        Container content = getContentPane();
        BoxLayout layout1 = new BoxLayout(content, BoxLayout.Y_AXIS);
        content.setLayout(layout1);
        content.add(purpose);
        content.add(place);
        content.add(btltype);
        content.add(new JLabel(LOGIN));
        login.setPreferredSize(new Dimension(200, 20));
        password.setPreferredSize(new Dimension(200, 20));
        opponent.setPreferredSize(new Dimension(200, 20));
        gladlimit.setPreferredSize(new Dimension(200, 20));
        maxlevel.setPreferredSize(new Dimension(200, 20));
        totallevel.setPreferredSize(new Dimension(200, 20));
        gladfilter.setPreferredSize(new Dimension(200, 20));
        content.add(login);
        content.add(new JLabel(PASSWORD));
        content.add(password);
        content.add(new JLabel(OPPONENT));
        content.add(opponent);
        content.add(new JLabel(GLADLIMIT));
        content.add(gladlimit);
        content.add(new JLabel(MAXLEVEL));
        content.add(maxlevel);
        content.add(new JLabel(TOTALLEVEL));
        content.add(totallevel);
        content.add(new JLabel(GLADFILTER));
        content.add(gladfilter);

        JPanel attackButtonPane = new JPanel();
        attackButtonPane.add(startStop);
        content.add(attackButtonPane);
        startStop.setActionCommand(START_STOP_ACTION);
        startStop.addActionListener(this);
        purpose.setActionCommand(PURPOSE_ACTION);
        purpose.addActionListener(this);
        pack();
    }

    public static void main(String s[]) {
        new KachGladov();
    }

    private static String[] getPurposeItems() {
        return new String[]{KACH, SLIV};
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (START_STOP_ACTION.equals(e.getActionCommand())) {
            if (started) {
                thread.stopThis();
                startStop.setText(isKach() ? KACH : SLIV);
                purpose.setEnabled(true);
                place.setEnabled(true);
                btltype.setEnabled(true);
                login.setEnabled(true);
                password.setEnabled(true);
                opponent.setEnabled(true);
                gladlimit.setEnabled(true);
                maxlevel.setEnabled(true);
                totallevel.setEnabled(true);
                gladfilter.setEnabled(true);
                started = false;
            } else {
                try {
                    Settings.init();
                } catch (IOException e1) {
                }
                thread = new KachThread(isKach(),
                        place.getSelectedItem().toString(),
                        btltype.getSelectedItem().toString(),
                        login.getText().trim(),
                        password.getText().trim(),
                        opponent.getText().trim(),
                        Integer.parseInt(gladlimit.getText().trim()),
                        Integer.parseInt(maxlevel.getText().trim()),
                        Integer.parseInt(totallevel.getText().trim()),
                        gladfilter.getText().trim(),
                        Settings.getDelay()
                        );
                thread.start();
                startStop.setText(STOP);
                purpose.setEnabled(false);
                place.setEnabled(false);
                btltype.setEnabled(false);
                login.setEnabled(false);
                password.setEnabled(false);
                opponent.setEnabled(false);
                gladlimit.setEnabled(false);
                maxlevel.setEnabled(false);
                totallevel.setEnabled(false);
                gladfilter.setEnabled(false);
                started = true;
            }
        } else if (PURPOSE_ACTION.equals(e.getActionCommand())) {
            if (purpose.getSelectedItem().equals(KACH)) {
                setTitle(KACH_GLADOV);
                startStop.setText(KACH);
            }
            if (purpose.getSelectedItem().equals(SLIV)) {
                setTitle(SLIV_GLADOV);
                startStop.setText(SLIV);
            }

        }

    }

    private boolean isMgt() {
        return place.getSelectedItem().equals(MGT);
    }

    private boolean isKach() {
        return purpose.getSelectedItem().equals(KACH);
    }

    public String[] getPlaceItems() {
        return new String[]{TRANING, MGT};
    }
    public String[] getBtlTypeItems() {
        return new String[]{BATTLE, SURVIVE};
    }

}
