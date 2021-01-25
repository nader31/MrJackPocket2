import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graph {
    private JButton button;
    private JPanel panel;
    private JLabel d1;
    private JLabel d2;
    private JLabel d3;
    private JLabel d4;
    private JLabel d5;
    private JLabel d6;
    private JLabel d7;
    private JLabel d8;
    private JLabel d9;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;

    public Graph() {

//        button1.addActionListener(MrJackActionListener.createActionListener(button1));
//        button2.addActionListener(MrJackActionListener.createActionListener(button2));
//        button3.addActionListener(MrJackActionListener.createActionListener(button3));
//        button4.addActionListener(MrJackActionListener.createActionListener(button4));
//        button5.addActionListener(MrJackActionListener.createActionListener(button5));
//        button6.addActionListener(MrJackActionListener.createActionListener(button6));
//        button7.addActionListener(MrJackActionListener.createActionListener(button7));
//        button8.addActionListener(MrJackActionListener.createActionListener(button8));
//        button9.addActionListener(MrJackActionListener.createActionListener(button9));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Hello World");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new Graph().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageIcon noir = new ImageIcon("noir.png");
        ImageIcon orange = new ImageIcon("orange.png");
        ImageIcon jaune = new ImageIcon("jaune.png");
        ImageIcon gris = new ImageIcon("gris.png");
        ImageIcon bleu = new ImageIcon("bleu.png");
        ImageIcon violet = new ImageIcon("violet.png");
        ImageIcon vert = new ImageIcon("vert.png");
        ImageIcon rose = new ImageIcon("rose.png");
        ImageIcon blanc = new ImageIcon("blanc.png");

        d1 = new JLabel(noir);
        d2 = new JLabel(orange);
        d3 = new JLabel(jaune);
        d4 = new JLabel(gris);
        d5 = new JLabel(bleu);
        d6 = new JLabel(violet);
        d7 = new JLabel(vert);
        d8 = new JLabel(rose);
        d9 = new JLabel(blanc);

        button1 = new JButton(d1.getIcon());
        button2 = new JButton(d2.getIcon());
        button3 = new JButton(d3.getIcon());
        button4 = new JButton(d4.getIcon());
        button5 = new JButton(d5.getIcon());
        button6 = new JButton(d6.getIcon());
        button7 = new JButton(d7.getIcon());
        button8 = new JButton(d8.getIcon());
        button9 = new JButton(d9.getIcon());


    }
}
