package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Эпик
 */
public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description, TypeTask.EPIC);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status, TypeTask.EPIC);
    }

    public Epic(int id, String name, String description, Status status,
                ArrayList<Subtask> subtasks) {
        super(id, name, description, status, TypeTask.EPIC);
        this.subtasks = subtasks;
    }

    /**
     * Вовзращает все подзадачи
     *
     * @return
     */
    public ArrayList<Subtask> getSubtasks() {
        if (subtasks == null) {
            return new ArrayList<>();
        } else {
            return subtasks;
        }
    }

    /**
     * Возвращает подзадачу по указанному ID
     *
     * @param subtaskID
     * @return
     */
    public Subtask getSubtaskByID(int subtaskID) {
        if (subtasks == null) return null;

        for (Subtask e : subtasks) {
            if (e.getId() == subtaskID) {
                return e;
            }
        }
        return null;
    }

    public Epic setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
        return this;
    }

    /**
     * Устанавливает дату начала работы с эпиком
     *
     * @param startTime
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Устанавливает дату окончания работы с эпиком
     *
     * @param startTime
     */

    public void setEndTime(LocalDateTime startTime) {
        this.endTime = startTime;
    }

    /**
     * Устанавливает продолжительность эпика
     *
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void deleteSubtasks() {
        subtasks.clear();
    }

    /**
     * добавление подзадачи в список эпика
     *
     * @param subtask
     */
    public void addSubtasks(Subtask subtask) {
        if (!subtasks.contains(subtask)) {
            subtasks.add(subtask);
        }
    }
}
