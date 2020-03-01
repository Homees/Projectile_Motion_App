package sk.mtmp.zadanie_1;

import java.io.Serializable;

public class Suradnice implements Serializable {
    private double x;
    private double y;
    private double time;

    public Suradnice(double x, double y, double time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) { this.x = x; }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}