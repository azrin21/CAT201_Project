import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import java.util.concurrent.ThreadLocalRandom;      //Import libraries

class Shark implements GameObject {
    private int WIDTH = 200;
    private int HEIGHT = 100;       //Set path, width and height of the shark
    private Asset assetUp = new Asset("/images/shark.png", WIDTH, HEIGHT);
    private Asset assetDown = new Asset("/images/shark.png", WIDTH, HEIGHT);
    private ArrayList<Sprite> spritesUp = new ArrayList<>();
    private ArrayList<Sprite> spritesDown = new ArrayList<>();

    private double screenHeight, screenWidth;
    private GraphicsContext ctx;

    public Shark(double screenWidth, double screenHeight, GraphicsContext ctx) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.ctx = ctx;

        Sprite sharks[] = createsharks(screenWidth + 200);
        FishEscape.activeSharks = sharks;
        spritesUp.add(sharks[0]);
        spritesDown.add(sharks[1]);
    }

    public void update(long now) {
        if (FishEscape.gameStarted) {
            for (int i = 0; i < spritesUp.size(); i++) {
                spritesUp.get(i).update();
                spritesDown.get(i).update();

                if (FishEscape.activeSharks[0].getPosX() + WIDTH < screenWidth / 2 - 56) {
                    FishEscape.activeSharks= new Sprite[] { spritesUp.get(i), spritesDown.get(i) };
                }
            }
        }
    }

    public void render() {
        for (Sprite sharks: spritesUp)
            sharks.render();

        for (Sprite sharks : spritesDown)
           sharks.render();

        if (spritesUp.get( spritesUp.size() - 1 ).getPosX() < screenWidth) {
            Sprite sharks[] = createsharks( spritesUp.get( spritesUp.size() - 1 ).getPosX() + 230 );
      
            spritesUp.add(sharks[0]);
            spritesDown.add(sharks[1]);
        }
    
        if (spritesUp.get(0).getPosX() < -WIDTH) {
            spritesUp.remove(0);
           spritesDown.remove(0);
        }
    }
    //random shark position
    private Sprite[] createsharks(double posX) {
        double usableHeight = screenHeight - 364;
        int randomNum = ThreadLocalRandom.current().nextInt(0, (int) usableHeight + 1);

        Sprite sharkUp = new Sprite(assetUp);
        sharkUp.setPos(posX, 20 + randomNum);
        sharkUp.setVel(-5.5, 0);
        sharkUp.setCtx(ctx);

       Sprite sharkDown = new Sprite(assetDown);
        sharkDown.setPos(posX, 50 + randomNum);
        sharkDown.setVel(5, 0);
        sharkDown.setCtx(ctx);

        return new Sprite[] {sharkUp,sharkDown};
    }
}