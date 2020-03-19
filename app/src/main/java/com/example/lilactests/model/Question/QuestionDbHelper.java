package com.example.lilactests.model.Question;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.lilactests.app.LilacTestsApp;
import com.example.lilactests.model.Question.QuestionContract;

/**
 * Created by Eventory on 2017/5/19.
 *
 */
public class QuestionDbHelper extends SQLiteOpenHelper {
    private static final String NAME = "Questions.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOL_TYPE = " BOOLEAN";
    private static final String DATE_TYPE = " DATETIME";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL = " not null";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "create table " +
            QuestionContract.FeedReaderContract.QuestionEntry.TABLE_NAME + "(" +
            QuestionContract.FeedReaderContract.QuestionEntry._ID + " INTEGER PRIMARY KEY," +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ID_CODE + INTEGER_TYPE + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SUBJECT + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER + INTEGER_TYPE + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SELECTED_ANSWER + INTEGER_TYPE + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_A + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_B + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_C + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_D + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_EXPLANATION + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_FINISH_DATE + DATE_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_REVIEW_DATE + DATE_TYPE + NOT_NULL + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_FAVORITE + BOOL_TYPE + " Default false" + COMMA_SEP +
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_MISTAKE + BOOL_TYPE + " Default false" + ");";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuestionContract.FeedReaderContract.QuestionEntry.TABLE_NAME;

    public QuestionDbHelper(Context context) {
        super(context, NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
