package manager;/*
 *author s.timofeev 08.04.2022
 */

public class Managers {
    public static TaskManager getManager() {
        return new InMemoryTaskManager();
    }
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getHistoryTask(){
        return new InMemoryHistoryManager();
    }

}
