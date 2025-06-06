package com.example.quizappmasster.view.post;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizappmasster.R;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.constant.KeyConstants;
import com.example.quizappmasster.core.dto.PostDTO;
import com.example.quizappmasster.core.dto.QuestionDTO;
import com.example.quizappmasster.core.dto.ResponseDTO;
import com.example.quizappmasster.core.dto.SubjectDTO;
import com.example.quizappmasster.core.event.OnItemClickListener;
import com.example.quizappmasster.core.sco.BaseSCO;
import com.example.quizappmasster.core.sco.PostSCO;
import com.example.quizappmasster.core.sco.QuestionSCO;
import com.example.quizappmasster.core.sco.TopicSCO;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.core.util.TransformerUtils;
import com.example.quizappmasster.core.view.DialogView;
import com.example.quizappmasster.view.post.dangtin.SavePostActivity;
import com.example.quizappmasster.view.post.detailpost.DetailPostActivity;
import com.example.quizappmasster.view.score.ScoreActivity;
import com.example.quizappmasster.view.tracnghiem.slide.ScreenSlidePagerActivity;
import com.fasterxml.jackson.core.type.TypeReference;

import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListPostActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnAddPost;
    RecyclerView rvListPost;
    ListPostAdapter listPostAdapter;

    String topicCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);
        initView();
        action();
    }

    private void fetchPost(String topicCode) {
        Log.e("topicCode", topicCode);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("limit", 30);
        jsonParams.put("deleteFlag", false);
        jsonParams.put("topicCode", topicCode);
        StringRequest request = new StringRequest(Request.Method.POST, "http://160.191.175.200:8103/post/search_post", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                ;
                ResponseDTO<List<PostDTO>> responseDTO = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<ResponseDTO<List<PostDTO>>>() {
                });
                List<PostDTO> postDTOS = new ArrayList<>();
                for (PostDTO postDTO : responseDTO.getData()) {
                    if (postDTO.getTopicCode().equals(topicCode)) {
                        postDTOS.add(postDTO);
                    }
                }
                listPostAdapter
                        = new ListPostAdapter(
                        getApplicationContext(),
                        postDTOS,
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object item) {
                                PostDTO postDTO = (PostDTO) item;
                                Intent i = new Intent(ListPostActivity.this, DetailPostActivity.class);
                                i.putExtra(KeyConstants.INTENT_KEY_POST_ID, postDTO.getId());
                                startActivity(i);
                            }
                        }
                );

                rvListPost.setAdapter(listPostAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Log.e("NetworkResponse", response.headers.toString());
                    // Parse the response using the correct encoding
                    String charset = HttpHeaderParser.parseCharset(response.headers);
                    String parsed = new String(response.data, "UTF-8");
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            public byte[] getBody() {
                return new JSONObject(jsonParams).toString().getBytes();
            }

            public String getBodyContentType() {
                return "application/json;charset=UTF-8";
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void action() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListPostActivity.this, SavePostActivity.class);
                i.putExtra(KeyConstants.INTENT_KEY_TOPIC_CODE, topicCode);
                startActivity(i);
            }
        });
    }

    private void initView() {
        topicCode = getIntent().getStringExtra(KeyConstants.INTENT_KEY_TOPIC_CODE);
        btnBack = findViewById(R.id.btn_back);
        btnAddPost = findViewById(R.id.btn_list_topic_add_post);
        rvListPost = findViewById(R.id.rv_list_post);
        LinearLayoutManager llm = new LinearLayoutManager(ListPostActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvListPost.setLayoutManager(llm);
        fetchPost(topicCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPost(topicCode);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}