package com.example.quizappmasster.view.tracnghiem.slide;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizappmasster.R;
import com.example.quizappmasster.core.dto.QuestionDTO;

import java.util.ArrayList;


public class ScreenSlidePageFragment extends Fragment {
    ArrayList<QuestionDTO> arr_Ques;
    public static final String ARG_PAGE = "page";
    public static final String ARG_CHECKANSWER = "checkAnswer";
    private int mPageNumber;
    public int checkAns;

    TextView txtQuesTion;
    ImageView imgIcon;
    RadioGroup radioGroup;
    RadioButton radA, radB, radC, radD, radE;


    public ScreenSlidePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        txtQuesTion = rootView.findViewById(R.id.tvQuestion);
        imgIcon = rootView.findViewById(R.id.ivIcon);
        radA = rootView.findViewById(R.id.radA);
        radB = rootView.findViewById(R.id.radB);
        radC = rootView.findViewById(R.id.radC);
        radD = rootView.findViewById(R.id.radD);
        radE = rootView.findViewById(R.id.radE);
        radioGroup = rootView.findViewById(R.id.radGroup);


        return rootView;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arr_Ques = new ArrayList<QuestionDTO>();
        ScreenSlidePagerActivity slidePagerActivity = (ScreenSlidePagerActivity) getActivity();
        arr_Ques = slidePagerActivity.getData();
        mPageNumber = getArguments().getInt(ARG_PAGE);
        checkAns = getArguments().getInt(ARG_CHECKANSWER);
    }

    public static ScreenSlidePageFragment create(int pageNumber, int checkAnswer) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_CHECKANSWER, checkAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtQuesTion.setText(arr_Ques.get(mPageNumber).getQuestion());
        if (getItem(mPageNumber).getImage() == null) {
            imgIcon.setVisibility(View.GONE);// ẩn ảnh
        } else {
            imgIcon.setImageResource(getResources().getIdentifier(getItem(mPageNumber).getImage() + "",
                    "drawable", "com.example.appthitracnghiem_001"));
        }
        radA.setText("A. " + getItem(mPageNumber).getAnswerA());
        radB.setText("B. " + getItem(mPageNumber).getAnswerB());
        radC.setText("C. " + getItem(mPageNumber).getAnswerC());
        radD.setText("D. " + getItem(mPageNumber).getAnswerD());
        radE.setText("E. " + getItem(mPageNumber).getAnswerE());

        radA.setTag(getItem(mPageNumber).getPointA());
        radB.setTag(getItem(mPageNumber).getPointB());
        radC.setTag(getItem(mPageNumber).getPointC());
        radD.setTag(getItem(mPageNumber).getPointD());
        radE.setTag(getItem(mPageNumber).getPointE());

        if (checkAns != 0) {
            radA.setClickable(false);
            radB.setClickable(false);
            radC.setClickable(false);
            radD.setClickable(false);
            radE.setClickable(false);

//            getCheckAns(getItem(mPageNumber).getResult(), getItem(mPageNumber).getTraloi());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getItem(mPageNumber).setTraloi(getChoiceFromId(checkedId));
            }
        });
    }

    public QuestionDTO getItem(int position) {
        return arr_Ques.get(position);
    }

    private String getChoiceFromId(int ID) {
        if (ID == R.id.radA) {
            getItem(mPageNumber).setPointTraloi((Integer) radA.getTag());
            return "A";
        } else if (ID == R.id.radB) {
            getItem(mPageNumber).setPointTraloi((Integer) radB.getTag());
            return "B";
        } else if (ID == R.id.radC) {
            getItem(mPageNumber).setPointTraloi((Integer) radC.getTag());
            return "C";
        } else if (ID == R.id.radD) {
            getItem(mPageNumber).setPointTraloi((Integer) radD.getTag());
            return "D";
        } else if (ID == R.id.radE) {
            getItem(mPageNumber).setPointTraloi((Integer) radE.getTag());
            return "E";
        } else{
            getItem(mPageNumber).setPointTraloi(0);
            return "";
        }
    }



//    private void getCheckAns(String result, String ans) {
//        if (result.equals(ans)) {
//            if (result.equals("A")) {
//                radA.setBackgroundColor(Color.GREEN);
//            } else if (result.equals("B")) {
//                radB.setBackgroundColor(Color.GREEN);
//            } else if (result.equals("C")) {
//                radC.setBackgroundColor(Color.GREEN);
//            } else if (result.equals("D")) {
//                radD.setBackgroundColor(Color.GREEN);
//            } else ;
//        } else if (result != ans) {
//            if (result.equals("A")) {
//                radA.setBackgroundColor(Color.RED);
//            } else if (result.equals("B")) {
//                radB.setBackgroundColor(Color.RED);
//            } else if (result.equals("C")) {
//                radC.setBackgroundColor(Color.RED);
//            } else if (result.equals("D")) {
//                radD.setBackgroundColor(Color.RED);
//            } else ;
//
//        }
//    }
}
