package com.example.quizappmasster.core.util;

import android.util.Log;

import com.example.quizappmasster.core.constant.GoogleSheetConstant;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

public class TransformerUtils {

    public static final Map<String, String> dtoToPayload (Object object, String action){
        Map<String, String> result = ObjectMapperUtils.dtoToMap(object, new TypeReference<Map<String, String>>(){});
        result.put("action", action);
        Log.e("dtoToPayload", ObjectMapperUtils.dtoToString(result));
        return result;
    }

    public static final Map<String, String> dtoToPayloadBackend (Object object){
        Map<String, String> result = ObjectMapperUtils.dtoToMap(object, new TypeReference<Map<String, String>>(){});
        Log.e("dtoToPayload", ObjectMapperUtils.dtoToString(result));
        return result;
    }
}
