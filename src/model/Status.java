package model;

import java.util.Locale;

public enum Status {
    NEW,
    IN_PROGRESS,
    DONE;

    public static Status getStatusByString(String StatusString) {
        Status status = null;
        StatusString = StatusString.toUpperCase(Locale.ROOT);
        if (StatusString.equals("NEW")) {
            return Status.NEW;
        } else if (StatusString.equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        } else if (StatusString.equals("DONE")) {
            return Status.DONE;
        }
        return null;
    }
}


