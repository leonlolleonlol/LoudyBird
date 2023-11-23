package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    public static final double WIDTH = 800, HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.setProperty("prism.forceGPU", "true");

        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        var context = canvas.getGraphicsContext2D();

        final CurrentGame[] currentGame = {new CurrentGame()};

        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE)
                Platform.exit();
            else
                Input.setKeyPressed(e.getCode(), true);
        });

        scene.setOnKeyReleased((e) -> Input.setKeyPressed(e.getCode(), false));

        var timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                currentGame[0].update(1 / 30.0);
                currentGame[0].draw(context);
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Loudy Bird");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}