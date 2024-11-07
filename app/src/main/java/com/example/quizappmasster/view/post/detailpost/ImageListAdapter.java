package com.example.quizappmasster.view.post.detailpost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.DBConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {

    private List<String> itemList;

    public ImageListAdapter(List<String> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String item = itemList.get(position);
        Picasso.get().load(DBConstant.URL_RESOURCE_FILE + item).into(holder.imvImage);

        // Load image if needed, e.g., using Glide or Picasso
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imvImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImage = itemView.findViewById(R.id.imv_item_image_content);
        }
    }
}