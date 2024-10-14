package com.example.quizappmasster.view.tracnghiem.testdone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizappmasster.R;
import com.example.quizappmasster.core.cache.AccountCache;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.constant.KeyConstants;
import com.example.quizappmasster.core.dto.AccountDTO;
import com.example.quizappmasster.core.dto.ResponseDTO;
import com.example.quizappmasster.core.dto.ScoreDTO;
import com.example.quizappmasster.core.dto.SubjectDTO;
import com.example.quizappmasster.core.dao.GeneralDAO;
import com.example.quizappmasster.core.dto.QuestionDTO;
import com.example.quizappmasster.core.service.GoogleSheetAPIService;
import com.example.quizappmasster.core.util.DateTimeUtils;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.core.util.TransformerUtils;
import com.example.quizappmasster.view.tracnghiem.slide.ScreenSlidePagerActivity;
import com.github.mikephil.charting.charts.PieChart;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class TestDoneActivity extends AppCompatActivity{
    private static float[] yData;
    private PieChart mChart;
    TextView tvPoint, tvNotAnswers, tvNotifi, tvSuggest;
    Button btnSave, btnAgain, btnExit;
    ImageView imvTestDone;

    RecyclerView rcSuggest;
    ArrayList<QuestionDTO> arr_QuesBegin = new ArrayList<QuestionDTO>();
    int point = 0;
    int numNotAnswers = 0;
    GeneralDAO generalDAO;
    AccountDTO accountDTO;
    String title = "";
    SubjectDTO subjectDTO;
    GoogleSheetAPIService googleSheetAPIService;

    SuggestResultAdapter suggestResultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done_2);

        generalDAO= new GeneralDAO(TestDoneActivity.this);
        accountDTO = AccountCache.getCache(getApplicationContext());
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        subjectDTO = (SubjectDTO) getIntent().getSerializableExtra(KeyConstants.INTENT_KEY_TOPIC_DTO);
        arr_QuesBegin = (ArrayList<QuestionDTO>) intent.getExtras().getSerializable(KeyConstants.INTENT_KEY_LIST_QUESTION);
        initView();
        action();
    }

    private void action() {
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                finish();
                Intent intent2= new Intent(TestDoneActivity.this, ScreenSlidePagerActivity.class);
                intent2.putExtra(KeyConstants.INTENT_KEY_LIST_QUESTION,arr_QuesBegin);
                intent2.putExtra(KeyConstants.INTENT_KEY_TOPIC_DTO, subjectDTO);
//                intent2.putExtra("test","no");
                startActivity(intent2);
            }

            private void refresh() {
                for(int i=0;i<arr_QuesBegin.size();i++){
                    arr_QuesBegin.get(i).setTraloi("");
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
        });
    }

    private void showResult(Integer point, Integer numNotAnswers) {
        tvPoint.setText(String.valueOf(point));
        tvNotAnswers.setText(String.valueOf(numNotAnswers));

    }

    public void initView() {
        tvPoint =  findViewById(R.id.tv_score_point);
        tvNotAnswers =  findViewById(R.id.tv_score_not_answers);
        btnAgain =  findViewById(R.id.btnAgain);
        btnSave =  findViewById(R.id.btnSaveScore);
        btnExit =  findViewById(R.id.btnExit);
        tvNotifi =  findViewById(R.id.notification_result);
        rcSuggest =  findViewById(R.id.rc_suggest_result);
        tvSuggest =  findViewById(R.id.tv_suggest_content);

        point = calculatorResult();
        numNotAnswers = calculatorNumNotAnswer();
        showResult(point, numNotAnswers);

        if(point>=90){
            tvSuggest.setText("Bạn thuộc nhóm người không có nguy cơ");
        }else if (point<90 && point>=75){
            tvSuggest.setText("Bạn thuộc nhóm người có nguy cơ");
        }else if (point<75 && point>=50){
            tvSuggest.setText("Bạn thuộc nhóm người có nguy cơ vừa");
        }else if (point<50 && point>=25){
            tvSuggest.setText("Bạn thuộc nhóm người có nguy cao");
        }else if (point<25){
            tvSuggest.setText("Bạn thuộc nhóm người có nguy rất cao");
        }

        suggestResultAdapter = new SuggestResultAdapter(getApplicationContext(), DBConstant.SUGGEST_DTOS_MAP.get(point));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcSuggest.setLayoutManager(llm);
        rcSuggest.setAdapter(suggestResultAdapter);
        saveScore();
    }

    public Integer calculatorResult() {
        Integer total = 0;
        Integer questionSize = arr_QuesBegin.size();
        QuestionDTO item;
        for (int i = 0; i < arr_QuesBegin.size(); i++) {
            item = arr_QuesBegin.get(i);
            total += Optional.ofNullable(item.getPointTraloi()).orElse(0);
        }

        return (int) total / questionSize;
    }
    public Integer calculatorNumNotAnswer() {
        Integer numNotAns = 0;
        QuestionDTO item;
        for (int i = 0; i < arr_QuesBegin.size(); i++) {
            item = arr_QuesBegin.get(i);
            if (StringUtils.isEmpty(item.getTraloi()) || item.getTraloi().equals("")){
                numNotAns ++;
            }
        }

        return numNotAns;
    }

void saveScore (){
    ScoreDTO scoreDTO = new ScoreDTO();
    scoreDTO.setUserName(accountDTO.getUserName());
    scoreDTO.setDisplayName(accountDTO.getDisplayName());
    scoreDTO.setClassRoom(accountDTO.getClassRoom());
    scoreDTO.setDescription(title);
    scoreDTO.setPoint(point);
    scoreDTO.setCreatedDateValue(DateTimeUtils.convertTimeToInteger(new Date().getTime()));
    scoreDTO.setCreatedDate(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy ").format(LocalDateTime.now()));
    scoreDTO.setType(DBConstant.DIEM_TRAC_NGHIEM_1);


    googleSheetAPIService = new GoogleSheetAPIService(getApplicationContext());
    StringRequest stringRequest =  new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("response", response);
            ResponseDTO responseDTO =  ObjectMapperUtils.stringToDTO(response, ResponseDTO.class);
            if(responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)){
                Toast.makeText(getApplicationContext(),"Lưu điểm thành công", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),"Lưu điểm thất bại", Toast.LENGTH_LONG).show();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();

        }
    }){
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return TransformerUtils.dtoToPayload(scoreDTO, GoogleSheetConstant.ACTION_SAVE_SCORE);
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(stringRequest);
}
}