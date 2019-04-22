package ua.opu.pnit.todolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String text;
    private Date date;

    public Task(long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    @Ignore
    public Task(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    @Ignore
    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
