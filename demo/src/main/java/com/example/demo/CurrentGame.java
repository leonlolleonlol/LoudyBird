package com.example.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.Random;

public class CurrentGame {
    private double tempsEcoule = 0;
    private final LoudyBird loudybird;
    private final ArrayList<GreenPipe> greenPipes = new ArrayList<>();
    private static boolean finished=false;


    public CurrentGame() throws LineUnavailableException {

        // Open the microphone
        loudybird = new LoudyBird();
        addPipes();
    }
    public int randomCenterOfTwoPipes()
    {
        return -1*new Random().nextInt(2)*new Random().nextInt(250)+(int)HelloApplication.HEIGHT/2;
    }
    public void addPipes()
    {
        int centerOfPipes=randomCenterOfTwoPipes();
        greenPipes.add(new Pipe2(centerOfPipes));
        greenPipes.add(new Pipe(centerOfPipes));
    }
    public void update(double deltaTemps) {
        tempsEcoule += deltaTemps;
        loudybird.update(deltaTemps);
        if (greenPipes.get(0).getGauche()<=-GreenPipe.GREEN_PIPE_WIDTH) {
            greenPipes.remove(0);
            greenPipes.remove(0);
        }
        if (tempsEcoule > 8) {
            tempsEcoule = 0;
            addPipes();
        }
        for (GreenPipe pipe : greenPipes)
            pipe.update(deltaTemps);
    }

    public void draw(GraphicsContext context) {
        if(!finished) {
            context.drawImage(new Image("bg.png"), 0, 0, HelloApplication.WIDTH, HelloApplication.HEIGHT);
            loudybird.draw(context);
            for (GreenPipe pipe : greenPipes)
                pipe.draw(context);
        }
        else {
            context.setFont(Font.font("calibri", 50));
            context.setFill(Color.RED);
            context.fillText("YOU LOST!", HelloApplication.WIDTH / 2 - 50, HelloApplication.HEIGHT / 2);
        }
    }

    public static void setFinished(boolean finished) {
        CurrentGame.finished = finished;
    }
}
