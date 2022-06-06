package model;

import java.time.LocalDateTime;
import  java.util.Objects;
/**
 * Задача
 * У задачи есть следующие свойства:
 * Название, кратко описывающее суть задачи (например, «Переезд»).
 * Описание, в котором раскрываются детали.
 * Уникальный идентификационный номер задачи, по которому её можно будет найти.
 * Статус, отображающий её прогресс. Мы будем выделять следующие этапы жизни задачи:
 * NEW — задача только создана, но к её выполнению ещё не приступили.
 * IN_PROGRESS — над задачей ведётся работа.
 * DONE — задача выполнена.
 */

public class Task {
    private int id;
    private String name;


    private String description;
    private Status status;
    private TypeTask typeTask;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long duration;


    public Task(int id, String name, String description, Status status, TypeTask typeTask, LocalDateTime startTime, LocalDateTime endTime, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = typeTask;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plusMinutes(duration);

    }
    public Task(int id,String name, String description, Status status,  LocalDateTime startTime, long duration) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plusMinutes(duration);

    }


    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask=setTypeTask();
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask=setTypeTask();
    }



    public TypeTask getTypeTask() {
        return typeTask;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.typeTask=setTypeTask();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TypeTask setTypeTask() {
    TypeTask typeTask=TypeTask.TASK;
        if (getClass().getSimpleName().equals("Subtask")){
            typeTask=TypeTask.SUBTASK;
        }else if(getClass().getSimpleName().equals("Epic")){
            typeTask=TypeTask.EPIC;
        }
        return typeTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }

    @Override
    public String toString() {
        return "\n"+ "model.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }


}
