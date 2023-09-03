import java.util.ArrayList;

public class EulerSolver {
    private ArrayList<Double> theta1;
    private ArrayList<Double> theta2;
    private ArrayList<Double> omega1;
    private ArrayList<Double> omega2;
    private DoublePendulum doublePendulum;
    private double T;
    private double timeStep;

    public EulerSolver() {
        this.theta1 = new ArrayList<>();
        this.theta2 = new ArrayList<>();
        this.omega1 = new ArrayList<>();
        this.omega2 = new ArrayList<>();
        this.doublePendulum = null;
        this.T = 0;
        this.timeStep = 0;
    }

    public void setDoublePendulum(DoublePendulum doublePendulum) {
        this.doublePendulum = doublePendulum;
    }

    public void setInitialValues(double t1, double t2, double o1, double o2) {
        this.theta1.clear();
        this.theta2.clear();
        this.omega1.clear();
        this.omega2.clear();
        this.theta1.add(t1);
        this.theta2.add(t2);
        this.omega1.add(o1);
        this.omega2.add(o2);
    }

    public void setT(double t) {
        T = t;
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    public ArrayList<Double> getTheta1() {
        return theta1;
    }

    public ArrayList<Double> getTheta2() {
        return theta2;
    }

    public ArrayList<Double> getOmega1() {
        return omega1;
    }

    public ArrayList<Double> getOmega2() {
        return omega2;
    }

    void solve() {
        int steps = (int)(this.T / this.timeStep);

        for (int iteration = 1; iteration <= steps; iteration ++) {
            double cTheta1 = this.theta1.get(iteration - 1);
            double cTheta2 = this.theta2.get(iteration - 1);
            double cOmega1 = this.omega1.get(iteration - 1);
            double cOmega2 = this.omega2.get(iteration - 1);

            this.theta1.add(cTheta1 + this.timeStep * theta1Derivative(cOmega1));
            this.theta2.add(cTheta2 + this.timeStep * theta2Derivative(cOmega2));
            this.omega1.add(cOmega1 + this.timeStep * omega1Derivative(cTheta1, cTheta2, cOmega1, cOmega2));
            this.omega2.add(cOmega2 + this.timeStep * omega2Derivative(cTheta1, cTheta2, cOmega1, cOmega2));
        }
    }

    double omega1Derivative(double theta1, double theta2, double omega1, double omega2) {
        return (
                9 * omega1 * omega1 * this.doublePendulum.getFirstPendulum().getLength() * this.doublePendulum.getSecondPendulum().getMass() * Math.sin(2 * theta1 - 2 * theta2)
                        + 12 * this.doublePendulum.getSecondPendulum().getMass() * this.doublePendulum.getSecondPendulum().getLength() * omega2 * omega2 * Math.sin(theta1 - theta2)
                        + 12 * (3 * Math.sin(- 2 * theta2 + theta1) * this.doublePendulum.getSecondPendulum().getMass() / 4 + Math.sin(theta1) * (this.doublePendulum.getFirstPendulum().getMass() + 5 * this.doublePendulum.getSecondPendulum().getMass() / 4)) * App.G

        )
                / (
                this.doublePendulum.getFirstPendulum().getLength() * (9 * this.doublePendulum.getSecondPendulum().getMass() * Math.cos(2 * theta1 - 2 * theta2) - 8 * this.doublePendulum.getFirstPendulum().getMass() - 15 * this.doublePendulum.getSecondPendulum().getMass())
        );
    }

    double omega2Derivative(double theta1, double theta2, double omega1, double omega2) {
        return (
                - 9 * App.G * Math.sin(2 * theta1 - theta2) * (this.doublePendulum.getFirstPendulum().getMass() + 2 * this.doublePendulum.getSecondPendulum().getMass())
                        - 9 * this.doublePendulum.getSecondPendulum().getMass() * this.doublePendulum.getSecondPendulum().getLength() * omega2 * omega2 * Math.sin(2 * theta1 - 2 * theta2)
                        - 12 * this.doublePendulum.getFirstPendulum().getLength() * omega1 * omega1 * (this.doublePendulum.getFirstPendulum().getMass() + 3 * this.doublePendulum.getSecondPendulum().getMass()) * Math.sin(theta1 - theta2)
                        + 3 * App.G * Math.sin(theta2) * (this.doublePendulum.getFirstPendulum().getMass() + 6 * this.doublePendulum.getSecondPendulum().getMass())
        )
                / (
                this.doublePendulum.getSecondPendulum().getLength() * (9 * this.doublePendulum.getSecondPendulum().getMass() * Math.cos(2 * theta1 - 2 * theta2) - 8 * this.doublePendulum.getFirstPendulum().getMass() - 15 * this.doublePendulum.getSecondPendulum().getMass())
        );
    }

    double theta1Derivative(double omega1) {
        return omega1;
    }

    double theta2Derivative(double omega2) {
        return omega2;
    }

}
