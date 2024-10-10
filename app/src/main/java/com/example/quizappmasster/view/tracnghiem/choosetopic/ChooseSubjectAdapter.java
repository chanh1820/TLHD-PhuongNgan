package com.example.quizappmasster.view.tracnghiem.choosetopic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.SubjectDTO;

import java.util.List;

public class ChooseSubjectAdapter extends ArrayAdapter<SubjectDTO> {
    public ChooseSubjectAdapter(@NonNull Context context, int resource, @NonNull List<SubjectDTO> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_subject_trac_nghiem, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tv_subject_ly_thuyet_name);
        TextView tvTime = convertView.findViewById(R.id.tv_subject_ly_thuyet_time);
        TextView tvTotalQuestion = convertView.findViewById(R.id.tv_subject_ly_thuyet_total_question);

        SubjectDTO item = getItem(position);
        tvName.setText(item.getTopicName());
        tvTime.setText(String.valueOf(item.getMinutesOfNumber()));
        tvTotalQuestion.setText(item.getNumItem() + " câu");
        convertView.setTag(item);
        return convertView;
    }
}
