package manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTPTaskManager. Он будет наследовать от FileBackedTasksManager.
 * Конструктор HTTPTaskManager должен будет вместо имени файла принимать URL к серверу KVServer.
 * Также HTTPTaskManager создаёт KVTaskClient, из которого можно получить исходное состояние менеджера.
 * Вам нужно заменить вызовы сохранения состояния в файлах на вызов клиента.
 * В конце обновите статический метод getDefault() в утилитарном классе Managers,
 * чтобы он возвращал HTTPTaskManager.
 */
public class HTTPTaskManager extends FileBackedTasksManager {

    final KVTaskClient kvTaskClient;
    final private Gson gson;


    public HTTPTaskManager(String url) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(url);
        gson = Managers.getGson();
        load();
    }

    @Override
    protected void load() {
        loadTasks(kvTaskClient.load("TASKS"));
        loadEpics(kvTaskClient.load("EPICS"));
        loadSubtasks(kvTaskClient.load("SUBTASKS"));
        loadHistory(kvTaskClient.load("HISTORY"));
    }

    private void loadTasks(String jsonString) {
        if (!jsonString.isEmpty()) {
            HashMap<Integer, Task> restoredTasks =
                    gson.fromJson(jsonString,
                            new TypeToken<HashMap<Integer, Task>>() {
                            }.getType());
            tasks.putAll(restoredTasks);

            for (Map.Entry<Integer, Task> entry : restoredTasks.entrySet()) {
                prioritizedTasks.add(entry.getValue());
            }
        }
    }

    private void loadEpics(String jsonString) {
        if (!jsonString.isEmpty()) {
            HashMap<Integer, Epic> restoredEpics =
                    gson.fromJson(jsonString,
                            new TypeToken<HashMap<Integer, Epic>>() {
                            }.getType());
            epics.putAll(restoredEpics);
        }
    }

    private void loadSubtasks(String jsonString) {
        if (!jsonString.isEmpty()) {
            ArrayList<Subtask> restoredSubtasks =
                    gson.fromJson(jsonString, new TypeToken<ArrayList<Subtask>>() {
                    }.getType());

            for (Subtask subtask : restoredSubtasks) {
                Epic epic = getEpicByID(subtask.getEpicID());
                ArrayList<Subtask> list = epic.getSubtasks();
                if (!list.contains(subtask)) {
                    list.add(subtask);
                    epic.setSubtasks(list);
                    prioritizedTasks.add(subtask);
                }
            }
        }
    }

    private void loadHistory(String jsonString) {
        if (!jsonString.isEmpty()) {
            ArrayList<Task> restoredHistory =
                    gson.fromJson(jsonString,
                            new TypeToken<ArrayList<Task>>() {
                            }.getType());
             for (Task t:restoredHistory){
                 historyManager.add(t);
             }
        }
    }

    /**
     * Сохраняет данные на сервере
     */
    @Override
    protected void save() {

        try {
            kvTaskClient.put("TASKS", gson.toJson(tasks));
            kvTaskClient.put("EPICS", gson.toJson(epics));
            kvTaskClient.put("SUBTASKS", gson.toJson(getSubtasksList()));
            kvTaskClient.put("HISTORY", gson.toJson(getHistory()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }

    }
}