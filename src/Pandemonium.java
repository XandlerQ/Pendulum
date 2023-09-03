import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Pandemonium {
    private TimeGraph theta1Graph;
    private TimeGraph theta2Graph;
    private TimeGraph omega1Graph;
    private TimeGraph omega2Graph;
    private Initializer initializer;
    private EulerSolver eulerSolver;
    private RungeKuttaSolver rungeKuttaSolver;
    private DoublePendulum doublePendulum;
    private int solverType;
    private int animationTick;
    private int animationLength;

    public Pandemonium(String iniFileName) {
        this.animationTick = 0;

        this.initializer = new Initializer(iniFileName);
        try {
            this.initializer.readProblem();
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }

        PojoProblem problem = this.initializer.getProblem();
        PojoSegment[] segments = problem.getSegments();

        Pendulum pendulum1 = new Pendulum(
                new Dot(500, 500),
                segments[0].getLength(),
                segments[0].getMass(),
                segments[0].getTheta(),
                segments[0].getOmega()
        );

        Pendulum pendulum2 = new Pendulum(
                segments[1].getLength(),
                segments[1].getMass(),
                segments[1].getTheta(),
                segments[1].getOmega()
        );

        this.doublePendulum = new DoublePendulum(pendulum1, pendulum2);

        switch (problem.getSolver().getType()) {
            case "Euler" -> {
                this.solverType = 0;
                this.eulerSolver = new EulerSolver();
                this.eulerSolver.setDoublePendulum(this.doublePendulum);
                this.eulerSolver.setT(problem.getSolver().getT());
                this.eulerSolver.setTimeStep(problem.getSolver().getDt());
            }
            case "Runge-Kutta" -> {
                this.solverType = 1;
                this.rungeKuttaSolver = new RungeKuttaSolver();
                this.rungeKuttaSolver.setDoublePendulum(this.doublePendulum);
                this.rungeKuttaSolver.setT(problem.getSolver().getT());
                this.rungeKuttaSolver.setTimeStep(problem.getSolver().getDt());
            }
        }

        this.animationLength = (int)(problem.getSolver().getT() / problem.getSolver().getDt());

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

    void solve() {
        switch (this.solverType) {
            case 0 : {
                this.eulerSolver.solve();
            }
            case 1 : {
                this.rungeKuttaSolver.solve();
            }
        }
    }

    void animate() {
        App.processingRef.background(0);
        double theta1 = 0;
        double theta2 = 0;
        double omega1 = 0;
        double omega2 = 0;
        switch (this.solverType) {
            case 0 -> {
                theta1 = this.eulerSolver.getTheta1().get(this.animationTick);
                theta2 = this.eulerSolver.getTheta2().get(this.animationTick);
                omega1 = this.eulerSolver.getOmega1().get(this.animationTick);
                omega2 = this.eulerSolver.getOmega2().get(this.animationTick);
            }
            case 1 -> {
                theta1 = this.rungeKuttaSolver.getTheta1().get(this.animationTick);
                theta2 = this.rungeKuttaSolver.getTheta2().get(this.animationTick);
                omega1 = this.rungeKuttaSolver.getOmega1().get(this.animationTick);
                omega2 = this.rungeKuttaSolver.getOmega2().get(this.animationTick);
            }
        }

        this.doublePendulum.getFirstPendulum().setTheta(theta1);
        this.doublePendulum.getSecondPendulum().setTheta(theta2);
        this.doublePendulum.getFirstPendulum().setOmega(omega1);
        this.doublePendulum.getSecondPendulum().setOmega(omega2);

        this.doublePendulum.getSecondPendulum().setFix(this.doublePendulum.getFirstPendulum().getEndDot());

        this.theta1Graph.addValue(theta1);
        this.theta2Graph.addValue(theta2);
        this.omega1Graph.addValue(omega1);
        this.omega2Graph.addValue(omega2);

        this.doublePendulum.render();
        this.theta1Graph.render();
        this.theta2Graph.render();
        this.omega1Graph.render();
        this.omega2Graph.render();

        this.animationTick += 1;
        if (this.animationTick > this.animationLength) {
            this.animationTick = 0;
        }
    }
}
