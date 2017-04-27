import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Character extends GameObj {

    private BufferedImage img;

    public Character(int px, int py, int bx, int by, String filename) {
        super(px, py, 0, 0, bx, by);
        try {
            img = ImageIO.read(new File(filename));
            this.setWidth(img.getWidth() / 2);
            this.setHeight(img.getHeight() / 2);
        }
        catch (Exception e) {
            System.out.println("ERROR: Character image not found.");
        }
    }

    public void behaveAutomatically(int timeStep) {
        walk(GameCourt.ONE_PIXEL);
    }

    //returns new potential x coordinate of character after walking
    public void walk(int vx) {

        int newPosX = getPosX() + vx;

        if (newPosX + getWidth() >= horizontalBound) {
            newPosX = horizontalBound - getWidth();
        }
        else if (newPosX <= 0) {
            newPosX = 0;
        }

        setPosX(newPosX);
    }

    //returns new potential y coordinate of character after moving vertically
    public void fall(int vy) {
        int newPosY = getPosY() - vy;

        if (newPosY < - verticalBound + getHeight() + 50) {
            newPosY = - verticalBound + getHeight() + 50;
        }
        if (newPosY > getHeight() + 50) {
            newPosY = getHeight() + 50;
        }

        setPosY(newPosY);
    }

    //is the character intersecting another character
    public boolean intersects(Character c) {
        return willCharacterIntersect(c.getPosX(), c.getPosY(), c);
    }
    @Override
    void draw(Graphics g) {
        g.drawImage(img, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }
}
