import processing.core.*;
import controlP5.*;

import java.io.IOException;

public class App extends PApplet{

    public static int renderType = 0;

    public static PApplet processingRef;
    public Pandemonium pandemonium;
    public static double G = 9.81;
    public void settings() {
        size(2000, 1000);
    }

    public void setup() {
        background(0);
        frameRate(1000);
        processingRef = this;
        this.pandemonium = new Pandemonium("problem.json");
        if (this.pandemonium.isExc()) return;
        this.pandemonium.solve();
    }

    public void draw() {
        if (!this.pandemonium.isExc()) this.pandemonium.animate();
    }

    public void mouseClicked() {
        this.pandemonium.resetAnimationTick();
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
