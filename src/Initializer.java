import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;

public class Initializer {
    private String iniFileName;
    private PojoProblem problem;

    public Initializer() {
        this.iniFileName = null;
    }

    public Initializer(String iniFileName) {
        this.iniFileName = iniFileName;
    }

    public PojoProblem getProblem() {
        return problem;
    }

    void readProblem() throws IOException, Exception  {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(this.iniFileName));
        this.problem = gson.fromJson(reader, PojoProblem.class);
        if (fileDataError(this.problem)) throw new Exception("File data error");
    }

    boolean fileDataError(PojoProblem problem) {
        double T = problem.getSolver().getT();
        double dt = problem.getSolver().getDt();
        String type = problem.getSolver().getType();
        double m1 = problem.getSegments()[0].getMass();
        double m2 = problem.getSegments()[1].getMass();
        double l1 = problem.getSegments()[0].getLength();
        double l2 = problem.getSegments()[1].getLength();
        return T <= 0
                || dt > T
                || dt <= 0
                || (type.equals(new String("Euler")) && type.equals(new String("Runge-Kutta")))
                || m1 < 0
                || m2 < 0
                || l1 <= 0
                || l2 <= 0;
    }

}
