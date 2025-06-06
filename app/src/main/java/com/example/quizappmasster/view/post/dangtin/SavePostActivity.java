package com.example.quizappmasster.view.post.dangtin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.Toast;

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
import com.example.quizappmasster.core.dto.ResponseUploadFileDTO;
import com.example.quizappmasster.core.event.OnItemClickListener;
import com.example.quizappmasster.core.util.NotifyUtils;
import com.example.quizappmasster.core.util.ObjectMapperUtils;
import com.example.quizappmasster.view.post.ListPostAdapter;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.internal.http.RealResponseBody;

public class SavePostActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_FILES = 1;

    private Button btnChooseFiles;
    private TextView tvSelectedFiles;
    private List<File> selectedFiles = new ArrayList<>();
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
                if (selectedFiles.isEmpty() || selectedFiles.size() == 0) {
                    callSavePost(new ArrayList<>());
                } else {
                    uploadFiles();
                }
            }
        });

        btnChooseFiles.setOnClickListener(v -> openFileChooser());

    }

    void callSavePost(List<String> files) {
        checkValidPost();
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("topicCode", getIntent().getStringExtra(KeyConstants.INTENT_KEY_TOPIC_CODE));
        jsonParams.put("title", edtTitle.getText().toString().trim());
        jsonParams.put("content", edtContent.getText().toString().trim());
        jsonParams.put("userId", accountDTO.getUserName());
        jsonParams.put("author", accountDTO.getDisplayName());
        if (files.isEmpty() || files.size() == 0) {
            files = new ArrayList<>();
        }
        jsonParams.put("listFile", ObjectMapperUtils.dtoToString(files));
        StringRequest request = new StringRequest(Request.Method.POST, "http://160.191.175.200:8103/post/insert", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                ;
                ResponseDTO<PostDTO> responseDTO = ObjectMapperUtils.stringToTypeReference(response, new TypeReference<ResponseDTO<PostDTO>>() {
                });
                if (responseDTO.getStatusCode().equals(GoogleSheetConstant.STATUS_SUCCESS)) {
                    Log.e("response", "1");
                    NotifyUtils.defaultNotify(getApplicationContext(), "Đăng tin thành công");
                    finish();

//                            if(topicCode.equals(DBConstant.TOPIC_CODE_2)){
//                                showDialog();
//                            }
                } else {
                    Log.e("response", "2");
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
                return new JSONObject(jsonParams).toString().getBytes();
            }

            public String getBodyContentType() {
                return "application/json;charset=UTF-8";
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void checkValidPost() {
        if (StringUtils.isBlank(edtTitle.getText().toString())) {
            NotifyUtils.defaultNotify(getApplicationContext(), "Chủ đề trống");
            return;
        }
        if (StringUtils.isBlank(edtContent.getText().toString())) {
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
        btnChooseFiles = findViewById(R.id.btn_save_post_choose_file);
        tvSelectedFiles = findViewById(R.id.tvSelectedFiles);
        tvDisplayName.setText(accountDTO.getDisplayName());
    }

    private void showDialog() {
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

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_FILES && resultCode == RESULT_OK) {
            if (data != null) {
                selectedFiles.clear(); // Clear any previously selected files
                StringBuilder fileNames = new StringBuilder();

                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri fileUri = data.getClipData().getItemAt(i).getUri();
                        File file = createTempFileFromUri(fileUri);
                        if (file != null) {
                            selectedFiles.add(file);
                            fileNames.append(file.getName()).append("\n");
                        }
                    }
                } else if (data.getData() != null) {
                    Uri fileUri = data.getData();
                    File file = createTempFileFromUri(fileUri);
                    if (file != null) {
                        selectedFiles.add(file);
                        fileNames.append(file.getName()).append("\n");
                    }
                }

                tvSelectedFiles.setText(fileNames.toString());
            }
        }
    }

    private File createTempFileFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            String fileName = getFileName(uri);
            File tempFile = new File(getCacheDir(), fileName);

            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getFileName(Uri uri) {
        String fileName = "temp_file";
        DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
        if (documentFile != null) {
            fileName = documentFile.getName();
        }
        return fileName;
    }

    private void uploadFiles() {
        String userName = DBConstant.USER_NAME_UPLOAD_FILES; // Replace with actual user name if needed

        callUploadFiles(selectedFiles, userName, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(SavePostActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                Log.e(" okhttp3.Response", ObjectMapperUtils.dtoToString( response));
                if (response.isSuccessful()) {
                    String responseBodyString = response.body().string(); // Get the response content as a String

                    Log.e("response.body().toString()", responseBodyString);
                    ResponseUploadFileDTO responseUploadFileDTO = ObjectMapperUtils.stringToTypeReference(responseBodyString, new TypeReference<ResponseUploadFileDTO>() {
                    });
                    runOnUiThread(() -> {
                        callSavePost(responseUploadFileDTO.getData());
                        Toast.makeText(SavePostActivity.this, "Upload ảnh thành công", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(SavePostActivity.this, "Upload failed with response: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    public static void callUploadFiles(List<File> files, String userName, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        // Create multipart form data request
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        // Add files to the request
        for (File file : files) {
            builder.addFormDataPart("files", file.getName(),
                    RequestBody.create(file, MediaType.parse("image/*")));
        }

        // Add additional form data for userName
        builder.addFormDataPart("userName", userName);

        // Build the request
        RequestBody requestBody = builder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://160.191.175.200:9000/api/file/multi-file-upload")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "multipart/form-data")
                .post(requestBody)
                .build();
        // Make asynchronous call
        client.newCall(request).enqueue(callback);
    }
}