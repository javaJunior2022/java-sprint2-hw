package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

import static java.util.Collections.sort;

public class InMemoryTaskManager implements TaskManager {

    private int id = 0;// уникальный нумератор
    protected final HashMap<Integer, Task> tasks = new HashMap<>(); // Список задач
    protected final HashMap<Integer, Epic> epics = new HashMap<>();// Список эпиков
    protected final Set<Task> taskTreeSet = new TreeSet<Task>(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    });// отсортированный список задач

    protected HistoryManager historyManager = new InMemoryHistoryManager();


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
            ArrayList<Subtask> subtasks = getSubtasksByEpic(epic);
            while (subtasks.size() > 0) {
                historyManager.remove(subtasks.get(0).getId());
                subtasks.remove(0);
            }
            epic.setSubtasks(subtasks);
            updateEpicAdditionInformation(epic);
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
        subtask.setTypeTask();
        Epic epic = epics.get(subtask.getEpicID());
        ArrayList<Subtask> subtasks = getSubtasksByEpic(epic);
        subtasks.add(subtask);
        epic.setSubtasks(subtasks);
        updateEpicAdditionInformation(epic);
        taskTreeSet.add(subtask);
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
        Epic epic = epics.get(subtask.getEpicID());
        updateEpicAdditionInformation(epic);

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
        epic.setTypeTask();
        epics.put(epic.getId(), epic);
        updateEpicAdditionInformation(epic);
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

            if (!(subtask == null)) {
                historyManager.add(subtask);
                return subtask;
            }

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
            historyManager.remove(epicID);
            // удаление подзадач из истории
            for (Subtask subtask : epic.getSubtasks()) {
                historyManager.remove(subtask.getId());
            }
            epic.deleteSubtasks();
        }
    }

    /**
     * Продолжительность эпика — сумма продолжительности всех его подзадач.
     * Время начала — дата старта самой ранней подзадачи,
     * а время завершения — время окончания самой поздней из задач
     *
     * @param epic
     */
    private void updateEpicTimer(Epic epic) {
        ArrayList<Subtask> subtasks = getSubtasksByEpic(epic);
        List<LocalDateTime> startDates = new ArrayList<>();

        if (subtasks == null || subtasks.size() == 0) {
            return;
        }
        // получаю массив даты начала всех задач в эпике
        // а также определяю суммарную продолжительность
        long sumDuration = 0;
        for (Subtask subtask : subtasks) {
            startDates.add(subtask.getStartTime());
            sumDuration += subtask.getDuration();
        }

        // сортирую, определяю дату начала и дату окончания
        // работы с эпиком
        // продолжительность
        sort(startDates);
        if (startDates.size() > 0) {
            epic.setStartTime(startDates.get(0));
            epic.setEndTime(startDates.get(startDates.size() - 1));
            epic.setDuration(sumDuration);
        }
    }

    private void updateEpicAdditionInformation(Epic epic) {
        updateEpicTimer(epic);
        updateEpicStatus(epic);
    }

    /**
     * выполняет расчет статуса эпика в зависимости от статусов подзадач
     *
     * @param epic
     */
    private void updateEpicStatus(Epic epic) {
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

        if (subtasks == null || subtasks.size() == 0) {
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
     * Возвращает отсортированный список задач и подзадач
     *
     * @return
     */
    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(taskTreeSet);
    }

    /**
     * Возвращает список задач, у которых пересекается время выполнения
     *
     * @return
     */
    @Override
    public boolean timeIsCrossed(Task task) {
        List<Task> list = getPrioritizedTasks();
        boolean isOverlapped = false;
        for (int i = 0; i < list.size(); i++) {
            Task sortedTask = list.get(i);
            if (task == sortedTask) {
                continue;
            }
            long startDate1 = task.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long endDate1 = task.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long startDate2 = sortedTask.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long endDate2 = sortedTask.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            isOverlapped = intervalsOverlap(startDate1, endDate1, startDate2, endDate2);
            if (isOverlapped) {
                break;
            }
        }
        return isOverlapped;
    }

    public boolean intervalsOverlap(long from1, long to1, long from2, long to2) {
        return (from2 <= from1 && to2 > from1) || (from2 >= from1 && from2 < to1);
    }


    /**
     * Удаляет все задачи
     */
    @Override
    public void deleteAllTasks() {
        for (Map.Entry entry : tasks.entrySet()) {
            Task task = (Task) entry.getValue();
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    /**
     * Удаляет все эпики
     */
    @Override
    public void deleteAllEpics() {
        for (Map.Entry entry : epics.entrySet()) {
            Epic epic = (Epic) entry.getValue();
            historyManager.remove(epic.getId());
        }
        epics.clear();
        deleteAllSubtasks();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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
        if (task != null) {
            historyManager.add(task);// добавление в историю задач
        }
        return task;
    }

    /**
     * возвращает задачу по ID, вне зависимости от типа самой задачи
     *
     * @param taskID
     * @return
     */
    @Override
    public Task findTaskByID(int taskID) {
        Task task = tasks.get(taskID);
        Epic epic = epics.get(taskID);
        Subtask subtask = null;

        if (epic != null) {
            ArrayList<Subtask> subtasks = epic.getSubtasks();
            if (subtasks != null) {
                for (Subtask e : subtasks) {
                    if (e.getId() == taskID) {
                        subtask = e;
                        break;
                    }
                }
            }
        }

        if (task != null) {
            return task;
        } else if (epic != null) {
            return epic;
        } else if (subtask != null) {
            return subtask;
        }

        return null;
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
        task.setTypeTask();
        tasks.put(task.getId(), task);
        taskTreeSet.add(task);
        return task;
    }

    /**
     * Удаляет задачу по указанному ID
     *
     * @param taskID
     */
    @Override
    public void deleteTaskByID(int taskID) {
        Task task = tasks.get(taskID);
        if (task != null) {
            tasks.remove(taskID);
            historyManager.remove(taskID);
        }
    }

    @Override
    public void deleteSubtaskByID(int subtaskID) {
        for (Map.Entry entry : epics.entrySet()) {
            Epic epic = (Epic) entry.getValue();
            ArrayList<Subtask> subtasks = getSubtasksByEpic(epic);
            for (Subtask subtask : subtasks) {
                if (subtask.getId() == subtaskID) {
                    historyManager.remove(subtaskID);
                    subtasks.remove(subtask);
                    break;
                }
            }
            epic.setSubtasks(subtasks);
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
        taskTreeSet.add(task);
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

    /**
     * установка ID нумератора задач, необходима при загрузке данных из файла
     *
     * @param id
     */
    protected void setId(int id) {
        this.id = id;
    }

}
