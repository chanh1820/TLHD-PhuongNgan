package com.example.quizappmasster.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.quizappmasster.core.cache.AccountCache;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.dto.AccountDTO;
import com.example.quizappmasster.core.dto.LoginDTO;
import com.example.quizappmasster.core.dto.ResponseDTO;
import com.example.quizappmasster.core.util.NotifyUtils;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.core.util.TransformerUtils;
import com.example.quizappmasster.core.util.ValidateUtils;
import com.example.quizappmasster.view.admin.danhsachdiem.ListScoreActivity;
import com.example.quizappmasster.view.admin.main.MainAdminActivity;
import com.example.quizappmasster.view.main.MainActivity;
import com.example.quizappmasster.view.registry.RegistryActivity;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    Button btnSignIn, btnSkip;
    EditText edtUserName, edtPassWord;
    TextView tvRegistry;
    ProgressBar pgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnSignIn = findViewById(R.id.btn_SignIn);
        btnSkip = findViewById(R.id.btn_skip);
        edtUserName = findViewById(R.id.edt_user_name);
        edtPassWord = findViewById(R.id.edt_password);
        tvRegistry = findViewById(R.id.tv_registry);
        pgMain = findViewById(R.id.pg_main);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignIn.setFocusable(false);
                pgMain.setVisibility(View.VISIBLE);
                LoginDTO loginDTO = new LoginDTO(
                        edtUserName.getText().toString().trim(),
                        edtPassWord.getText().toString().trim()
                );
                if (ValidateUtils.isValidLoginDTO(loginDTO, getApplicationContext())) {
                    login(loginDTO);
                }
            }
        });
        tvRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, RegistryActivity.class);
                startActivity(i);
            }
        });
//        btnSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(SignInActivity.this, MainActivity.class);
//                i.putExtra("display_name", "");
//                i.putExtra("user_name", "");
//                startActivity(i);
//            }
//        });


    }

    private void login(LoginDTO loginDTO) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                pgMain.setVisibility(View.INVISIBLE);
                ResponseDTO<AccountDTO> responseDTO
                        = ObjectMapperUtils.stringToTypeReference(
                        response,
                        new TypeReference<ResponseDTO<AccountDTO>>() {
                        }
                );
                if (responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)) {
                    AccountDTO accountDTO = responseDTO.getData();
                    AccountCache.setCache(SignInActivity.this, accountDTO);
                    Intent intent = new Intent();
                    switch (accountDTO.getRole()) {
                        case GoogleSheetConstant.ACCOUNT_TYPE_USER:
                            intent = new Intent(SignInActivity.this, MainActivity.class);
                            break;
                        case GoogleSheetConstant.ACCOUNT_TYPE_ADMIN:
                            intent = new Intent(SignInActivity.this, MainAdminActivity.class);
                            break;
                        default:
                            NotifyUtils.defaultNotify(getApplicationContext(), "Tài khoản không hợp lệ");
                            break;
                    }
                    startActivity(intent);
                } else {
                    NotifyUtils.notifyByMessage(getApplicationContext(), responseDTO.getStatusCode());
                }
                btnSignIn.setFocusable(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnSignIn.setFocusable(true);
                Toast.makeText(getApplicationContext(), "Bạn chưa bật kết nối Internet ?", Toast.LENGTH_LONG).show();
                pgMain.setVisibility(View.INVISIBLE);
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return TransformerUtils.dtoToPayload(loginDTO, GoogleSheetConstant.ACTION_LOGIN);
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