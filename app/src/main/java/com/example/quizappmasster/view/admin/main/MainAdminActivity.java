package com.example.quizappmasster.view.admin.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.cache.AccountCache;
import com.example.quizappmasster.view.SignInActivity;
import com.example.quizappmasster.view.admin.danhsachdiem.ListScoreActivity;
import com.example.quizappmasster.view.main.MainActivity;

public class MainAdminActivity extends AppCompatActivity {

    private TextView tvLogOut;
    private Button btnListScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        initView();
        action();
        
    }

    private void initView() {
        tvLogOut = findViewById(R.id.tv_main_admin_logout);
        btnListScore = findViewById(R.id.btn_admin_main_danh_sach_diem);
    }

    private void action() {
        btnListScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainAdminActivity.this, ListScoreActivity.class);
                startActivity(i);
            }
        });
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainAdminActivity.this);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AccountCache.removeCache(getApplicationContext());
                        finishAffinity();
                        Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }
    
    
}