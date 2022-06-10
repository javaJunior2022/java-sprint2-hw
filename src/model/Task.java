package model;

import java.time.LocalDateTime;
import java.util.Objects;

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
 * <p>
 * Добавьте новые поля в задачи:
 * duration — продолжительность задачи, оценка того, сколько времени она займёт в минутах (число);
 * startTime — дата, когда предполагается приступить к выполнению задачи.
 * getEndTime() — время завершения задачи, которое рассчитывается исходя из startTime и duration.
 * Менять сигнатуры методов интерфейса TaskManager не понадобится: при создании
 * или обновлении задач все его методы будут принимать и возвращать объект,
 * в который вы добавите два новых поля.
 * С классом Epic придётся поработать дополнительно.
 * Продолжительность эпика — сумма продолжительности всех его подзадач.
 * Время начала — дата старта самой ранней подзадачи,
 * а время завершения — время окончания самой поздней из задач.
 * Новые поля duration и startTime этого класса будут расчётные — аналогично полю статус.
 * Для реализации getEndTime() удобно добавить поле endTime в Epic
 * и рассчитать его вместе с другими полями.
 */

public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;
    private TypeTask typeTask;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected long duration;

    public Task(int id, String name, String description, Status status,
                LocalDateTime startTime, long duration, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.typeTask = TypeTask.TASK;
    }

    public Task(int id, String name, String description, Status status,
                LocalDateTime startTime, long duration, LocalDateTime endTime, TypeTask typeTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.typeTask = typeTask;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public long getDuration() {
        return duration;
    }


    public Task(String name, String description, Status status,
                LocalDateTime startTime, long duration, TypeTask typeTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plusMinutes(duration);
        this.typeTask = typeTask;

    }

    public Task(String name, String description, LocalDateTime startTime, long duration, TypeTask typeTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plusMinutes(duration);
        this.typeTask = typeTask;

    }

    public Task(int id, String name, String description, Status status,
                LocalDateTime startTime, long duration, TypeTask typeTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plusMinutes(duration);
        this.typeTask = typeTask;
    }

    public Task(String name, String description, TypeTask typeTask) {
        this.name = name;
        this.description = description;
        this.typeTask = typeTask;
    }

    public Task(int id, String name, String description, Status status, TypeTask typeTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeTask = typeTask;
    }

    public TypeTask getTypeTask() {
        return typeTask;
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

    public void setTypeTask(TypeTask typeTask) {
        this.typeTask = typeTask;
    }

    @Override
    public String toString() {
        return "\n" + "model.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
