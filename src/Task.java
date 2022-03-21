public class Task {

    public enum Status {NEW, IN_PROGRESS, DONE};
    protected int id=1;
    protected String name;
    protected String description;
    protected Status status;

    // make the status as NEW
    public  Task(String name, String  description){
        this.name=name;
        this.description=description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }


}
