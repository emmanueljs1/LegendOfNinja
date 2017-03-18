import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Game implements Runnable {

    @Override
    public void run() {

        final JFrame frame = new JFrame("The Legend of Ninja");
        frame.setLocation(300, 300);
        
        int numStages = 3;
        
        String[] stageFilenames = new String[numStages];
        Enemy[] enemyTypes = new Enemy[numStages];
        
        for (int i = 0; i < numStages; i++) {
            if (i % 3 == 0) {
                stageFilenames[i] = "background.png";
                enemyTypes[i] = Enemy.RedNinja;
            }
            else if (i % 3 == 1) {
                stageFilenames[i] = "background1.png";
                enemyTypes[i] = Enemy.BlueNinja;
            }
            else if (i % 3 == 2) {
                stageFilenames[i] = "background2.png";
                enemyTypes[i] = Enemy.YellowNinja;
            }
        }
        
        String playerFilename = "player.png";
        String swordFilename = "sword.png";
        final String highScoreTextFile = "highscores.txt";

        final GameCourt court = new GameCourt(stageFilenames, enemyTypes, playerFilename, 
                swordFilename, highScoreTextFile);
    
        frame.add(court, BorderLayout.CENTER);
        
        final JTextField textField = new JTextField(20);
        final JPanel panel = new JPanel();
        final JLabel nameButtonLabel = new JLabel();
        nameButtonLabel.setText("Set Name");
        final JButton setNameButton = new JButton();
        setNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (court.playerWon()) {
                    court.updateHighScores(textField.getText());
                    court.toTitleScreen();
                    court.requestFocus();
                }
            }
        });
        textField.addKeyListener(new KeyListener() {
            private void loseFocus(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || !court.playerWon()) {
                    court.requestFocus();
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                loseFocus(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                loseFocus(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                loseFocus(e);
            }
        });
        setNameButton.add(nameButtonLabel);
        panel.add(textField, BorderLayout.WEST);
        panel.add(setNameButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);
            
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}