package manager;
/*
 *author s.timofeev 08.04.2022
 */

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.List;


public interface TaskManager {

    List<Subtask> getSubtasksList();

    void deleteAllSubtasks();

    Subtask addNewSubtask(Subtask subtask);

    void updateSubtaskStatus(Subtask subtask, Status status);

    Epic addNewEpic(Epic epic);

    List<Epic> getEpicsList();

    Epic getEpicByID(int epicID);

    List<Subtask> getSubtasksByEpic(Epic epic);

    List<Subtask> getSubtasksByEpicID(int epicID);

    void updateEpic(Epic epic);

    void deleteEpicByID(int epicID);

    List<Task> getTasksList();

    void deleteAllTasks();

    Task getTaskByID(int taskID);

    Task findTaskByID(int taskID);

    Task addNewTask(Task task);

    void deleteTaskByID(int taskID);

    void deleteSubtaskByID(int subtaskID);

    void updateTask(Task task);

    List<Task> getHistory();

    void updateTaskStatus(Task task, Status status);

    Subtask getSubtaskByID(int subtaskID);
}
