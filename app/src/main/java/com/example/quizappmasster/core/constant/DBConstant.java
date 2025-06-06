package com.example.quizappmasster.core.constant;


import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.LinkDTO;
import com.example.quizappmasster.core.dto.LyThuyetDTO;
import com.example.quizappmasster.core.dto.SubjectLyThuyetDTO;
import com.example.quizappmasster.core.dto.SuggestResultDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConstant {


    public static final String TYPE_SUGGEST_RESULT_PDF = "1";
    public static final String TYPE_SUGGEST_RESULT_LINK = "2";

    public static final String TYPE_INTERACTION_LIKE = "1";

    public static final List<LyThuyetDTO> listLyThuyetDTO = new ArrayList<LyThuyetDTO>() {{
        add(new LyThuyetDTO(0, "BÀI 1: ĐI BỘ AN TOÀN", "", "BÀI 1. ĐI BỘ AN TOÀN.pdf", "A01"));
        add(new LyThuyetDTO(1, "BÀI 2: ĐỘI MŨ BẢO HIỂM KHI THAM GIA GIAO THÔNG", "", "BÀI 2. ĐỘI MŨ BẢO HIỂM KHI THAM GIA GIAO THÔNG.pdf", "A01"));
        add(new LyThuyetDTO(2, "BÀI 3: CHẤP HÀNH BIỂN BÁO HIỆU ĐƯỜNG BỘ", "", "BÀI 3. CHẤP HÀNH BIỂN BÁO HIỆU ĐƯỜNG BỘ.pdf", "A01"));
        add(new LyThuyetDTO(3, "BÀI 4: CHUẨN BỊ VÀ ĐIỀU KHIỂN XE ĐẠP, XE ĐẠP ĐIỆN AN TOÀN", "", "BÀI 4. CHUẨN BỊ VÀ ĐIỀU KHIỂN XE ĐẠP, XE ĐẠP ĐIỆN AN TOÀN.pdf", "A01"));
        add(new LyThuyetDTO(3, "BÀI 5: AN TOÀN KHI NGỒI TRÊN XE ĐẠP, XE MÁY, Ô TÔ", "", "BÀI 5. AN TOÀN KHI NGỒI TRÊN XE ĐẠP, XE MÁY, Ô TÔ.pdf", "A01"));
        add(new LyThuyetDTO(4, "BÀI 6: AN TOÀN KHI ĐI XE BUÝT, XE ĐƯA ĐÓN HỌC SINH", "", "BÀI 6. AN TOÀN KHI ĐI XE BUÝT, XE ĐƯA ĐÓN HỌC SINH.pdf", "A01"));
        add(new LyThuyetDTO(6, "BÀI 7: AN TOÀN KHI ĐI XE MÁY CHỞ KHÁCH, XE TAXI", "", "BÀI 7. AN TOÀN KHI ĐI XE MÁY CHỞ KHÁCH, XE TAXI.pdf", "A01"));
        add(new LyThuyetDTO(7, "Bài 8: PHÒNG NGỪA RỦI RO VÀ XỬ LÍ SỰ CỐ GIAO THÔNG", "", "Bài 8. PHÒNG NGỪA RỦI RO VÀ XỬ LÍ SỰ CỐ GIAO THÔNG.pdf", "A01"));
        add(new LyThuyetDTO(8, "BÀI 9:  TẦM QUAN TRỌNG CỦA VIỆC TUÂN THỦ CÁC QUY TẮC GIAO THÔNG ĐƯỜNG BỘ", "", "BÀI 9  TẦM QUAN TRỌNG CỦA VIỆC TUÂN THỦ CÁC QUY TẮC GIAO THÔNG ĐƯỜNG BỘ.pdf", "A01"));
        add(new LyThuyetDTO(9, "BÀI 10: HỆ THỐNG BÁO HIỆU GIAO THÔNG ĐƯỜNG BỘ", "", "BÀI 10 HỆ THỐNG BÁO HIỆU GIAO THÔNG ĐƯỜNG BỘ.pdf", "A01"));
        add(new LyThuyetDTO(10, "Bài 11: DỰ ĐOÁN VÀ PHÒNG TRÁNH NGUY HIỂM", "", "Bài 11 DỰ ĐOÁN VÀ PHÒNG TRÁNH NGUY HIỂM.pdf", "A01"));
        add(new LyThuyetDTO(11, "6 NHÓM BIỂN BÁO GIAO THÔNG ĐƯỜNG BỘ", "", "6 NHÓM BIỂN BÁO GIAO THÔNG ĐƯỜNG BỘ.pdf", "A02"));
        add(new LyThuyetDTO(11, "Dự tháo luật đường bộ sửa đổi", "", "du-thao-luat-giao-thong-duong-bo-sua-doi.pdf", "A03"));

    }};
    public static final List<SubjectLyThuyetDTO> SUBJECT_LY_THUYET_DTO_LIST = new ArrayList<SubjectLyThuyetDTO>() {{
        add(new SubjectLyThuyetDTO(1, "A01", "Các bài học"));
        add(new SubjectLyThuyetDTO(2, "A02", "Các biển báo"));
        add(new SubjectLyThuyetDTO(3, "A03", "Luật"));
    }};
    public static final List<String> CLASS_ROOM_STRING = new ArrayList<String>() {{
        add("6-1");
        add("6-2");
        add("6-3");
        add("6-4");
        add("7-1");
        add("7-2");
        add("7-3");
        add("7-4");
        add("8-1");
        add("8-2");
        add("8-3");
        add("8-4");
        add("9-1");
        add("9-2");
        add("9-3");
        add("9-4");
    }};
    public static final Integer DIEM_TRAC_NGHIEM_1 = 1;

    public static final Integer DIEM_TRAC_NGHIEM_2 = 2;
    public static final String USER_NAME_UPLOAD_FILES = "TLHD-LEDIEM";
    public static final String URL_RESOURCE_FILE = "http://160.191.175.200:9000/resource/" + USER_NAME_UPLOAD_FILES + "/";

    public static List<LinkDTO> linkDTOList = new ArrayList<LinkDTO>() {{
        add(new LinkDTO(1, "Trang Luật Việt Nam", "https://luatvietnam.vn/", R.drawable.ic_baseline_info_24));
        add(new LinkDTO(1, "Trang An toàn giao thông", "http://antoangiaothong.gov.vn/", R.drawable.ic_baseline_info_24));
        add(new LinkDTO(1, "Trang web Trường THCS Nguyễn Thành Đô", "http://thcsnguyenthanhdo.haugiang.edu.vn/", R.drawable.ic_baseline_info_24));
    }};


    public static List<String> LIST_CLASS_ROOM = new ArrayList<String>() {{
        add("Tất cả");
        add("6-1");
        add("6-2");
        add("6-3");
        add("6-4");
        add("7-1");
        add("7-2");
        add("7-3");
        add("7-4");
        add("8-1");
        add("8-2");
        add("8-3");
        add("8-4");
        add("9-1");
        add("9-2");
        add("9-3");
        add("9-4");
    }};

    public static final List<SuggestResultDTO> suggestResultDTOListLv1 = new ArrayList<SuggestResultDTO>() {{
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_PDF, "Tài liệu tuyên truyền phòng, chống bạo lực học đường (1).pdf", "Tài liệu tuyên truyền phòng, chống bạo lực học đường"));
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_LINK, "https://tamly.com.vn/lam-gi-khi-bi-bao-luc-hoc-duong-6353.html", "Làm gì khi bị bàn bè cô lập"));
    }};
    public static final List<SuggestResultDTO> suggestResultDTOListLv2 = new ArrayList<SuggestResultDTO>() {{
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_PDF, "Tài liệu tuyên truyền phòng, chống bạo lực học đường (1).pdf", "Tài liệu tuyên truyền phòng, chống bạo lực học đường"));
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_LINK, "https://tamly.com.vn/lam-gi-khi-bi-bao-luc-hoc-duong-6353.html", "Làm gì khi bị bàn bè cô lập"));
    }};
    public static final List<SuggestResultDTO> suggestResultDTOListLv3 = new ArrayList<SuggestResultDTO>() {{
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_PDF, "Tài liệu tuyên truyền phòng, chống bạo lực học đường (1).pdf", "Tài liệu tuyên truyền phòng, chống bạo lực học đường"));
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_LINK, "https://tamly.com.vn/lam-gi-khi-bi-bao-luc-hoc-duong-6353.html", "Làm gì khi bị bàn bè cô lập"));
    }};
    public static final List<SuggestResultDTO> suggestResultDTOListLv4 = new ArrayList<SuggestResultDTO>() {{
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_PDF, "Tài liệu tuyên truyền phòng, chống bạo lực học đường (1).pdf", "Tài liệu tuyên truyền phòng, chống bạo lực học đường"));
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_LINK, "https://tamly.com.vn/lam-gi-khi-bi-bao-luc-hoc-duong-6353.html", "Làm gì khi bị bàn bè cô lập"));
    }};
    public static final List<SuggestResultDTO> suggestResultDTOListLv5 = new ArrayList<SuggestResultDTO>() {{
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_PDF, "Tài liệu tuyên truyền phòng, chống bạo lực học đường (1).pdf", "Tài liệu tuyên truyền phòng, chống bạo lực học đường"));
        add(new SuggestResultDTO(TYPE_SUGGEST_RESULT_LINK, "https://tamly.com.vn/lam-gi-khi-bi-bao-luc-hoc-duong-6353.html", "Nên làm gì khi bị bạo lực học đường? Cách xử lý tình huống"));
    }};

    public static final Map<Integer, List<SuggestResultDTO>> SUGGEST_DTOS_MAP = new HashMap<Integer, List<SuggestResultDTO>>() {{
        put(0, suggestResultDTOListLv1);
        put(1, suggestResultDTOListLv1);
        put(2, suggestResultDTOListLv1);
        put(3, suggestResultDTOListLv1);
        put(4, suggestResultDTOListLv1);
        put(5, suggestResultDTOListLv1);
        put(6, suggestResultDTOListLv1);
        put(7, suggestResultDTOListLv1);
        put(8, suggestResultDTOListLv1);
        put(9, suggestResultDTOListLv1);
        put(10, suggestResultDTOListLv1);
        put(11, suggestResultDTOListLv1);
        put(12, suggestResultDTOListLv1);
        put(13, suggestResultDTOListLv1);
        put(14, suggestResultDTOListLv1);
        put(15, suggestResultDTOListLv1);
        put(16, suggestResultDTOListLv1);
        put(17, suggestResultDTOListLv1);
        put(18, suggestResultDTOListLv1);
        put(19, suggestResultDTOListLv1);
        put(20, suggestResultDTOListLv1);
        put(21, suggestResultDTOListLv1);
        put(22, suggestResultDTOListLv1);
        put(23, suggestResultDTOListLv1);
        put(24, suggestResultDTOListLv1);
        put(25, suggestResultDTOListLv1);
        put(26, suggestResultDTOListLv1);
        put(27, suggestResultDTOListLv1);
        put(28, suggestResultDTOListLv1);
        put(29, suggestResultDTOListLv1);
        put(30, suggestResultDTOListLv1);
        put(31, suggestResultDTOListLv1);
        put(32, suggestResultDTOListLv1);
        put(33, suggestResultDTOListLv1);
        put(34, suggestResultDTOListLv1);
        put(35, suggestResultDTOListLv1);
        put(36, suggestResultDTOListLv1);
        put(37, suggestResultDTOListLv1);
        put(38, suggestResultDTOListLv1);
        put(39, suggestResultDTOListLv1);
        put(40, suggestResultDTOListLv1);
        put(41, suggestResultDTOListLv1);
        put(42, suggestResultDTOListLv1);
        put(43, suggestResultDTOListLv1);
        put(44, suggestResultDTOListLv1);
        put(45, suggestResultDTOListLv1);
        put(46, suggestResultDTOListLv1);
        put(47, suggestResultDTOListLv1);
        put(48, suggestResultDTOListLv1);
        put(49, suggestResultDTOListLv1);
        put(50, suggestResultDTOListLv1);
        put(51, suggestResultDTOListLv1);
        put(52, suggestResultDTOListLv1);
        put(53, suggestResultDTOListLv1);
        put(54, suggestResultDTOListLv1);
        put(55, suggestResultDTOListLv1);
        put(56, suggestResultDTOListLv1);
        put(57, suggestResultDTOListLv1);
        put(58, suggestResultDTOListLv1);
        put(59, suggestResultDTOListLv1);
        put(60, suggestResultDTOListLv1);
        put(61, suggestResultDTOListLv1);
        put(62, suggestResultDTOListLv1);
        put(63, suggestResultDTOListLv1);
        put(64, suggestResultDTOListLv1);
        put(65, suggestResultDTOListLv1);
        put(66, suggestResultDTOListLv1);
        put(67, suggestResultDTOListLv1);
        put(68, suggestResultDTOListLv1);
        put(69, suggestResultDTOListLv1);
        put(70, suggestResultDTOListLv1);
        put(71, suggestResultDTOListLv1);
        put(72, suggestResultDTOListLv1);
        put(73, suggestResultDTOListLv1);
        put(74, suggestResultDTOListLv1);
        put(75, suggestResultDTOListLv1);
        put(76, suggestResultDTOListLv1);
        put(77, suggestResultDTOListLv1);
        put(78, suggestResultDTOListLv1);
        put(79, suggestResultDTOListLv1);
        put(80, suggestResultDTOListLv1);
        put(81, suggestResultDTOListLv1);
        put(82, suggestResultDTOListLv1);
        put(83, suggestResultDTOListLv1);
        put(84, suggestResultDTOListLv1);
        put(85, suggestResultDTOListLv1);
        put(86, suggestResultDTOListLv1);
        put(87, suggestResultDTOListLv1);
        put(88, suggestResultDTOListLv1);
        put(89, suggestResultDTOListLv1);
        put(90, suggestResultDTOListLv1);
        put(91, suggestResultDTOListLv1);
        put(92, suggestResultDTOListLv1);
        put(93, suggestResultDTOListLv1);
        put(94, suggestResultDTOListLv1);
        put(95, suggestResultDTOListLv1);
        put(96, suggestResultDTOListLv1);
        put(97, suggestResultDTOListLv1);
        put(98, suggestResultDTOListLv1);
        put(99, suggestResultDTOListLv1);
        put(100, suggestResultDTOListLv1);
    }};

    public static final String TOPIC_KHANCAP = "KHANCAP";
    public static final String TOPIC_TUVANCHIASE = "TUVANCHIASE";
    public static final String TOPIC_CHAMSOCSUCKHOE = "CHAMSOCSUCKHOE";
    public static final String TOPIC_KYNANGSONG = "KYNANGSONG";
    public static final String TOPIC_GOCHUALANH = "GOCHUALANH";
}
