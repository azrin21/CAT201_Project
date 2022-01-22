import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;        //Import libraries

class Sprite {
    private Image image;            //Declare the variables
    private GraphicsContext ctx;
    private String path;
    private double posX,
        posY,
        velX,
        velY,
        width,
        height;

    public Sprite(Asset asset) {
        width = asset.getWidth();
        height = asset.getHeight();
        path = asset.getPath();

        image = new Image(path, width, height, false, false);
    }

    public void changeImage(Asset asset) {      //Change the image
        width = asset.getWidth();
        height = asset.getHeight();
        path = asset.getPath();

        image = new Image(path, width, height, false, false);
    }

    public void resizeImage(double width, double height) {      //Resize the image
        image = new Image(path, width, height, false, false);
    }

    public boolean intersects(Sprite sprite) {
        return sprite.getSize().intersects(this.getSize());
    }

    public boolean intersects(Rectangle2D rectangle) {
        return rectangle.intersects(this.getSize());
    }

    public void update() {
        posX += velX;
        posY += velY;
    }
  
    public void updateTimescaled(int timescale) {       //Update the time scale
        posX += velX * timescale;
        posY += velY * timescale;
    }

    public void render() {
        ctx.drawImage(image, posX, posY);
    }

    public void setSize(double width, double height) {      //Set the size
        this.width = width;
        this.height = height;
    }

    public Rectangle2D getSize() {      //Get the size
        return new Rectangle2D(posX, posY, width, height);
    }

    public void setWidth(double width) {        //Set the width
        this.width = width;
    }

    public void setHeight(double height) {      //Set the height
        this.height = height;
    }

    public double getWidth() {      //Get the width
        return width;
    }

    public double getHeight() {     //Get the height
        return height;
    }
  
    public void setPos(double posX, double posY) {      //Set the position
        this.posX = posX;
        this.posY = posY;
    }

    public void setPosX(double posX) {      //Set the position x
        this.posX = posX;
    }

    public void setPosY(double posY) {      //Set the position y
        this.posY = posY;
    }

    public double[] getPos() {              //Get the position
        return new double[] { posX, posY };
    }

    public double getPosX() {           //Get the position x
        return posX;
    }

    public double getPosY() {           //Get the position y
        return posY;
    }

    public void setVel(double velX, double velY) {
        this.velX = velX;
        this.velY = velY;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setCtx(GraphicsContext ctx) {
        this.ctx = ctx;
    }


}
