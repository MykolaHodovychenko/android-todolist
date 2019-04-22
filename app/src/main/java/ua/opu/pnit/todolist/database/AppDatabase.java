package ua.opu.pnit.todolist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ua.opu.pnit.todolist.Task;

@Database(entities = {Task.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "appdb.db";
    private static volatile AppDatabase instance;

    private static final Object LOCK = new Object();

    // Метод возвращает объект интерфейса
    public abstract TaskDAO taskDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}