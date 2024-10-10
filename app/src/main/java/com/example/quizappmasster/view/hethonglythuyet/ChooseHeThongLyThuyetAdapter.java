package com.example.quizappmasster.view.hethonglythuyet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.LyThuyetDTO;
import com.example.quizappmasster.core.dto.SubjectLyThuyetDTO;

import java.util.List;

public class ChooseHeThongLyThuyetAdapter extends ArrayAdapter<LyThuyetDTO> {
    public ChooseHeThongLyThuyetAdapter(@NonNull Context context, int resource, @NonNull List<LyThuyetDTO> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_subject_ly_thuyet, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tv_subject_ly_thuyet_name);
//        TextView tvSubject = convertView.findViewById(R.id.tv_subject_ly_thuyet_subject);
        LinearLayout lnItem = convertView.findViewById(R.id.ln_item_subject_ly_thuyet);

        LyThuyetDTO item = getItem(position);

        tvName.setText(item.getName());
//        tvSubject.setText(item.getSubject());
        lnItem.setTag(item.getFileName());
        return convertView;
    }
}
