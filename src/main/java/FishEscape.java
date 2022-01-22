import javafx.application.Application; //Import library
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import java.nio.file.Paths;
import java.util.Map;
import java.util.LinkedHashMap;

public class FishEscape extends Application {   //Main program
 
    private Scene scene;
    private GraphicsContext ctx;
                                        //Make the font style and get from resource font folder
    public static Font appFont = Font.loadFont(FishEscape.class.getResource("./fonts/04b_19.ttf").toExternalForm(), 42);
    public static Color appColor = Color.web("#543847");    //Make the color
    private double width = 450;
    private double height = 600;
    private double minWidth = 365;
    private double minHeight = 412;


    public static Map<String, GameObject> gameObjects = new LinkedHashMap<String, GameObject>();
    private Fish fish;
    private Restart restart;
    public static Sprite activeSharks[];

    private AnimationTimer timer;
    public static boolean gameStarted = false;
    public static boolean gameEnded = false;

    public static int score = 0;
    public static int highscore = 0;

    public void start(Stage stage) {        //Start the stage which is the window
        stage.setTitle("Fish Escape");      //Set the title of the window
        stage.getIcons().add(new Image("/images/icon.jpeg"));   //Set the icon
        stage.setMinWidth(minWidth);        //Set the width and height of the window
        stage.setMinHeight(minHeight);

        setScene(stage);
        initRender();
        startGameLoop();

        stage.show();   //Show the content of the window
    }

    private void setScene(Stage stage) {        //Set the content of the window
        Pane pane = new Pane();
        Canvas canvas = new Canvas();
        ctx = canvas.getGraphicsContext2D();

        canvas.heightProperty().bind(pane.heightProperty());
        canvas.widthProperty().bind(pane.widthProperty());

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = newVal.doubleValue();
            resizeHandler();
        });
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = newVal.doubleValue();
            resizeHandler();
        });

        pane.getChildren().addAll(canvas);
        scene = new Scene(pane, width, height);

        setInputHandlers(scene);
        stage.setScene(scene);
    }

    private void setInputHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {     //When press the space bar, the fish will swim
                inputHandler(-1, -1);    //and play the sound effect
                music1();
            }
        });

        scene.setOnMousePressed(e -> {          //When click the mouse, the fish will swim
            inputHandler(e.getX(), e.getY());   //and play the sound effect
            music1();
        });
    }

    private void inputHandler(double posX, double posY) {   //Process the input handler
        if (!gameEnded) {
            fish.jumpHandler();
            gameStarted = true;
        } else if (posX == -1 && posY == -1 || restart.checkClick(posX, posY)) {
            gameStarted = false;
            gameEnded = false;

            FishEscape.score = 0;
            initRender();
        }
    }

    private void resizeHandler() {
        initRender();
    }

    private void initRender() {     //Put the elements and objects of the game inside the scene
        ctx.clearRect(0, 0, width, height);
        gameObjects.clear();

        gameObjects.put("background",   new Background(width, height, ctx));
        gameObjects.put("shark",        new Shark(width, height, ctx));

        restart = new Restart(width, height, ctx);
        fish = new Fish(width, height, ctx);

        gameObjects.put("fish", fish);
        gameObjects.put("replay",      restart);
        gameObjects.put("score",        new Score(width, height, ctx));
        gameObjects.put("title",        new Title(width, height, ctx));
        gameObjects.put("gameover",     new GameOver(width, height, ctx));
    }

    private void updateGame(long now) {
        for (GameObject gameObject : gameObjects.values())
            gameObject.update(now);
    }

    private void renderGame() {
        ctx.clearRect(0, 0, width, height);

        for (GameObject gameObject : gameObjects.values())
            gameObject.render();
    }

    private void startGameLoop() {
        timer = new AnimationTimer() {
            public void handle(long now) {
                updateGame(now);
                renderGame();
            }
        };
        timer.start();
    }

    public void music1() {      //Declare the first sound effect
        String s1 = "src/main/resources/audio/bubble.mp3";
        Media music1 = new Media(Paths.get(s1).toUri().toString());
        MediaPlayer m = new MediaPlayer(music1);
        m.play();
    }

    public static void music2() {   //Declare the second sound effect
        String s2 = "src/main/resources/audio/score.mp3";
       Media music2 = new Media(Paths.get(s2).toUri().toString());
        MediaPlayer m = new MediaPlayer(music2);
        m.setVolume(0.3);
        m.play();
    }

    public static void music3() {   //Declare the third sound effect
        String s3 = "src/main/resources/audio/lose.mp3";
        Media music3 = new Media(Paths.get(s3).toUri().toString());
        MediaPlayer m = new MediaPlayer(music3);
        m.play();
    }

}


