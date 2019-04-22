package ua.opu.pnit.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import ua.opu.pnit.todolist.database.AppDatabase;

public class TaskActivity extends AppCompatActivity {

    private AppDatabase db;

    private long id;

    private Task task;

    private boolean isNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        new Thread(() -> {
            db = AppDatabase.getInstance(this);
        }).start();

        initActivity();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText text = findViewById(R.id.task_text);
                task.setText(text.getText().toString());

                if (isNewNote)
                    task.setDate(new Date());

                new Thread(() -> {
                    db.taskDAO().insertNote(task);
                    setResult(RESULT_OK);
                    finish();
                }).start();
            }
        });
    }

    private void initActivity() {

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            setTitle("New note");
            isNewNote = true;

            task = new Task();
        } else {
            setTitle("Edit note");
            id = bundle.getLong("id");
            isNewNote = false;

            new Thread(() -> {
                task = db.taskDAO().getNoteById(id);

                runOnUiThread(() -> {
                    EditText text = findViewById(R.id.task_text);
                    text.setText(task.getText());
                });

            }).start();
        }
    }
}
