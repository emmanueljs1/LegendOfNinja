import java.awt.Graphics;

public class Player extends Character {
    
    private Weapon sword; //the sword the player is holding
    
    public Player(int px, int py, int bx, int by, String playerFilename, String swordFilename) {
        super(px, py, bx, by, playerFilename);
        setWidth(getWidth() * 2);
        setHeight(getHeight() + 40);
        sword = new Weapon(px + getWidth(), py + getHeight() / 2, bx, by, swordFilename);
        sword.setWidth(sword.getWidth() * 2);
        sword.setHeight(sword.getHeight() * 2);
    }

    public void resetPos() {
        setPosX(0);
        setPosY(0);
        sword.setPosX(getPosX() + getWidth());
        sword.setPosY(getPosY() + getHeight() / 2);
    }
    
    //calculate the new horizontal position of the player, taking into account his sword
    @Override
    public void walk(int vx) {
        super.walk(vx);
        sword.setPosX(getPosX() + getWidth());
        
        if (getPosX() + getWidth() + sword.getWidth() > horizontalBound) {
            sword.setPosX(horizontalBound - sword.getWidth());
            setPosX(sword.getPosX() - getWidth());
        }  
    }
    
    @Override
    public void fall(int vy) {
        int oldPosY = getPosY();
        super.fall(vy);
        sword.setPosY(sword.getPosY() - oldPosY + getPosY());
        
        if (getPosY() > verticalBound - 50 - getHeight()) {
            oldPosY = getPosY();
            setPosY(verticalBound - 50 - getHeight());
            sword.setPosY(sword.getPosY() - oldPosY + getPosY());
        }  
    }
    
    public boolean swordIntersectsEnemy(Character c) {
       return sword.willCharacterIntersect(c.getPosX(), c.getPosY(), c);
    }
    
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        sword.draw(g);
    }
}