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
        TaskManager manager = Managers.getManager();

        Task task1 = new Task("task1", "description task1");
        Task task2 = new Task("task2", "description task2");
        Task task3 = new Task("TASK 3!!", "description task3");
        Task task4 = new Task("task4", "description task4");
        Task task5 = new Task("TASK 5!!", "description task5");
        Task task7 = new Task("TASK 7!!", "description task5");
        Task task8 = new Task("TASK 8!!", "description task5");
        Task task9 = new Task("TASK 9", "description task5");
        Task task10 = new Task("TASK 10", "description task5");
        Task task6 = new Task("TASK 6", "description task5");
        Task task11 = new Task("11", "description task5");
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.addNewTask(task4);
        manager.addNewTask(task5);
        manager.addNewTask(task6);
        manager.addNewTask(task7);
        manager.addNewTask(task8);
        manager.addNewTask(task9);
        manager.addNewTask(task10);
        manager.addNewTask(task11);
        manager.getTaskByID(task1.getId());
        manager.getTaskByID(task2.getId());
        manager.getTaskByID(task3.getId());
        manager.getTaskByID(task2.getId());
        manager.getTaskByID(task3.getId());
        manager.getTaskByID(task4.getId());
        manager.getTaskByID(task5.getId());
        manager.getTaskByID(task6.getId());
        manager.getTaskByID(task7.getId());
        manager.getTaskByID(task8.getId());
        manager.getTaskByID(task9.getId());
        manager.getTaskByID(task10.getId());

        System.out.println("Печать истории просмотра 1");
        System.out.println(manager.getHistory());

        manager.getTaskByID(task11.getId());
        System.out.println("Печать истории просмотра 2");
        System.out.println(manager.getHistory());


        Epic epic1 = new Epic("epic1", "Description epic 1");
        manager.addNewEpic(epic1);

        Epic epic2 = new Epic("epic2", "Description epic 2");
        manager.addNewEpic(epic2);
        manager.getEpicByID(epic2.getId());

        Epic epic3 = new Epic("3 EPIC 3", "Description epic 3");
        manager.addNewEpic(epic3);
        manager.getEpicByID(epic3.getId());

        Epic epic4 = new Epic("4 EPIC 4", "Description epic 4");
        manager.addNewEpic(epic4);
        manager.getEpicByID(epic4.getId());

        System.out.println("Печать истории просмотра 3");
        System.out.println(manager.getHistory());
        Subtask subtask1 = new Subtask("subtask1", "description subtask 1");
        subtask1.setEpicID(epic1.getId());
        manager.addNewSubtask(subtask1);
        manager.getSubtaskByID(subtask1.getId());

        Subtask subtask2 = new Subtask("subtask2!!!", "description subtask 2!!!!!!!!!");
        subtask2.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask2);

        Subtask subtask3 = new Subtask("subtask3!", "description 3333subtask 333");
        subtask3.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask3);

        Subtask subtask4 = new Subtask("subtask4!", "description 4 subtask");
        subtask4.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask4);

        Subtask subtask5 = new Subtask("55subtask5!", "description SUBTASK5");
        subtask5.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask5);

        //  System.out.println("Печать истории просмотра 4");
        // System.out.println(manager.getHistory());

        manager.getSubtaskByID(subtask1.getId());
        manager.getSubtaskByID(subtask2.getId());
        manager.getSubtaskByID(subtask3.getId());
        manager.getSubtaskByID(subtask4.getId());
        manager.getSubtaskByID(subtask5.getId());

        System.out.println("Печать истории просмотра 5");
        System.out.println(manager.getHistory());


        manager.updateTaskStatus(task1, Status.DONE);
        manager.updateTaskStatus(task2, Status.IN_PROGRESS);
        //

        manager.updateSubtaskStatus(subtask1, Status.DONE);
        manager.updateSubtaskStatus(subtask2, Status.DONE);
        manager.getSubtasksByEpicID(epic1.getId());


        System.out.println("Печать истории просмотра 6");
        System.out.println(manager.getHistory());


    }
}
