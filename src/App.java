import processing.core.*;
import controlP5.*;

import java.io.IOException;

public class App extends PApplet{

    public static int renderType = 0;

    public static PApplet processingRef;
    public Pandemonium pandemonium;
    public static double G = 9.81;
    public void settings() {
        size(1500, 1000);
    }

    public void setup() {
        background(0);
        frameRate(1000);
        processingRef = this;
        this.pandemonium = new Pandemonium("problem.json");
        this.pandemonium.solve();
    }

    public void draw() {
        this.pandemonium.animate();
    }

    public void mouseClicked() {
        background(0);
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
