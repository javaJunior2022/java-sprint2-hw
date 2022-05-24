package model;

import java.util.Locale;

public enum TypeTask {
    TASK,
    SUBTASK,
    EPIC;

    public static TypeTask getTypeTaskByString(String typeTaskString) {
        Status status = null;
        typeTaskString = typeTaskString.toUpperCase(Locale.ROOT);
        if (typeTaskString.equals("TASK")) {
            return TypeTask.TASK;
        } else if (typeTaskString.equals("SUBTASK")) {
            return TypeTask.SUBTASK;
        } else if (typeTaskString.equals("EPIC")) {
            return TypeTask.EPIC;
        }
        return null;
    }
}
