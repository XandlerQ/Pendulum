public class PojoProblem {
    private String title;
    private PojoSegment[] segments;
    private PojoSolver solver;

    public String getTitle() {
        return title;
    }

    public PojoSegment[] getSegments() {
        return segments;
    }

    public PojoSolver getSolver() {
        return solver;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSegments(PojoSegment[] segments) {
        this.segments = segments;
    }

    public void setSolver(PojoSolver solver) {
        this.solver = solver;
    }
}
