import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager= Managers.getManager();

       Task task1=new Task("task1","description task1");
       Task task2=new Task("task2","description task2");
        manager.addNewTask(task1);
        manager.addNewTask(task2);
//
//        Epic epic1=new Epic("epic1","Description epic 1");
//        InMemoryTaskManager.addNewEpic(epic1);
//
//        Epic epic2=new Epic("epic2","Description epic 2");
//        InMemoryTaskManager.addNewEpic(epic2);
//
//        Subtask subtask1=new Subtask("subtask1","description subtask 1");
//        subtask1.setEpicID(epic1.getId());
//
//        Subtask subtask2=new Subtask("subtask2!!!","description subtask 2!!!!!!!!!");
//        subtask1.setEpicID(epic1.getId());
//
//
//        Subtask subtask3=new Subtask("subtask3!","description subtask 2");
//        subtask1.setEpicID(epic1.getId());
//        subtask2.setEpicID(epic2.getId());
//        subtask3.setEpicID(epic2.getId());
//
//
//        InMemoryTaskManager.addNewSubtask(subtask1);
//        InMemoryTaskManager.addNewSubtask(subtask2);
//        InMemoryTaskManager.addNewSubtask(subtask3);
//        InMemoryTaskManager.updateTaskStatus(task1, Status.DONE);
//        InMemoryTaskManager.updateTaskStatus(task2,Status.IN_PROGRESS);
//        System.out.println(TaskManager.getTasksList());
//        InMemoryTaskManager.updateSubtaskStatus (subtask1,Status.DONE);
//        InMemoryTaskManager.updateSubtaskStatus (subtask2,Status.DONE);
//        System.out.println(InMemoryTaskManager.getEpicsList());
//        System.out.println(TaskManager.getTasksList());
//        System.out.println(InMemoryTaskManager.getSubtasksList());
//
//
//        System.out.println(InMemoryTaskManager.getEpicsList());
//        System.out.println(TaskManager.getTasksList());


    }
}

