package ua.opu.pnit.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ua.opu.pnit.todolist.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    private RecyclerView rv;
    private TaskAdapter adapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rv = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(manager);

        new Thread(() -> {
            db = AppDatabase.getInstance(this);
            updateAdapter();
        }).start();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            updateAdapter();
        }
    }

    private void updateAdapter() {
        new Thread(() -> {
            tasks = db.taskDAO().getAll();
            this.runOnUiThread(() -> {
                rv.setAdapter(new TaskAdapter(tasks, this));
            });
        }).start();
    }

    public void deleteTask(Task task) {
        new Thread(() -> {
            db.taskDAO().deleteNode(task);
            runOnUiThread(() -> {
                updateAdapter();
            });
        }).start();
    }

    // region Меню

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all) {

            new Thread(() -> {
                db.taskDAO().deleteAll();
                runOnUiThread(() -> {
                    updateAdapter();
                });
            }).start();

        }
        return super.onOptionsItemSelected(item);
    }
    // endregion
}
