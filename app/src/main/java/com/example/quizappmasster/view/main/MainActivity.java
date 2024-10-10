package com.example.quizappmasster.view.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.cache.AccountCache;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.DBHelper;
import com.example.quizappmasster.core.constant.KeyConstants;
import com.example.quizappmasster.core.dto.AccountDTO;
import com.example.quizappmasster.core.security.SecurityConfig;
import com.example.quizappmasster.core.service.GoogleSheetAPIService;
import com.example.quizappmasster.view.SignInActivity;
import com.example.quizappmasster.view.hethonglythuyet.ChooseHeThongLyThuyetActivity;
import com.example.quizappmasster.view.post.ListPostActivity;
import com.example.quizappmasster.view.tracnghiem.choosetopic.ChooseSubjectActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnKhanCap, btnTracNghiem, btnThaoLuan;
    TextView tvInfo, tvLogOut;
    AccountDTO accountDTO;
    GoogleSheetAPIService googleSheetAPIService;

    ChooseLinkAdapter chooseLinkAdapter;

    ChooseLyThuyetAdapter chooseLyThuyetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SecurityConfig.firebase(getApplicationContext());
        initView();
        actions();
    }

    private void actions() {
        btnTracNghiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChooseSubjectActivity.class);
                startActivity(i);
            }
        });
        btnThaoLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListPostActivity.class);
                i.putExtra(KeyConstants.INTENT_KEY_TOPIC_CODE, DBConstant.TOPIC_CODE_1);
                startActivity(i);
            }
        });
        btnKhanCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListPostActivity.class);
                i.putExtra(KeyConstants.INTENT_KEY_TOPIC_CODE, DBConstant.TOPIC_CODE_2);
                startActivity(i);
            }
        });

//        btnTroChoi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), MainChoiGameActivity.class);
//                startActivity(i);
//            }
//        });

        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_link);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setAttributes(lp);
                dialog.setTitle("Các trang web");
                dialog.show();
                chooseLinkAdapter = new ChooseLinkAdapter(DBConstant.linkDTOList, getApplicationContext());
                GridView gvListSession = dialog.findViewById(R.id.gv_list_session);
                gvListSession.setAdapter(chooseLinkAdapter);
                gvListSession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openBrowswer(DBConstant.linkDTOList.get(position).getUrl());
                        dialog.dismiss();
                    }
                });
            }
        });

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    private void openBrowswer(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void initView() {
        DBHelper db = new DBHelper(this);
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnTracNghiem = findViewById(R.id.btn_trac_nghiem);
        btnKhanCap = findViewById(R.id.btn_tin_khan_cap);
        btnThaoLuan = findViewById(R.id.btn_thao_luan);
        tvInfo = findViewById(R.id.tv_main_link);
        tvLogOut = findViewById(R.id.tv_main_logout);
    }
}