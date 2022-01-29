import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;     //Import library

class Background implements GameObject {
    private int WIDTH = 288;
    private int HEIGHT = 598;           //Set the path, width and height of the background
    private Asset asset = new Asset("/images/background.png", WIDTH, HEIGHT);
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public Background(double screenWidth, double screenHeight, GraphicsContext ctx) {
        int backgroundWidth = 0;
        do {
            Sprite background = new Sprite(asset); //sprite program to motion the background
            //selection for changing the background based on screenHeight
            if ((screenHeight - 112) < HEIGHT)
                background.resizeImage(WIDTH, HEIGHT);
            else
                background.resizeImage(WIDTH, screenHeight );
            
            if (screenHeight > HEIGHT)
                background.setPos(backgroundWidth, 0);
            else
                background.setPos(backgroundWidth, screenHeight);

            background.setVel(0, 0);
            background.setCtx(ctx);

            sprites.add(background);
            backgroundWidth += WIDTH;
        } while (backgroundWidth < (screenWidth + WIDTH));
    }

    public void update(long now) {
    }

    public void render() {
        for (Sprite background : sprites)
            background.render();
    }
}
