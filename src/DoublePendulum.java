import java.awt.*;

public class DoublePendulum {
    private Pendulum firstPendulum;
    private Pendulum secondPendulum;
    private double timeStep;
    private double currentTime;
    private TimeGraph theta1Graph;
    private TimeGraph theta2Graph;
    private TimeGraph omega1Graph;
    private TimeGraph omega2Graph;

    public double getCurrentTime() {
        return currentTime;
    }

    public DoublePendulum() {
        this.firstPendulum = new Pendulum();
        this.secondPendulum = connect(new Pendulum());
        this.timeStep = 0.001;
        this.currentTime = 0.;

        ScaleSynchronizer thetaScaleSynchronizer = new ScaleSynchronizer();

        this.theta1Graph = new TimeGraph(500, 500, 500);
        this.theta1Graph.setTitle("Theta");
        this.theta1Graph.setOrigin(1000, 0);
        this.theta1Graph.setPlainCl(new Color(0, 0, 0));
        this.theta1Graph.setBorderCl(new Color(100, 100, 100));
        this.theta1Graph.setDotCl(Color.RED);
        this.theta1Graph.setLineCl(Color.RED);
        this.theta1Graph.setLevelLineCl(Color.RED);
        this.theta1Graph.setValueTextCl(Color.WHITE);
        this.theta1Graph.setTitleTextCl(Color.WHITE);
        this.theta1Graph.setScaleTextCl(Color.WHITE);
        this.theta1Graph.setTextSize(8);
        this.theta1Graph.setInteger(false);
        this.theta1Graph.setScaleSynchronizer(thetaScaleSynchronizer);

        this.theta2Graph = new TimeGraph(500, 500, 500);
        this.theta2Graph.setTitle("");
        this.theta2Graph.setOrigin(1000, 0);
        this.theta2Graph.setPlainCl(new Color(0, 0, 0, 0));
        this.theta2Graph.setBorderCl(new Color(100, 100, 100));
        this.theta2Graph.setDotCl(Color.BLUE);
        this.theta2Graph.setLineCl(Color.BLUE);
        this.theta2Graph.setLevelLineCl(Color.BLUE);
        this.theta2Graph.setValueTextCl(Color.WHITE);
        this.theta2Graph.setTitleTextCl(Color.WHITE);
        this.theta2Graph.setScaleTextCl(Color.WHITE);
        this.theta2Graph.setTextSize(8);
        this.theta2Graph.setInteger(false);
        this.theta2Graph.setScaleSynchronizer(thetaScaleSynchronizer);

        thetaScaleSynchronizer.addGraph(theta1Graph);
        thetaScaleSynchronizer.addGraph(theta2Graph);

        ScaleSynchronizer omegaScaleSynchronizer = new ScaleSynchronizer();

        this.omega1Graph = new TimeGraph(500, 500, 500);
        this.omega1Graph.setTitle("Omega");
        this.omega1Graph.setOrigin(1000, 500);
        this.omega1Graph.setPlainCl(new Color(0, 0, 0));
        this.omega1Graph.setBorderCl(new Color(100, 100, 100));
        this.omega1Graph.setDotCl(Color.RED);
        this.omega1Graph.setLineCl(Color.RED);
        this.omega1Graph.setLevelLineCl(Color.RED);
        this.omega1Graph.setValueTextCl(Color.WHITE);
        this.omega1Graph.setTitleTextCl(Color.WHITE);
        this.omega1Graph.setScaleTextCl(Color.WHITE);
        this.omega1Graph.setTextSize(8);
        this.omega1Graph.setInteger(false);
        this.omega1Graph.setScaleSynchronizer(omegaScaleSynchronizer);

        this.omega2Graph = new TimeGraph(500, 500, 500);
        this.omega2Graph.setTitle("");
        this.omega2Graph.setOrigin(1000, 500);
        this.omega2Graph.setPlainCl(new Color(0, 0, 0, 0));
        this.omega2Graph.setBorderCl(new Color(100, 100, 100));
        this.omega2Graph.setDotCl(Color.BLUE);
        this.omega2Graph.setLineCl(Color.BLUE);
        this.omega2Graph.setLevelLineCl(Color.BLUE);
        this.omega2Graph.setValueTextCl(Color.WHITE);
        this.omega2Graph.setTitleTextCl(Color.WHITE);
        this.omega2Graph.setScaleTextCl(Color.WHITE);
        this.omega2Graph.setTextSize(8);
        this.omega2Graph.setInteger(false);
        this.omega2Graph.setScaleSynchronizer(omegaScaleSynchronizer);

        omegaScaleSynchronizer.addGraph(omega1Graph);
        omegaScaleSynchronizer.addGraph(omega2Graph);
    }

