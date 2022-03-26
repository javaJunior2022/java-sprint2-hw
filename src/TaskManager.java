import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskManager<epicSubtaskHashMap> {

    private static int id;// уникальный нумератор для всех типов задач/подзадач/эпиков
    private static HashMap<Integer, Task> tasks = new HashMap<>(); // Список задач
    private static HashMap<Integer, Subtask> subtasks = new HashMap<>(); //Cписок подздач
    private static HashMap<Integer, Epic> epics = new HashMap<>();// Список эпиков
    private static HashMap<Epic, HashMap<Integer, Subtask>> epicSubtaskHashMap = new HashMap<>(); //МАР с эпиками и их подзадачами


    /**
     * вовзращает список всех подзадач
     *
     * @return ArrayList
     */
    public static ArrayList<Subtask> getSubtasksList() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * удаляет все подзадачи
     */
    public static void deleteAllSubtask() {
        subtasks.clear();
    }

    /**
     * возвращает ссылку на подзадачу по указанному ID
     *
     * @param subTaskID
     * @return subtask
     */
    public static Subtask getSubtaskByID(int subTaskID) {
        return (subtasks.get(subTaskID));
    }

    /**
     * Добавляет новую подзадачу к указанному ID эпика
     *
     * @param epicID
     * @param subtask
     * @return subtask
     */
    public static Subtask addNewSubtask(int epicID, Subtask subtask) {
        subtask.id = ++id;
        subtask.status = Task.Status.NEW;

        subtasks.put(subtask.id, subtask);// добавляем подзадачу
        Epic epic = epics.get(epicID);// находим эпик по ID

        HashMap<Integer, Subtask> subtasksHashMap;
        subtasksHashMap = epicSubtaskHashMap.get(epic);

        if (subtasksHashMap == null) {
            subtasksHashMap = new HashMap<>();
        }
        subtasksHashMap.put(subtask.id, subtask);
        epicSubtaskHashMap.put(epic, subtasksHashMap);

        return subtask;
    }

    /**
     * Обновляет подзадачу
     * @param epic
     * @param subtaskID
     * @param subtask
     */
    public static void updateSubtask(Epic epic,int subtaskID,Subtask subtask) {
        subtasks.put(subtaskID,subtask);

        HashMap<Integer, Subtask> subtasksHashMap;
        subtasksHashMap = epicSubtaskHashMap.get(epic);

        if (subtasksHashMap == null) {
            subtasksHashMap = new HashMap<>();
        }
        subtasksHashMap.put(subtask.id, subtask);
        epicSubtaskHashMap.put(epic, subtasksHashMap);
        updateEpicStatus(epic);

    }

    /**
     * Удаляет подзадачу по ID
     *
     * @param subtaskID
     */
    public static void deleteSubtaskByID(int subtaskID) {
        subtasks.remove(subtaskID);
    }

    /**
     * Обновляет статус подзадачи
     *
     * @param subtask
     * @param status
     */
    public static void updateSubtaskStatus(Subtask subtask, Task.Status status) {

        if (subtask == null) return;
        subtask.status = status;
        Epic epic = getEpicBySubtask(subtask);
        updateEpicStatus(epic);
    }

    /**
     * возвращает ссылку на эпик в котором находится подзадача
     *
     * @param subtask
     * @return
     */
    private static Epic getEpicBySubtask(Subtask subtask) {

        for (Map.Entry entryEpic : epicSubtaskHashMap.entrySet()) {
            HashMap<Integer, Subtask> subtaskHahMap = epicSubtaskHashMap.get(entryEpic.getKey());
            for (Map.Entry entry : subtaskHahMap.entrySet()) {
                if (subtask.id == (int) entry.getKey()) {
                    return (Epic) entryEpic.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Возвращает список всех эпиков
     *
     * @return tasks
     */
    public static ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(epics.values());
    }

    /**
     * удаление всех эпиков,
     */
    public static void deleteAllEpics() {
        epics.clear();
    }

    /**
     * Возвращает ссылку на эпик по указанному ID
     *
     * @param epicID
     * @return epic
     */
    public static Epic getEpicByID(int epicID) {
        Epic epic = epics.get(epicID);
        return epic;
    }


    /**
     * Добавляет новый эпик
     *
     * @param epic
     * @return epic
     */
    public static Epic addNewEpic(Epic epic) {
        epic.id = ++id;
        epics.put(epic.id, epic);
        updateEpicStatus(epic);
        return epic;
    }

    /**
     * Удаляет эпик по ID
     *
     * @param epicID
     */
    public static void deleteEpicByID(int epicID) {
        epics.remove(epicID);

    }

    /**
     * Возвращает все подзадачи по указанному эпику
     *
     * @param epicID
     * @return ArrayList
     */
    public static ArrayList<Subtask> getSubtasksByEpicID(int epicID) {
        Epic epic = epics.get(epicID);
        HashMap<Integer, Subtask> subtasksHashMap = epicSubtaskHashMap.get(epic);
        return new ArrayList<>(subtasksHashMap.values());

    }

    /**
     * Автоматически рассчитывает статус эпика
     *
     * @param epic
     */
    public static void updateEpicStatus(Epic epic) {
        /**
         если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
         если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
         во всех остальных случаях статус должен быть IN_PROGRESS.
         */

        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;
        Task.Status status = null;

        if (epicSubtaskHashMap.get(epic) == null) {
            epic.status = Task.Status.NEW;
            return;
        }

        HashMap<Integer, Subtask> subtaskHahMap = epicSubtaskHashMap.get(epic);

        for (Map.Entry entry : subtaskHahMap.entrySet()) {
            Subtask subtask = (Subtask) entry.getValue();
            if (subtask.status == Task.Status.NEW) {
                hasNew = true;
            }
            if (subtask.status == Task.Status.DONE) {
                hasDone = true;
            }
            if (subtask.status == Task.Status.IN_PROGRESS) {
                hasInProgress = true;
            }
        }

        if (hasDone == true & hasNew == false & hasInProgress == false) {
            status = Task.Status.DONE;
        } else if (hasNew == true & hasDone == false & hasInProgress == false) {
            status = Task.Status.NEW;
        } else {
            status = Task.Status.IN_PROGRESS;
        }
        epic.status = status;
    }


    /**
     * Возвращает список всех задач
     *
     * @return tasks
     */
    public static ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Удаляет все задачи
     */
    public static void deleteAllTasks() {
        tasks.clear();
    }

    /**
     * возвращает задачу по указанному ID
     *
     * @param id
     * @return Task
     */
    public static Task getTaskByID(int id) {
        Task task = tasks.get(id);
        return task;
    }

    /**
     * Добавляет новую задачу, предварительно присваивая ей уникальный идентификатор
     *
     * @param task
     */
    public static Task addNewTask(Task task) {
        task.id = ++id;
        task.status = Task.Status.NEW;
        tasks.put(task.id, task);
        return task;
    }

    /**
     * Удаляет задачу по указанному ID
     *
     * @param taskID
     */
    public static void deleteTaskByID(int taskID) {
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
    public static void updateTask(Task task) {
        tasks.put(task.id, task);
    }


    /**
     * Возвращает статус эпика
     *
     * @param epic
     * @return Task.Status
     */
    public static Task.Status getEpicStatus(Epic epic) {
        return epic.status;
    }

    /**
     * Обновление статус задачи
     *
     * @param task
     * @param status
     */
    public void updateTaskStatus(Task task, Task.Status status) {
        task.status = status;
    }


}
