public class Epic extends Task{


    public Epic(String name, String description, Status status) {
        super(name, description);
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return "EPIC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

}
