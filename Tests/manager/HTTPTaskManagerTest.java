package manager;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest {

    @Test
    void main() throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();

        HTTPTaskManager httpTaskManager= new HTTPTaskManager("http://localhost:8078");


        // create a task
        Task task = new Task("task1", "desription",
                Status.NEW, LocalDateTime.now(), 15, TypeTask.TASK);

        httpTaskManager.addNewTask(task);

        Task task1 = new Task("task2", "desription",
                Status.NEW, LocalDateTime.now().plusMinutes(20), 15, TypeTask.TASK);

        httpTaskManager.addNewTask(task1);

        httpTaskManager.getTaskByID(task.getId());
        // System.out.println(manager.historyManager.toString());

        Epic epic = new Epic("EPIC", "EPIC Description");
        httpTaskManager.addNewEpic(epic);
        Epic epic1 = new Epic("EPIC", "EPIC Description");
        httpTaskManager.addNewEpic(epic1);

        // создание подзадачи и привязка её к эпику
        Subtask subtask = new Subtask(
                "Subtask",
                "Subtask, linked with EPIC id=" + epic.getId(),
                LocalDateTime.now().plusMinutes(10),
                20);


        subtask.setEpicID(epic.getId());
        httpTaskManager.addNewSubtask(subtask);
//
        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask, linked with EPIC id=" + epic.getId(),
                LocalDateTime.now().plusMinutes(81),
                20);


        subtask1.setEpicID(epic.getId());
        httpTaskManager.addNewSubtask(subtask1);
//
//
//        Subtask subtask2 = new Subtask(
//                "Subtask",
//                "Subtask, linked with EPIC id=" + epic1.getId(),
//                LocalDateTime.now().plusMinutes(81),
//                20);
//
//
//        subtask.setEpicID(epic1.getId());
//        httpTaskManager.addNewSubtask(subtask2);
        // System.out.println(manager.getSubtasksList());
      //  String t=httpTaskManager.kvTaskClient.load("TASKS");
        httpTaskManager.load();
        System.out.println(httpTaskManager.tasks);

//
//        String e=httpTaskManager.kvTaskClient.load("EPICS");
//        String s=httpTaskManager.kvTaskClient.load("SUBTASKS");
//        httpTaskManager.deleteAllTasks();
//       Gson gson = getGson();
//
//        HashMap<Integer,Task> restoredTasks = gson.fromJson(t, new TypeToken<HashMap<Integer,Task>>(){}.getType());
//        HashMap<Integer,Task> restoredEpics = gson.fromJson(e, new TypeToken<HashMap<Integer,Epic>>(){}.getType());
//        ArrayList<Subtask> restoredSubtasks = gson.fromJson(s, new TypeToken<ArrayList<Subtask>>(){}.getType());
//
//        System.out.println(restoredEpics);
//        System.out.println(restoredSubtasks);
        //
//
        //httpTaskManager.manager.tasks.putAll(restoredTasks);
//        System.out.println(manager.tasks);
//       System.out.println(restoredTasks);
//        System.out.println(manager.getTasksList());


        kvServer.stop();
    }
}