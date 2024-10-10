package com.example.quizappmasster.view.admin.danhsachdiem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.ScoreDTO;

import java.util.List;

public class ListScoreAdapter extends RecyclerView.Adapter<ListScoreAdapter.MyViewHolder> {

    Context context;
    List<ScoreDTO> items;

    public ListScoreAdapter(Context context, List<ScoreDTO> items) {
        this.context = context;
        this.items = items;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvStudentInfo, tvCreateDate, tvScore, tvIndex;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentInfo = itemView.findViewById(R.id.tv_item_score_student_info);
            tvCreateDate = itemView.findViewById(R.id.tv_item_score_time);
            tvScore = itemView.findViewById(R.id.tv_item_score_score_info);
            tvIndex = itemView.findViewById(R.id.tv_item_score_index);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_score, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScoreDTO item = items.get(position);
        holder.tvIndex.setText(String.valueOf(position + 1) );
        holder.tvStudentInfo.setText(item.getDisplayName() + " | " + item.getClassRoom());
        holder.tvCreateDate.setText(item.getCreatedDate());
        holder.tvScore.setText(item.getNumTrue() + " | " + item.getPoint() + " Điểm");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
