import org.junit.*;
import static org.junit.Assert.*;

public class CollisionTests {

    @Test
    public void testObjectIntersectsAnotherObject() {
        
        GameObj go0 = new GameObj(0, 0, 50, 50, 100, 100);
        GameObj go1 = new GameObj(30, 30, 60, 60, 100, 100);
        
        assertTrue(go0.intersects(go1));
        
    }
    
    @Test
    public void testObjectDoesNotIntersectsAnotherObject() {
        
        GameObj go0 = new GameObj(0, 0, 50, 50, 1000, 1000);
        GameObj go1 = new GameObj(51, 51, 3, 3, 1000, 1000);
        
        assertFalse(go0.intersects(go1));
        
    }
    
    @Test
    public void testCharacterIntersectsAnObject() {
        Character c = new Character(0, 0, 1000, 1000, "player.png");
        GameObj go = new GameObj(0, 0, 50, 50, 1000, 1000);
        
        assertTrue(go.willCharacterIntersect(0, 0, c));
    }
    
    @Test
    public void testCharacterDoesNotIntersectAnObject() {
        Character c = new Character(0, 0, 1000, 1000, "player.png");
        GameObj go = new GameObj(50, 50, 50, 50, 1000, 1000);
        
        assertFalse(go.willCharacterIntersect(0, 0, c));
    }
    
    @Test
    public void testCharacterIntersectsAnotherCharacter() {
        
        Character c0 = new Character(0, 0, 600, 600, "player.png");
        Character c1 = new Character(10, 10, 600, 600, "player.png");
        
        assertTrue(c0.intersects(c1));
        
    }
    
    @Test
    public void testCharacterDoesNotIntersectAnotherCharacter() {
        
        Character c0 = new Character(0, 0, 600, 600, "player.png");
        Character c1 = new Character(40, 40, 600, 600, "player.png");
        
        assertFalse(c0.intersects(c1));
        
    }
    
    @Test
    public void testPlayerSwordIntersectsAnotherCharacter() {
        
        Player p = new Player(0, 0, 1000, 1000, "player.png", "sword.png");
        Character c = new Character(p.getPosX() + p.getWidth() + 2, 0, 1000, 1000, "blueninja.png");
        
        assertTrue(p.swordIntersectsEnemy(c));
        
    }
    
    @Test
    public void testPlayerSwordDoesNotIntersectAnotherCharacter() {
        
        Player p = new Player(0, 0, 5000, 5000, "player.png", "sword.png");
        Weapon sword = new Weapon(p.getWidth(), p.getPosY() + p.getHeight() / 2, 5000, 
                5000, "sword.png");
        sword.setWidth(sword.getWidth() * 2);
        Character c = new Character(p.getWidth() + sword.getWidth(), 0, 5000,
                5000, "blueninja.png");
        
        assertFalse(p.swordIntersectsEnemy(c));
        
    }
    
    @Test
    public void testPlayerIntersectsACharacter() {
        
        Player p = new Player(0, 0, 5000, 5000, "player.png", "sword.png");
        Character c = new Character(0, 0, 5000, 5000, "blueninja.png");
        
        assertTrue(p.intersects(c));
    }
    
    @Test
    public void testPlayerDoesNotIntersectACharacter() {
        
        Player p = new Player(0, 0, 5000, 5000, "player.png", "sword.png");
        Character c = new Character(p.getWidth(), 0, 5000, 5000, "blueninja.png");
        
        assertFalse(p.intersects(c));
    }
    
}