package com.example.quizappmasster.core.service;

import android.content.Context;

import androidx.annotation.Nullable;

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
import com.example.quizappmasster.core.dao.GeneralDAO;
import com.example.quizappmasster.core.dto.QuestionDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleSheetAPIService {
    Context context;


    public GoogleSheetAPIService(Context context) {
        this.context = context;
    }

    public void syncQuestionDTO(Context context) {
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<QuestionDTO> listQuestionDTO = objectMapper.readValue(response, new TypeReference<List<QuestionDTO>>(){});
                    GeneralDAO generalDAO = new GeneralDAO(context);
//                    generalDAO.saveAllQuestionDTO(listQuestionDTO, context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>( );
                params.put("action","GET_ALL");
                return params;
            }
        };

        RetryPolicy retryPolicy = new DefaultRetryPolicy(
                50000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

//    public synchronized VersionDTO checkVersion() {
//
//        return listVersionDTO.get(0);
//    }
}
