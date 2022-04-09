package manager;/*
 *author s.timofeev 08.04.2022
 */

import model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private LinkedList<Task> history = new LinkedList<>();

    /**
     * Добавляет задачу в историю
     *
     * @param task
     */
    @Override
    public void add(Task task) {
        // в истории хранятся только 10 последних задач
        if (history.size() >= 10) {
            history.removeFirst();
        }
        history.add(task);
    }

    /**
     * Получает историю задач
     *
     * @return List
     */
    @Override
    public List<Task> getHistory() {
        return history;
    }
}
