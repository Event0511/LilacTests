package com.example.lilactests.model.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.lilactests.BuildConfig;
import com.example.lilactests.model.domain.Note;
import com.example.lilactests.utils.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wing on 2016/4/13.
 * The Model Implement.
 */
public class NoteModel implements INoteModel {

    private static final String TAG = "NoteModel";
    private SQLiteOpenHelper mDbHelper;
    public static final String[] PROJECTION = new String[]{
            NoteContract.FeedReaderContract.NoteEntry._ID,
            NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_TITLE,
            NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_CONTENT,
            NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM,
            NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM_TIME,
            NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_CREATE_TIME,
    };

    public NoteModel(Context context) {
        this.mDbHelper = new NoteDbHelper(context);
    }

    @Override
    public boolean addNote(Note note) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        if (note.id != 0) {
            values.put(NoteContract.FeedReaderContract.NoteEntry._ID, note.id);
        }
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_TITLE, note.title);
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_CONTENT, note.content);
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM, note.hasAlarm);

        String dateTime = TimeUtils.formatDateTime(note.date);
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM_TIME, dateTime);

        String createDate = TimeUtils.formatDateTime(note.createDate);
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_CREATE_TIME, createDate);
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                NoteContract.FeedReaderContract.NoteEntry.TABLE_NAME,
                NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_NULLABLE,
                values);
        mDbHelper.close();
        return newRowId != -1;
    }

    @Override
    public boolean updateNote(Note note) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_TITLE, note.title);
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_CONTENT, note.content);
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM, note.hasAlarm);

        String dateTime = TimeUtils.formatDateTime(note.date);
        values.put(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM_TIME, dateTime);

        int affectRows = db.update(
                NoteContract.FeedReaderContract.NoteEntry.TABLE_NAME,
                values, NoteContract.FeedReaderContract.NoteEntry._ID + " = ?",
                new String[]{String.valueOf(note.id)});
        return affectRows != 0;
    }

    @Override
    public void deleteNote(Long id) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = NoteContract.FeedReaderContract.NoteEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        int result = db.delete(NoteContract.FeedReaderContract.NoteEntry.TABLE_NAME, selection, selectionArgs);
        Log.i(TAG, "result ::" + result);
        //代替断言
        // assert result!=0:"delete 0";
//        if (BuildConfig.DEBUG) {
//            Assert.assertTrue("delete 0", result == 0);
//        }
    }

    @Override
    public List<Note> selectAll() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                NoteContract.FeedReaderContract.NoteEntry.TABLE_NAME,                   // The table to query
                PROJECTION,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        List<Note> notes = new CopyOnWriteArrayList<>();
        if (cursor.getCount() == 0) {
            return notes;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Note item = getNoteAndMoveNext(cursor);
            notes.add(item);
        }
        cursor.close();
        return notes;
    }

    @Override
    public Note selectNote(long id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                NoteContract.FeedReaderContract.NoteEntry.TABLE_NAME,                   // The table to query
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
        Note note = getNoteAndMoveNext(cursor);

        cursor.close();
        return note;
    }

    @NonNull
    private Note getNoteAndMoveNext(Cursor cursor) {
        long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(NoteContract.FeedReaderContract.NoteEntry._ID));
        String title = cursor.getString(
                cursor.getColumnIndexOrThrow(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_TITLE));
        String content = cursor.getString(
                cursor.getColumnIndexOrThrow(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_CONTENT));
        long alarmL = cursor.getLong(
                cursor.getColumnIndexOrThrow(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM));
        boolean alarm = alarmL != 0;

        String alarmTimeStr = cursor.getString(
                cursor.getColumnIndexOrThrow(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_ALARM_TIME));
        Date date = TimeUtils.parse(alarmTimeStr);

        String createTimeStr = cursor.getString(
                cursor.getColumnIndexOrThrow(NoteContract.FeedReaderContract.NoteEntry.COLUMN_NAME_CREATE_TIME));
        Date createTime = TimeUtils.parse(createTimeStr);

        Note item = new Note(itemId, title, content, alarm, date, createTime);
        cursor.moveToNext();
        return item;
    }
}
