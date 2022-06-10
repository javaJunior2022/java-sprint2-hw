package manager;/*
 *author s.timofeev 08.04.2022
 */

import java.io.File;
import java.io.IOException;

public class Managers {

    private static HistoryManager inMemoryHistoryManager;
    private static TaskManager inMemoryTaskManager;
    private static FileBackedTasksManager fileBackedTasksManager;
    private static HTTPTaskManager httpTaskManager;


    static {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager();
        fileBackedTasksManager = new FileBackedTasksManager(new File("tasks.csv"));
        try {
            httpTaskManager=new HTTPTaskManager("http://localhost:8078");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static TaskManager getDefault() {
        // return inMemoryTaskManager;
        //return fileBackedTasksManager;
        return httpTaskManager;
    }

    public static TaskManager getInMemoryTaskManager() {
        return inMemoryTaskManager;
    }

    public static TaskManager getFileBackedTasksManager() {
        return fileBackedTasksManager;
    }

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }


}
