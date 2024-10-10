package com.example.quizappmasster.view.admin.danhsachdiem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.dto.ScoreDTO;
import com.example.quizappmasster.core.sco.ScoreSCO;
import com.example.quizappmasster.core.util.NotifyUtils;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.core.util.TransformerUtils;
import com.example.quizappmasster.core.util.ValidateUtils;
import com.example.quizappmasster.view.score.ScoreAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListScoreActivity extends AppCompatActivity {
    EditText edtName, edtMinScore, edtMaxScore;
    Button btnSearch;
    ImageButton imbDeleteText;
    Spinner spnClassRoom;
    RecyclerView rvListScore;
    ScoreSCO scoreSCO
            = new ScoreSCO("", "", 0, 10, DBConstant.DIEM_TRAC_NGHIEM_1, 0);

    SPNChooseClassAdapter spnChooseClassAdapter;
    ListScoreAdapter listScoreAdapter;
    Context context;

    List<ScoreDTO> scoreDTOList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_score);
        initView();
        action();
    }

    private void action() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = StringUtils.isNotBlank(edtName.getText().toString().trim()) ? edtName.getText().toString().trim() : "";
                String minScore
                        = StringUtils.isNotBlank(edtMinScore.getText().toString().trim()) && NumberUtils.isNumber(edtMinScore.getText().toString().trim())
                        ? edtMinScore.getText().toString().trim() : null;
                String maxScore
                        = StringUtils.isNotBlank(edtMaxScore.getText().toString().trim()) && NumberUtils.isNumber(edtMaxScore.getText().toString().trim())
                        ? edtMaxScore.getText().toString().trim() : null;

                scoreSCO.setName(name);
                scoreSCO.setMinScore(Integer.valueOf(minScore));
                scoreSCO.setMaxScore(Integer.valueOf(maxScore));
                Boolean idValid = ValidateUtils.isValidScoreSCO(scoreSCO, getApplicationContext());
                if(idValid){
                    searchScore(scoreSCO);
                }
            }
        });
        imbDeleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.clearFocus();
                edtName.getText().clear();
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(StringUtils.isNotBlank(edtName.getText().toString())){
                    imbDeleteText.setVisibility(View.VISIBLE);
                }else {
                    imbDeleteText.setVisibility(View.INVISIBLE);
                }
            }
        });
        spnClassRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    scoreSCO.setClassRoom("");
                } else {
                    scoreSCO.setClassRoom(DBConstant.LIST_CLASS_ROOM.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView() {
        context = getApplicationContext();
        edtName = findViewById(R.id.edt_liss_score_studen_name);
        edtMinScore = findViewById(R.id.edt_list_score_min_score);
        edtMaxScore = findViewById(R.id.edt_list_score_max_score);
        spnClassRoom = findViewById(R.id.spn_list_code_class_room);
        btnSearch = findViewById(R.id.btn_list_score_search);
        rvListScore = findViewById(R.id.rv_list_score);
        imbDeleteText = findViewById(R.id.imb_list_score_delete_text);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvListScore.setLayoutManager(llm);

        spnChooseClassAdapter = new SPNChooseClassAdapter(context, DBConstant.LIST_CLASS_ROOM);
        spnClassRoom.setAdapter(spnChooseClassAdapter);
        searchScore(scoreSCO);
    }

    private void searchScore(ScoreSCO scoreSCO) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                scoreDTOList = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<List<ScoreDTO>>() {
                });
                listScoreAdapter = new ListScoreAdapter(getApplicationContext(), scoreDTOList);
                NotifyUtils.defaultNotify(getApplicationContext(), scoreDTOList.size()+"");
                rvListScore.setAdapter(listScoreAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                scoreDTOList = new ArrayList<>();
                listScoreAdapter = new ListScoreAdapter(getApplicationContext(), scoreDTOList);
                rvListScore.setAdapter(listScoreAdapter);

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return TransformerUtils.dtoToPayload(scoreSCO, GoogleSheetConstant.ACTION_SEARCH_SCORE);
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
}