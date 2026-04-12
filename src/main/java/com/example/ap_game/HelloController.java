package com.example.ap_game;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HelloController {
    public Stage stage;
    public Scene scene;
    public Line line;
    public double lineLength = 1.0;
    public double startx = 93;
    public double starty = 500;
    public double endx = 93;
    public double endy = 500;
    public double ladderEndCoordinate = startx;
    public double prevLadderEndCoordinate = 93.0;
    public boolean newLadder = true;
    public double prevBase = 93;
    public List<List<Double>> hillList = new ArrayList<>();
    Rectangle startRectangle;
    public Rectangle rectangle;
    public Rectangle midRectangle;
    public Rectangle baseMidRectangle;
    double breadth;
    double xCoordinate;
    public Line prevLine = new Line(0, 0, 0, 0);
    public ImageView imageView;
    public static int Score = 0;
    public boolean inTransition = true;
    public ImageView cherryImage;
    public KeyCode lastKeyCode;
    public int flipDirection = 0;
    public Label scoreLabel;
    public boolean isCollision = false;
    public Timeline timeline;
    public Timeline hillTimeLine;
    public int highScore = 0;
    public int cherryCount = 0;
    public int highCherryCount = 0;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    public int isMusicPlaying = 0;
    @FXML
    Label scoreLabelOnScene3;
    MediaPlayer mediaPlayer;
    private ShapeFactory shapeFactory;


    public void startGame(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        if(isMusicPlaying != 1) music();

        startRectangle = new Rectangle(93, 200);
        startRectangle.setLayoutY(500);
        startRectangle.setFill(Color.BLACK);

        baseMidRectangle = new Rectangle(5, 5);
        baseMidRectangle.setLayoutY(500);
        baseMidRectangle.setLayoutX(46.5);
        baseMidRectangle.setFill(Color.RED);

        Image image = new Image("C:\\Users\\WASIF\\Downloads\\helloworld\\AP_Game\\src\\main\\java\\com\\example\\ap_game\\Stickhero.png");

        imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setX(50);
        imageView.setY(459.5);

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\WASIF\\Downloads\\helloworld\\AP_Game\\src\\main\\java\\com\\example\\ap_game\\data"));
        highScore = Integer.parseInt(reader.readLine());
        highCherryCount = Integer.parseInt(reader.readLine());
//        Score = Integer.parseInt(reader.readLine());
//        cherryCount = Integer.parseInt(reader.readLine());

//        System.out.println("highScore : " + highScore);
//        System.out.println("highRewardScore : " + highCherryCount);
//        System.out.println("cherry : " + cherryCount);

        reader.close();

        Group rootGroup = new Group(root, startRectangle, baseMidRectangle,imageView);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootGroup);

        displayMessage("Press SPACE to Create Ladder!\nPress DOWN to Flip DownWards!\nPress UP to Flip UpWards!", 0, 100);
        displayScores();

        // Set event handlers for key press and release
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyRelease());

        generateHills();
        addLine();

        Iterator<List<Double>> outerIterator = hillList.iterator();
        while (outerIterator.hasNext()) {
            List<Double> innerList = outerIterator.next();
            Iterator<Double> innerIterator = innerList.iterator();

            while (innerIterator.hasNext()) {
                Double value = innerIterator.next();
//                System.out.print(value + " ");
            }

//            System.out.println(); // Move to the next line for each inner list
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setShapeFactory(ShapeFactory shapeFactory) {
        this.shapeFactory = shapeFactory;
    }

    public void music() {
        String s = "C:\\Users\\WASIF\\Downloads\\helloworld\\AP_Game\\src\\main\\java\\com\\example\\ap_game\\02.mp3.crdownload";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        isMusicPlaying = 1;
    }

    public void checkLadder() {
        boolean isLadder = false;
        Iterator<List<Double>> iterator = hillList.iterator();
        while (iterator.hasNext()) {
            List<Double> ite = iterator.next();
//            System.out.println(ladderEndCoordinate + " " + ite.get(0) + " " + (ite.get(0) + ite.get(1)));
            if (ladderEndCoordinate >= ite.get(0) && ladderEndCoordinate <= (ite.get(0) + ite.get(1))) {
                double ordinate = ite.get(0);
                double wid = ite.get(1);
                iterator.remove();

                isLadder = true;

                if(ladderEndCoordinate >= ite.get(0) + (breadth / 2) - midRectangle.getWidth() && ladderEndCoordinate <= ite.get(0) + (breadth / 2) + midRectangle.getWidth()) {
                    displayMessage("BONUS!", 200, 300);
                    Score += 2;
                }
                else Score++;

                moveSubject(ordinate + wid);
                break;
            }
        }

        if(!isLadder){
            moveTillDeath(ladderEndCoordinate);
        }
    }

    public void moveTillDeath(double ladderEndCoordinate) {
        // Create a TranslateTransition
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(imageView);
        translateTransition.setByX(ladderEndCoordinate - startRectangle.getWidth() + imageView.getFitWidth());
        translateTransition.setDuration(Duration.seconds(2));

        translateTransition.play();

        // Set an event handler to be triggered when the animation finishes
        translateTransition.setOnFinished(event1 -> {
            terminateGamePlay(stage);
        });
//        System.out.println("Score : " + Score);
    }

    // Method to display bonus on the scene
    public void displayMessage(String bonusMessage, int x, int y) {
        Label bonusLabel = new Label(bonusMessage);
        bonusLabel.setLayoutX(x);
        bonusLabel.setLayoutY(y);
        bonusLabel.setStyle("-fx-font-size: 35; -fx-font-weight: bold; -fx-text-fill: red;");

        Group rootGroup = (Group) scene.getRoot();
        rootGroup.getChildren().add(bonusLabel);

        // Create a FadeTransition to make the bonus label disappear after a while
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(4), bonusLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Set an event handler to remove the bonus label after it fades out
        fadeOut.setOnFinished(event -> rootGroup.getChildren().remove(bonusLabel));

        // Start the fade-out animation
        fadeOut.play();
    }

    public void displayScores() {
        if (scoreLabel == null) {
            scoreLabel = new Label("SCORE : " + Score);
            scoreLabel.setLayoutX(0);
            scoreLabel.setLayoutY(0);
            scoreLabel.setStyle("-fx-font-size: 40; -fx-font-weight: bold; -fx-text-fill: blue;");

            Group rootGroup = (Group) scene.getRoot();
            rootGroup.getChildren().add(scoreLabel);
        } else {
            scoreLabel.setText("SCORE : " + Score);
        }
    }

    public void moveSubject(double baseEndOrdinate) {
        // Create a TranslateTransition
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(imageView);
        translateTransition.setByX(baseEndOrdinate - prevLadderEndCoordinate);
        translateTransition.setDuration(Duration.seconds(2));

        prevLadderEndCoordinate = breadth;

        checkCollisionDuringTranslation();
//        checkCollisionWithHills check = new checkCollisionWithHills(imageView, rectangle, stage);
        checkCollisionWthHills();
        // Set an event handler to be triggered when the animation finishes
        translateTransition.setOnFinished(event -> {
            timeline.stop();
            if(isCollision) isCollision = false;
            else {
//                System.out.println("in!");
                Group rootGroup = (Group) scene.getRoot();
                rootGroup.getChildren().remove(cherryImage);
            }
            runParallel();
        });

        // Start the animation
        translateTransition.play();
    }

    public void checkCollisionWthHills() {
        hillTimeLine = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            Bounds imageViewBounds = imageView.getBoundsInParent();
            Bounds rectangeBound = rectangle.getBoundsInParent();
            if (imageViewBounds.intersects(rectangeBound)) {
                terminateGamePlay(stage);
                hillTimeLine.stop();
            }
        }));

        hillTimeLine.setCycleCount(Timeline.INDEFINITE);
        hillTimeLine.play();
    }

    public void checkCollisionDuringTranslation() {
        if(!isCollision) {
            timeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
                Bounds imageViewBounds = imageView.getBoundsInParent();
                Bounds cherryBounds = cherryImage.getBoundsInParent();
//                System.out.println(imageViewBounds);
//                System.out.println(cherryBounds);
                if (imageViewBounds.intersects(cherryBounds)) {
                    isCollision = true;
                    timeline.stop();
                    handleCherryCollision();
                }
            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    public void addLine() {
//        System.out.println("line dimention: " + startx + " " + endx);
        lineLength = 1.0;
        line = new Line(startx, starty, endx, endy);
        line.setStroke(Color.BLACK);
        Group rootGroup = (Group) scene.getRoot();
        rootGroup.getChildren().add(line);
    }

    public void handleKeyPress(KeyCode code) {
        lastKeyCode = code;

        if (code == KeyCode.SPACE && newLadder && inTransition) {
            // Increase the line length while the 'K' key is pressed
            line.setStrokeWidth(3);
            lineLength += 0.1;
            updateLine();
        }

        else if (code == KeyCode.DOWN && newLadder) {
            // Rotate the image upside down when the 'DOWN' key is pressed
            rotateImageDown();
        }

        else if (code == KeyCode.UP && newLadder) {
            // Rotate the image upside down when the 'UP' key is pressed
            rotateImageUP();
        }
    }

    public void rotateImageDown() {
        if (flipDirection == 0 && !inTransition) {
            double dir = imageView.getFitWidth();
            RotateTransition transition = new RotateTransition(Duration.millis(50), imageView);

            // Set the pivot point to the bottom edge of the hero
            imageView.setRotationAxis(Rotate.X_AXIS);
            transition.setByAngle(180); // Rotate by 180 degrees for a complete flip

            transition.play();

            TranslateTransition yTransition = new TranslateTransition();
            yTransition.setNode(imageView);
            yTransition.setByY(dir);
            yTransition.setDuration(Duration.millis(50));
            yTransition.play();

            flipDirection = 1;
        }
    }

    public void rotateImageUP() {
        if (flipDirection == 1 && !inTransition) {
            double dir = imageView.getFitWidth();
            RotateTransition transition = new RotateTransition(Duration.millis(50), imageView);

            // Set the pivot point to the bottom edge of the hero
            imageView.setRotationAxis(Rotate.X_AXIS);
            transition.setByAngle(180); // Rotate by 180 degrees for a complete flip

            transition.play();

            TranslateTransition yTransition = new TranslateTransition();
            yTransition.setNode(imageView);
            yTransition.setByY(-dir);
            yTransition.setDuration(Duration.millis(50));
            yTransition.play();

            flipDirection = 0;
        }
    }

    public void handleKeyRelease() {
        // Handle any logic when the key is released (if needed)
        if(lastKeyCode == KeyCode.DOWN) rotateImageDown();

        else if(lastKeyCode == KeyCode.UP) rotateImageUP();

        else if(lastKeyCode == KeyCode.SPACE) {
            newLadder = false;
            rotataLine();
            newLadder = true;

            inTransition = false;
        }

        lastKeyCode = null;
    }

    public void updateLine() {
        // Update the line with the new length
        line.setEndY(line.getEndY() - lineLength);
        ladderEndCoordinate += lineLength;
    }

    public void rotataLine() {
        Transition rotate = new Transition() {
            {
                setCycleDuration(Duration.millis(500));
            }

            @Override
            protected void interpolate(double frac) {
                double angle = frac * 90.0;
                Rotate rotation = new Rotate(angle, line.getStartX(), line.getStartY());
                line.getTransforms().setAll(rotation);
            }
        };

        rotate.setOnFinished(event -> {
            checkLadder();
        });

        rotate.play();
    }

    public double randomNumberGenerator(double lowerLimit, double upperLimit) {
        Random random = new Random();
        return random.nextDouble(upperLimit - lowerLimit + 1) + lowerLimit;
    }

    public void generateHills() {
        double length = 200;
        breadth = randomNumberGenerator(40.0, 120.0);
        xCoordinate = randomNumberGenerator(prevBase, 480.0);

//        System.out.println("breadth and xCoordinate : " + breadth + ", " + xCoordinate + " " + (600-xCoordinate));

        if(breadth + xCoordinate <= 600){
            prevBase = breadth;
//            System.out.println("prevBase : " + prevBase);

            rectangle = new Rectangle(breadth, length);
            rectangle.setLayoutY(500);
            rectangle.setLayoutX(600 - breadth);
            rectangle.setFill(Color.BLACK);

            midRectangle = new Rectangle(5,5);
            midRectangle.setLayoutY(500);
            midRectangle.setLayoutX(600 - (breadth / 2));
            midRectangle.setFill(Color.RED);

            Group rootGroup = (Group) scene.getRoot();
            rootGroup.getChildren().add(rectangle);
            rootGroup.getChildren().add(midRectangle);

            List<Double> list = new ArrayList<>();
            list.add(xCoordinate);
            list.add(breadth);
            hillList.add(list);

            // Create a TranslateTransition to move the rectangle to its final position
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(rectangle);
            transition.setByX(xCoordinate + breadth - 600);
            transition.setDuration(Duration.seconds(1));

            TranslateTransition midTransition = new TranslateTransition();
            midTransition.setNode(midRectangle);
            midTransition.setByX(xCoordinate + breadth - 600);
            midTransition.setDuration(Duration.seconds(1));

            midTransition.setOnFinished(event -> {
            generateCherry();
            });

            transition.play();
            midTransition.play();

        }
    }

    public void moveEntireScene() {
        // Move the circle
        TranslateTransition circleTransition = new TranslateTransition();
        circleTransition.setNode(imageView);
        circleTransition.setByX(-xCoordinate);
        circleTransition.setDuration(Duration.seconds(1));
        circleTransition.play();

        // Move the line
        TranslateTransition lineTransition = new TranslateTransition();
        lineTransition.setNode(line);
        lineTransition.setByX(-xCoordinate);
        lineTransition.setDuration(Duration.seconds(1));
        lineTransition.play();

        // Move the line
        TranslateTransition lineTransition2 = new TranslateTransition();
        lineTransition2.setNode(prevLine);
        lineTransition2.setByX(-xCoordinate);
        lineTransition2.setDuration(Duration.seconds(1));
        lineTransition2.play();

        // Move the baseRectangle
        TranslateTransition baseRectangleTransition = new TranslateTransition();
        baseRectangleTransition.setNode(startRectangle);
        baseRectangleTransition.setByX(-xCoordinate);
        baseRectangleTransition.setDuration(Duration.seconds(1));
        baseRectangleTransition.play();

        // Move the rectangle
        TranslateTransition rectangleTransition = new TranslateTransition();
        rectangleTransition.setNode(rectangle);
        rectangleTransition.setByX(-xCoordinate);
        rectangleTransition.setDuration(Duration.seconds(1));
        rectangleTransition.play();

        // Move the midrectangle
        TranslateTransition midRectangleTransition = new TranslateTransition();
        midRectangleTransition.setNode(midRectangle);
        midRectangleTransition.setByX(-xCoordinate);
        midRectangleTransition.setDuration(Duration.seconds(1));
        midRectangleTransition.play();

        // Move the baseMidRectangle
        TranslateTransition baseMidRectangleTransition = new TranslateTransition();
        baseMidRectangleTransition.setNode(baseMidRectangle);
        baseMidRectangleTransition.setByX(-xCoordinate);
        baseMidRectangleTransition.setDuration(Duration.seconds(1));
        baseMidRectangleTransition.play();

        baseMidRectangle = midRectangle;
        startRectangle = rectangle;

        prevLine = line;

        startx = breadth;
        starty = 500;
        endx = breadth;
        endy = 500;
        ladderEndCoordinate = breadth;

        displayScores();
        addLine();

        baseMidRectangleTransition.setOnFinished(event ->{
            inTransition = true;
        });
    }

    public void runParallel() {
        CompletableFuture.runAsync(() -> {
            // Perform background tasks here...

            Platform.runLater(() -> {
                // Update UI components here...
                moveEntireScene();
                generateHills();
            });
        }, executorService);
    }

    public void generateCherry() {
        boolean generate = new Random().nextBoolean();

        if (true) {
            try {
                double xordinate = randomNumberGenerator(startRectangle.getWidth(), xCoordinate - 30);
                cherryImage = new ImageView("C:\\Users\\WASIF\\Downloads\\helloworld\\AP_Game\\src\\main\\java\\com\\example\\ap_game\\cherry.png");
                cherryImage.setFitWidth(30);
                cherryImage.setFitHeight(30);
                cherryImage.setX(xordinate);
                cherryImage.setY(500);

                Group rootGroup = (Group) scene.getRoot();
                rootGroup.getChildren().add(cherryImage);
            }
            catch (Exception e) {
                System.out.println("Error in generating cherry.");
            }
        }
    }

    public void handleCherryCollision() {
//        System.out.println("Collide!");
        // Your logic for handling cherry collision
        // For example, remove the cherry from the scene and increment the score
        Group rootGroup = (Group) scene.getRoot();
        rootGroup.getChildren().remove(cherryImage);

        if(isCollision) {
            Score += 5; // Adjust the score increment as needed
            displayMessage("BONUS!", 200, 300);
            ++cherryCount;
        }
//        System.out.println("Score: " + Score);
    }

    public void terminateGamePlay(Stage stage) {
        writeScores();

        // Update the high score if the current score is higher
        if (Score > highScore) {
            highScore = Score;
        }

        TranslateTransition uptransition = new TranslateTransition();
        uptransition.setNode(imageView);
        uptransition.setByY(-30);
        uptransition.setDuration(Duration.millis(100));

        uptransition.setOnFinished(actionEvent -> {
            TranslateTransition fallTransition = new TranslateTransition();
            fallTransition.setNode(imageView);
            fallTransition.setByY(500);
            fallTransition.setDuration(Duration.seconds(1));

            fallTransition.setOnFinished(event3 -> {
                // Instead of printing to the terminal, display the bonus on the scene
                try {
                    // Load the new FXML file (scene3.fxml)
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
                    Parent root = loader.load();

                    // Set the new scene in the stage
                    Scene newScene = new Scene(root);
                    stage.setScene(newScene);


                    Label l1 = new Label();
                    int scr = Score;
                    l1.setText("SCORE : " + Integer.toString(scr));
                    l1.setLayoutX(190);
                    l1.setLayoutY(380);
                    l1.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: blue;");
                    Pane pane = (Pane) newScene.getRoot();
                    pane.getChildren().add(l1);

                    Label l2 = new Label();
                    int hscr;
                    if(Score > highScore) hscr = Score;
                    else hscr = highScore;

                    l2.setText("SCORE : " + Integer.toString(hscr));
                    l2.setLayoutX(190);
                    l2.setLayoutY(240);
                    l2.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: blue;");
                    pane.getChildren().add(l2);

                    Label l3 = new Label();
                    int chr = cherryCount;
                    l3.setText("CHERRY : " + Integer.toString(chr));
                    l3.setLayoutX(310);
                    l3.setLayoutY(380);
                    l3.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: blue;");
                    pane.getChildren().add(l3);

                    Label l4 = new Label();

                    int highchr;
                    if(cherryCount > highCherryCount) highchr = cherryCount;
                    else highchr = highCherryCount;
                    l4.setText("CHERRY : " + Integer.toString(highchr));
                    l4.setLayoutX(310);
                    l4.setLayoutY(240);  // Update the Y-coordinate for l4
                    l4.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: blue;");
                    pane.getChildren().add(l4);

                    // Show the stage with the new scene
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            fallTransition.play();
        });

        uptransition.play();
    }

    public void writeScores() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\WASIF\\Downloads\\helloworld\\AP_Game\\src\\main\\java\\com\\example\\ap_game\\data"));
            if (Score > highScore) {
                writer.write(Integer.toString(Score));  // Update the highest score.
                writer.newLine();

                writer.write(Integer.toString(cherryCount));  // Update the reward score.
                writer.newLine();

                writer.write(Integer.toString(Score));  // Update the reward score.
                writer.newLine();

                writer.write(Integer.toString(cherryCount));  // Update the reward score.
                writer.close();
            }

            else {
                writer.write(Integer.toString(highScore));  // Keep the highest score.
                writer.newLine();

                writer.write(Integer.toString(highCherryCount));  // Update the reward score.
                writer.newLine();

                writer.write(Integer.toString(Score));  // Update the reward score.
                writer.newLine();

                writer.write(Integer.toString(cherryCount));  // Update the reward score.
                writer.close();
            }

//            Stage currentStage = (Stage) scoreText.getScene().getWindow();
//            currentStage.close();
        }
        catch (Exception e){
            System.out.println("Error in writing scores.");
        }
    }

    public void terminateProgram() {
//        writeScores();

        Platform.exit();
    }
}