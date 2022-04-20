package model;

import java.util.ArrayList;

/**
 * Эпик
 */
public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Epic(int id, String name, String description, Status status, ArrayList<Subtask> subtasks) {
        super(id, name, description, status);
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
