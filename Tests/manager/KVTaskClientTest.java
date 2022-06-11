package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Status;
import model.Task;
import model.TypeTask;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class KVTaskClientTest {

    @Test
    void putAndLoad() throws IOException, InterruptedException {
        // starting server
        KVServer kvServer = new KVServer();
        kvServer.start();

        KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078");

        Gson gson = Managers.getGson();
        // create a task
        Task task = new Task("task1", "desription",
                Status.NEW, LocalDateTime.now(), 15, TypeTask.TASK);
        String toJson = gson.toJson(task);
        kvTaskClient.put("TASKTEST", toJson);
        String fromJson = kvTaskClient.load("TASKTEST");

        assertEquals(toJson, fromJson, "KV server methods put and load do not work well");
        kvServer.stop();

    }
}