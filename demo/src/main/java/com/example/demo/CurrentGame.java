package com.example.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class CurrentGame {
    public double tempsEcoule,threeSecondsStopwatch,timeForBG;
    private final LoudyBird loudybird;
    private final ArrayList<GreenPipe> greenPipes;
    private static boolean finished;
    private int score;
    private boolean possibilityToAddScore;

    public CurrentGame() throws LineUnavailableException {
        loudybird = new LoudyBird();
        greenPipes = new ArrayList<>();
        finished=false;
        possibilityToAddScore=true;
        tempsEcoule = 0;
        threeSecondsStopwatch=0;
        timeForBG=0;
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
        if(!finished) {
            tempsEcoule += deltaTemps;
            timeForBG+= deltaTemps;
            loudybird.update(deltaTemps);
            if (greenPipes.get(0).getGauche() <= -GreenPipe.GREEN_PIPE_WIDTH) {
                greenPipes.remove(0);
                greenPipes.remove(0);
                possibilityToAddScore = true;
            }
            if (tempsEcoule>8) {
                tempsEcoule=0;
                addPipes();
            }
            for (GreenPipe pipe : greenPipes)
                pipe.update(deltaTemps);
            if (loudybird.soundProcessor.isStartAnimation()) {
                checkForCollisions();
                if (greenPipes.get(1).getDroite() < loudybird.getGauche() && possibilityToAddScore) {
                    score++;
                    possibilityToAddScore = false;
                }
            }
        }
        else {
            threeSecondsStopwatch+=deltaTemps;
        }
    }
    public void checkForCollisions()
    {
        for (GreenPipe pipe : greenPipes) {
                if (Objects.equals(pipe.toString(), "Pipe2"))
                    if (loudybird.getHaut()+10 < pipe.getBas()&&checkForCollisionsX(pipe))
                        finished=true;
                if (Objects.equals(pipe.toString(), "Pipe"))
                    if(loudybird.getBas()-10 > pipe.getHaut()&&checkForCollisionsX(pipe))
                        finished=true;
        }
    }
    public boolean checkForCollisionsX(GreenPipe pipe)
    {
        return loudybird.getDroite()-10 > pipe.getGauche()
                && loudybird.getGauche()< pipe.getDroite();
    }

    public void draw(GraphicsContext context) {
        if((int)timeForBG==21)
            timeForBG=0;
        context.drawImage(new Image("bg.png"), -timeForBG*30, 0, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        context.drawImage(new Image("bg.png"), HelloApplication.WIDTH-timeForBG*30-185, 0, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        if(!finished) {
            loudybird.draw(context);
            for (GreenPipe pipe : greenPipes)
                pipe.draw(context);
            sizeAndColorText(50, BLUE, context);
            context.fillText(loudybird.soundProcessor.getSoundLevel()+" dB Score: "+score, 0, HelloApplication.HEIGHT-50);
        }
        else {
            sizeAndColorText(50, RED, context);
            context.fillText("YOU LOST! Restarting in "+Math.round(3-threeSecondsStopwatch), 150, HelloApplication.HEIGHT / 2);
        }
    }

    public static void setFinished(boolean finished) {
        CurrentGame.finished = finished;
    }

    public static boolean isFinished() {
        return finished;
    }

    public static void sizeAndColorText(int size, Color color, GraphicsContext context)
    {
        context.setFont(Font.font("calibri", size));
        context.setFill(color);
    }
}
