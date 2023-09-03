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

    void readProblem() throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(this.iniFileName));
        this.problem = gson.fromJson(reader, PojoProblem.class);
    }

}