    public DoublePendulum(Pendulum pendulum1, Pendulum pendulum2, double timeStep) {
        this();
        this.firstPendulum = pendulum1;
        this.secondPendulum = connect(pendulum2);
        this.timeStep = timeStep;
    }

    public Pendulum connect(Pendulum pendulum) {
        if (this.firstPendulum != null)
            pendulum.setFix(this.firstPendulum.getEndDot());
        return pendulum;
    }

    void eulerSolveStep() {
        double cTheta1 = this.firstPendulum.getTheta();
        double cTheta2 = this.secondPendulum.getTheta();
        double cOmega1 = this.firstPendulum.getOmega();
        double cOmega2 = this.secondPendulum.getOmega();

        this.firstPendulum.setTheta(cTheta1 + this.timeStep * theta1Derivative(cOmega1));
        this.secondPendulum.setTheta(cTheta2 + this.timeStep * theta2Derivative(cOmega2));
        this.firstPendulum.setOmega(cOmega1 + this.timeStep * omega1Derivative(cTheta1, cTheta2, cOmega1, cOmega2));
        this.secondPendulum.setOmega(cOmega2 + this.timeStep * omega2Derivative(cTheta1, cTheta2, cOmega1, cOmega2));

        this.secondPendulum.setFix(this.firstPendulum.getEndDot());

        this.currentTime += this.timeStep;
    }

    void rungeKuttaSolveStep() {
        double cTheta1 = this.firstPendulum.getTheta();
        double cTheta2 = this.secondPendulum.getTheta();
        double cOmega1 = this.firstPendulum.getOmega();
        double cOmega2 = this.secondPendulum.getOmega();

        double theta1K1 = theta1Derivative(cOmega1);
        double theta2K1 = theta2Derivative(cOmega2);
        double omega1K1 = omega1Derivative(cTheta1, cTheta2, cOmega1, cOmega2);
        double omega2K1 = omega2Derivative(cTheta1, cTheta2, cOmega1, cOmega2);

        double theta1K2 = theta1Derivative(cOmega1 + this.timeStep * omega1K1 / 2);
        double theta2K2 = theta2Derivative(cOmega2 + this.timeStep * omega2K1 / 2);
        double omega1K2 = omega1Derivative(cTheta1 + this.timeStep * theta1K1 / 2, cTheta2 + this.timeStep * theta2K1 / 2, cOmega1 + this.timeStep * omega1K1 / 2, cOmega2 + this.timeStep * omega2K1 / 2);
        double omega2K2 = omega2Derivative(cTheta1 + this.timeStep * theta1K1 / 2, cTheta2 + this.timeStep * theta2K1 / 2, cOmega1 + this.timeStep * omega1K1 / 2, cOmega2 + this.timeStep * omega2K1 / 2);

        double theta1K3 = theta1Derivative(cOmega1 + this.timeStep * omega1K2 / 2);
        double theta2K3 = theta2Derivative(cOmega2 + this.timeStep * omega2K2 / 2);
        double omega1K3 = omega1Derivative(cTheta1 + this.timeStep * theta1K2 / 2, cTheta2 + this.timeStep * theta2K2 / 2, cOmega1 + this.timeStep * omega1K2 / 2, cOmega2 + this.timeStep * omega2K2 / 2);
        double omega2K3 = omega2Derivative(cTheta1 + this.timeStep * theta1K2 / 2, cTheta2 + this.timeStep * theta2K2 / 2, cOmega1 + this.timeStep * omega1K2 / 2, cOmega2 + this.timeStep * omega2K2 / 2);

        double theta1K4 = theta1Derivative(cOmega1 + this.timeStep * omega1K3);
        double theta2K4 = theta2Derivative(cOmega2 + this.timeStep * omega2K3);
        double omega1K4 = omega1Derivative(cTheta1 + this.timeStep * theta1K3, cTheta2 + this.timeStep * theta2K3, cOmega1 + this.timeStep * omega1K3, cOmega2 + this.timeStep * omega2K3);
        double omega2K4 = omega2Derivative(cTheta1 + this.timeStep * theta1K3, cTheta2 + this.timeStep * theta2K3, cOmega1 + this.timeStep * omega1K3, cOmega2 + this.timeStep * omega2K3);

        this.firstPendulum.setTheta(cTheta1 + this.timeStep * (theta1K1 + 2 * theta1K2 + 2 * theta1K3 + theta1K4) / 6);
        this.secondPendulum.setTheta(cTheta2 + this.timeStep * (theta2K1 + 2 * theta2K2 + 2 * theta2K3 + theta2K4) / 6);
        this.firstPendulum.setOmega(cOmega1 + this.timeStep * (omega1K1 + 2 * omega1K2 + 2 * omega1K3 + omega1K4) / 6);
        this.secondPendulum.setOmega(cOmega2 + this.timeStep * (omega2K1 + 2 * omega2K2 + 2 * omega2K3 + omega2K4) / 6);

        this.secondPendulum.setFix(this.firstPendulum.getEndDot());

        this.currentTime += this.timeStep;
    }

