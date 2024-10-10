package com.example.quizappmasster.view.trochoi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.dto.CauDoDTO;

import java.util.List;

public class MainChoiGameActivity extends AppCompatActivity {
    private LinearLayout layout_play;
    private TextView tvStart;
    private ImageView imgLogo;
    private View layout;
    private Button btnPlay, btnChart;
    public static MediaPlayer mpBackground;
    public static Animation animation, animBtn, slide_up;
//    private List<User> listUser, listPlayer;
//    private UserDB userDB;
//    private CauDoDB cauDoDB;
    public static List<CauDoDTO> listQuestions;
//    private User user;
    private SharedPreferences sharedPreferences, sharedPreferencesUser;
    private int currentQuestion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choi_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        getData();
        imgLogo.startAnimation(animation);
        tvStart.startAnimation(animBtn);
        startMusicBackground(this);
        onClick();


    }

    private void onClick() {
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySound.playClick(v.getContext());
                PlaySound.animClick(v);
//                if (btnPlay.getText().equals("New Game")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("countCorrect", 0);
                    editor.putInt("currentQuestion",0);
                    editor.apply();
//                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonScaleAnim();
                        Intent intent = new Intent(MainChoiGameActivity.this, ChoiGameActivity.class);
                        //ResultingJSONstring: {"isAdmin":0,"name":"123","score":200}
//                        intent.putExtra("user", new User);
                        startActivity(intent);
                        finish();
                    }
                }, 600);
//                PlaySound.playClick(v.getContext());
//                PlaySound.animClick(v);
//                imgLogo.startAnimation(slide_up);
//                btnPlay.setText("New Game");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Animation slide_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
//                        Animation slide_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
//                        tvStart.setVisibility(View.GONE);
//                        layout_play.setVisibility(View.VISIBLE);
//                        btnPlay.startAnimation(slide_right);
//                        btnChart.startAnimation(slide_left);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                buttonScaleAnim();
//                            }
//                        }, 500);
//
//                    }
//                }, 600);

            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySound.playClick(v.getContext());
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySound.playClick(v.getContext());
                PlaySound.animClick(v);
                if (btnPlay.getText().equals("New Game")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("countCorrect", 0);
                    editor.putInt("currentQuestion",0);
                    editor.apply();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonScaleAnim();
                        Intent intent = new Intent(MainChoiGameActivity.this, ChoiGameActivity.class);
                        //ResultingJSONstring: {"isAdmin":0,"name":"123","score":200}
//                        intent.putExtra("user", new User);
                        startActivity(intent);
                        finish();
                    }
                }, 600);
            }
        });

//        btnChart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PlaySound.playClick(v.getContext());
//                PlaySound.animClick(v);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        buttonScaleAnim();
//                        dialog_Leaderboard();
//                    }
//                }, 500);
//            }
//        });

    }

    private void getData() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<CauDoDB> cauDoDBList = new ArrayList<>();
//        try {
//                cauDoDBList = objectMapper.readValue(DbContant.jsonDataDHBC, new TypeReference<List<CauDoDB>>() {
//                });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.e("cauDoDBList.size()",cauDoDBList.size()+"");

//        cauDoDB = new CauDoDB();
//        listQuestions = DBConstant.cauDoList;
//        userDB = new UserDB();
//        listUser = userDB.getDataUser();
        sharedPreferences = getSharedPreferences("currentQuestion", MODE_PRIVATE);
        sharedPreferencesUser = getSharedPreferences("user", MODE_PRIVATE);
//        user = new User(sharedPreferencesUser.getString("username", "null"));
        currentQuestion = sharedPreferences.getInt("currentQuestion", 0);

    }

    private void buttonScaleAnim() {
        btnPlay.startAnimation(animBtn);
        btnChart.startAnimation(animBtn);
    }

//    private List<User> getListPlayer(List<User> list) {
//        List<User> listPlayer = new ArrayList<>();
//        for (User user : list) {
//            if (user.getIsAdmin() == 0) {
//                listPlayer.add(user);
//            }
//        }
//        return listPlayer;
//    }

    private void init() {
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_logo);
        animBtn = AnimationUtils.loadAnimation(this, R.anim.anim_btn);
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        layout = findViewById(R.id.mainLayout);
        imgLogo = findViewById(R.id.imgLogo);
        tvStart = findViewById(R.id.tvStart);
        layout_play = findViewById(R.id.layout_play);
        btnChart = findViewById(R.id.btnLeaderBoard);
        btnPlay = findViewById(R.id.btnPlayGame);
    }

//    private void sapXep() {
//        Collections.sort(listPlayer, new Comparator<User>() {
//            @Override
//            public int compare(User o1, User o2) {
//                return Integer.compare(o2.getScore(), o1.getScore());
//            }
//        });
//    }


    private static void startMusicBackground(Context context) {
        mpBackground = MediaPlayer.create(context, R.raw.background);
        mpBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mpBackground.start();
                mpBackground.setLooping(true);
                mpBackground.setVolume(0.5f, 0.5f);
            }
        });
        mpBackground.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mpBackground.release();
            }
        });
    }

//    public static Animation getClickAnim(Context context) {
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_click);
//        return animation;
//    }

    @Override
    protected void onPause() {
        mpBackground.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mpBackground.start();
        getData();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void dialogCreateUser(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_inputname);
        Button btnOk;
        EditText edtName;
        btnOk = dialog.findViewById(R.id.btnOK);
        edtName = dialog.findViewById(R.id.edtUsername);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.windowAnimations = R.style.DialogAnimation;
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity || Gravity.CENTER == gravity || Gravity.TOP == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                PlaySound.playClick(v.getContext());
                PlaySound.animClick(v);
                SharedPreferences.Editor editor = sharedPreferencesUser.edit();
                editor.putString("username", name);
                editor.putInt("diem", 0);
                editor.apply();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

//    public void dialog_Leaderboard() {
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_leaderboard);
//        RecyclerView rcvUser;
//        rcvUser = dialog.findViewById(R.id.rcv_leaderboard);
//        Window window = dialog.getWindow();
//        if (window == null) {
//            return;
//        }
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        windowAttributes.windowAnimations = R.style.DialogAnimation;
//        windowAttributes.gravity = Gravity.CENTER;
//        window.setAttributes(windowAttributes);
//        dialog.setCancelable(true);
////        listPlayer = getListPlayer(listUser);
////        sapXep();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        rcvUser.setLayoutManager(layoutManager);
//        PlayerAdapter playerAdapter = new PlayerAdapter(listPlayer, this);
//        rcvUser.setAdapter(playerAdapter);
//        dialog.show();
//    }

}