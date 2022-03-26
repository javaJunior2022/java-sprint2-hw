public class Task {


    public enum Status {NEW, IN_PROGRESS, DONE};
    protected int id=1;
    protected String name;
    protected String description;
    protected Status status;


    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, Status status ) {
        this.name = name;
        this.description = description;
        this.status = status;
    }
    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Task setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "\n"+ "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }


}
