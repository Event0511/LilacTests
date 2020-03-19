package com.example.lilactests.model.User;

import android.provider.BaseColumns;

/**
 * Created by Eventory on 2017/5/19.
 * Contract to create User table
 */
public class UserContract {
    public static final class FeedReaderContract {
        public FeedReaderContract() {
        }

        /* Inner class that defines the table contents */
        public static abstract class UserEntry implements BaseColumns {
            public static final String TABLE_NAME = "user";
            public static final String COLUMN_NAME_PASSWORD = "name";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_NICKNAME = "nickname";
            public static final String COLUMN_NAME_GENDER = "gender";
            public static final String COLUMN_NAME_BIRTHDAY = "birthday";
            public static final String COLUMN_NAME_ACCOUNT = "account";
            public static final String COLUMN_NAME_SIGNATURE = "signature";
            public static final String COLUMN_NAME_FACULTY = "faculty";
            public static final String COLUMN_NAME_FREQUENT_COURSE = "frequent_course";
            public static String COLUMN_NAME_NULLABLE = "NULL";
        }
    }
}
