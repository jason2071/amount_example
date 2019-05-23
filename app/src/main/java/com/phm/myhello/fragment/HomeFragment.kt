package com.phm.myhello.fragment


import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.provider.BaseColumns
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phm.myhello.Common
import com.phm.myhello.R
import com.phm.myhello.activity.MainActivity
import com.phm.myhello.adapter.AmountAdapter
import com.phm.myhello.database.AmountContract.AmountEntry.*
import com.phm.myhello.database.AmountDBHelper
import com.phm.myhello.model.Amount
import com.phm.myhello.model.AmountTotal
import com.phm.myhello.model.NewAmount
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var mActivity: MainActivity
    private lateinit var mDatabase: SQLiteDatabase
    private lateinit var amountAdapter: AmountAdapter
    private var amountIncome = 0
    private var amountExpense = 0
    private var amountTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbHelper = AmountDBHelper(mActivity)
        mDatabase = dbHelper.writableDatabase

        generateAmountList()
        calculatorAmount()
        buildRecyclerView()

        swipeRefresh.setOnRefreshListener {
            //Common.amountList.clear()
            Handler().postDelayed({
                swipeRefresh.isRefreshing = false
                //Common.amountList = getAmountList()
            }, 1000)
        }
    }

    private fun generateAmountList() {
        Common.amountList = getAmountList()
        for (i in 0 until Common.amountList.size) {
            val item = Common.amountList[i]
            val date = "${item.date} ${item.month}"
            Common.newAmount.add(NewAmount(item.id, date, item.type, item.title, 0))
        }
    }

    private fun buildRecyclerView() {
        amountAdapter = AmountAdapter(Common.newAmount)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        recyclerView.adapter = amountAdapter
    }

    private fun calculatorAmount() {
        for (i in 0 until Common.amountList.size) {
            val item = Common.amountList[i]
            if (item.type == Common.TYPE_INCOME) amountIncome += item.amount
            if (item.type == Common.TYPE_EXPENSE) amountExpense += item.amount
        }
        amountTotal = amountIncome - amountExpense
        mActivity.onShowAmount.setTotalAmount(AmountTotal(amountIncome, amountExpense, amountTotal))
    }

    private fun getAmountList(): ArrayList<Amount> {
        Common.amountList.clear()
        val cursor = getAllItem()
        cursor.moveToFirst()
        val amountList = ArrayList<Amount>()
        while (!cursor.isAfterLast) {
            amountList.add(getAmount(cursor)!!)
            cursor.moveToNext()
        }
        cursor.close()
        return amountList
    }

    private fun getAmount(cursor: Cursor): Amount? {
        try {
            val id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
            val date = cursor.getInt(cursor.getColumnIndex(COLUMN_DATE))
            val month = cursor.getInt(cursor.getColumnIndex(COLUMN_MONTH))
            val year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR))
            val type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT))
            return Amount(id, date, month, year, type, title, amount)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getAllItem(): Cursor {
        return mDatabase.query(
                TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , "${BaseColumns._ID} DESC"
        )
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}
