import javafx.scene.canvas.GraphicsContext;     //Import library

class GameOver implements GameObject {
    private int WIDTH = 400;
    private int HEIGHT = 400;       //Set the path, width anf height of the game over image
    private Asset asset = new Asset("/images/game_over.png", WIDTH, HEIGHT);
    private Sprite sprite;

    public GameOver(double screenWidth, double screenHeight, GraphicsContext ctx) {
        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);        //Set the position of the game over image
        sprite.setPosY(-120);
     //   sprite.setPosY(40);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }

    public void update(long now) {
    }

    public void render() { //graphics buffer to the default device
        if (FishEscape.gameEnded)
            sprite.render();
    }
}
