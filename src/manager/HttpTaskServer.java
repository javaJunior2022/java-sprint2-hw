package manager;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * класс HttpTaskServer слушает порт 8080 и принимает запросы.
 */
public class HttpTaskServer {

    private static final int PORT = 8080;
    public static TaskManager httpTaskManager;


    public HttpTaskServer() throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new requestHandler());
        httpServer.start();

        httpTaskManager = Managers.getDefault();

    }

    static class requestHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String method = httpExchange.getRequestMethod();

            switch (method) {
                case "POST" -> postHandle(httpExchange);
                case "GET" -> getHandle(httpExchange);
                case "DELETE" -> deleteHandle(httpExchange);
                default -> {
                    String response = "Method is not available";
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                }
            }
        }

        private static void getHandle(HttpExchange httpExchange) throws IOException {
            URI requestURI = httpExchange.getRequestURI();

            String path = requestURI.getPath();
            String[] splitStrings = path.split("/");
            String type = splitStrings[splitStrings.length - 1];
            String query = requestURI.getQuery();
            String[] splitParam;

            int id;
            String response;
            Gson gson = Managers.getGson();


            switch (type.toUpperCase()) {
                case "GETTASKS" -> response = gson.toJson(httpTaskManager.getTasksList());
                case "GETTASKBYID" -> {
                    splitParam = query.split("=");
                    id = Integer.parseInt(splitParam[1]);
                    response = gson.toJson(httpTaskManager.getTaskByID(id));
                }
                case "GETEPICS" -> response = gson.toJson(httpTaskManager.getEpicsList());
                case "GETEPICBYID" -> {
                    splitParam = query.split("=");
                    id = Integer.parseInt(splitParam[1]);
                    response = gson.toJson(httpTaskManager.getEpicByID(id));
                }
                case "GETSUBASKS" -> response = gson.toJson(httpTaskManager.getSubtasksList());
                case "GETSUBTASKBYID" -> {
                    splitParam = query.split("=");
                    id = Integer.parseInt(splitParam[1]);
                    response = gson.toJson(httpTaskManager.getSubtaskByID(id));
                }
                case "GETSUBTASKBYEPICID" -> {
                    splitParam = query.split("=");
                    id = Integer.parseInt(splitParam[1]);
                    response = gson.toJson(httpTaskManager.getSubtasksByEpicID(id));
                }
                case "GETHISTORY" -> response = gson.toJson(httpTaskManager.getHistory());
                case "GETPRIORITIZEDTASKS" -> response = gson.toJson(httpTaskManager.getPrioritizedTasks());
                default -> response = "Unknown request";
            }

            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private static void deleteHandle(HttpExchange httpExchange) throws IOException {
            URI requestURI = httpExchange.getRequestURI();

            String path = requestURI.getPath();
            String[] splitStrings = path.split("/");
            String type = splitStrings[splitStrings.length - 1];
            String query = requestURI.getQuery();
            String[] splitParam;

            int id;
            String response = "Success";

            switch (type.toUpperCase()) {
                case "DELETALLTASKS" -> httpTaskManager.deleteAllTasks();
                case "DELETALLSUBTASKS" -> httpTaskManager.deleteAllSubtasks();
                case "DELETALLEPICS" -> httpTaskManager.deleteAllEpics();
                case "DELETETASKBYID" -> {
                    splitParam = query.split("=");
                    id = Integer.parseInt(splitParam[1]);
                    httpTaskManager.deleteTaskByID(id);
                }
                case "DELETEEPICBYID" -> {
                    splitParam = query.split("=");
                    id = Integer.parseInt(splitParam[1]);
                    httpTaskManager.deleteEpicByID(id);
                }
                default -> response = "Unknown request";
            }

            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private static void postHandle(HttpExchange httpExchange) throws IOException {
            URI requestURI = httpExchange.getRequestURI();

            String path = requestURI.getPath();
            String[] splitStrings = path.split("/");
            String type = splitStrings[splitStrings.length - 1];
            String response = "Success";
            Gson gson = Managers.getGson();

            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), Charset.defaultCharset());

            switch (type.toUpperCase()) {
                case "ADDNEWTASK":
                    Task task = gson.fromJson(body, Task.class);
                    httpTaskManager.addNewTask(task);
                    break;
                case "ADDNEWEPIC":
                    Epic epic = gson.fromJson(body, Epic.class);
                    epic.setTypeTask(TypeTask.EPIC);
                    httpTaskManager.addNewEpic(epic);
                    break;
                case "ADDNEWESUBTASK":
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    httpTaskManager.addNewTask(subtask);
                case "UPDATETASK":
                    Task taskUpdate = gson.fromJson(body, Task.class);
                    httpTaskManager.updateTask(taskUpdate);
                    break;
                case "UPDATEEPIC":
                    Epic epicUpdate = gson.fromJson(body, Epic.class);
                    httpTaskManager.updateEpic(epicUpdate);
                    break;
                default:
                    response = "Unknown request";
            }

            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
