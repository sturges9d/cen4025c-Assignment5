package entity;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "displayTasks", query = "SELECT t FROM Tasks t")
@NamedQuery(name = "removeTask", query = "DELETE FROM Tasks t WHERE t.taskId= ?1")
public class Tasks {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "task_id")
    private int taskId;
    @Basic
    @Column(name = "task")
    private String task;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tasks tasks = (Tasks) o;

        if (taskId != tasks.taskId) return false;
        if (task != null ? !task.equals(tasks.task) : tasks.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskId;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return task;
    }
}
