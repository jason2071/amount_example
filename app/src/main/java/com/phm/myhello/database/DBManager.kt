package com.phm.myhello.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.google.gson.Gson
import com.phm.myhello.Parameter.newAmountList
import com.phm.myhello.database.AmountContract.AmountEntry.*
import com.phm.myhello.model.Amount
import com.phm.myhello.model.MonthAmount
import com.phm.myhello.model.NewAmount
import com.phm.myhello.utils.MonthData
import com.phm.myhello.utils.log

class DBManager(mContext: Context) {

    private val dbHelper = AmountDBHelper(mContext)
    private var mDatabase: SQLiteDatabase
    private lateinit var mCursor: Cursor

    init {
        mDatabase = dbHelper.writableDatabase
    }

    fun insert(newAmount: NewAmount): Long {
        val cv = ContentValues()
        val dateArray = newAmount.date.split("-")
        cv.put(COLUMN_DATE, dateArray[0])
        cv.put(COLUMN_MONTH, dateArray[1])
        cv.put(COLUMN_YEAR, dateArray[2])
        cv.put(COLUMN_TYPE, newAmount.type)
        cv.put(COLUMN_TITLE, newAmount.title)
        cv.put(COLUMN_AMOUNT, newAmount.amount)
        return mDatabase.insert(TABLE_NAME, null, cv)
    }

    fun update(amount: Amount): Int {
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, amount.title)
        cv.put(COLUMN_AMOUNT, amount.amount)
        return mDatabase.update(TABLE_NAME, cv, "$_ID = ${amount.id}", null)
    }

    fun getYear(): MutableList<String> {
        val list = mutableListOf<String>()
        mCursor = mDatabase.rawQuery("SELECT DISTINCT $COLUMN_YEAR FROM $TABLE_NAME", null)
        for (i in 0 until mCursor.count) {
            if (!mCursor.moveToPosition(i)) {
                return mutableListOf()
            } else {
                val year = mCursor.getString(mCursor.getColumnIndex(COLUMN_YEAR))
                list.add(year)
            }
        }
        mCursor.close()
        return list
    }

    fun getAllDataByYear(mYear: String): MutableList<Amount> {
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
        mCursor.close()
        return list
    }

    fun getDataByMonthAmount(monthAmount: MonthAmount) {
        val janList = mutableListOf<Amount>()
        val febList = mutableListOf<Amount>()
        val marList = mutableListOf<Amount>()
        val aprList = mutableListOf<Amount>()
        val mayList = mutableListOf<Amount>()
        val junList = mutableListOf<Amount>()
        val julList = mutableListOf<Amount>()
        val augList = mutableListOf<Amount>()
        val sepList = mutableListOf<Amount>()
        val octList = mutableListOf<Amount>()
        val novList = mutableListOf<Amount>()
        val decList = mutableListOf<Amount>()

        val data = getAllDataByYear(monthAmount.year!!)

        for (i in 0 until data.size) {
            when (data[i].month) {
                MonthData.JAN.id -> janList.add(data[i])
                MonthData.FEB.id -> febList.add(data[i])
                MonthData.MAR.id -> marList.add(data[i])
                MonthData.APR.id -> aprList.add(data[i])
                MonthData.MAY.id -> mayList.add(data[i])
                MonthData.JUN.id -> junList.add(data[i])
                MonthData.JUL.id -> julList.add(data[i])
                MonthData.AUG.id -> augList.add(data[i])
                MonthData.SEP.id -> sepList.add(data[i])
                MonthData.OCT.id -> octList.add(data[i])
                MonthData.NOV.id -> novList.add(data[i])
                MonthData.DEC.id -> decList.add(data[i])
            }
        }
        newAmountList.clear()
        newAmountList.add(janList)
        newAmountList.add(febList)
        newAmountList.add(marList)
        newAmountList.add(aprList)
        newAmountList.add(mayList)
        newAmountList.add(junList)
        newAmountList.add(julList)
        newAmountList.add(augList)
        newAmountList.add(sepList)
        newAmountList.add(octList)
        newAmountList.add(novList)
        newAmountList.add(decList)

    }
}