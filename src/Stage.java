import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Stage extends JPanel {
    
    private BufferedImage img; //image of stage
    private Player player; //player object
    private LinkedList<Character> enemies; //collection of enemies in stage
    
    public Stage(String filename, Player player, Enemy enemyType, int numEnemies) {
        try {
            img = ImageIO.read(new File(filename));
        }
        catch (Exception e) {
            System.out.println("ERROR: Stage file not found");
        }
        
        enemies = new LinkedList<>();
        this.player = player;
        
        switch (enemyType) {
        case RedNinja:
            for (int i = 0; i < numEnemies; i++) {
                enemies.add(new RedNinja(300 * (i + 1), 0, "redninja.png", getPreferredSize().width, 
                        getPreferredSize().height));
            }
            break;
        case BlueNinja:
            for (int i = 0; i < numEnemies; i++) {
                enemies.add(new BlueNinja(300 * (i + 1), 0, "blueninja.png",
                        getPreferredSize().width, getPreferredSize().height));
            }
            break;
        case YellowNinja:
            for (int i = 0; i < numEnemies; i++) {
                enemies.add(new YellowNinja(300 * (i + 1), 0, "yellowninja.png",
                        getPreferredSize().width, getPreferredSize().height));
            }
            break;
        }
    }
    
    public int getNumEnemies() {
        return enemies.size();
    }
    
    public void updateEnemies(int timeStep) {
        for (Character enemy : enemies) {
            enemy.behaveAutomatically(timeStep);
        }
    }
    
    public void killEnemy(Character c) {
        if (enemies.size() > 0) {
            enemies.remove(c);
        }
    }
    
    public Character swordIntersectsAnEnemy() {
        for (Character enemy: enemies) {
            if (player.swordIntersectsEnemy(enemy)) {
                return enemy;
            }
        }
        return null;
    }
    
    public boolean playerIntersectsAnEnemy() {
        for (Character enemy : enemies) {
            if (enemy.intersects(player)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getPreferredSize().width, getPreferredSize().height, null);
        g.translate(0, img.getHeight() - 50 - player.getHeight());
        player.draw(g);
        if (enemies.size() > 0) {
            for (Character enemy : enemies) {  
                enemy.draw(g);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(img.getWidth(), img.getHeight());
    }
}
