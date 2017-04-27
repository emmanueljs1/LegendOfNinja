import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Weapon extends GameObj {

    private BufferedImage img;

    public Weapon(int px, int py, int bx, int by, String filename) {
        super(px, py, 0, 0, bx, by);
        try {
            img = ImageIO.read(new File(filename));
            this.setWidth(img.getWidth() / 2);
            this.setHeight(img.getHeight() / 2);
        }
        catch (Exception e) {
            System.out.println("ERROR: Weapon image not found.");
        }
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, getPosX(), getPosY(), getWidth(), getHeight(), null);
    }
}
