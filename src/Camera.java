public class Camera {

    public double camX;
    private double offsetX;
    private Bird player = null;

    public Camera(Bird player, double offsetX) {
        this.player = player;
        this.offsetX = offsetX;
    }

    public void update() {
        camX = player.getX() + offsetX;
    }
}
