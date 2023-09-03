import processing.core.*;
import controlP5.*;

public class App extends PApplet{

    public static int renderType = 1;

    public static PApplet processingRef;
    public DoublePendulum doublePendulum;
    public static double G = 9.81;
    public void settings() {
        size(1500, 1000);
    }

    public void setup() {
        background(0);
        frameRate(1000);
        processingRef = this;
        Pendulum pendulum1 = new Pendulum(new Dot(500, 500), 150, 20, 2., 0);
        Pendulum pendulum2 = new Pendulum(150, 20, -2, 0);

        this.doublePendulum = new DoublePendulum(pendulum1, pendulum2, 0.1);
    }

    public void draw() {
        //this.doublePendulum.eulerSolveStep();
        this.doublePendulum.rungeKuttaSolveStep();
        this.doublePendulum.addGraphData();
        this.doublePendulum.render();
    }

    public void mouseClicked() {
        background(0);
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
