package com.example.quizappmasster.view.post.detailpost;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.PostCommentDTO;
import com.example.quizappmasster.core.dto.PostDTO;
import com.example.quizappmasster.core.event.OnItemClickListener;

import java.util.List;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.MyViewHolder> {

    Context context;
    List<PostCommentDTO> items;
    OnItemClickListener listener;

    public ListCommentAdapter(Context context, List<PostCommentDTO> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public ListCommentAdapter(Context context, List<PostCommentDTO> items) {
        this.context = context;
        this.items = items;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvAuthor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_item_list_comment_content);
            tvAuthor = itemView.findViewById(R.id.tv_item_list_comment_display_name);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_comment, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostCommentDTO item = items.get(position);
//        holder.bindOnClickItem(item, listener);
        holder.tvAuthor .setText(item.getDisplayName()+" :");
        holder.tvContent.setText(String.valueOf(item.getContent()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
