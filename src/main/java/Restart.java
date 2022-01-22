import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;     //Import libraries

class Restart implements GameObject {
    private int WIDTH = 200;
    private int HEIGHT = 200;       //Set the path, width and height of the replay button image
    private Asset asset = new Asset("/images/replay.png", WIDTH, HEIGHT);
    private Sprite sprite;

    public Restart(double screenWidth, double screenHeight, GraphicsContext ctx) {
        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);        //Set the position of the replay button
        sprite.setPosY(screenHeight - 200);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }

    public boolean checkClick(double posX, double posY) {
        return sprite.intersects( new Rectangle2D(posX, posY, 1, 1) );
    }

    public void update(long now) {
    }

    public void render() {
        if (FishEscape.gameEnded)
            sprite.render();
    }
}