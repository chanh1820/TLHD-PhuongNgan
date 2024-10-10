package com.example.quizappmasster.view.hethonglythuyet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.dto.LyThuyetDTO;

import java.util.ArrayList;
import java.util.List;

public class ChooseHeThongLyThuyetActivity extends AppCompatActivity {
    ChooseHeThongLyThuyetAdapter chooseHeThongLyThuyetAdapter;
    ListView lvChooseLyThuyet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_he_thong_ly_thuyet);
        chooseHeThongLyThuyetAdapter = new ChooseHeThongLyThuyetAdapter(
                getApplicationContext(),
                1,
                fillLyThuyetBySubjectCode(DBConstant.listLyThuyetDTO, getIntent().getStringExtra("subject_code"))
                );
        lvChooseLyThuyet = findViewById(R.id.lv_choose_he_thong_ly_thuyet);
        lvChooseLyThuyet.setAdapter(chooseHeThongLyThuyetAdapter);
        lvChooseLyThuyet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ChooseHeThongLyThuyetActivity.this, DetailLyThuyetActivity.class);
                intent.putExtra("file_name",view.getTag().toString()) ;
                startActivity(intent);
            }
        });
    }

    private List<LyThuyetDTO> fillLyThuyetBySubjectCode(List<LyThuyetDTO> listLyThuyetDTO, String subjectCode) {
        List<LyThuyetDTO> result = new ArrayList<>();
        for (LyThuyetDTO item: listLyThuyetDTO         ) {
            if(item.getSubjectCode().equals(subjectCode)){
                result.add(item);
            }
        }
        return result;
    }


}