package com.example.quizappmasster.view.tracnghiem.slide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.KeyConstants;
import com.example.quizappmasster.core.dao.GeneralDAO;
import com.example.quizappmasster.core.dto.QuestionDTO;
import com.example.quizappmasster.core.dto.SubjectDTO;
import com.example.quizappmasster.view.tracnghiem.testdone.TestDoneActivity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 20;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    TextView tvTimer, txtKiemTra, tvXemDiem, txtCurrentPosition, tvTitle, tvBack ;
    String test = "";

    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime;
    GeneralDAO generalDAO;
    ArrayList<QuestionDTO> questionDTOList;
    CounterClass timer;
    SubjectDTO subjectDTO;
    public int checkAns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_pager);
        initView();

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExit();
            }
        });
        txtCurrentPosition = findViewById(R.id.txtCurentPosition);

        txtKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        tvXemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent(ScreenSlidePagerActivity.this, TestDoneActivity.class);
                intent1.putExtra(KeyConstants.INTENT_KEY_LIST_QUESTION, questionDTOList);
                intent1.putExtra("time", convertSecondToMinute(String.valueOf(Duration.between(startTime, endTime).getSeconds()))+ " giây");
                intent1.putExtra("title", subjectDTO.getTopicName());
                intent1.putExtra(KeyConstants.INTENT_KEY_TOPIC_DTO, subjectDTO);
                startActivity(intent1);
            }
        });

    }

    private void initView() {
        txtKiemTra = findViewById(R.id.tvKiemTra);
        tvTimer = findViewById(R.id.tvTimer);
        tvXemDiem = findViewById(R.id.tvScore);
        tvTitle = findViewById(R.id.tv_quiz_title);
        tvXemDiem.setVisibility(View.GONE);
        txtKiemTra.setVisibility(View.VISIBLE);
        tvBack = findViewById(R.id.tv_back);
        generalDAO = new GeneralDAO(this);

        test = getIntent().getStringExtra(KeyConstants.INTENT_KEY_REDO_TEST);
        subjectDTO = (SubjectDTO) getIntent().getSerializableExtra(KeyConstants.INTENT_KEY_TOPIC_DTO);
        tvTitle.setText(subjectDTO.getTopicName());
        timer = new CounterClass(subjectDTO.getMinutesOfNumber() * 60 * 1000, 1000);
        timer.start();
        questionDTOList = (ArrayList<QuestionDTO>) getIntent().getExtras().getSerializable(KeyConstants.INTENT_KEY_LIST_QUESTION);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                txtCurrentPosition.setText(position + 1 + "/"+questionDTOList.size());

            }

        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            dialogExit();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public ArrayList<QuestionDTO> getData() {
        return questionDTOList;
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ScreenSlidePageFragment.create(position, checkAns);
        }

        @Override
        public int getItemCount() {
            return questionDTOList.size();
        }
    }

    public class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    public void checkAnswer() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_answer_dialog);
        dialog.setTitle("Danh sách câu trả lời");
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        // truyền giá arr_Ques cho dialog
        CheckAnswerAdapter answerAdapter = new CheckAnswerAdapter(questionDTOList, this);
        GridView gvLsQuestion = dialog.findViewById(R.id.gvLsQuestion);
        gvLsQuestion.setAdapter(answerAdapter);

        gvLsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //chuyển trang đến vị trí thứ position
                viewPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });
        Button btnCancel, btnFinish;
        btnCancel = dialog.findViewById(R.id.btn_Cancel);
        btnFinish = dialog.findViewById(R.id.btn_Finish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                endTime= LocalDateTime.now();
                result();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void result() {
        checkAns = 1;// thay đổi bên fragment
        if (viewPager.getCurrentItem() >= 5) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 4);
        } else if (viewPager.getCurrentItem() <= 5) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 4);
        }
            tvXemDiem.setVisibility(View.VISIBLE);// hiện lên
        txtKiemTra.setVisibility(View.GONE);// ẩn đi
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");
            Toast.makeText(ScreenSlidePagerActivity.this, "Đã hết giờ làm bài", Toast.LENGTH_LONG);
            result();

        }
    }
    public void dialogExit(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(ScreenSlidePagerActivity.this);
        builder.setIcon(R.drawable.exit);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thoát hay không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }


    // Phương thức xoa database viết vào hàm MainActivity
    //        try {
//            db.deleteDataBase();
//            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "bi loi rui", Toast.LENGTH_SHORT).show();
//        }

    public String convertSecondToMinute(String number){
        int n= Integer.parseInt(number);
        int minute, second;
        minute = n % 3660 / 60;
        second = n % 3600 % 60;
        return String.valueOf(minute+":"+second);
    }
}