package manager;

import model.Status;
import model.Task;
import model.TypeTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    @BeforeEach
    void init() {
        manager = new InMemoryTaskManager();
        super.init();
    }

    @Test
    void add() {
        Task task1 = new Task("Task init 1", "Tasks description init1",
                Status.NEW, LocalDateTime.now(), 15, TypeTask.TASK);
        manager.addNewTask(task1);
        manager.getTaskByID(task1.getId());
        // должен увидеть таск в истории
        List<Task> historyList = manager.getHistory();
        assertTrue(historyList.contains(task1), "Task had not been added in History");
    }

    @Test
    void remove() {
        Task task1 = new Task("Task init 1", "Tasks description init1",
                Status.NEW, LocalDateTime.now(), 15,TypeTask.TASK);
        manager.addNewTask(task1);
        manager.getTaskByID(task1.getId());
        // удаляю таск, и должен удалиться из истории
        manager.deleteTaskByID(task1.getId());
        List<Task> historyList = manager.getHistory();
        assertTrue(!historyList.contains(task1), "Task had not been deleted from History");
    }

    @Test
    void getHistory() {
        Task task1 = new Task("Task history", "Description",
                Status.NEW, LocalDateTime.now(), 15,TypeTask.TASK);
        Task task2 = new Task("Task history2", "Description2",
                Status.NEW, LocalDateTime.now().plusMinutes(40), 15,TypeTask.TASK);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.getTaskByID(task1.getId());
        manager.getTaskByID(task2.getId());

        List<Task> historyList = manager.getHistory();
        assertEquals(3, historyList.size(), "Could not add 2 tasks in History and 1 in init");
    }
}