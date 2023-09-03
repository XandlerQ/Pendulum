import java.awt.*;

public class DoublePendulum {
    private Pendulum firstPendulum;
    private Pendulum secondPendulum;

    public DoublePendulum() {
        this.firstPendulum = new Pendulum();
        this.secondPendulum = connect(new Pendulum());
    }

    public DoublePendulum(Pendulum pendulum1, Pendulum pendulum2) {
        this();
        this.firstPendulum = pendulum1;
        this.secondPendulum = connect(pendulum2);
    }

    public Pendulum getFirstPendulum() {
        return firstPendulum;
    }

    public Pendulum getSecondPendulum() {
        return secondPendulum;
    }

    public Pendulum connect(Pendulum pendulum) {
        if (this.firstPendulum != null)
            pendulum.setFix(this.firstPendulum.getEndDot());
        return pendulum;
    }

    public void setState(double theta1, double theta2) {
        this.firstPendulum.setTheta(theta1);
        this.secondPendulum.setTheta(theta2);
    }

    void render() {
        if(App.renderType == 0) {
            App.processingRef.background(0);
            this.firstPendulum.render();
            this.secondPendulum.render();
        }
        else if(App.renderType == 1) {
            App.processingRef.stroke(Color.WHITE.getRGB());
            App.processingRef.circle((float)this.secondPendulum.getEndDot().getX(), (float)this.secondPendulum.getEndDot().getY(), 1);
        }
    }

}
