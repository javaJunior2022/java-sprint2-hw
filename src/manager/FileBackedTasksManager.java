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
    private static boolean initializationFlag = false;

    public FileBackedTasksManager(File file) {
        this(file, false);
    }


    public FileBackedTasksManager(File file, boolean load) {
        this.file = file;
        if (load) {
            initializationFlag=true;
            load();
            initializationFlag=false;
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
        Task t= super.getTaskByID(taskID);
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
     * Метод создания задачи из переданной строки
     *
     * @param value
     * @return
     */
    private Task fromString(String value) {
        final String[] field = value.split(",");
        return null;
    }


    private List<Integer> historyFromString(String value) {
        final String[] idList = value.split(",");
        List<Integer> list = new ArrayList<>();
        for (String id : idList) {
            list.add(Integer.parseInt(id));
        }
        return list;
    }

    /**
     * Сохранение данных в файл
     */
    private void save() {
        if (initializationFlag) {
            // в момент инициализации данных запись в файл не производится
            return;
        }

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
            try {
                throw new ManagerSaveException("Ошибка при сохранении истории в файл");
            } catch (ManagerSaveException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Выполняет парсинг строки и создает объекты в системе
     *
     * @param data
     */
    private void createNewTask(String[] data) {

        int id = Integer.parseInt(data[0]);
        String type = data[1];
        String name = data[2];
        String StatusString = data[3];
        String description = data[4];
        Status status = Status.getStatusByString(StatusString);


        if (type.equals("Task")) {
            Task task = new Task(name, description);
            task.setId(id);
            task.setStatus(status);
            addNewTask(task);

        } else if (type.equals("Epic")) {

            Epic epic = new Epic(name, description);
            epic.setId(id);
            epic.setStatus(status);
            addNewEpic(epic);

        } else if (type.equals("Subtask")) {
            int epicID = Integer.parseInt(data[5]);

            Subtask subtask = new Subtask(name, description);
            subtask.setEpicID(epicID);
            subtask.setId(id);
            subtask.setStatus(status);
            addNewSubtask(subtask);
        }


    }

    private void load() {
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();// заголовки пропускаем


            while (line != null) {
                line = bufferedReader.readLine();

                if (line == null) {
                    break;
                }

                String[] data = line.split(",");
                // вначале загружаем данные по задачам, эпикам и их подзадачам
                if (data.length == 5 | data.length == 6) {
                    createNewTask(data);
                }
                //История вызовов
                // парсим строку и записать данные в мапу
                if (line.isEmpty()) {
                    // история
                    line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }

                    String s[] = line.split(",");
                    for (int i = 0; i < s.length; i++) {
                        Integer historyid = Integer.parseInt(s[i]);
                        try {
                            Task task = findTaskByID(historyid);
                            if (task != null) {
                                historyManager.add(task);
                            }
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Ошибка при чтении истории из файла");
            } catch (ManagerSaveException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * загрузка данных из файла
     */
    public static FileBackedTasksManager loadFromFile(File file) {
        initializationFlag = true;
        final FileBackedTasksManager manager = new FileBackedTasksManager(file, true);
        initializationFlag = false;
           return manager;
    }

    public static void main(String[] args) {
        FileBackedTasksManager manager=new FileBackedTasksManager(new File("tasks.csv"),true);
//        Task task1 = new Task("task1_1", "description task1_1");
//        Task task2 = new Task("task2_1", "description task2_1");
//        Task task3 = new Task("task3_1", "description task3_1");
//
//        manager.addNewTask(task1);
//        manager.addNewTask(task2);
//        manager.addNewTask(task3);
//        manager.getTaskByID(1);



    }

}
