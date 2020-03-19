package com.example.lilactests.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import android.util.Log;

import com.example.lilactests.BuildConfig;
import com.example.lilactests.model.domain.User;
import com.example.lilactests.utils.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Eventory on 2017/5/19.
 * The Model Implement.
 */
public class UserModel implements IUserModel {

    private static final String TAG = "UserModel";
    private SQLiteOpenHelper mDbHelper;
    public static final String[] PROJECTION = new String[]{
            UserContract.FeedReaderContract.UserEntry._ID,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_ACCOUNT,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NAME,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NICKNAME,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_GENDER,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FACULTY,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_BIRTHDAY,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_SIGNATURE,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FREQUENT_COURSE,
            UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_PASSWORD,
    };

    public UserModel(Context context) {
        this.mDbHelper = new UserDbHelper(context);
    }

    @Override
    public boolean addUser(User user) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        if (user.getId() != 0) {
            values.put(UserContract.FeedReaderContract.UserEntry._ID, user.getId());
        }
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_ACCOUNT, user.getAccount());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NAME, user.getName());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NICKNAME, user.getNickname());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_GENDER, user.getGender());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_SIGNATURE, user.getSignature());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FACULTY, user.getFaculty());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FREQUENT_COURSE, user.getFrequentCourse());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_PASSWORD,user.getPassword());
        String birthday = TimeUtils.formatDate(user.getBirthDay());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_BIRTHDAY, birthday);
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                UserContract.FeedReaderContract.UserEntry.TABLE_NAME,
                UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NULLABLE,
                values);
        mDbHelper.close();
        return newRowId != -1;
    }

    @Override
    public boolean updateUser(User user) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_ACCOUNT, user.getAccount());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NAME, user.getName());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NICKNAME, user.getNickname());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_GENDER, user.getGender());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_SIGNATURE, user.getSignature());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FACULTY, user.getFaculty());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FREQUENT_COURSE, user.getFrequentCourse());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_PASSWORD, user.getPassword());
        String birthday = TimeUtils.formatDate(user.getBirthDay());
        values.put(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_BIRTHDAY, birthday);

        int affectRows = db.update(
                UserContract.FeedReaderContract.UserEntry.TABLE_NAME,
                values, UserContract.FeedReaderContract.UserEntry._ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        return affectRows != 0;
    }

    @Override
    public void deleteUser(Long id) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = UserContract.FeedReaderContract.UserEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        int result = db.delete(UserContract.FeedReaderContract.UserEntry.TABLE_NAME, selection, selectionArgs);
        Log.i(TAG, "result ::" + result);
        //代替断言
        // assert result!=0:"delete 0";
//        if (BuildConfig.DEBUG) {
//            Assert.assertTrue("delete 0", result == 0);
//        }
    }

    @Override
    public List<User> selectAll() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                UserContract.FeedReaderContract.UserEntry.TABLE_NAME,                   // The table to query
                PROJECTION,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        List<User> users = new CopyOnWriteArrayList<>();
        if (cursor.getCount() == 0) {
            return users;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            User item = getUserAndMoveNext(cursor);
            users.add(item);
        }
        cursor.close();
        return users;
    }

    @Override
    public User selectUser(long id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                UserContract.FeedReaderContract.UserEntry.TABLE_NAME,                   // The table to query
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
        User user = getUserAndMoveNext(cursor);

        cursor.close();
        return user;
    }

    @NonNull
    private User getUserAndMoveNext(Cursor cursor) {
        long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry._ID));
        long account = cursor.getLong(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_ACCOUNT));
        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NAME));
        String nickname = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_NICKNAME));
        String gender = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_GENDER));
        String signature = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_SIGNATURE));
        String faculty = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FACULTY));
        String frequentCourse = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_FREQUENT_COURSE));
        String password = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_PASSWORD)
        );

        String birthdayStr = cursor.getString(
                cursor.getColumnIndexOrThrow(UserContract.FeedReaderContract.UserEntry.COLUMN_NAME_BIRTHDAY));
        Date birthday = TimeUtils.parseDate(birthdayStr);

        User item = new User(itemId, account, name, password, nickname, gender, signature, birthday, faculty, frequentCourse);
        cursor.moveToNext();
        return item;
    }
}
