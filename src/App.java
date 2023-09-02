import processing.core.*;
import controlP5.*;

public class App extends PApplet{

    public static PApplet processingRef;
    public DoublePendulum doublePendulum;
    public static double G = 9.81;
    public void settings() {
        size(1500, 1000);
    }

    public void setup() {
        frameRate(1000);
        processingRef = this;
        Pendulum pendulum1 = new Pendulum(new Dot(500, 500), 150, 20, 2., 0);
        Pendulum pendulum2 = new Pendulum(75, 10, 0, 0);

        this.doublePendulum = new DoublePendulum(pendulum1, pendulum2, 0.1);
    }

    public void draw() {
        background(0);
        //this.doublePendulum.eulerSolveStep();
        this.doublePendulum.rungeKuttaSolveStep();
        this.doublePendulum.addGraphData();
        this.doublePendulum.render();
        text((float)this.doublePendulum.getCurrentTime(), 2, 10);
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
