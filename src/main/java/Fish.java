import javafx.scene.canvas.GraphicsContext;     //Import library

class Fish implements GameObject {
    private int WIDTH = 76;
    private int HEIGHT = 60;
    private Asset assets[] = {      //Set the path, width and height of the fish
        new Asset("/images/fish0.png", WIDTH, HEIGHT),
        new Asset("/images/fish1.png", WIDTH, HEIGHT),
        new Asset("/images/fish2.png", WIDTH, HEIGHT)
    };
    private Sprite sprite;                  //Declared the variables
    private int currentAssetIndex = 0;
    private long prevTime = 0;
    private float terminalVel = 8;
    private float shiftMax = 10;
    private float shiftDelta = 0;
    private double screenHeight;

    public Fish(double screenWidth, double screenHeight, GraphicsContext ctx) {
        this.screenHeight = screenHeight;

        sprite = new Sprite(assets[currentAssetIndex]);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);
        sprite.setPosY( FishEscape.gameEnded ? screenHeight - 112 - HEIGHT : (screenHeight - 112) / 2 );
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }

    public void jumpHandler() {
        sprite.setVelY(-8);
    }

    public void update(long now) {      //Update the fish status
        if (!FishEscape.gameStarted && !FishEscape.gameEnded){
            updateFishHovering();
        } else if (FishEscape.gameEnded) {
            updateFishFalldown();
        } else if (FishEscape.gameStarted) {
            if (now - prevTime > 90000000) {
                updateSprite();
                prevTime = now;
            }

            if ((sprite.getPosY() + HEIGHT) > (screenHeight - 112) ||
                sprite.intersects(FishEscape.activeSharks[0]) ||
                sprite.intersects(FishEscape.activeSharks[1])
                ) {
                
                FishEscape.gameStarted = false;
                FishEscape.gameEnded = true;
                FishEscape.music3(); // sound effect when the game over
            }

            updateFishPlay();
        }

        sprite.update();
    }

    public void updateFishHovering() {      //Update the fish hovering
        double vel = sprite.getVelY();


        if (shiftDelta == 0) {
            sprite.setVelY(0.5);
            shiftDelta += 0.5;
        } else if (shiftDelta > 0) {
            if (vel > 0.1) {            
                if (shiftDelta < shiftMax / 2) {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                } else {
                    float shift = (float) (vel * 0.8);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            } else if (vel < 0.1) {
                if (vel > 0) {
                    sprite.setVelY(-0.5);
                } else {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            }
        } else if (shiftDelta < 0) {
            if (vel < -0.1) {            
                if (shiftDelta > -shiftMax / 2) {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                } else {
                    float shift = (float) (vel * 0.8);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            } else if (vel > -0.1) {
                if (vel < 0) {
                    sprite.setVelY(0.5);
                } else {
                    float shift = (float) (vel * 1.06);
                    sprite.setVelY(shift);
                    shiftDelta += shift;
                }
            }
        }
    }

    public void updateFishPlay() {      //Update the fish motion when playing
        double vel = sprite.getVelY();

        if (vel >= terminalVel)
            sprite.setVelY(vel + 0.2);
        else
            sprite.setVelY(vel + 0.44);
    }

    public void updateFishFalldown() {      //Update the fish motion of falling down
        if (sprite.getPosY() + HEIGHT >= screenHeight - 112) {
            sprite.setVel(0, 0);
            sprite.setPosY(screenHeight - 112 - HEIGHT);

        } else {
            updateFishPlay();
        }
        
    }

    public void updateSprite() {
        if (currentAssetIndex == 3)
            currentAssetIndex = 0;

        sprite.changeImage(assets[currentAssetIndex]);

        currentAssetIndex++;
    }

    public void render() {
        sprite.render();
    }
}