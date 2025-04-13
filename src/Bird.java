import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird implements KeyListener {

    private double x = 100;
    public double y = Game.HEIGHT * Game.SCALE / 2;
    public final int HEIGHT = 32;
    private final int WIDTH = 40;

    public double xVel = 2.5;
    public double yVel = 0;
    public double gravity = 0.5;
    private final int MAX_FALL = 10;

    public static boolean paused = true;
    public static boolean restart = false;
    private double jump = 9;
    private boolean jumping = false;
    public boolean gameOver = false;
    public int score = 0;

    private BufferedImage bird = null;

    private Ground ground;

    public Bird(Ground ground) {
        this.ground = ground;

        try {
            bird = ImageIO.read(getClass().getResource("/flappy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g.setColor(Color.yellow);
        if (y < Game.HEIGHT * Game.SCALE) {
            if (gameOver) {
                g.setColor(Color.orange);
            }
            g2D.drawImage(bird, (int) x, (int) y, WIDTH, HEIGHT, null);
        }
    }

    public void update() {
        if (!paused) {
            yVel += gravity;

            x += xVel;
            y += yVel;

            if (yVel > MAX_FALL)
                yVel = MAX_FALL;

            if (y < 0 && !gameOver) {
                y = 0;
                yVel = 0;
            }

            if (y + HEIGHT >= ground.getY() && !gameOver) {
                gameOver = true;
                xVel = 0;
                yVel = -8;
                gravity = 0.25;
            }
        }

        if (restart) {
            gameOver = false;
            paused = true;
            y = Game.HEIGHT * Game.SCALE / 2;
            x = 100;
            xVel = 2.5;
            yVel = 0;
            gravity = 0.5;
            score = 0;
            ground.setX(0);

            restart = false;
        }
    }

    public Bird getInstance() {
        return this;
    }

    public double getX() {
        return x;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && !jumping & !gameOver) {
            paused = false;
            jumping = true;
            yVel = -jump;
        }

        if (key == KeyEvent.VK_ESCAPE && !gameOver) {
            paused = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            jumping = false;
        }
    }
}
