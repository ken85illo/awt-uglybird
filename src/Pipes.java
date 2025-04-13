import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Pipes {

    private int pos = 0;
    private int distance = 140;
    public final int WIDTH = 80;
    private int height = 0;
    Random rand = new Random();
    private int bounds = 50;
    public boolean hasScored = false;

    public Pipes() {
        height = rand.nextInt(bounds, (Game.HEIGHT * Game.SCALE - distance) - bounds);
    }

    public void render(Graphics g) {
        g.setColor(new Color(0x328003));
        g.fillRect(pos, 0, WIDTH, height);
        g.fillRect(pos - 10, height - 10, WIDTH + 20, 10);
        g.fillRect(pos, height + distance, WIDTH, Game.HEIGHT * Game.SCALE - height + distance);
        g.fillRect(pos - 10, height + distance, WIDTH + 20, 10);
    }

    public Rectangle getBounds(int index) {
        int width = 30;
        switch (index) {
            case 0:
                return new Rectangle(pos, 0, WIDTH, height);
            case 1:
                return new Rectangle((pos + width / 2) + 10, height, width, (height + distance) - height);
            case 2:
                return new Rectangle(pos, height + distance, WIDTH, Game.HEIGHT * Game.SCALE - height + distance);
        }
        return null;
    }

    public void setPos(int position) {
        this.pos = position;
    }

    public int getPos() {
        return pos;
    }

}
