package com.example.quizappmasster.view.hethonglythuyet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.quizappmasster.R;
import com.github.barteksc.pdfviewer.PDFView;


public class DetailLyThuyetActivity extends AppCompatActivity {
    PDFView pdfDetailLyThuyet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ly_thuyet);
        Log.e("filename", getIntent().getStringExtra("file_name"));
        pdfDetailLyThuyet = findViewById(R.id.pdfView);
        pdfDetailLyThuyet.fromAsset(getIntent().getStringExtra("file_name")).load();
    }
}