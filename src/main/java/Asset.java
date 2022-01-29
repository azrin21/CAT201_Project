// class to store typical files that we want to read in an application at a later time.
class Asset {   //Create the class named Asset
    private String path;        //Declare the variables
    private double width, height;

    public Asset(String path, double width, double height) {
        this.path = path;       //Set the path, width and the height of the asset
        this.width = width;
        this.height = height;        
    }
    
    public double getWidth() {      //Get the width of the asset
        return width;
    }
    
    public double getHeight() {     //Get the height of the asset
        return height;
    }
    
    public String getPath() {       //Get the path of the asset
        return path;
    }
}
