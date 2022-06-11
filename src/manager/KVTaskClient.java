package manager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Данный класс KVTaskClient использует класс HTTPTaskManager
 * <p>
 * Конструктор принимает URL к серверу хранилища и регистрируется.
 * При регистрации выдаётся токен (API_TOKEN), который нужен при работе с сервером.
 */

public class KVTaskClient {
    private String API_TOKEN;
    private final String url;

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
                throw new RuntimeException();
            }
        } catch (NullPointerException | InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод String load(String key)
     * должен возвращать состояние менеджера задач через запрос GET /load/<ключ>?API_TOKEN=
     *
     * @param key - key
     * @return stringResponse
     */
    String load(String key) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/load/" + key + "?API_TOKEN=" + API_TOKEN))
                .GET()
                .build();
        String stringResponse = "";
        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                stringResponse = response.body();
            } else {
                throw new RuntimeException();
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
     * @param key  - key
     * @param json -json string
     */
    void put(String key, String json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/save/" + key + "?API_TOKEN=" + API_TOKEN))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }
        } catch (NullPointerException | InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
