package manager;

import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;
    Task task = null;
    Epic epic = null;
    Subtask subtask = null;


    @BeforeEach
    @Test
    void init() {
        task = new Task("Task init 1", "Tasks description init1",
                Status.NEW, LocalDateTime.now(), 15,TypeTask.TASK);
        manager.addNewTask(task);
        manager.getTaskByID(task.getId());
        // создание эпика
        epic = new Epic("EPIC", "EPIC Description");
        manager.addNewEpic(epic);

        // создание подзадачи и привязка её к эпику
        subtask = new Subtask(
                "Subtask",
                "Subtask, linked with EPIC " + epic.getId(),
                LocalDateTime.now(),
                20);
        subtask.setEpicID(epic.getId());
        manager.addNewSubtask(subtask);
    }

    @Test
    void getPrioritizedTasks() {
        Task task1 = new Task("Task init 11", "Tasks description init1",
                Status.NEW, LocalDateTime.now().plusMinutes(4000), 15,TypeTask.TASK);

        manager.addNewTask(task1);

        Task task2 = new Task("Task init 2", "Tasks description init1",
                Status.NEW, LocalDateTime.now().minusMinutes(3600), 15,TypeTask.TASK);

        manager.addNewTask(task2);
        assertTrue(manager.getPrioritizedTasks().size() > 0, "Priority list is empty");
    }

    @Test
    void tasksCrossing() {
        Task task1 = new Task("Task init 11", "Tasks description init1",
                Status.NEW, LocalDateTime.now().plusMinutes(4000), 15,TypeTask.TASK);

        assertFalse(manager.timeIsCrossed(task1), "TTime is crossing, it is wrong");
        manager.addNewTask(task1);

        Task task2 = new Task("Task init 2", "Tasks description init1",
                Status.NEW, LocalDateTime.now().minusMinutes(3600), 15,TypeTask.TASK);
        assertFalse(manager.timeIsCrossed(task2), "Time is crossing, it is wrong");
        manager.addNewTask(task2);

        Task task3 = new Task("Task init 3", "Tasks description init1",
                Status.NEW, LocalDateTime.now().minusMinutes(7200), 15,TypeTask.TASK);
        assertFalse(manager.timeIsCrossed(task3), "Time is crossing, it is wrong");

        manager.addNewTask(task3);

        Task task4 = new Task("Task init 4", "Tasks description init1",
                Status.NEW, LocalDateTime.now().plusMinutes(70), 15,TypeTask.TASK);
        assertFalse(manager.timeIsCrossed(task4), "Time is crossing, it is wrong");
        manager.addNewTask(task4);
        Task task5 = new Task("Task 5 cross", "Tasks description 5",
                Status.NEW, LocalDateTime.now().plusMinutes(60), 15,TypeTask.TASK);
        assertTrue(manager.timeIsCrossed(task5), "Task 5 and 4 are crossed by the time");
     }

    @Test
    void subtasksListIsEmpty() {
        manager.deleteAllSubtasks();
        assertEquals(Status.NEW, epic.getStatus(), "If subtasks list is empty, EPIC must be NEW");
    }

    @Test
    void allSubtasksAreNew() {
        Epic epic2 = new Epic("EPIC2", "EPIC2 Description");
        manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask1, linked with EPIC " + epic2.getId(),
                LocalDateTime.now(),
                5);
        subtask1.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask(
                "Subtask2",
                "Subtask2, linked with EPIC " + epic2.getId(),
                LocalDateTime.now().plusMinutes(50),
                5);
        subtask2.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask2);
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.NEW);
        assertEquals(Status.NEW, epic2.getStatus(), "If all subtasks have NEW status, EPIC must be NEW");
    }

    @Test
    void allSubtasksAreDone() {
        Epic epic2 = new Epic("EPIC2", "EPIC2 Description");
        manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask1, linked with EPIC " + epic2.getId(),
                LocalDateTime.now(),
                5);
        subtask1.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask(
                "Subtask2",
                "Subtask2, linked with EPIC " + epic2.getId(),
                LocalDateTime.now().plusMinutes(50),
                5);
        subtask2.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask2);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        manager.updateEpicAdditionInformation(epic2);

        assertEquals(Status.DONE, epic2.getStatus(), "If all subtasks have DONE status, EPIC must be DONE");
    }

    @Test
    void SubtasksAreNewAndDone() {
        Epic epic2 = new Epic("EPIC2", "EPIC2 Description");
        manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask1, linked with EPIC " + epic2.getId(),
                LocalDateTime.now(),
                5);
        subtask1.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask1);
        Subtask subtask2 = new Subtask(
                "Subtask2",
                "Subtask2, linked with EPIC " + epic2.getId(),
                LocalDateTime.now().plusMinutes(50),
                5);
        subtask2.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask2);

        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        manager.updateEpicAdditionInformation(epic2);


        assertEquals(Status.IN_PROGRESS, epic2.getStatus(),
                "If subtasks have NEW and DONE status, EPIC must be IN_PROGRESS");
    }

    @Test
    void SubtasksAreNewDoneAndIn_Progress() {
        Epic epic2 = new Epic("EPIC2", "EPIC2 Description");
        manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask1, linked with EPIC " + epic2.getId(),
                LocalDateTime.now(),
                5);
        subtask1.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask1);


        Subtask subtask2 = new Subtask(
                "Subtask2",
                "Subtask2, linked with EPIC " + epic2.getId(),
                LocalDateTime.now().plusMinutes(50),
                5);
        subtask2.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask2);

        Subtask subtask3 = new Subtask(
                "Subtask2",
                "Subtask2, linked with EPIC " + epic2.getId(),
                LocalDateTime.now().plusMinutes(200),
                5);
        subtask3.setEpicID(epic2.getId());
        manager.addNewSubtask(subtask3);

        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.IN_PROGRESS);
        manager.updateEpicAdditionInformation(epic2);

        assertEquals(Status.IN_PROGRESS, epic2.getStatus(),
                "If  subtasks have NEW,  DONE and, IN_PROGRESS status, EPIC must be IN_PROGRESS");
    }


    @Test
    void getSubtasksList() {
        manager.deleteAllSubtasks();
        Epic epic1 = new Epic("EPIC1", "EPIC Description");
        manager.addNewEpic(epic1);

        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask1, linked with EPIC " + epic1.getId(),
                LocalDateTime.now(),
                5);
        subtask1.setEpicID(epic1.getId());
        manager.addNewSubtask(subtask1);


        final List<Subtask> subtasks = manager.getSubtasksList();
        assertNotNull(subtasks);
        assertEquals(1, subtasks.size(), "Subtask List has more than 1 subtask");
        assertEquals(subtask1, subtasks.get(0), "This subtask  is not in the subtask List");
    }

    @Test
    void deleteAllSubtasks() {
        manager.deleteAllSubtasks();
        final List<Subtask> subtasks = manager.getSubtasksList();
        assertEquals(0, subtasks.size(), "Method deleteAllSubtasks does not work well");
    }

    @Test
    void addNewSubtask() {
        manager.deleteAllSubtasks();
        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask1, linked with EPIC " + epic.getId(),
                LocalDateTime.now().plusMinutes(200),
                5);
        subtask1.setEpicID(epic.getId());
        manager.addNewSubtask(subtask1);


        Subtask subtask2 = new Subtask(
                "Subtask2",
                "Subtask1, linked with EPIC " + epic.getId(),
                LocalDateTime.now().minusMinutes(400),
                17);
        subtask2.setEpicID(epic.getId());
        manager.addNewSubtask(subtask2);


        Subtask subtask3 = new Subtask(
                "Subtask3",
                "Subtask3, linked with EPIC " + epic.getId(),
                LocalDateTime.now().minusMinutes(900),
                30);
        subtask3.setEpicID(epic.getId());
        manager.addNewSubtask(subtask3);


        final List<Subtask> subtasks = manager.getSubtasksList();
        assertEquals(3, subtasks.size(), "Method addNewSubtask does not work well");
    }

    @Test
    void updateSubtaskStatus() {
        subtask.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, subtask.getStatus(), "Method updateSubtaskStatus  IN_PROGRESS does not work well");
    }

    @Test
    void addNewEpic() {
        manager.deleteAllEpics();
        Epic epic1 = new Epic("EPIC2", "EPIC2");
        manager.addNewEpic(epic1);
        final List<Epic> epics = manager.getEpicsList();
        assertEquals(1, epics.size(), "Method addNewEpic does not work well");
    }

    @Test
    void getEpicsList() {
        manager.deleteAllEpics();
        Epic epic1 = new Epic("EPIC TEST", "EPIC Test");
        manager.addNewEpic(epic1);
        final List<Epic> epics = manager.getEpicsList();
        assertNotNull(epics);
        assertEquals(1, epics.size(), "EPIC List has more than 1 EPIC");
        assertEquals(epic1, epics.get(0), "This EPIC  is not in the EPIC List");
    }

    @Test
    void getEpicByID() {
        assertEquals(epic, manager.getEpicByID(epic.getId()), "Can' get the epic by ID");
    }

    @Test
    void getSubtasksByEpic() {
        // создаю список с подзадачей
        // и сравниваю с тем, что возвращает метод
        List<Subtask> expectedArray = new ArrayList<>();
        expectedArray.add(subtask);

        List<Subtask> actualArray = manager.getSubtasksByEpic(epic);
        assertArrayEquals(expectedArray.toArray(), actualArray.toArray(), "Method getSubtasksByEpic goes wrong");
    }

    @Test
    void getSubtasksByEpicID() {
        // создаю список с подзадачей
        // и сравниваю с тем, что возвращает метод
        List<Subtask> expectedArray = new ArrayList<>();
        expectedArray.add(subtask);

        List<Subtask> actualArray = manager.getSubtasksByEpicID(epic.getId());
        assertArrayEquals(expectedArray.toArray(), actualArray.toArray(), "Method getSubtasksByEpicID goes wrong");
    }

    @Test
    void getSubtaskByID() {
        assertEquals(subtask, manager.getSubtaskByID(subtask.getId()), "Can' get the subtask by ID");
    }
