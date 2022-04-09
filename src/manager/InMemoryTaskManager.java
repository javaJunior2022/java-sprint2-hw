package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int id = 0;// уникальный нумератор
    private final HashMap<Integer, Task> tasks = new HashMap<>(); // Список задач
    private final HashMap<Integer, Epic> epics = new HashMap<>();// Список эпиков
    private HistoryManager historyManager = new InMemoryHistoryManager();

    /**
     * Получение списка всех подзадач
     *
     * @return
     */
    @Override
    public ArrayList<Subtask> getSubtasksList() {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        // добавляем все подзадачи из всех эпиков
        for (Map.Entry entry : epics.entrySet()) {
            Epic epic = (Epic) entry.getValue();
            subtasks.addAll(epic.getSubtasks());
        }
        return subtasks;
    }

    /**
     * Удаляет все подзадачи
     */
    @Override
    public void deleteAllSubtasks() {
        for (Map.Entry entry : epics.entrySet()) {
            Epic epic = (Epic) entry.getValue();
            epic.deleteSubtasks();
        }
    }

    /**
     * Добавляет новую подзадачу
     *
     * @param subtask
     * @return
     */
    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        subtask.setId(++id);
        subtask.setStatus(Status.NEW);
        Epic epic = getEpicByID(subtask.getEpicID());
        ArrayList<Subtask> subtasks = getSubtasksByEpic(epic);
        subtasks.add(subtask);
        epic.setSubtasks(subtasks);
        updateEpicStatus(epic);
        return subtask;

    }

    /**
     * Обновление статус подзадачи
     *
     * @param subtask
     * @param status
     */
    @Override
    public void updateSubtaskStatus(Subtask subtask, Status status) {
        subtask.setStatus(status);
        updateEpicStatus(getEpicByID(subtask.getEpicID()));
    }


    /**
     * Добавляет новый эпик
     *
     * @param epic
     * @return epic
     */
    @Override
    public Epic addNewEpic(Epic epic) {
        epic.setId(++id);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        return epic;
    }

    /**
     * Возвращает список всех эпиков
     *
     * @return epics
     */
    @Override
    public ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(epics.values());
    }

    /**
     * Возвращает эпик по идентификатору
     *
     * @param epicID
     * @return epic
     */
    @Override
    public Epic getEpicByID(int epicID) {
        Epic epic = epics.get(epicID);
        historyManager.add(epic);// добавление в историю задач
        return epic;
    }

    /**
     * Возвращает подзадачи по эпику
     *
     * @param epic
     * @return
     */
    @Override
    public ArrayList<Subtask> getSubtasksByEpic(Epic epic) {
        return new ArrayList<Subtask>(epic.getSubtasks());
    }

    /**
     * Возвращает подзадачи по ID эпика
     *
     * @param epicID
     * @return
     */
    @Override
    public ArrayList<Subtask> getSubtasksByEpicID(int epicID) {
        Epic epic = getEpicByID(epicID);

        if (epic != null) {
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            // добавить подзадачи в историю
            for (Subtask e : subtasks) {
                historyManager.add(e);
            }
            return subtasks;
        } else {
            return new ArrayList<Subtask>();
        }
    }

    @Override
    public Subtask getSubtaskByID(int subtaskID) {

        // делаем поиск в списке эпиков и их подзадач
        for (Map.Entry entry : epics.entrySet()) {
            Epic epic = (Epic) entry.getValue();
            Subtask subtask = epic.getSubtaskByID(subtaskID);
            historyManager.add(subtask);
        }
        return null;
    }


    /**
     * Обновление эпика
     *
     * @param epic
     */
    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            return;
        }
        epics.put(epic.getId(), epic);
    }

    /**
     * Удаление эпика по идентификатору
     *
     * @param epicID
     */
    @Override
    public void deleteEpicByID(int epicID) {
        Epic epic = epics.get(epicID);
        if (epic != null) {
            epics.remove(epicID);
        }
    }

    /**
     * выполняет расчет статуса эпика в зависимости от статусов подзадач
     *
     * @param epic
     */
    @Override
    public void updateEpicStatus(Epic epic) {
        /**
         если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
         если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
         во всех остальных случаях статус должен быть IN_PROGRESS.
         */

        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;
        Status status = null;

        ArrayList<Subtask> subtasks = getSubtasksByEpic(epic);

        if (subtasks == null) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == Status.NEW) {
                hasNew = true;
            }
            if (subtask.getStatus() == Status.DONE) {
                hasDone = true;
            }
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                hasInProgress = true;
            }
        }

        if (hasDone == true && hasNew == false && hasInProgress == false) {
            status = Status.DONE;
        } else if (hasNew == true && hasDone == false && hasInProgress == false) {
            status = Status.NEW;
        } else {
            status = Status.IN_PROGRESS;
        }
        epic.setStatus(status);
    }

    /**
     * Возвращает список всех задач
     *
     * @return tasks
     */
    @Override
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Удаляет все задачи
     */
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    /**
     * возвращает задачу по указанному ID
     *
     * @param taskID
     * @return model.Task
     */
    @Override
    public Task getTaskByID(int taskID) {
        Task task = tasks.get(taskID);
        historyManager.add(task);// добавление в историю задач
        return task;
    }

    /**
     * Добавляет новую задачу
     *
     * @param task
     * @return task
     */
    @Override
    public Task addNewTask(Task task) {
        task.setId(++id);
        task.setStatus(Status.NEW);
        tasks.put(task.getId(), task);
        return task;
    }

    /**
     * Удаляет задачу по указанному ID
     *
     * @param taskID
     */
    @Override
    public void deleteTaskByID(int taskID) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(taskID);
        }
    }

    /**
     * Обновление задачи
     *
     * @param task
     */
    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            return;
        }

        tasks.put(task.getId(), task);
    }


    /**
     * Обновление статус задачи
     *
     * @param task
     * @param status
     */
    @Override
    public void updateTaskStatus(Task task, Status status) {
        task.setStatus(status);
    }

}