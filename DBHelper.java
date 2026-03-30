package com.vighnesh.smarteligibilitychecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "SchemesDB";
    public static final int DB_VERSION = 6;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE schemes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "minAge INTEGER," +
                "maxAge INTEGER," +
                "maxIncome INTEGER," +
                "category TEXT," +
                "occupation TEXT," +
                "education TEXT)");

        db.execSQL("INSERT INTO schemes VALUES (1,'Girl Merit Scholarship',16,25,200000,'General','Student','12th')");
        db.execSQL("INSERT INTO schemes VALUES (2,'OBC Scholarship',16,30,100000,'OBC','Student','12th')");
        db.execSQL("INSERT INTO schemes VALUES (3,'SC Scholarship',16,30,250000,'SC','Student','12th')");
        db.execSQL("INSERT INTO schemes VALUES (4,'Old Age Pension',60,100,100000,'General','Any','Any')");
        db.execSQL("INSERT INTO schemes VALUES (5,'PM Kisan',0,100,200000,'General','Farmer','Any')");
        db.execSQL("INSERT INTO schemes VALUES (6,'Ujjwala Yojana',18,60,100000,'General','Any','Any')");
        db.execSQL("INSERT INTO schemes VALUES (7,'PMEGP',18,60,1000000,'General','Entrepreneur','Any')");
        db.execSQL("INSERT INTO schemes VALUES (8,'Mudra Loan',18,60,1000000,'General','Business','Any')");
        db.execSQL("INSERT INTO schemes VALUES (9,'Kisan Credit Card',18,60,1000000,'General','Farmer','Any')");
        db.execSQL("INSERT INTO schemes VALUES (10,'Sukanya Samriddhi',0,10,1000000,'General','Any','Any')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS schemes");
        onCreate(db);
    }
}