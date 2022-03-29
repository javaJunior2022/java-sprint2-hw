import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {
        Task task1=new Task("task1","description task1");
        Task task2=new Task("task2","description task2");
        TaskManager.addNewTask(task1);
        TaskManager.addNewTask(task2);

        Epic epic1=new Epic("epic1","Description epic 1");
        TaskManager.addNewEpic(epic1);

        Epic epic2=new Epic("epic2","Description epic 2");
        TaskManager.addNewEpic(epic2);

        Subtask subtask1=new Subtask("subtask1","description subtask 1");
        subtask1.setEpicID(epic1.getId());

        Subtask subtask2=new Subtask("subtask2!!!","description subtask 2!!!!!!!!!");
        subtask1.setEpicID(epic1.getId());


        Subtask subtask3=new Subtask("subtask3!","description subtask 2");
        subtask1.setEpicID(epic1.getId());
        subtask2.setEpicID(epic2.getId());
        subtask3.setEpicID(epic2.getId());


        TaskManager.addNewSubtask(subtask1);
        TaskManager.addNewSubtask(subtask2);
        TaskManager.addNewSubtask(subtask3);
        TaskManager.updateTaskStatus(task1, Status.DONE);
        TaskManager.updateTaskStatus(task2,Status.IN_PROGRESS);
        System.out.println(TaskManager.getTasksList());
        TaskManager.updateSubtaskStatus (subtask1,Status.DONE);
        TaskManager.updateSubtaskStatus (subtask2,Status.DONE);
        System.out.println(TaskManager.getEpicsList());
        System.out.println(TaskManager.getTasksList());
        System.out.println(TaskManager.getSubtasksList());


        System.out.println(TaskManager.getEpicsList());
        System.out.println(TaskManager.getTasksList());


    }
}

