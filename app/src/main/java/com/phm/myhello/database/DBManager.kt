package com.phm.myhello.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.google.gson.Gson
import com.phm.myhello.database.AmountContract.AmountEntry.*
import com.phm.myhello.model.Amount
import com.phm.myhello.model.NewAmount
import com.phm.myhello.utils.log

class DBManager(mContext: Context) {

    private val dbHelper = AmountDBHelper(mContext)
    private var mDatabase: SQLiteDatabase
    private lateinit var mCursor: Cursor

    init {
        mDatabase = dbHelper.writableDatabase
    }

    fun insert(newAmount: NewAmount) {

        Gson().toJson(newAmount).log()

        val cv = ContentValues()
        val dateArray = newAmount.date.split("-")
        cv.put(COLUMN_DATE, dateArray[0])
        cv.put(COLUMN_MONTH, dateArray[1])
        cv.put(COLUMN_YEAR, dateArray[2])
        cv.put(COLUMN_TYPE, newAmount.type)
        cv.put(COLUMN_TITLE, newAmount.title)
        cv.put(COLUMN_AMOUNT, newAmount.amount)
        mDatabase.insert(TABLE_NAME, null, cv)
    }

    fun getAllItems(mYear: String): MutableList<Amount> {
        val list = mutableListOf<Amount>()
        mCursor = mDatabase.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_YEAR = $mYear", null)

        for (i in 0 until mCursor.count) {
            if (!mCursor.moveToPosition(i)) {
                return mutableListOf()
            } else {
                val id = mCursor.getInt(mCursor.getColumnIndex(_ID))
                val date = mCursor.getString(mCursor.getColumnIndex(COLUMN_DATE))
                val month = mCursor.getString(mCursor.getColumnIndex(COLUMN_MONTH))
                val year = mCursor.getString(mCursor.getColumnIndex(COLUMN_YEAR))
                val type = mCursor.getString(mCursor.getColumnIndex(COLUMN_TYPE))
                val title = mCursor.getString(mCursor.getColumnIndex(COLUMN_TITLE))
                val amount = mCursor.getString(mCursor.getColumnIndex(COLUMN_AMOUNT))
                list.add(Amount(id, date.toInt(), month.toInt(), year.toInt(), type, title, amount.toInt()))
            }
        }
        cursorClose()
        return list
    }


    private fun cursorClose() {
        mCursor.close()
    }
}