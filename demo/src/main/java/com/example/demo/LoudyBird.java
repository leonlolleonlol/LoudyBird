package com.example.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoudyBird extends GameObject {
    SoundProcessor soundProcessor;
    public static final int LOUDY_BIRD_WIDTH=50;

    public LoudyBird() {
        imageView.setImage(new Image("bird.jpg"));
        soundProcessor = new SoundProcessor( this);
        soundProcessor.startProcessing();
        w = LOUDY_BIRD_WIDTH;
        h = 50;
        ax = vx = vy = 0;
        ay = 490;
        x = HelloApplication.WIDTH / 3;
        y = HelloApplication.HEIGHT / 3;
    }

    public void update(double dt) {
        if(soundProcessor.isStartAnimation()) {
            updatePhysique(dt);
            changerVitesse();
        }
    }

    @Override
    public void draw(GraphicsContext context) {
            context.drawImage(imageView.getImage(), x, y, w, h);
        if(!soundProcessor.isStartAnimation()) {
            context.setFont(Font.font("calibri", 25));
            context.setFill(Color.BLACK);
            context.fillText("Start Singing!", HelloApplication.WIDTH / 2, HelloApplication.HEIGHT / 2);
        }
    }
    public void setVy(int soundLevel)
    {
        vy= soundLevel ;
    }

}
