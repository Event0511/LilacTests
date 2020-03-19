package com.example.lilactests.model.Question;

import android.provider.BaseColumns;

/**
 * Created by Eventory on 2017/5/19.
 * Contract to create User table
 */
public class QuestionContract {
    public static final class FeedReaderContract {
        public FeedReaderContract() {
        }

        /* Inner class that defines the table contents */
        public static abstract class QuestionEntry implements BaseColumns {
            public static final String TABLE_NAME = "question";
            public static final String COLUMN_NAME_ID_CODE = "id_code";//标识码
            public static final String COLUMN_NAME_SUBJECT = "subject";//题目
            public static final String COLUMN_NAME_ANSWER = "answer";//答案
            public static final String COLUMN_NAME_SELECTED_ANSWER = "selected_answer";//用户选中的答案
            public static final String COLUMN_NAME_ANSWER_A = "answer_a";//四个选项
            public static final String COLUMN_NAME_ANSWER_B = "answer_b";
            public static final String COLUMN_NAME_ANSWER_C = "answer_c";
            public static final String COLUMN_NAME_ANSWER_D = "answer_d";
            public static final String COLUMN_NAME_EXPLANATION = "explanation";//详情
            public static final String COLUMN_NAME_FINISH_DATE = "finish_date";
            public static final String COLUMN_NAME_REVIEW_DATE = "review_date";
            public static final String COLUMN_NAME_IS_FAVORITE = "is_favorite";
            public static final String COLUMN_NAME_IS_MISTAKE = "is_mistake";
            public static String COLUMN_NAME_NULLABLE = "NULL";
        }
    }
}
