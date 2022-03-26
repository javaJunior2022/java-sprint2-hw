public class Subtask extends Task {


    public Subtask(String name, String description) {
        super(name, description);
    }
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
