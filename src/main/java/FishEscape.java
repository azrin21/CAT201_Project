import javafx.application.Application;
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

public class FishEscape extends Application {
 
    private Scene scene;
    private GraphicsContext ctx;

    public static Font appFont = Font.loadFont(FishEscape.class.getResource("./fonts/04b_19.ttf").toExternalForm(), 42);
    public static Color appColor = Color.web("#543847");
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

    public void start(Stage stage) {
        stage.setTitle("Fish Escape");
        stage.getIcons().add(new Image("/images/icon.jpeg"));
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);

        setScene(stage);
        initRender();
        startGameLoop();

        stage.show();
    }

    private void setScene(Stage stage) {
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
            if (e.getCode() == KeyCode.SPACE)
                inputHandler(-1, -1);
                music1();
        });

        scene.setOnMousePressed(e -> {
            inputHandler(e.getX(), e.getY());
              //music1();
        });
    }

    private void inputHandler(double posX, double posY) {
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

    private void initRender() {
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

    public void music1() {
        String s1 = "src/main/resources/audio/bubble.mp3";
        Media music1 = new Media(Paths.get(s1).toUri().toString());
        MediaPlayer m = new MediaPlayer(music1);
        m.play();
    }

    public static void music2() {
        String s2 = "src/main/resources/audio/score.mp3";
       Media music2 = new Media(Paths.get(s2).toUri().toString());
        MediaPlayer m = new MediaPlayer(music2);
        m.setVolume(0.3);
        m.play();
    }

    public static void music3() {
        String s3 = "src/main/resources/audio/lose.mp3";
        Media music3 = new Media(Paths.get(s3).toUri().toString());
        MediaPlayer m = new MediaPlayer(music3);
        m.play();
    }

}


