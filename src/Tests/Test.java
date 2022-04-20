package Tests;

import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import model.Status;
import model.Task;
import model.Epic;
import model.Subtask;

public class Test {


    public static void performTest() {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("task1", "description task1");
        Task task2 = new Task("task2", "description task2");
        Task task3 = new Task("task3", "description task3");

        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.getTaskByID(task1.getId());
        manager.getTaskByID(task2.getId());
        System.out.println("История после просмотра задачи 1 и 2");
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");
        System.out.println("История после повторного просмотра задачи 1 и первого просмотра задачи 3");
        manager.getTaskByID(task1.getId());
        manager.getTaskByID(task3.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");
        System.out.println("История повторный просмотр задачи 2");
        manager.getTaskByID(task2.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");
        System.out.println("Список всех задач");
        System.out.println(manager.getTasksList());
        System.out.println("--------------------------------------");


        System.out.println("Добавление эпика 1 и его просмотр");

        Epic epic1 = new Epic("epic1", "Description epic 1");
        manager.addNewEpic(epic1);
        manager.getEpicByID(epic1.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

        System.out.println("Добавление подзадачи 1 (и ее просмотр) к эпику 1");
        Subtask subtask1 = new Subtask("subtask1", "description subtask 1");
        subtask1.setEpicID(epic1.getId());
        manager.addNewSubtask(subtask1);
        manager.getSubtaskByID(subtask1.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

        System.out.println("Добавление подзадачи 2 к эпику 1 и её просмотр");
        Subtask subtask2 = new Subtask("subtask2!!!", "description subtask 2!!!!!!!!!");
        subtask2.setEpicID(epic1.getId());
        manager.addNewSubtask(subtask2);
        manager.getSubtaskByID(subtask2.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");
        System.out.println("Повторный просмотр подзадачи 1");
        manager.getSubtaskByID(subtask1.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");
//
        System.out.println("Добавление подзадачи 3 к эпику 1");
        Subtask subtask3 = new Subtask("subtask3!", "description 3333subtask 333");
        subtask3.setEpicID(epic1.getId());
        manager.addNewSubtask(subtask3);
        System.out.println("--------------------------------------");
        System.out.println("Добавление эпика 2 и затем его просмотр");

        Epic epic2 = new Epic("epic2", "Description epic 2");
        manager.addNewEpic(epic2);
        manager.getEpicByID(epic2.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

        System.out.println("Удаление задачи 3");
        manager.deleteTaskByID(task3.getId());
       System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

        System.out.println("Просмотр ПОДЗАДАЧИ 3");
        manager.getSubtaskByID(subtask3.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

        System.out.println("Удаление ПОДЗАДАЧИ 3");
        manager.deleteSubtaskByID(subtask3.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

        System.out.println("Удаление всех подзадач ");
        manager.deleteAllSubtasks();
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");


        System.out.println("Удаление всех ЗАДАЧ ");
        manager.deleteAllTasks();
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

        System.out.println("Удаление эпика 1, должны пропасть подзадачи и эпик из истории");
        manager.deleteEpicByID(epic1.getId());
        System.out.println(manager.getHistory());
        System.out.println("--------------------------------------");

    }
}
