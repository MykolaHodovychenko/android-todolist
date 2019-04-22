package ua.opu.pnit.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);

        TaskViewHolder vh = new TaskViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.id.setText(String.valueOf(taskList.get(position).getId()));
        holder.text.setText(taskList.get(position).getText());

        holder.edit.setOnClickListener(v -> {
            Intent intent = new Intent(holder.edit.getContext(), TaskActivity.class);
            intent.putExtra("id", taskList.get(position).getId());
            AppCompatActivity activity = (AppCompatActivity) holder.edit.getContext();
            activity.startActivityForResult(intent, 100);
        });

        holder.delete.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) holder.edit.getContext();
            activity.deleteTask(taskList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView text;
        public ImageView edit;
        public ImageView delete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.index);
            text = itemView.findViewById(R.id.text);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
