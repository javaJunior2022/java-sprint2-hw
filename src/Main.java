import manager.*;
import model.Status;
import model.Task;
import model.TypeTask;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        // starting server
        KVServer kvServer = new KVServer();
        kvServer.start();
        // starting http server
        HttpTaskServer httpTaskServer=new HttpTaskServer();

        TaskManager taskManager=Managers.getDefault();
        // create a task
        Task task = new Task("task1", "description",
                Status.NEW, LocalDateTime.now(), 15, TypeTask.TASK);
        taskManager.addNewTask(task);
        taskManager.getTaskByID(task.getId());
        System.out.println(taskManager.getTasksList());

    }
}



