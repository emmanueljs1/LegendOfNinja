import java.awt.Graphics;

/* An object in the game that cannot move by itself. Examples are: platforms,
 * stairs, weapons, etc.
 */

public class GameObj {
    
    public int horizontalBound; //horizontal bound of object
    public int verticalBound; //vertical bound of object
    private int px; //x coordinate
    private int py; //y coordinate
    private int width; //width of object
    private int height; //height of object
    
    //constructs the game object with the x and y coordinates, the width and
    //the height, and the x and y bounds
    public GameObj(int px, int py, int width, int height, int bx, int by) {
        
        this.horizontalBound = bx;
        this.verticalBound = by;
        
        if (px < 0) 
            this.px = 0;
        else if (px >= horizontalBound - width) 
            this.px = horizontalBound - width;
        else
            this.px = px;
        
        if (py < 0) 
            this.py = 0;
        else if (py >= verticalBound - height)
            this.py = verticalBound - height;
        else
            this.py = py;
        
        this.width = width;
        this.height = height;
        
    }
    
    public int getPosX() {
        return px;
    }
    public int getPosY() {
        return py;
    }
    public void setPosX(int px) {
        this.px = px;
    }
    public void setPosY(int py) {
        this.py = py;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    //is a character going to intersect with this game object
    public boolean willCharacterIntersect(int characterPosX, int characterPosY, Character c) {
        return (characterPosX + c.getWidth() > px && characterPosX < px + width &&
                characterPosY + c.getHeight() > py && characterPosY < py + height);
    }
    public boolean intersects(GameObj go) {
        return (go.getPosX() + go.getWidth() > px && go.getPosX() < px + width &&
                go.getPosY() + go.getHeight() > py && go.getPosX() < py + height);
    }
    void draw(Graphics g) {
    }
}