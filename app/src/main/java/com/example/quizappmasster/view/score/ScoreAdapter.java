package com.example.quizappmasster.view.score;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.ScoreDTO;
import com.example.quizappmasster.core.dto.LyThuyetDTO;

import java.util.List;


public class ScoreAdapter extends ArrayAdapter<ScoreDTO> {
    public ScoreAdapter(@NonNull Context context, int resource, @NonNull List<ScoreDTO> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_score, parent, false);
        }
        TextView txtScore= convertView.findViewById(R.id.txtScore);
        TextView txtPlayerName= convertView.findViewById(R.id.txtPlayerName);
        TextView txtDate= convertView.findViewById(R.id.txtTime);

        ScoreDTO item = getItem(position);
        txtPlayerName.setText(item.getDisplayName());
        txtScore.setText(item.getPoint()+" Điểm");
        txtDate.setText(item.getCreatedDate());
        return convertView;
    }

}
