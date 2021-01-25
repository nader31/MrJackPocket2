import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Graphics {

    private static ArrayList<JButton> mBouton;


    public static void InitialisationPanel() {

        JFrame frame = new JFrame("MrJackPocket");

//--------------------------Panneau du haut-----------------------------

        JPanel northPanel = new JPanel();
        JButton northButton = new JButton("Entrer"); //ajoute un bouton
//        northButton.addActionListener(MrJackActionListener.createActionListener(northButton));
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(new JTextField("Entrez votre nom"));
        northPanel.add(northButton);

        frame.add(northPanel, BorderLayout.NORTH); //position du panneau Nord

//--------------------------Panneau de gauche-----------------------------

        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.setPreferredSize(new Dimension(500, 750));
        leftPanel.add(new Label("Cartes Alibi")); //ajoute un texte pour décrire le panneau
        frame.add(leftPanel, BorderLayout.LINE_START); //position du panneau de gauche

//--------------------------Panneau de droite-----------------------------

        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setPreferredSize(new Dimension(250, 750));
        rightPanel.setLayout(new GridLayout(6, 1));

        rightPanel.add(new Label("Jetons d'action")); //ajoute un texte pour décrire le panneau
        rightPanel.add(new Label(""));
        JButton action1 = new JButton("text");
        JButton action2 = new JButton("text");
        JButton action3 = new JButton("text");
        JButton action4 = new JButton("text");

        rightPanel.add(action1);
        rightPanel.add(action2);
        rightPanel.add(action3);
        rightPanel.add(action4);




        frame.add(rightPanel, BorderLayout.LINE_END); //position du panneau de droite

//--------------------------Panneau du centre-----------------------------

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        centerPanel.setPreferredSize(new Dimension(750, 750));
        centerPanel.setLayout(new GridLayout(5, 5));

        //Get image
        String url = "vide.png";
        ImageIcon icone = new ImageIcon(url);

        for (int i = 0; i < 6; i++) {
            JLabel caseVide = new JLabel();
            caseVide.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//            centerPanel.add(new Label("case " + i));
//            BufferedImage buttonIcon = ImageIO.read(new File(i % 8 + ".png"));
//            District district = new District (new ImageIcon(buttonIcon));
//            centerPanel.add(district);
            centerPanel.add(caseVide);
        }



        JButton bouton1 = new JButton(Game.mList.get(0).getmImage());
        JButton bouton2 = new JButton(Game.mList.get(1).getmImage());
        JButton bouton3 = new JButton(Game.mList.get(2).getmImage());
        JButton bouton4 = new JButton(Game.mList.get(3).getmImage());
        JButton bouton5 = new JButton(Game.mList.get(4).getmImage());
        JButton bouton6 = new JButton(Game.mList.get(5).getmImage());
        JButton bouton7 = new JButton(Game.mList.get(6).getmImage());
        JButton bouton8 = new JButton(Game.mList.get(7).getmImage());
        JButton bouton9 = new JButton(Game.mList.get(8).getmImage());

        centerPanel.add(bouton1);
        centerPanel.add(bouton2);
        centerPanel.add(bouton3);

        for (int i = 9; i < 11; i++) {
            JLabel caseVide = new JLabel();
            caseVide.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            centerPanel.add(caseVide);
        }

        centerPanel.add(bouton4);
        centerPanel.add(bouton5);
        centerPanel.add(bouton6);

        for (int i = 14; i < 16; i++) {
            JLabel caseVide = new JLabel();
            caseVide.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            centerPanel.add(caseVide);
        }

        centerPanel.add(bouton7);
        centerPanel.add(bouton8);
        centerPanel.add(bouton9);

        for (int i = 19; i < 25; i++) {
            JLabel caseVide = new JLabel();
            caseVide.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            centerPanel.add(caseVide);
        }

        frame.add(centerPanel, BorderLayout.CENTER); //position du panneau Central

//--------------------------Panneau du bas-----------------------------

//        JButton southButton = new JButton("Sabliers");
//        frame.add(southButton, BorderLayout.SOUTH); //position du panneau Sud

        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        southPanel.setPreferredSize(new Dimension(1550, 150));
        southPanel.add(new Label("Sabliers")); //ajoute un texte pour décrire le panneau
        frame.add(southPanel, BorderLayout.SOUTH); //position du panneau Sud

//        jLabel.setIcon(new ImageIcon("0.png"));
//        jLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
//        jLabel.setPreferredSize(new Dimension(500, 500));
//        jLabel.setVerticalTextPosition(SwingConstants.CENTER);
//        jLabel.setVerticalAlignment(SwingConstants.CENTER);



//--------------------------Instructions fenetre-----------------------------

        frame.setSize(1500,950);
//        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
