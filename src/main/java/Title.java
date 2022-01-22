import javafx.scene.canvas.GraphicsContext;

class Title implements GameObject {
    private int WIDTH = 300;
    private int HEIGHT = 300;
    private Asset asset = new Asset("/images/title2.png", WIDTH, HEIGHT);
    private Sprite sprite;

    public Title(double screenWidth, double screenHeight, GraphicsContext ctx) {
        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);
        sprite.setPosY(30);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }

    public void update(long now) {
    }

    public void render() {
        if (!FishEscape.gameStarted && !FishEscape.gameEnded)
            sprite.render();
    }
}