package com.phm.myhello.database;

import android.provider.BaseColumns;

public class AmountContract {

    private AmountContract() {}

    public static final class AmountEntry implements BaseColumns {
        public static final String TABLE_NAME = "table_amount";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AMOUNT = "amount";
    }
}