    void addGraphData() {
        this.theta1Graph.addValue(this.firstPendulum.getTheta());
        this.theta2Graph.addValue(this.secondPendulum.getTheta());
        this.omega1Graph.addValue(this.firstPendulum.getOmega());
        this.omega2Graph.addValue(this.secondPendulum.getOmega());
    }

    double omega1Derivative(double theta1, double theta2, double omega1, double omega2) {
        return (
                        9 * omega1 * omega1 * this.firstPendulum.getLength() * this.secondPendulum.getMass() * Math.sin(2 * theta1 - 2 * theta2)
                        + 12 * this.secondPendulum.getMass() * this.secondPendulum.getLength() * omega2 * omega2 * Math.sin(theta1 - theta2)
                        + 12 * (3 * Math.sin(- 2 * theta2 + theta1) * this.secondPendulum.getMass() / 4 + Math.sin(theta1) * (this.firstPendulum.getMass() + 5 * this.secondPendulum.getMass() / 4)) * App.G

                )
                / (
                        this.firstPendulum.getLength() * (9 * this.secondPendulum.getMass() * Math.cos(2 * theta1 - 2 * theta2) - 8 * this.firstPendulum.getMass() - 15 * this.secondPendulum.getMass())
                );
    }

    double omega2Derivative(double theta1, double theta2, double omega1, double omega2) {
        return (
                        - 9 * App.G * Math.sin(2 * theta1 - theta2) * (this.firstPendulum.getMass() + 2 * this.secondPendulum.getMass())
                        - 9 * this.secondPendulum.getMass() * this.secondPendulum.getLength() * omega2 * omega2 * Math.sin(2 * theta1 - 2 * theta2)
                        - 12 * this.firstPendulum.getLength() * omega1 * omega1 * (this.firstPendulum.getMass() + 3 * this.secondPendulum.getMass()) * Math.sin(theta1 - theta2)
                        + 3 * App.G * Math.sin(theta2) * (this.firstPendulum.getMass() + 6 * this.secondPendulum.getMass())
                )
                / (
                        this.secondPendulum.getLength() * (9 * this.secondPendulum.getMass() * Math.cos(2 * theta1 - 2 * theta2) - 8 * this.firstPendulum.getMass() - 15 * this.secondPendulum.getMass())
                );
    }

    double theta1Derivative(double omega1) {
        return omega1;
    }

    double theta2Derivative(double omega2) {
        return omega2;
    }

    void render() {
        if(App.renderType == 0) {
            App.processingRef.background(0);
            this.firstPendulum.render();
            this.secondPendulum.render();
            this.theta1Graph.render();
            this.theta2Graph.render();
            this.omega1Graph.render();
            this.omega2Graph.render();
        }
        else if(App.renderType == 1) {
            App.processingRef.stroke(Color.WHITE.getRGB());
            App.processingRef.circle((float)this.secondPendulum.getEndDot().getX(), (float)this.secondPendulum.getEndDot().getY(), 1);
        }
    }

}
