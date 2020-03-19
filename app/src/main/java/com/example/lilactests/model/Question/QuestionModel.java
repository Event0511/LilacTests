package com.example.lilactests.model.Question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.lilactests.BuildConfig;
import com.example.lilactests.model.domain.Question;
import com.example.lilactests.utils.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Eventory on 2017/5/19.
 * The Model Implement.
 */
public class QuestionModel implements IQuestionModel {

    private static final String TAG = "QuestionModel";
    private SQLiteOpenHelper mDbHelper;
    public static final String[] PROJECTION = new String[]{
            QuestionContract.FeedReaderContract.QuestionEntry._ID,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ID_CODE,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SUBJECT,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SELECTED_ANSWER,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_A,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_B,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_C,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_D,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_EXPLANATION,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_FINISH_DATE,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_REVIEW_DATE,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_FAVORITE,
            QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_MISTAKE

    };

    public QuestionModel(Context context) {
        this.mDbHelper = new QuestionDbHelper(context);
    }

    @Override
    public boolean addQuestion(Question question) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        if (question.ID != 0) {
            values.put(QuestionContract.FeedReaderContract.QuestionEntry._ID, question.ID);
        }
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ID_CODE, question.idCode);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SUBJECT, question.subject);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER, question.answer);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SELECTED_ANSWER, question.selectedAnswer);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_A, question.answerA);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_B, question.answerB);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_C, question.answerC);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_D, question.answerD);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_EXPLANATION, question.explanation);

        String finishDate = TimeUtils.formatDateTime(question.finishDate);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_FINISH_DATE, finishDate);
        String reviewDate = TimeUtils.formatDateTime(question.reviewDate);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_REVIEW_DATE, reviewDate);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_FAVORITE, question.isFavorite);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_MISTAKE, question.isMistake);
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                QuestionContract.FeedReaderContract.QuestionEntry.TABLE_NAME,
                QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_NULLABLE,
                values);
        mDbHelper.close();
        return newRowId != -1;
    }

    @Override
    public boolean updateQuestion(Question question) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SUBJECT, question.subject);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER, question.answer);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SELECTED_ANSWER, question.selectedAnswer);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_A, question.answerA);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_B, question.answerB);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_C, question.answerC);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_D, question.answerD);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_EXPLANATION, question.explanation);

        String reviewDate = TimeUtils.formatDateTime(question.reviewDate);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_REVIEW_DATE, reviewDate);

        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_FAVORITE, question.isFavorite);
        values.put(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_MISTAKE, question.isMistake);

        int affectRows = db.update(
                QuestionContract.FeedReaderContract.QuestionEntry.TABLE_NAME,
                values, QuestionContract.FeedReaderContract.QuestionEntry._ID + " = ?",
                new String[]{String.valueOf(question.ID)});
        return affectRows != 0;
    }

    @Override
    public void deleteQuestion(Long id) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = QuestionContract.FeedReaderContract.QuestionEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        int result = db.delete(QuestionContract.FeedReaderContract.QuestionEntry.TABLE_NAME, selection, selectionArgs);
        Log.i(TAG, "result ::" + result);
        //代替断言
        // assert result!=0:"delete 0";
//        if (BuildConfig.DEBUG) {
//            Assert.assertTrue("delete 0", result == 0);
//        }
    }

    @Override
    public List<Question> selectAll() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                QuestionContract.FeedReaderContract.QuestionEntry.TABLE_NAME,                   // The table to query
                PROJECTION,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        List<Question> questions = new CopyOnWriteArrayList<>();
        if (cursor.getCount() == 0) {
            return questions;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Question item = getQuestionAndMoveNext(cursor);
            questions.add(item);
        }
        cursor.close();
        return questions;
    }

    @Override
    public Question selectQuestion(long id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                QuestionContract.FeedReaderContract.QuestionEntry.TABLE_NAME,                   // The table to query
                PROJECTION,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        boolean hasItem = cursor.moveToFirst();
        if (BuildConfig.DEBUG && !hasItem) {
            throw new AssertionError("id not exist");
        }
        Question question = getQuestionAndMoveNext(cursor);

        cursor.close();
        return question;
    }

    @NonNull
    private Question getQuestionAndMoveNext(Cursor cursor) {
        long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry._ID));
        long idCode = cursor.getLong(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ID_CODE));
        String subject = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SUBJECT));
        int answer = cursor.getInt(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER));
        int selectedAnswer = cursor.getInt(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_SELECTED_ANSWER));
        String answerA = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_A));
        String answerB = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_B));
        String answerC = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_C));
        String answerD = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_ANSWER_D));
        String explanation = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_EXPLANATION));

        String finishDateStr = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_FINISH_DATE));
        Date finishDate = TimeUtils.parseText(finishDateStr);
        String reviewDateStr = cursor.getString(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_REVIEW_DATE));
        Date reviewDate = TimeUtils.parseText(reviewDateStr);

        boolean isFavorite = cursor.getInt(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_FAVORITE))>0;
        boolean isMistake = cursor.getInt(
                cursor.getColumnIndexOrThrow(QuestionContract.FeedReaderContract.QuestionEntry.COLUMN_NAME_IS_MISTAKE))>0;

        Question item = new Question(itemId, idCode, subject, answer, selectedAnswer, answerA, answerB, answerC, answerD, explanation, finishDate, reviewDate, isFavorite, isMistake);
        cursor.moveToNext();
        return item;
    }
}
