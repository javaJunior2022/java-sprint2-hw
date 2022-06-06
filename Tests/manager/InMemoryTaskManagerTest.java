package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import model.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
protected TaskManager manager = Managers.getDefault();
Task task;
Epic epic;
Subtask subtask;
@BeforeEach
void init(){
    manager = Managers.getDefault();
   // создание задачи
     task = new Task(1,"Task", "Tasks description",Status.NEW, LocalDateTime.now(),15);
    manager.addNewTask(task);
    // создание эпика
     epic = new Epic("EPIC", "EPIC Description");
    manager.addNewEpic(epic);
    // создание подзадачи и привязка её к эпику
     subtask = new Subtask("Subtask", "Subtask, linked with EPIC "+epic.getId());
    subtask.setEpicID(epic.getId());
    manager.addNewSubtask(subtask);


}



    @Test
    void getSubtasksList() {
    }

    @Test
    void deleteAllSubtasks() {
    }

    @Test
    void addNewSubtask() {
    }

    @Test
    void updateSubtaskStatus() {
    }

    @Test
    void addNewEpic() {
    }

    @Test
    void getEpicsList() {
    }

    @Test
    void getEpicByID() {
    }

    @Test
    void getSubtasksByEpic() {
    }

    @Test
    void getSubtasksByEpicID() {
    }

    @Test
    void getSubtaskByID() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void deleteEpicByID() {
    }

    @Test
    void getTasksList() {
    final List<Task> tasks=manager.getTasksList();
    assertNotNull(task);
    assertEquals(1,tasks.size(),"Task List has more than 1 task");
    assertEquals(task, tasks.get(0), "This task is not in the Task List");

    }

    @Test
    void deleteAllTasks() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void getTaskByID() {
    }

    @Test
    void findTaskByID() {
    }

    @Test
    void addNewTask() {
    }

    @Test
    void deleteTaskByID() {
    }

    @Test
    void deleteSubtaskByID() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateTaskStatus() {
    }

    @Test
    void setId() {
    }
}