package com.example.quizappmasster.core.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.quizappmasster.core.DBHelper;
import com.example.quizappmasster.core.dto.QuestionDTO;
import com.example.quizappmasster.core.dto.ScoreDTO;

import java.util.ArrayList;
import java.util.List;

public class GeneralDAO {

    DBHelper dbHelper;

    public GeneralDAO(Context context) {
        dbHelper = new DBHelper(context);
    }
//
//
//    public ArrayList<QuestionDTO> findQuestionBySubjectAndLimit(Integer subject, Integer limit) {
//        ArrayList<QuestionDTO> lsData = new ArrayList<QuestionDTO>();
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        Log.e("sql", "SELECT * FROM tracnghiem_tbl WHERE subject=" + subject + " ORDER BY random() LIMIT " + limit);
//        Cursor cursor = db.rawQuery("SELECT * FROM tracnghiem_tbl WHERE subject=" + subject + " ORDER BY random() LIMIT " + limit, null);
//        cursor.moveToFirst();
//        Log.e(cursor.getCount() + "", ":" + cursor.getCount());
//        if (cursor.getCount() != 0) {
//            do {
//                QuestionDTO item;
//                item = new QuestionDTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
//                        cursor.getString(8), cursor.getString(9), "");
//                lsData.add(item);
//            } while (cursor.moveToNext());
//        }
//        return lsData;
//    }
//
//    public ArrayList<QuestionDTO> findQuestionByLimit(Integer limit) {
//        ArrayList<QuestionDTO> lsData = new ArrayList<QuestionDTO>();
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//
//        Cursor cursor = db.rawQuery("SELECT * FROM tracnghiem_tbl ORDER BY random() LIMIT " + limit, null);
//        cursor.moveToFirst();
//        Log.e(cursor.getCount() + "", ":" + cursor.getCount());
//        if (cursor.getCount() != 0) {
//            do {
//                QuestionDTO item;
//                item = new QuestionDTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
//                        cursor.getString(8), cursor.getString(9), "");
//                lsData.add(item);
//            } while (cursor.moveToNext());
//        }
//        return lsData;
//    }
//
//    public ArrayList<QuestionDTO> findQuestionBySubSubject(Integer limit, int[] subSubject) {
//        ArrayList<QuestionDTO> lsData = new ArrayList<QuestionDTO>();
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        StringBuilder sqlCondition = new StringBuilder();
//        for (int i = 0; i < subSubject.length; i++) {
//            if (i == 0) {
//                sqlCondition.append("WHERE subject = " + subSubject[i]);
//            } else {
//                sqlCondition.append(" OR subject = " + subSubject[i]);
//            }
//        }
//        String sql = "SELECT * FROM tracnghiem_tbl " + sqlCondition + " ORDER BY random() LIMIT " + limit;
//        Log.e("sql",sql);
//        Cursor cursor = db.rawQuery(sql , null);
//        cursor.moveToFirst();
//        Log.e(cursor.getCount() + "", ":" + cursor.getCount());
//        if (cursor.getCount() != 0) {
//            do {
//                QuestionDTO item;
//                item = new QuestionDTO(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
//                        cursor.getString(8), cursor.getString(9), "");
//                lsData.add(item);
//            } while (cursor.moveToNext());
//        }
//        return lsData;
//    }
//
//    public ArrayList<QuestionDTO> getAllQuestion() {
//        ArrayList<QuestionDTO> lsData = new ArrayList<QuestionDTO>();
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM tracnghiem_tbl", null);
//        cursor.moveToFirst();
//        Log.e(cursor.getCount() + "", ":" + cursor.getCount());
//        if (cursor.getCount() != 0) {
//            do {
//                QuestionDTO item;
//                item = new QuestionDTO(cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                        cursor.getString(4), cursor.getString(5), cursor.getString(6));
//                lsData.add(item);
//            } while (cursor.moveToNext());
//        }
//        return lsData;
//    }
//
//
//    public void insertScore(ScoreDTO scoreDTO) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("name", scoreDTO.getDisplayName());
//        values.put("num_true", scoreDTO.getNumTrue());
//        values.put("score", scoreDTO.getPoint());
//        values.put("type", scoreDTO.getType());
//        db.insert("score_tbl", null, values);
//        db.close();
//    }
//
//    public Cursor findScoreByType(Integer type) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM score_tbl WHERE type = '" + type + "' ORDER BY _id DESC", null);
//        Log.e("db.toString()", db.toString());
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
//
//    public List<ScoreDTO> findAllScore() {
//        List<ScoreDTO> scoreDTOList = new ArrayList<>();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM score_tbl ORDER BY _id DESC", null);
//        Log.e("db.toString()", db.toString());
//        cursor.moveToFirst();
//        Log.e(cursor.getCount() + "", ":" + cursor.getCount());
//        if (cursor.getCount() != 0) {
//            do {
//                ScoreDTO item;
//                item = new ScoreDTO(
//                        cursor.getInt(0),
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getInt(3),
//                        cursor.getString(4),
//                        cursor.getInt(5)
//                );
//                scoreDTOList.add(item);
//            } while (cursor.moveToNext());
//        }
//        return scoreDTOList;
//    }
//
//    public void saveAllQuestionDTO( List<QuestionDTO> listQuestionDTO, Context context){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("DELETE FROM tracnghiem_tbl");
//        int i =0;
//        for (QuestionDTO item: listQuestionDTO){
//
//            ContentValues values = new ContentValues();
//            values.put("question", item.getQuestion());
//            values.put("ans_a", item.getAnswerA());
//            values.put("ans_b", item.getAnswerB());
//            values.put("ans_c", item.getAnswerC());
//            values.put("ans_d", item.getAnswerD());
//            values.put("result", item.getResult());
//            values.put("subject", item.getSubject());
//            values.put("image", item.getImage());
//            db.insert("tracnghiem_tbl", null, values);
//            i++;
//        }
//        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
//        db.close();
//    }
}

