import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    public static int id;// уникальный нумератор задач
    public static HashMap<Integer, Task> tasks = new HashMap<>(); // Список задач
    public static HashMap<Integer, Task> subtasks = new HashMap<>(); //Cписок подздач, в иерархии к каждой задаче
    public static HashMap<Integer, Subtask> epics = new HashMap<>();// Список эпиков и их подзадач

    public static void addNewTask(Task task) {
        task.id = id++;
        task.status = Task.Status.NEW;
        tasks.put(task.id, task);
    }

    public static void updateTaskStatus(int taskID, Task.Status status) {
        Task task = tasks.get(taskID);
        if (task != null) {
            task.status = status;
        }
    }

    public static Task getTaskByID(int id) {
        Task task = tasks.get(id);
        return task;
    }

    public static void deleteAllTasks() {
        tasks.clear();
    }

    public static void deleteTaskByID(int taskID){
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(taskID);
        }
    }

    public static void updateTask(Task task){
        tasks.put(task.id,task);
    }

    public static Epic addNewEpic(String name, String description) {
        int epicID = id++;
        Epic epic = new Epic(epicID, name, description);
        return epic;
    }
    /* public static Subtask addNewSubtask( String name, String description){
     *//*  int epicID= id ++;
        Epic epic =new Epic(epicID,name, description);
        return epic;*//*
    }*/

    public static void printAllTasks() {
        for (Map.Entry entry : tasks.entrySet()) {
            System.out.print("Task id=" + entry.getKey() + " task.name= " + entry.getValue());
        }
        System.out.println();
    }


    public void changeTaskStatus(Task task, Task.Status status) {
        task.status = status;
    }
}
