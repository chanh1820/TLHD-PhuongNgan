package com.example.quizappmasster.view.tracnghiem.testdone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.dto.SuggestResultDTO;
import com.example.quizappmasster.view.custom.PDFOpenView;

import java.util.ArrayList;
import java.util.List;

public class SuggestResultAdapter extends
        RecyclerView.Adapter<SuggestResultAdapter.ViewHolder> {

    private Context context;
    private List<SuggestResultDTO> list;

    public SuggestResultAdapter(Context context, List<SuggestResultDTO> list) {
        this.context = context;
        this.list = list;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvIcon;
        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvIcon = itemView.findViewById(R.id.imv_test_done_icon);
            tvTitle = itemView.findViewById(R.id.tv_test_done_title);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View heroView = inflater.inflate(R.layout.item_result_test_done, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuggestResultDTO item = list.get(position);
        if (item.getType().equals(DBConstant.TYPE_SUGGEST_RESULT_PDF)){
            holder.imvIcon.setImageResource(R.drawable.ic_book_psychological);
        }else if (item.getType().equals(DBConstant.TYPE_SUGGEST_RESULT_LINK)){
            holder.imvIcon.setImageResource(R.drawable.ic_link_website);
        }
        holder.tvTitle.setText(item.getName());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getType().equals(DBConstant.TYPE_SUGGEST_RESULT_PDF)) {
                    Intent i = new Intent(context, PDFOpenView.class);
                    i.putExtra("file_name",item.getValue()) ;
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (item.getType().equals(DBConstant.TYPE_SUGGEST_RESULT_LINK)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getValue()));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                }
            }
        });
        holder.imvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getType().equals(DBConstant.TYPE_SUGGEST_RESULT_PDF)) {
                    Intent i = new Intent(context, PDFOpenView.class);
                    i.putExtra("file_name",item.getValue()) ;
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (item.getType().equals(DBConstant.TYPE_SUGGEST_RESULT_LINK)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getValue()));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(browserIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
