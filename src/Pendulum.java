import java.awt.*;

public class Pendulum {
    private Dot fix;
    private double length;
    private double mass;
    private double theta;
    private double omega;

    private Color color;

    public Pendulum() {
        this.fix = new Dot();
        this.length = 1.;
        this.mass = 1.;
        this.theta = 0.;
        this.omega = 0.;
        this.color = new Color(255, 255, 255);
    }

    public Pendulum(double length, double mass, double theta, double omega) {
        this.length = length;
        this.mass = mass;
        this.theta = theta;
        this.omega = omega;
        this.color = new Color(255, 255, 255);
    }

    public Pendulum(Dot fix, double length, double mass, double theta, double omega) {
        this(length, mass, theta, omega);
        this.fix = fix;
    }

    public void setFix(Dot fix) {
        this.fix = fix;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setTheta(double theta) {
        this.theta = normalizeDirection(theta);
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }

    public Dot getFix() {
        return fix;
    }

    public double getLength() {
        return length;
    }

    public double getMass() {
        return mass;
    }

    public double getTheta() {
        return theta;
    }

    public double getOmega() {
        return omega;
    }

    public Dot getEndDot() {
        return this.fix.add(
                new Dot(this.length * Math.sin(this.theta),
                        this.length * Math.cos(this.theta))
        );
    }

    static double normalizeDirection(double argDir){
        double dir = argDir;
        while(dir < -Math.PI){
            dir += 2 * Math.PI;
        }
        while(dir >= Math.PI){
            dir -= 2 * Math.PI;
        }
        return dir;
    }

    public void render() {
        App.processingRef.stroke(this.color.getRGB());
        App.processingRef.strokeWeight(5);
        Dot endDot = getEndDot();
        App.processingRef.line((float)this.fix.getX(), (float)this.fix.getY(), (float)endDot.getX(), (float)endDot.getY());
        App.processingRef.stroke(Color.YELLOW.getRGB());
        App.processingRef.circle((float)this.fix.getX(), (float)this.fix.getY(), 10);
        App.processingRef.stroke(Color.RED.getRGB());
        App.processingRef.circle((float)endDot.getX(), (float)endDot.getY(), 10);
    }
}
