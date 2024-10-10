package com.example.quizappmasster.view.post.dangtin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import com.example.quizappmasster.core.dto.PostDTO;
import com.example.quizappmasster.core.dto.ResponseDTO;
import com.example.quizappmasster.core.event.OnItemClickListener;
import com.example.quizappmasster.core.util.NotifyUtils;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.view.post.ListPostAdapter;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class SavePostActivity extends AppCompatActivity {
    TextView tvDisplayName;
    EditText edtTitle, edtContent;

    Button btnSavePost;

    AccountDTO accountDTO = new AccountDTO();

    String topicCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_post);

        initView();
        action();
    }

    private void action() {
        btnSavePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidPost();
                Map<String, Object> jsonParams = new ArrayMap<>();
                jsonParams.put("topicCode", getIntent().getStringExtra(KeyConstants.INTENT_KEY_TOPIC_CODE));
                jsonParams.put("title", edtTitle.getText().toString().trim());
                jsonParams.put("content", edtContent.getText().toString().trim());
                jsonParams.put("userId", accountDTO.getUserName());
                jsonParams.put("author", accountDTO.getDisplayName());
                StringRequest request = new StringRequest(Request.Method.POST, "http://103.218.122.240:8102/post/insert", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        ;
                        ResponseDTO<PostDTO> responseDTO = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<ResponseDTO<PostDTO>>() {
                        });
                        if (responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)){
                            Log.e("response", "1");
                            NotifyUtils.defaultNotify(getApplicationContext(), "Đăng tin thành công");
                            if(topicCode.equals(DBConstant.TOPIC_CODE_2)){
                                showDialog();
                            }
                        }else {
                            Log.e("response", "2");
                            NotifyUtils.defaultNotify(getApplicationContext(), "Đăng tin không thành công");
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString() );
                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        try {
                            Log.e("NetworkResponse", response.headers.toString() );
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
        });
    }

    private void checkValidPost() {
        if(StringUtils.isBlank(edtTitle.getText().toString())){
            NotifyUtils.defaultNotify(getApplicationContext(), "Chủ đề trống");
            return;
        }
        if(StringUtils.isBlank(edtContent.getText().toString())){
            NotifyUtils.defaultNotify(getApplicationContext(), "Nội dung trống");
            return;
        }
    }

    private void initView() {
        topicCode = getIntent().getStringExtra(KeyConstants.INTENT_KEY_TOPIC_CODE);
        accountDTO = AccountCache.getCache(getApplicationContext());
        tvDisplayName = findViewById(R.id.tv_save_post_display_name);
        edtTitle = findViewById(R.id.edt_save_post_title);
        edtContent = findViewById(R.id.edt_save_post_content);
        btnSavePost = findViewById(R.id.btn_save_post_save);

        tvDisplayName.setText(accountDTO.getDisplayName());
    }

    private  void showDialog(){
        Dialog dialog = new Dialog(SavePostActivity.this);
        dialog.setContentView(R.layout.dialog_khan_cap);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        dialog.show();

        Button btnNext = dialog.findViewById(R.id.btn_dialog_khan_cap_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }
}