@Test
void TaskType(){
        Task task1=new Task("task N","Description",Status.NEW,
                LocalDateTime.now(),60,TypeTask.TASK);
        assertEquals(TypeTask.TASK,task1.getTypeTask(),"New TASK is not TypeTask.TASK)");
}

    @Test
    void EpicType(){
        Epic epic1=new Epic ("EPIC N","Description");
        assertEquals(TypeTask.EPIC,epic1.getTypeTask(),"New EPIC is not TypeTask.EPIC)");
    }

    @Test
    void SubtaskType(){
        Epic epic1=new Epic ("EPIC N","Description");
        manager.addNewEpic(epic1);
        Subtask subtask1=new Subtask("Subtask", "Subtask description",LocalDateTime.now(),50);
        subtask1.setEpicID(epic1.getId());
        manager.addNewSubtask(subtask1);

        assertEquals(TypeTask.SUBTASK,subtask1.getTypeTask(),"New SUBTASK is not TypeTask.SUBTASK)");
    }

    @Test
    void deleteEpicByID() {
        manager.deleteAllEpics();
        Epic epic1 = new Epic("Test EPIC", "Test EPIC");
        manager.addNewEpic(epic1);
        manager.deleteEpicByID(epic1.getId());
        final List<Epic> epics = manager.getEpicsList();
        assertEquals(0, epics.size(), "Method deleteEpicByID does not work well");
    }

    @Test
    void getTasksList() {
        manager.deleteAllTasks();
        Task task1 = new Task("Test task", "Description test task",
                Status.NEW, LocalDateTime.now(), 40,TypeTask.TASK);
        manager.addNewTask(task1);
        final List<Task> tasks = manager.getTasksList();
        assertEquals(1, tasks.size(), "Task List has more than 1 task");
        assertEquals(task1, tasks.get(0), "This task is not in the Task List");
    }

    @Test
    void deleteAllTasks() {
        manager.deleteAllTasks();
        final List<Task> tasks = manager.getTasksList();
        assertEquals(0, tasks.size(), "Method deleteAllTasks does not work well");
    }

    @Test
    void getHistoryTask() {
        Task task1 = new Task("task h", "description task h", Status.NEW,
                LocalDateTime.now(), 100,TypeTask.TASK);
        manager.addNewTask(task1);
        manager.getTaskByID(task1.getId());

        assertTrue(manager.getHistory().size() > 0, "Task was not added in history");
    }

    @Test
    void getHistorySubtask() {
        Subtask subtask1 = new Subtask(
                "Subtask1",
                "Subtask1, linked with EPIC " + epic.getId(),
                LocalDateTime.now().plusMinutes(200),
                5);
        subtask1.setEpicID(epic.getId());
        manager.addNewSubtask(subtask1);

        manager.getSubtaskByID(subtask1.getId());

        assertTrue(manager.getHistory().size() > 0, "Subtask was not added in history");
    }


    @Test
    void getTaskByID() {
        assertEquals(task, manager.findTaskByID(task.getId()), "Can' get the task by ID");
    }

    @Test
    void getTaskByWrongID() {
        assertNull(manager.findTaskByID(-200), "Find task by wrong ID=-200");
        // assertEquals(null, manager.findTaskByID(0), "Find task by wrong ID=0");
    }

    @Test
    void findTaskByID() {
        assertEquals(task, manager.findTaskByID(task.getId()), "Can' find the task by ID");
    }

    @Test
    void addNewTask() {
        assertNotNull(task, "Task is NULL");
    }

    @Test
    void deleteTaskByID() {
        manager.deleteTaskByID(task.getId());
        Task task1 = manager.getTaskByID(task.getId());
        assertNull(task1, "Method deleteTaskByID does not work well");
    }

    @Test
    void deleteSubtaskByID() {
        manager.deleteSubtaskByID(subtask.getId());
        Subtask subtask1 = manager.getSubtaskByID(subtask.getId());
        assertNull(subtask1, "Method deleteSubtaskByID does not work well");
    }


    @Test
    void updateTaskStatus() {
        task.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, task.getStatus(), "Method updateTaskStatus  IN_PROGRESS does not work well");

    }

    @Test
    void taskDurationIsNotZero() {
        assertTrue(task.getDuration() > 0, "TASK Duration must be more than 0");
    }

    @Test
    void SubtaskDurationIsNotZero() {
        assertTrue(subtask.getDuration() > 0, "SUBTASK Duration must be more than 0");
    }


}