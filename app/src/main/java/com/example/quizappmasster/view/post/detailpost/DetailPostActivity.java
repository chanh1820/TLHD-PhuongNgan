package com.example.quizappmasster.view.post.detailpost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizappmasster.R;
import com.example.quizappmasster.core.cache.AccountCache;
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.constant.KeyConstants;
import com.example.quizappmasster.core.dto.AccountDTO;
import com.example.quizappmasster.core.dto.PostCommentDTO;
import com.example.quizappmasster.core.dto.PostDTO;
import com.example.quizappmasster.core.dto.PostInteractionDTO;
import com.example.quizappmasster.core.dto.ResponseDTO;
import com.example.quizappmasster.core.util.NotifyUtils;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.view.post.ListPostActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.android.gms.common.util.CollectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class DetailPostActivity extends AppCompatActivity {
    TextView tvAuthor, tvTitle, tvContent, tvCountLike, tvCountComment;
    RecyclerView rvListComment;
    ImageButton imbIconLike;
    Button btnSend;
    EditText edtInputComment;
    RecyclerView rvListImage;
    ImageListAdapter imageListAdapter;

    AccountDTO accountDTO = new AccountDTO();
    ListCommentAdapter listCommentAdapter;

    PostDTO postDTO = new PostDTO();
    Integer postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        initView();
        action();
    }

    private void action() {
        imbIconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((Boolean) postDTO.getIsLike()) {
                    deleteLike();
                } else {
                    insertLike();
                }

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtInputComment.getText().toString().trim();
                if (StringUtils.isBlank(input)) {
                    NotifyUtils.defaultNotify(getApplicationContext(), "Nội dung trống");
                    return;
                }

                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("userName", accountDTO.getUserName());
                jsonParams.put("displayName", accountDTO.getDisplayName());
                jsonParams.put("content", input);
                jsonParams.put("postId", postDTO.getId());
                jsonParams.put("author", accountDTO.getDisplayName());
                StringRequest request = new StringRequest(Request.Method.POST, "http://160.191.175.200:8103/post/comment/insert", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        ;
                        ResponseDTO<PostCommentDTO> responseDTO = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<ResponseDTO<PostCommentDTO>>() {
                        });
                        if (responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)) {
                            NotifyUtils.defaultNotify(getApplicationContext(), "Gửi thành công");
                            fetchDetailPost(postId);
                        } else {
                            NotifyUtils.defaultNotify(getApplicationContext(), "Gửi không thành công");
                        }

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

                btnSend.setText("");
            }
        });

    }

    private void deleteLike() {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userName", accountDTO.getUserName());
        jsonParams.put("type", DBConstant.TYPE_INTERACTION_LIKE);
        jsonParams.put("postId", postDTO.getId());
        StringRequest request = new StringRequest(Request.Method.POST, "http://160.191.175.200:8103/post/interact/delete", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                ;
                ResponseDTO<PostInteractionDTO> responseDTO = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<ResponseDTO<PostInteractionDTO>>() {
                });
                if (responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)) {
                    imbIconLike.setBackgroundResource(R.drawable.ic_non_like);
                    Integer countLike = Integer.valueOf(tvCountLike.getText().toString().trim());
                    tvCountLike.setText(String.valueOf(countLike - 1));
                    postDTO.setIsLike(false);
                } else {
                    NotifyUtils.defaultNotify(getApplicationContext(), "Lỗi");
                }

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
                Log.e("getBody", jsonParams.toString());
                return new JSONObject(jsonParams).toString().getBytes();
            }

            public String getBodyContentType() {
                return "application/json;charset=UTF-8";
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void insertLike() {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userName", accountDTO.getUserName());
        jsonParams.put("type", DBConstant.TYPE_INTERACTION_LIKE);
        jsonParams.put("postId", postDTO.getId());
        StringRequest request = new StringRequest(Request.Method.POST, "http://160.191.175.200:8103/post/interact/insert", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                ;
                ResponseDTO<PostInteractionDTO> responseDTO = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<ResponseDTO<PostInteractionDTO>>() {
                });
                if (responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)) {
                    imbIconLike.setBackgroundResource(R.drawable.ic_liked);
                    Integer countLike = Integer.valueOf(tvCountLike.getText().toString().trim());
                    tvCountLike.setText(String.valueOf(countLike + 1));
                    postDTO.setIsLike(true);
                } else {
                    NotifyUtils.defaultNotify(getApplicationContext(), "Lỗi");
                }

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
                Log.e("getBody", jsonParams.toString());
                return new JSONObject(jsonParams).toString().getBytes();
            }

            public String getBodyContentType() {
                return "application/json;charset=UTF-8";
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void initView() {
        accountDTO = AccountCache.getCache(getApplicationContext());
        postId = getIntent().getIntExtra(KeyConstants.INTENT_KEY_POST_ID, 0);
        tvAuthor = findViewById(R.id.tv_detail_post_author);
        tvTitle = findViewById(R.id.tv_detail_post_title);
        tvContent = findViewById(R.id.tv_detail_post_content);
        imbIconLike = findViewById(R.id.imb_detail_post_like);
        edtInputComment = findViewById(R.id.edt_detail_post_input_comment);
        btnSend = findViewById(R.id.btn_detail_post_send_comment);
        rvListImage = findViewById(R.id.tv_detail_post_list_image);
        rvListImage.setLayoutManager(new LinearLayoutManager(this));


        tvCountLike = findViewById(R.id.tv_detail_post_count_like);
        tvCountComment = findViewById(R.id.tv_detail_post_count_comment);
        rvListComment = findViewById(R.id.rv_list_comment);
        LinearLayoutManager llm = new LinearLayoutManager(DetailPostActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvListComment.setLayoutManager(llm);
        fetchDetailPost(postId);
    }

    void fetchDetailPost(Integer postId) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("userId", accountDTO.getUserName());
        jsonParams.put("type", DBConstant.TYPE_INTERACTION_LIKE);
        jsonParams.put("id", postId);
        StringRequest request = new StringRequest(Request.Method.POST, "http://160.191.175.200:8103/post/get", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                ;
                ResponseDTO<PostDTO> responseDTO = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<ResponseDTO<PostDTO>>() {
                });
                if (responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)) {
                    postDTO = responseDTO.getData();
                    listCommentAdapter = new ListCommentAdapter(getApplicationContext(), postDTO.getPostCommentDTOList());
                    rvListComment.setAdapter(listCommentAdapter);
                    tvAuthor.setText(postDTO.getAuthor());
                    tvTitle.setText(postDTO.getTitle());
                    tvContent.setText(postDTO.getContent());
                    tvCountLike.setText(String.valueOf(postDTO.getCountInteract()));
                    tvCountComment.setText(String.valueOf(postDTO.getCountComment()));

                    if (postDTO.getIsLike()) {
                        imbIconLike.setBackgroundResource(R.drawable.ic_liked);
                    } else {
                        imbIconLike.setBackgroundResource(R.drawable.ic_non_like);
                    }

                    List<String> listImage = ObjectMapperUtils.stringToTypeReference(postDTO.getListFile(), new TypeReference<List<String>>() {
                    });
                    if (CollectionUtils.isEmpty(listImage) || listImage.size() == 0) {
                        rvListImage.setVisibility(View.GONE);
                    } else {
                        imageListAdapter = new ImageListAdapter(listImage);
                        rvListImage.setAdapter(imageListAdapter);
                        RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
                        rvListImage.setItemAnimator(animator);
                        tvContent.setMovementMethod(new ScrollingMovementMethod());
                    }
                } else {
                    NotifyUtils.defaultNotify(getApplicationContext(), "Đăng tin không thành công");
                    finish();
                }

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
                Log.e("getBody", jsonParams.toString());
                return new JSONObject(jsonParams).toString().getBytes();
            }

            public String getBodyContentType() {
                return "application/json;charset=UTF-8";
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}