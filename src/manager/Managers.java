package manager;/*
 *author s.timofeev 08.04.2022
 */

public class Managers {

    private static InMemoryHistoryManager inMemoryHistoryManager;
    private static InMemoryTaskManager inMemoryTaskManager;

    static {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager();
    }

    public static TaskManager getManager() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }


}
