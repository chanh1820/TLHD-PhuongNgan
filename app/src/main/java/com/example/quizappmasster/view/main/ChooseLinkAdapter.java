package com.example.quizappmasster.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.LinkDTO;

import java.util.List;

public class ChooseLinkAdapter extends BaseAdapter {

    List<LinkDTO> items;
    Context context;

    public ChooseLinkAdapter(List<LinkDTO> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_link, parent, false);
        LinkDTO item = (LinkDTO) getItem(position);
        TextView tvName = rootView.findViewById(R.id.tv_settings_name);
        ImageView imvIcon = rootView.findViewById(R.id.imvIconSetting);
        tvName.setText(item.getTitle());
        imvIcon.setImageResource(item.getDrawable());
        return rootView;
    }
}
