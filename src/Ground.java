import java.awt.Color;
import java.awt.Graphics;

public class Ground {

    private int groundHeight = 20;
    private final int y = Game.HEIGHT * Game.SCALE - groundHeight;
    private int x = 0;

    public void update(Camera cam, Bird player) {
        int tempX = x + Game.WIDTH * Game.SCALE;

        if (cam.camX >= tempX) {
            x = tempX;
        }

    }

    public void render(Graphics g) {
        g.setColor(new Color(0xae8f60));
        g.fillRect(x, y, Game.WIDTH * Game.SCALE * 2, groundHeight);
        g.setColor(Color.green);
        g.fillRect(x, y, Game.WIDTH * Game.SCALE * 2, (int) (groundHeight / 2 - 2.5));
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

}
