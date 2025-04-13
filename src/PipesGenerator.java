import java.awt.Graphics;
import java.util.LinkedList;

public class PipesGenerator {

    LinkedList<Pipes> p = new LinkedList<Pipes>();
    private int space = 200;
    public int pos = 200;
    public Bird player;

    public PipesGenerator(Bird player) {
        this.player = player;
    }

    public void render(Graphics g, Camera cam) {
        for (int i = 0; i < 6; i++) {
            if (p.size() < 6) {
                Pipes temp = new Pipes();
                pos = (pos + space) + 80;
                temp.setPos(pos);
                p.add(temp);
            }

            p.get(i).render(g);

            if ((player.getBounds().intersects(p.get(i).getBounds(0))
                    || player.getBounds().intersects(p.get(i).getBounds(2))) && !player.gameOver) {
                player.gameOver = true;
                player.xVel = 0;
                player.yVel = -8;
                player.gravity = 0.25;
            }

            if (player.getBounds().intersects(p.get(i).getBounds(1)) && !p.get(i).hasScored) {
                p.get(i).hasScored = true;
                player.score++;
                if (player.score > 0 && player.score % 10 == 0) {
                    if (player.xVel < 8) {
                        player.xVel += 0.25;
                        System.out.println(player.xVel);
                    }
                }
            }

            if (Bird.restart) {
                p.get(i).hasScored = false;
            }

            if (p.get(i).getPos() + p.get(i).WIDTH < cam.camX) {
                p.remove();
            }
        }
    }
}
