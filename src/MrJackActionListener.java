import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MrJackActionListener implements ActionListener {

    public static void main(String[] args) {
        JButton button = new JButton();
        button.addActionListener(MrJackActionListener.createActionListener(new Game(), button));
//        JLabel label = new JLabel();
//        label.addMouseListener(MrJackMouseListener.createMouseListener(label));
    }

    private Game game;
    private JButton button;

    public MrJackActionListener(Game game, JButton button) {
        this.game = game;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.turnOverDistrict();
        button.setText("success");
//        JOptionPane.showMessageDialog(null,"Hello World");
//        if (button.getText().equals("Entrer")) {
//            button.setText("Validé");
//        } else {
//            button.setText("Entrer");
//        }
    }

// ------------------------------------------------------------------------------

//    public MrJackActionListener(Game game) {
//        this.game = game;
//    }

// ------------------------------------------------------------------------------

    public static ActionListener createActionListener(Game game, JButton button) {
        return new MrJackActionListener(game, button);
    }

}