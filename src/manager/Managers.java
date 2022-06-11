package manager;/*
 *author s.timofeev 08.04.2022
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Managers {

    private static final HistoryManager inMemoryHistoryManager;
    private static final TaskManager inMemoryTaskManager;
    private static final FileBackedTasksManager fileBackedTasksManager;
    private static HTTPTaskManager httpTaskManager;


    static {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager();
        fileBackedTasksManager = new FileBackedTasksManager(new File("tasks.csv"));
        try {
            httpTaskManager = new HTTPTaskManager("http://localhost:8078");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static TaskManager getDefault() {
        // return inMemoryTaskManager;
        //return fileBackedTasksManager;
        return httpTaskManager;
    }

    public static TaskManager getInMemoryTaskManager() {
        return inMemoryTaskManager;
    }

    public static TaskManager getFileBackedTasksManager() {
        return fileBackedTasksManager;
    }

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            if (localDateTime == null) {
                jsonWriter.value("null");
                return;
            }
            jsonWriter.value(localDateTime.format(fmt));
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            final String text = jsonReader.nextString();
            if (text.equals("null")) {
                return null;
            }
            return LocalDateTime.parse(text, fmt);
        }
    }


}
