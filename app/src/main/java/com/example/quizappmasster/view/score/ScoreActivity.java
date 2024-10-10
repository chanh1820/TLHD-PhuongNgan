package com.example.quizappmasster.view.score;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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
import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.dao.GeneralDAO;
import com.example.quizappmasster.core.dto.ScoreDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScoreActivity extends AppCompatActivity {
    GeneralDAO generalDAO;
    ScoreAdapter scoreAdapter ;
    ListView lvScore;
    List<ScoreDTO> listScoreDTO;

    String type;
    String userName;
    SharedPreferences accountCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        generalDAO = new GeneralDAO(getApplicationContext());
        lvScore= findViewById(R.id.lvScore);
        type = String.valueOf(getIntent().getIntExtra("type",0));
        accountCache = getSharedPreferences("account",MODE_PRIVATE);

        userName = accountCache.getString("user_name","");
//        listScoreDTO = generalDAO.findAllScore();
//        scoreAdapter=  new ScoreAdapter(getApplicationContext(),1,listScoreDTO);
//                   lvScore.setAdapter(scoreAdapter);

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Log.e("response",response);
                    listScoreDTO = objectMapper.readValue(response, new TypeReference<List<ScoreDTO>>(){});
                    scoreAdapter =  new ScoreAdapter(getApplicationContext(),1,listScoreDTO);
                    lvScore.setAdapter(scoreAdapter);
                   } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                List<ScoreDTO> list = new ArrayList<>();
                scoreAdapter=  new ScoreAdapter(getApplicationContext(),1,list);
                lvScore.setAdapter(scoreAdapter);

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>( );
                params.put("action","GET_SCORE");
                params.put("userName",userName);
                params.put("type",type);
                return params;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}