package com.example.quizappmasster.core.constant;

import java.util.HashMap;
import java.util.Map;

public class GoogleSheetConstant {

    /**
     * Url
     */
    public static final String END_POINT_URL = "https://script.google.com/macros/s/AKfycby3LKhDtINaGan_7sLIcgMxQMtWpaveWUByNIkym26S3NSiQvGfKEikdT7WMx8UgA/exec";

    public static final String BACKEND_URL = "http://103.218.122.240:8103";


    /**
     * action
     */
    public static final String ACTION_SAVE_SCORE = "SAVE_SCORE";

    public static final String ACTION_SEARCH_SCORE = "SEARCH_SCORE";

    public static final String ACTION_LOGIN = "LOGIN";

    public static final String ACTION_REGISTRY = "REGISTRY";

    public static final String ACTION_FIND_QUESTION_BY_SUBJECT = "FIND_QUESTION_BY_SUBJECT";

    public static final String ACTION_SEARCH_TOPIC = "SEARCH_TOPIC";



    /**
     * Type
     */
    public static final String ACCOUNT_TYPE_ADMIN = "0";

    public static final String ACCOUNT_TYPE_USER = "1";

    public static final String FLAG_IS_LOGIN = "1";

    public static final String FLAG_NONE_LOGIN = "0";

    /**
     * Status Code
     */
    public static final String STATUS_SUCCESS = "200";

    public static final String MES_CODE_LOGIN_PASSWORD_FAIL = "ERR_01";

    public static final String MES_CODE_LOGIN_USER_FAIL = "ERR_02";

    public static final String MES_CODE_ACCOUNT_IS_EXISTING= "ERR_04";

    public static final Map<String, String> MESSAGE_CODE_MAP = new HashMap<String, String>(){{
        put(MES_CODE_LOGIN_PASSWORD_FAIL, "Sai mật khẩu");
        put(MES_CODE_LOGIN_USER_FAIL, "Tài khoản không tồn tại");
        put(MES_CODE_ACCOUNT_IS_EXISTING, "Tài khoản đã tồn tại");
    }};

    public static final String BACKEND_SEARCH_POST = "/post/search_post";
}
