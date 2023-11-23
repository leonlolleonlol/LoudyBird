package com.example.demo;

import javafx.scene.image.Image;

public class Pipe extends GreenPipe {
    public Pipe(int centerOfPipes) {
        imageView.setImage(new Image("pipe.png"));
        h = (int)HelloApplication.HEIGHT-centerOfPipes-125;
        y = HelloApplication.HEIGHT-h;
    }

    @Override
    public String toString() {
        return "Pipe";
    }
}
