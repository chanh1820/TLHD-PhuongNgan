package com.example.quizappmasster.view.tracnghiem.choosetopic;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.constant.KeyConstants;
import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.dto.QuestionDTO;
import com.example.quizappmasster.core.dto.SubjectDTO;
import com.example.quizappmasster.core.sco.QuestionSCO;
import com.example.quizappmasster.core.sco.TopicSCO;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.core.util.TransformerUtils;
import com.example.quizappmasster.core.view.DialogView;
import com.example.quizappmasster.view.score.ScoreActivity;
import com.example.quizappmasster.view.tracnghiem.slide.ScreenSlidePagerActivity;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ChooseSubjectActivity extends AppCompatActivity {
    ImageButton btnBack, btnShowScore;
    EditText edtSearch;
    Button btnSearch;
    ListView lvChooseSubjectTracNghiem;
    ChooseSubjectAdapter chooseSubjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosse_subject_trac_nghiem);
        initView();
        action();
    }

    private void fetchTopic(String name) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                List<SubjectDTO> subjectDTOList = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<List<SubjectDTO>>() {});
                chooseSubjectAdapter
                        = new ChooseSubjectAdapter(
                        getApplicationContext(),
                        1,
                        subjectDTOList
                );
                lvChooseSubjectTracNghiem.setAdapter(chooseSubjectAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Bạn chưa bật kết nối Internet ?", Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                TopicSCO topicSCO = new TopicSCO();
                topicSCO.setName(name);
                return TransformerUtils.dtoToPayload(topicSCO, GoogleSheetConstant.ACTION_SEARCH_TOPIC);
            }
        };

        RetryPolicy retryPolicy = new DefaultRetryPolicy(
                50000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void action() {
        lvChooseSubjectTracNghiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SubjectDTO item = (SubjectDTO) view.getTag();
                DialogView dialogView = new DialogView();
                dialogView.setDialog(new Dialog(ChooseSubjectActivity.this));
                dialogView.showDialogProcessBar();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialogView.dismissDialogProcessBar();
                        Log.e("response", response);
                        List<QuestionDTO> questionDTOList
                                = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<List<QuestionDTO>>() {
                        });
                        Intent intent = new Intent(ChooseSubjectActivity.this, ScreenSlidePagerActivity.class);
                        intent.putExtra(KeyConstants.INTENT_KEY_LIST_QUESTION, (Serializable) questionDTOList);
                        intent.putExtra(KeyConstants.INTENT_KEY_REDO_TEST, "yes");
                        intent.putExtra(KeyConstants.INTENT_KEY_TOPIC_DTO, item);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialogView.dismissDialogProcessBar();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        QuestionSCO questionSCO = new QuestionSCO();
                        questionSCO.setTopicCode(item.getTopicCode());
                        return TransformerUtils.dtoToPayload(questionSCO, GoogleSheetConstant.ACTION_FIND_QUESTION_BY_SUBJECT);                    }
                };

                RetryPolicy retryPolicy = new DefaultRetryPolicy(
                        50000,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(retryPolicy);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnShowScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseSubjectActivity.this, ScoreActivity.class);
                i.putExtra("type", DBConstant.DIEM_TRAC_NGHIEM_1);
                startActivity(i);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchTopic(edtSearch.getText().toString().trim());
            }
        });
    }

    private void initView() {
        btnBack = findViewById(R.id.btn_back_choose_subject_trac_nghiem);
        btnSearch = findViewById(R.id.btn_search_topic);
        edtSearch = findViewById(R.id.edt_input_search_topic);
        btnShowScore = findViewById(R.id.btn_show_score_1);
        lvChooseSubjectTracNghiem = findViewById(R.id.lv_choose_subject_trac_nghiem);
        fetchTopic("");
    }

}