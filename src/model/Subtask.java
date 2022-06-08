package model;

import java.time.LocalDateTime;

/**
 * Подзадача
 */
public class Subtask extends Task {
    int epicID;

    public Subtask(int id, String name, String description, Status status, int epicID,
                   LocalDateTime startTime, long duration,LocalDateTime endTime) {
        super(id,name, description, status, startTime, duration,endTime);
        this.epicID = epicID;
    }
    public Subtask(String name, String description, Status status,
                   LocalDateTime localDateTime, long duration) {
        super(name, description, status, localDateTime, duration);

    }
    public Subtask(String name, String description, LocalDateTime localDateTime, long duration) {
        super(name, description, localDateTime, duration);

    }

    public Subtask( String name, String description, Status status, int epicID,
                   LocalDateTime localDateTime, long duration) {
        super(name, description, status, localDateTime, duration);
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
