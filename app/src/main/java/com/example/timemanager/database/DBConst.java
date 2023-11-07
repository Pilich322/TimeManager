package com.example.timemanager.database;

public class DBConst {

    public static final String DATABASE_NAME = "goals.db";
    public static final int DATABASE_VERSION = 1;
    public static final String GOALS_TABLE_NAME = "goals";
    public static final String GOALS_ID = "goal_id";
    public static final String GOALS_NAME= "goal_name";
    public static final String GOALS_UNIT = "goal_unit";
    public static final String GOALS_COUNT_NOW= "goal_count_now";
    public static final String GOALS_COUNT= "goal_count";
    public static final String GOALS_DATE_END= "goal_date";
    public static final String GOALS_IMAGE= "goal_image";

    public static final String GOALS_DESCRIPTION= "goal_description";


    public static final String CREATE_TABLE_GOALS = "create table if not exists " +
            GOALS_TABLE_NAME + " ( " + GOALS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            GOALS_UNIT + " text not null, " + GOALS_COUNT + " INTEGER not null, " +
            GOALS_COUNT_NOW + " INTEGER , " + GOALS_DESCRIPTION + " text not null, " +
            GOALS_NAME + " text not null, " +GOALS_IMAGE+" text ,"+
            GOALS_DATE_END + " text )";

    public static final String DELETE_TABLE_GOALS = "drop table if exists " + GOALS_TABLE_NAME;
}
