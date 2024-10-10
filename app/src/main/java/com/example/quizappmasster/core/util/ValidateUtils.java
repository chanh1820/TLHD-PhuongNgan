package com.example.quizappmasster.core.util;

import android.content.Context;

import com.example.quizappmasster.core.dto.LoginDTO;
import com.example.quizappmasster.core.sco.ScoreSCO;

import org.apache.commons.lang3.StringUtils;
import java.util.regex.Pattern;

public class ValidateUtils {

    private Pattern patternUserName;
    private Pattern patternPassword;
    private static final String USERNAME_PATTERN = "^[a-z0-9._-]{3,15}$";
    private static final String PASSWORD_PATTERN = "((?=.*[a-z]).{3,50})";

    public ValidateUtils() {
        patternUserName = Pattern.compile(USERNAME_PATTERN);
        patternPassword = Pattern.compile(PASSWORD_PATTERN);

    }

    public boolean validateUserName(final String username) {
        return patternUserName.matcher(username).matches();
    }


    public boolean validatePassword(final String password) {
        return patternPassword.matcher(password).matches();
    }

    public static final boolean isValidScoreSCO(ScoreSCO scoreSCO, Context context){
        if(scoreSCO.getMinScore() != null && scoreSCO.getMaxScore()!=null){
            if(scoreSCO.getMinScore() <= scoreSCO.getMaxScore()){
                return true;
            }else {
                NotifyUtils.defaultNotify(context, "Điểm không hợp lệ 1");
                return false;
            }
        }else {
            NotifyUtils.defaultNotify(context, "Điểm không hợp lệ 2");
            return false;
        }
    }

    public static final boolean isValidLoginDTO(LoginDTO loginDTO, Context context){
        if(StringUtils.isNotBlank(loginDTO.getUserName())){
            if (StringUtils.isNotBlank(loginDTO.getPassWord())){
                return true;
            }else {
                NotifyUtils.defaultNotify(context, "Mật khẩu không được để trống");
                return false;
            }
        }else {
            NotifyUtils.defaultNotify(context, "Tên đăng nhập không được để trống");
            return false;
        }
    }
}
