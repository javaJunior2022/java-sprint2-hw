package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this(file, false);
    }


    public FileBackedTasksManager(File file, boolean load) {
        this.file = file;
        if (load) {
            load();
        }
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        Subtask s = super.addNewSubtask(subtask);
        save();
        return s;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        Epic e = super.addNewEpic(epic);
        save();
        return e;
    }

    @Override
    public Epic getEpicByID(int epicID) {
        Epic epic = super.getEpicByID(epicID);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskByID(int subtaskID) {
        Subtask subtask = super.getSubtaskByID(subtaskID);
        save();
        return subtask;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpicByID(int epicID) {
        super.deleteEpicByID(epicID);
        save();
    }

    @Override
    public Task addNewTask(Task task) {
        Task t = super.addNewTask(task);
        save();
        return t;
    }

    @Override
    public void deleteTaskByID(int taskID) {
        super.deleteTaskByID(taskID);
        save();
    }

    @Override
    public void deleteSubtaskByID(int subtaskID) {
        super.deleteSubtaskByID(subtaskID);
        save();
    }

    @Override
    public Task getTaskByID(int taskID) {
        Task t = super.getTaskByID(taskID);
        save();
        return t;
    }

    /**
     * Метод сохранения задачи в строку
     *
     * @param task - Task
     * @return строковое представление задачи
     */
    private String toString(Task task) {

        return String.join(",",
                String.valueOf(task.getId()),
                task.getClass().getSimpleName(),
                task.getName(),
                String.valueOf(task.getStatus()),
                task.getDescription());
    }

    /**
     * Метод сохранения подзадачи в строку
     *
     * @param task Task
     * @return values separated by the comma
     */
    private String toString(Subtask task) {

        return String.join(",",
                String.valueOf(task.getId()),
                task.getClass().getSimpleName(),
                task.getName(),
                String.valueOf(task.getStatus()),
                task.getDescription(),
                String.valueOf(task.getEpicID()));
    }

    /**
     * сохранение истории в файл
     *
     * @param manager map
     * @return values separated by the comma
     */
    private String toString(HistoryManager manager) {
        List<String> list = new ArrayList<>();

        for (Task t : manager.getHistory()) {
            list.add(String.valueOf(t.getId()));
        }
        return String.join(",", list);
    }


    /**
     * Сохранение данных в файл
     */
    private void save() {

        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            // заголовок
            bufferedWriter.append("id,type,name,status,description,epic");
            bufferedWriter.newLine();

            // tasks
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                bufferedWriter.append(toString(entry.getValue()));
                bufferedWriter.newLine();
            }

            // epics
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                Epic epic = entry.getValue();
                bufferedWriter.append(toString(entry.getValue()));
                bufferedWriter.newLine();

                for (Subtask subtask : getSubtasksByEpic(epic)) {
                    bufferedWriter.append(toString(subtask));
                    bufferedWriter.newLine();
                }
            }

            // пропуск 1 строка
            bufferedWriter.newLine();
            bufferedWriter.append(toString(historyManager));

            bufferedWriter.close();

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении истории в файл");
        }
    }

    /**
     * Выполняет парсинг строки и создает объекты в системе
     *
     * @param data создает/восстанавливает таски из строки
     */
    private void loadTaskFromFile(String[] data) {

        int id = Integer.parseInt(data[0]);
        String type = data[1];// данные по типу таска получаем из файла, обрабатывая соответствующий кейс
        String name = data[2];
        Status status = Status.getStatusByString(data[3]); // статус из файла приводим к перечислению
        String description = data[4];

        // в зависимсости от типа задачи, создаем объект
        // и добавляем их в соответствующие структуры для хранения
        switch (type) {
            case "Task" -> {
                Task task = new Task(id, name, description, status);
                tasks.put(id, task);
            }
            case "Epic" -> {
                Epic epic = new Epic(id, name, description, status);
                epics.put(id, epic);
            }
            case "Subtask" -> {
                int epicID = Integer.parseInt(data[5]);
                Subtask subtask = new Subtask(id, name, description, status, epicID);
                Epic epic = epics.get(epicID);
                // получаю список сабтасков по эпику и добавляю
                ArrayList<Subtask> subtasks = getSubtasksByEpic(epic);
                if (!subtasks.contains(subtask)) {
                    subtasks.add(subtask);
                }
            }
        }
    }

    /**
     * Загружает информацию по истории вызовов тасков
     *
     * @param line
     */
    private void loadHistoryFromFile(String line) {
        String[] s = line.split(",");
        for (String value : s) {
            int historyId = Integer.parseInt(value);
            try {
                Task task = findTaskByID(historyId);
                if (task != null) {
                    historyManager.add(task);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void load() {
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();// заголовки пропускаем

            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }

                String[] data = line.split(",");
                // вначале загружаем данные по задачам, эпикам и их подзадачам
                if (data.length == 5 || data.length == 6) {
                    loadTaskFromFile(data);
                }

                // в случае, если находим пустую строку, то следующей пойдет история вызовов
                if (line.isEmpty()) {
                    // история
                    line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    loadHistoryFromFile(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * загрузка данных из файла
     */
    public static void loadFromFile(File file) {
        final FileBackedTasksManager manager = new FileBackedTasksManager(file, true);
    }

    public static void main(String[] args) {
        loadFromFile(new File("tasks.csv"));
    }

}
