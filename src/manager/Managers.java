package manager;/*
 *author s.timofeev 08.04.2022
 */

import java.io.File;

public class Managers {

    private static HistoryManager inMemoryHistoryManager;
    private static TaskManager inMemoryTaskManager;
    private static FileBackedTasksManager fileBackedTasksManager;

    static {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager();
        fileBackedTasksManager=new FileBackedTasksManager(new File("tasks.csv"));
    }

    public static TaskManager getDefault() {
       // return inMemoryTaskManager;
        return fileBackedTasksManager;
    }

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }


}
