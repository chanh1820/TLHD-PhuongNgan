package com.example.quizappmasster.view.hethonglythuyet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizappmasster.R;

public class ChooseSubjectActivity extends AppCompatActivity {
    Button btn_item_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject);
        btn_item_detail = findViewById(R.id.btn_item_detail);
        btn_item_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailLyThuyetActivity.class);
                startActivity(i);
            }
        });
    }
}