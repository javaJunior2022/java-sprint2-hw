package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Status;
import model.Task;
import model.TypeTask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;



/**
 * Данный класс KVTaskClient использует класс HTTPTaskManager
 * <p>
 * Конструктор принимает URL к серверу хранилища и регистрируется.
 * При регистрации выдаётся токен (API_TOKEN), который нужен при работе с сервером.
 */

public class KVTaskClient {
    private String API_TOKEN;
    private String url;

    public KVTaskClient(String url) {
        this.url = url;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/register"))
                .GET()
                .build();

        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                this.API_TOKEN = response.body();
            } else {
                System.out.println(response.statusCode());
            }
        } catch (NullPointerException | InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод String load(String key)
     * должен возвращать состояние менеджера задач через запрос GET /load/<ключ>?API_TOKEN=
     *
     * @param key
     * @return
     */
    String load(String key) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/load/" + key + "?API_TOKEN=" + API_TOKEN))
                .GET()
                .build();
        String stringResponse = new String();
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                stringResponse = response.body();
            } else {
                System.out.println(response.statusCode());
            }
        } catch (NullPointerException | InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
        return stringResponse;
    }

    /**
     * Метод void put(String key, String json)
     * олжен сохранять состояние менеджера задач
     * через запрос POST /save/<ключ>?API_TOKEN=
     *
     * @param key
     * @param json
     */
    void put(String key, String json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/save/" + key + "?API_TOKEN=" + API_TOKEN))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078");

        HTTPTaskManager httpTaskManager=new HTTPTaskManager("http://localhost:8078");
        Gson gson = getGson();
        Task task = new Task("task1", "desription",
                Status.NEW, LocalDateTime.now(), 15, TypeTask.TASK);
        kvTaskClient.put("task1", gson.toJson(task));

        Task task1 = new Task("task2", "desription",
                Status.NEW, LocalDateTime.now(), 15, TypeTask.TASK);
        kvTaskClient.put("task2", gson.toJson(task));
        System.out.println(kvTaskClient.load("task1"));
        String t2=kvTaskClient.load("task2");
        System.out.println(gson.fromJson(t2,Task.class));
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new manager.LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}
