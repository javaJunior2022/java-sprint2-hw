package model;

/**
 * Подзадача
 */
public class Subtask extends Task {
    int epicID;

    public Subtask(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Subtask(String name, String description) {
        super(name, description);
    }

    public Subtask(int id, String name, String description, Status status, int epicID) {
        super(id, name, description, status);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public Subtask setEpicID(int epicID) {
        this.epicID = epicID;
        return this;
    }
}
