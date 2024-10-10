package com.example.quizappmasster.core.util;

import android.content.Context;
import android.widget.Toast;

import com.example.quizappmasster.core.constant.GoogleSheetConstant;

public class NotifyUtils {

    public static final void defaultNotify(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static final void notifyByMessage(Context context, String messageCode){
        String message = GoogleSheetConstant.MESSAGE_CODE_MAP.get(messageCode);
        defaultNotify(context, message);
    }


}
