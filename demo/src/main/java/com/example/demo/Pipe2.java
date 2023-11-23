package com.example.demo;

import javafx.scene.image.Image;

public class Pipe2 extends GreenPipe {
    public Pipe2(int centerOfPipes) {
        imageView.setImage(new Image("pipe2.png"));
        h = centerOfPipes-125;
        y = 0;
    }
    @Override
    public String toString() {
        return "Pipe2";
    }
}
