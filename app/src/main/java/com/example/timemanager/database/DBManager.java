package com.example.timemanager.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timemanager.data.Goals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @SuppressLint("Range")
    public List<Goals> getGoals(){
        List<Goals> goalsList = new ArrayList<>();
        Cursor cursor = db.rawQuery(String.format("Select * From %s", DBConst.GOALS_TABLE_NAME),null);
        while (cursor.moveToNext()){
            Goals goals = new Goals();
            goals.setCount(cursor.getInt(cursor.getColumnIndex(DBConst.GOALS_COUNT)));
            goals.setCountNow(cursor.getInt(cursor.getColumnIndex(DBConst.GOALS_COUNT_NOW)));
            goals.setId(cursor.getInt(cursor.getColumnIndex(DBConst.GOALS_ID)));
            goals.setDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(DBConst.GOALS_DATE_END))));
            goals.setDescription(cursor.getString(cursor.getColumnIndex(DBConst.GOALS_DESCRIPTION)));
            goals.setUnit(cursor.getString(cursor.getColumnIndex(DBConst.GOALS_UNIT)));
            goals.setName(cursor.getString(cursor.getColumnIndex(DBConst.GOALS_NAME)));
            goalsList.add(goals);
        }
        cursor.close();
        return  goalsList;
    }
    public void addGoal(Goals goals){
        ContentValues cv = new ContentValues();
        cv.put(DBConst.GOALS_COUNT,goals.getCount());
        cv.put(DBConst.GOALS_NAME,goals.getName());
        cv.put(DBConst.GOALS_DESCRIPTION,goals.getDescription());
        cv.put(DBConst.GOALS_UNIT,goals.getUnit());
        cv.put(DBConst.GOALS_COUNT_NOW,goals.getCountNow());
        cv.put(DBConst.GOALS_DATE_END,goals.getDate().toString());
        db.insert(DBConst.GOALS_TABLE_NAME,null,cv);
    }
    public void updateGoal(Goals goals){
        ContentValues cv = new ContentValues();
        cv.put(DBConst.GOALS_COUNT,goals.getCount());
        cv.put(DBConst.GOALS_NAME,goals.getName());
        cv.put(DBConst.GOALS_DESCRIPTION,goals.getDescription());
        cv.put(DBConst.GOALS_UNIT,goals.getUnit());
        cv.put(DBConst.GOALS_COUNT_NOW,goals.getCountNow());
        cv.put(DBConst.GOALS_DATE_END,goals.getDate().toString());
        db.update(DBConst.GOALS_TABLE_NAME,cv, DBConst.GOALS_ID + " = " + goals.getId(),null);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDb() {
        db.close();
    }
}
