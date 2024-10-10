package com.example.quizappmasster.view.post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.PostDTO;
import com.example.quizappmasster.core.event.OnItemClickListener;
import com.example.quizappmasster.core.util.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ListPostAdapter extends RecyclerView.Adapter<ListPostAdapter.MyViewHolder> {

    Context context;
    List<PostDTO> items;
    OnItemClickListener listener;

    public ListPostAdapter(Context context, List<PostDTO> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public ListPostAdapter(Context context, List<PostDTO> items) {
        this.context = context;
        this.items = items;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnParent;
        TextView tvTile, tvCountLike, tvCountComment, tvAuthor, tvCreateDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lnParent = itemView.findViewById(R.id.ln_item_list_post);
            tvTile = itemView.findViewById(R.id.tv_item_list_post_title);
            tvCountLike = itemView.findViewById(R.id.tv_item_list_post_count_like);
            tvCountComment = itemView.findViewById(R.id.tv_item_list_post_count_comment);
            tvCreateDate = itemView.findViewById(R.id.tv_item_list_post_create_date);
            tvAuthor = itemView.findViewById(R.id.tv_item_list_post_author);
        }
        public void bindOnClickItem(final PostDTO item, final OnItemClickListener listener) {
            lnParent.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_post, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostDTO item = items.get(position);
        holder.bindOnClickItem(item, listener);

        holder.tvAuthor.setText(item.getAuthor());
        holder.tvCreateDate .setText(DateTimeUtils.convertLocalDateTimeToDateTime(item.getCreateDate()));
        holder.tvTile .setText(item.getTitle());
        holder.tvCountComment.setText(String.valueOf(item.getCountComment()));
        holder.tvCountLike.setText(String.valueOf(item.getCountInteract()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
