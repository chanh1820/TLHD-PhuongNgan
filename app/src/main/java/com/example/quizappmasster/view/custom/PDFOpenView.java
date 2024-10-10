package com.example.quizappmasster.view.custom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizappmasster.R;
import com.github.barteksc.pdfviewer.PDFView;


public class PDFOpenView extends AppCompatActivity {
    PDFView pdfDetailLyThuyet;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_open_pdf);
        Log.e("filename", getIntent().getStringExtra("file_name"));
        pdfDetailLyThuyet = findViewById(R.id.pdf_open_view);
        pdfDetailLyThuyet.fromAsset(getIntent().getStringExtra("file_name")).load();
    }
}