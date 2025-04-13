import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, MouseListener {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 4 * 3;
    public static final int SCALE = 2;
    private final static String TITLE = "Bad Flappy Bird";

    private JFrame frame;

    private Thread thread;
    private boolean running = false;
    private Bird player;
    private Ground ground;
    private Camera cam;
    private PipesGenerator gen;

    private Game() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);

        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    public synchronized void start() {
        if (running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void init() {
        requestFocus();
        ground = new Ground();
        player = new Bird(ground);
        gen = new PipesGenerator(player);
        cam = new Camera(player, -100);

        addKeyListener(player);
        addMouseListener(this);
    }

    int width = 200;
    int height = 50;

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // --------------------------------
        // Render graphics here

        g.setColor(new Color(0x09abe6)); // sky blue
        g.fillRect(0, 0, getWidth(), getHeight());

        g.translate((int) -cam.camX, 0);

        gen.render(g, cam);
        ground.render(g);
        player.render(g);

        g.translate((int) cam.camX, 0);

        if (Bird.paused)
            g.drawString("PAUSED", 0, 10);

        g.setColor(Color.white);
        g.setFont(new Font("Bahnschrift", Font.BOLD, 50));
        g.drawString(String.valueOf(player.score), getWidth() / 2 - 15, 60);

        if (player.y > Game.HEIGHT * Game.SCALE + 10) {
            g.setColor(Color.white);
            g.fillRect(WIDTH * SCALE / 2 - width / 2, 180, width, height);
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Play Again?", 225, 215);
        }

        // --------------------------------

        g.dispose();
        bs.show();
    }

    boolean restarted = false;

    private void update() {
        if (Bird.restart && !restarted) {
            gen = new PipesGenerator(player);
            restarted = true;
        }

        player.update();
        cam.update();
        ground.update(cam, player);

        restarted = false;
    }

    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double amountOfTicks = 60.0;
        double ns = 1000000000.0 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        int updates = 0;

        // game loop
        while (running) {
            long initialTime = System.nanoTime();
            delta += (initialTime - lastTime) / ns;
            lastTime = initialTime;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                frame.setTitle(TITLE + " | " + frames + " fps, " + updates + "ups");
                frames = 0;
                updates = 0;
                timer += 1000;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Game game = new Game();

        game.frame = new JFrame(TITLE);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setResizable(false);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseX >= 219 && mouseX <= 420 && player.y > Game.HEIGHT * Game.SCALE + 10) {
            if (mouseY >= 179 && mouseY <= 231) {
                Bird.restart = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
