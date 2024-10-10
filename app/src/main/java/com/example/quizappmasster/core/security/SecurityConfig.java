package com.example.quizappmasster.core.security;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class SecurityConfig {
    public static final String TOKEN = "tlhd_lediem";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void firebase(Context context) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(SecurityConfig.TOKEN + "@gmail.com", "123456")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        } else {
                            System.exit(0);
                        }
                    }
                });
    }

    public static void validateDevice(Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyR35f0jnQbjQABVVV4zLXajCDgLkFjlr50xRaIRNC58MqhlwpRuNTQf6RD6SCMkbc/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                ResponseNewDTO<DeviceDTO> responseDTO
                        = stringToTypeReference(
                        response,
                        new TypeReference<ResponseNewDTO<DeviceDTO>>() {
                        }
                );
                if (responseDTO.getStatusCode().equals("ERR_001")) {
                    Toast.makeText(context, responseDTO.getMessage(), Toast.LENGTH_LONG);
                    System.exit(0);
                } else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setId(0);
                deviceDTO.setDeviceCode(TOKEN + "_" + getDeviceId(context));
                deviceDTO.setLassLogin(LocalDateTime.now().toString());
                deviceDTO.setStatus(999);
                return dtoToPayload(deviceDTO, "VALIDATE_DEVICE");
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

    private static class DeviceDTO {

        private Integer id;

        private String deviceCode;

        private String lassLogin;

        private Integer status;


        public DeviceDTO() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deveiceCode) {
            this.deviceCode = deveiceCode;
        }

        public String getLassLogin() {
            return lassLogin;
        }

        public void setLassLogin(String lassLogin) {
            this.lassLogin = lassLogin;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }

    private static class ResponseNewDTO<T> {

        private String statusCode;

        private String message;

        private T data;

        public ResponseNewDTO() {
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    private static String getDeviceId(Context context) {
        String deviceId = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return deviceId + "-" + Build.MODEL;
    }

    private static final Map<String, String> dtoToPayload(Object object, String action) {
        Map<String, String> result = dtoToMap(object, new TypeReference<Map<String, String>>() {
        });
        result.put("action", action);

        return result;
    }

    private static <D, T> D dtoToMap(final Object dto, TypeReference<Map<String, String>> outClass) {
        return objectMapper.convertValue(dto, outClass);
    }

    private static <D, T> D stringToTypeReference(final String entitys, TypeReference<D> typeReference) {
        try {
            return objectMapper.readValue(entitys, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }
        return null;
    }
}
