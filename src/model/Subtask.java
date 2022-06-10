package model;

import java.time.LocalDateTime;

/**
 * Подзадача
 */
public class Subtask extends Task {
    int epicID;

    public Subtask(int id, String name, String description, Status status, int epicID,
                   LocalDateTime startTime, long duration, LocalDateTime endTime) {
        super(id, name, description, status, startTime, duration, endTime, TypeTask.SUBTASK);
        this.epicID = epicID;
    }

    public Subtask(String name, String description, Status status,
                   LocalDateTime localDateTime, long duration) {
        super(name, description, status, localDateTime, duration, TypeTask.SUBTASK);

    }

    public Subtask(String name, String description, LocalDateTime localDateTime,
                   long duration) {
        super(name, description, localDateTime, duration, TypeTask.SUBTASK);

    }

    public Subtask(String name, String description, Status status, int epicID,
                   LocalDateTime localDateTime, long duration) {
        super(name, description, status, localDateTime, duration, TypeTask.SUBTASK);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public Subtask setEpicID(int epicID) {
        this.epicID = epicID;
        return this;
    }

    @Override
    public String toString() {
        return "\n" + "model.Subtask{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", status=" + super.getStatus() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", epicID=" + epicID +
                '}';
    }
}
