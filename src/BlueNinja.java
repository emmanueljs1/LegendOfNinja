public class BlueNinja extends Character {
    
    private int vy = 5 * GameCourt.ONE_PIXEL;
    
    public BlueNinja(int px, int py, String filename, int bx, int by) {
        super(px, py, by, bx, filename);
        setWidth(getWidth() * 2);
        setHeight(getHeight() + 40);
    }
    @Override
    public void behaveAutomatically(int timeStep) {
        if (timeStep == 0 || timeStep == 20) {
            fall(vy);
            vy = -vy;
        }
    }
}