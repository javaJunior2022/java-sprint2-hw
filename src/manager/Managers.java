package manager;/*
 *author s.timofeev 08.04.2022
 */

public class Managers {

    private static HistoryManager inMemoryHistoryManager;
    private static TaskManager inMemoryTaskManager;

    static {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }


}
