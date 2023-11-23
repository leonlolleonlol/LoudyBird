package com.example.demo;

import javafx.scene.canvas.GraphicsContext;

public class GreenPipe extends GameObject{
    public static final int GREEN_PIPE_WIDTH=75;
    public GreenPipe() {
        w = GREEN_PIPE_WIDTH;
        ay = ax = vy = 0;
        vx = -50;
        x = HelloApplication.WIDTH;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(imageView.getImage(), x, y, w, h);
    }
}
