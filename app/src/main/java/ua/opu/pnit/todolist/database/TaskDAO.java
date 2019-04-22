package ua.opu.pnit.todolist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ua.opu.pnit.todolist.Task;

@Dao
public interface TaskDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Delete
    void deleteNode(Task task);

    // Параметры должны совпадать
    @Query("SELECT * FROM tasks WHERE id=:id")
    Task getNoteById(long id);

    @Query("SELECT * FROM tasks ORDER BY date DESC")
    List<Task> getAll();

    @Query("DELETE FROM tasks")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM tasks")
    int getCount();
}
