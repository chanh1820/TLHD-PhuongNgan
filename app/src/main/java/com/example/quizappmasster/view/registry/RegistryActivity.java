package com.example.quizappmasster.view.registry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.quizappmasster.core.constant.DBConstant;
import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.example.quizappmasster.core.dto.AccountDTO;
import com.example.quizappmasster.core.dto.ResponseDTO;
import com.example.quizappmasster.core.util.NotifyUtils;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.core.util.TransformerUtils;
import com.example.quizappmasster.core.util.ValidateUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class RegistryActivity extends AppCompatActivity {
    EditText edtDisplayName, edtUserName, edtPassWord1, edtPassWord2;
    Button btnRegistry;
    Spinner spnChooseClassRoom;
    AccountDTO accountDTO = new AccountDTO();

    ChooseClassRoomAdapter chooseClassRoomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        initView();
        action();
    }

    private void action() {
        btnRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String displayName = edtDisplayName.getText().toString().trim();
                String userName = edtUserName.getText().toString().trim();
                String passWord1 = edtPassWord1.getText().toString().trim();
                String passWord2 = edtPassWord2.getText().toString().trim();
                if (displayName.isEmpty() ||userName.isEmpty()||passWord1.isEmpty()||passWord2.isEmpty()){
                    NotifyUtils.defaultNotify(getApplicationContext(), "Thông tin chưa chính xác");
                    return;
                }
                if (!passWord1.equals(passWord2)){
                    NotifyUtils.defaultNotify(getApplicationContext(), "Mật khẩu xác nhận không khớp");
                    return;
                }
                ValidateUtils validateUtils = new ValidateUtils();
                if (!validateUtils.validatePassword(passWord1)){
                    NotifyUtils.defaultNotify(getApplicationContext(), "Mật khẩu không hợp lệ");
                    return;
                }

                if (!validateUtils.validateUserName(userName)){
                    NotifyUtils.defaultNotify(getApplicationContext(), "Tên đăng nhập không hợp lệ");
                    return;
                }

                accountDTO.setUserName(userName);
                accountDTO.setPassWord(passWord1);
                accountDTO.setDisplayName(displayName);
                accountDTO.setRole("1");
                accountDTO.setFlagLogin(GoogleSheetConstant.FLAG_NONE_LOGIN);
                registryAccount(accountDTO);

            }
        });

    }

    private void registryAccount(AccountDTO accountDTO) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GoogleSheetConstant.END_POINT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                ResponseDTO<AccountDTO> responseDTO
                        = ObjectMapperUtils.stringToTypeReference(
                        response,
                        new TypeReference<ResponseDTO<AccountDTO>>() {
                        }
                );
                if(responseDTO.getStatusCode().toString().equals(GoogleSheetConstant.STATUS_SUCCESS)){
                    NotifyUtils.defaultNotify(getApplicationContext(), "Đăng kí thành công");
                    finish();
                }else {
                    NotifyUtils.defaultNotify(getApplicationContext(), responseDTO.getMessage());
                }
                btnRegistry.setFocusable(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnRegistry.setFocusable(true);
                Toast.makeText(getApplicationContext(), "Bạn chưa bật kết nối Internet ?", Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return TransformerUtils.dtoToPayload(accountDTO, GoogleSheetConstant.ACTION_REGISTRY);
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

    private void initView() {
        edtDisplayName = findViewById(R.id.edt_registry_display_name);
        edtUserName = findViewById(R.id.edt_registry_username);
        edtPassWord1 = findViewById(R.id.edt_registry_pass_word_1);
        edtPassWord2 = findViewById(R.id.edt_registry_pass_word_2);
        btnRegistry = findViewById(R.id.btn_registry_accept);
        spnChooseClassRoom = findViewById(R.id.spn_choose_class_room);

        chooseClassRoomAdapter = new ChooseClassRoomAdapter(getApplicationContext(), DBConstant.CLASS_ROOM_STRING);

        spnChooseClassRoom.setAdapter(chooseClassRoomAdapter);
        spnChooseClassRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accountDTO.setClassRoom(DBConstant.CLASS_ROOM_STRING.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}