package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Status;
import model.Task;
import model.TypeTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    @Test
    void putAndLoad() throws IOException, InterruptedException {
        // starting server
        KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskServer httpTaskServer=new HttpTaskServer();


        KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078");

        Gson gson = getGson();
        // create a task
        Task task = new Task("task1", "desription",
                Status.NEW, LocalDateTime.now(), 15, TypeTask.TASK);
        String toJson = gson.toJson(task);
        kvTaskClient.put("TASKTEST", toJson);
        String fromJson = kvTaskClient.load("TASKTEST");



    }
    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new manager.LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

}