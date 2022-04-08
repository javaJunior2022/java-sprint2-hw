package manager;/*
 *author s.timofeev 08.04.2022
 */

import model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List <Task> getHistory();
}
