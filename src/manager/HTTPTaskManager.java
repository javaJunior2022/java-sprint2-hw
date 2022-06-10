package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.IOException;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;

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
        gson = getGson();
        restoreDataFromServer();
    }

    protected void restoreDataFromServer() {
        restoreTasks(kvTaskClient.load("TASKS"));
        restoreEpics(kvTaskClient.load("EPICS"));
        restoreSubtasks(kvTaskClient.load("SUBTASKS"));
        restoreHistory(kvTaskClient.load("HISTORY"));
    }

    private void restoreTasks(String jsonString) {
        if (!jsonString.isEmpty()) {
            HashMap<Integer, Task> restoredTasks =
                    gson.fromJson(jsonString,
                            new TypeToken<HashMap<Integer, Task>>() {
                            }.getType());
            tasks.putAll(restoredTasks);
        }
    }

    private void restoreEpics(String jsonString) {
        if (!jsonString.isEmpty()) {
            HashMap<Integer, Epic> restoredEpics =
                    gson.fromJson(jsonString,
                            new TypeToken<HashMap<Integer, Epic>>() {
                            }.getType());
            epics.putAll(restoredEpics);
        }
    }

    private void restoreSubtasks(String jsonString) {
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
                }
            }
        }
    }

    private void restoreHistory(String jsonString) {
        //to do
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
            e.printStackTrace();
        }

    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new manager.LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}