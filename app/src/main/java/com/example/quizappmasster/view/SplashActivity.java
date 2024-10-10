package com.example.quizappmasster.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.cache.AccountCache;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.dto.AccountDTO;
import com.example.quizappmasster.core.util.NotifyUtils;
import com.example.quizappmasster.view.admin.danhsachdiem.ListScoreActivity;
import com.example.quizappmasster.view.admin.main.MainAdminActivity;
import com.example.quizappmasster.view.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    AccountDTO accountDTO = new AccountDTO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }

    private void nextActivity() {
        accountDTO = AccountCache.getCache(getApplicationContext());
        Intent intent = new Intent();
        if(accountDTO.getFlagLogin().equals(GoogleSheetConstant.FLAG_NONE_LOGIN)){
            intent = new Intent(SplashActivity.this, SignInActivity.class);
        }else {
            switch (accountDTO.getRole()){
                case GoogleSheetConstant.ACCOUNT_TYPE_USER:
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    break;
                case  GoogleSheetConstant.ACCOUNT_TYPE_ADMIN:
                    intent = new Intent(SplashActivity.this, MainAdminActivity.class);
                    break;
                default:
                    NotifyUtils.defaultNotify(getApplicationContext(), "Tài khoản không hợp lệ");
                    break;
            }
        }
        startActivity(intent);
        finish();
    }
}