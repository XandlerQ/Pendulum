import java.util.ArrayList;

public class RungeKuttaSolver {
    private ArrayList<Double> theta1;
    private ArrayList<Double> theta2;
    private ArrayList<Double> omega1;
    private ArrayList<Double> omega2;
    private ArrayList<Double> energy;
    private DoublePendulum doublePendulum;
    private double T;
    private double timeStep;

    public RungeKuttaSolver() {
        this.theta1 = new ArrayList<>();
        this.theta2 = new ArrayList<>();
        this.omega1 = new ArrayList<>();
        this.omega2 = new ArrayList<>();
        this.energy = new ArrayList<>();
        this.doublePendulum = null;
        this.T = 0;
        this.timeStep = 0;
    }

    public void setDoublePendulum(DoublePendulum doublePendulum) {
        this.doublePendulum = doublePendulum;
        this.theta1.clear();
        this.theta2.clear();
        this.omega1.clear();
        this.omega2.clear();
        this.energy.clear();
        this.theta1.add(doublePendulum.getFirstPendulum().getTheta());
        this.theta2.add(doublePendulum.getSecondPendulum().getTheta());
        this.omega1.add(doublePendulum.getFirstPendulum().getOmega());
        this.omega2.add(doublePendulum.getSecondPendulum().getOmega());
        this.energy.add(calcEnergy(
                doublePendulum.getFirstPendulum().getTheta(),
                doublePendulum.getSecondPendulum().getTheta(),
                doublePendulum.getFirstPendulum().getOmega(),
                doublePendulum.getSecondPendulum().getOmega()
                ));
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

    public ArrayList<Double> getEnergy() {
        return energy;
    }

    void solve() {
        int steps = (int)(this.T / this.timeStep);

        for (int iteration = 1; iteration <= steps; iteration ++) {
            double cTheta1 = this.theta1.get(iteration - 1);
            double cTheta2 = this.theta2.get(iteration - 1);
            double cOmega1 = this.omega1.get(iteration - 1);
            double cOmega2 = this.omega2.get(iteration - 1);

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

            double theta1 = cTheta1 + this.timeStep * (theta1K1 + 2 * theta1K2 + 2 * theta1K3 + theta1K4) / 6;
            double theta2 = cTheta2 + this.timeStep * (theta2K1 + 2 * theta2K2 + 2 * theta2K3 + theta2K4) / 6;
            double omega1 = cOmega1 + this.timeStep * (omega1K1 + 2 * omega1K2 + 2 * omega1K3 + omega1K4) / 6;
            double omega2 = cOmega2 + this.timeStep * (omega2K1 + 2 * omega2K2 + 2 * omega2K3 + omega2K4) / 6;

            this.theta1.add(theta1);
            this.theta2.add(theta2);
            this.omega1.add(omega1);
            this.omega2.add(omega2);

            this.energy.add(calcEnergy(theta1, theta2, omega1, omega2));
        }
    }

    double calcEnergy(double theta1, double theta2, double omega1, double omega2) {
        return (
                this.doublePendulum.getFirstPendulum().getMass() * this.doublePendulum.getFirstPendulum().getLength() * this.doublePendulum.getFirstPendulum().getLength() * omega1 * omega1 / 6
                        + this.doublePendulum.getSecondPendulum().getMass() * this.doublePendulum.getFirstPendulum().getLength() * this.doublePendulum.getFirstPendulum().getLength() * omega1 * omega1 / 2
                        + this.doublePendulum.getSecondPendulum().getMass() * this.doublePendulum.getSecondPendulum().getLength() * this.doublePendulum.getSecondPendulum().getLength() * omega2 * omega2 / 6
                        + this.doublePendulum.getSecondPendulum().getMass() * this.doublePendulum.getFirstPendulum().getLength() * this.doublePendulum.getSecondPendulum().getLength() * omega1 * omega2 * Math.cos(theta1 - theta2) / 2
        ) + (
                - this.doublePendulum.getFirstPendulum().getMass() * App.G * this.doublePendulum.getFirstPendulum().getLength() * Math.cos(theta1) / 2
                        - this.doublePendulum.getSecondPendulum().getMass() * App.G * this.doublePendulum.getFirstPendulum().getLength() * Math.cos(theta1)
                        - this.doublePendulum.getSecondPendulum().getMass() * App.G * this.doublePendulum.getSecondPendulum().getLength() * Math.cos(theta2) / 2
        );
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