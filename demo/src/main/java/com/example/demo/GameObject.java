package com.example.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public abstract class GameObject {
    protected double x, y, vx, vy, ax, ay, w, h;
    protected ImageView imageView = new ImageView();

    protected void updatePhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;

        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }

    protected void changerVitesse() {
        if (y > HelloApplication.HEIGHT + h) {
            y=HelloApplication.HEIGHT - h;
            CurrentGame.setFinished(true);
        }
    }

    public double getCentreX() {
        return y + h / 2;
    }

    public double getCentreY() {
        return x + w / 2;
    }

    public double getHaut() {
        return y;
    }

    public double getBas() {
        return y + h;
    }

    public double getGauche() {
        return x;
    }

    public double getDroite() {
        return x + w;
    }

    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    }

    public abstract void draw(GraphicsContext context);

}