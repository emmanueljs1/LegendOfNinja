public class RedNinja extends Character {
    
    public RedNinja(int px, int py, String filename, int bx, int by) {
        super(px, py, by, bx, filename);
        setWidth(getWidth() * 2);
        setHeight(getHeight() + 40);
    }
    @Override
    public void behaveAutomatically(int timeStep) {
        if (timeStep < 10) {
            walk(- GameCourt.ONE_PIXEL);
        }
        else {
            walk(GameCourt.ONE_PIXEL);
        }
    }
}
