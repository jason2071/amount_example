package com.phm.myhello.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phm.myhello.Parameter.RETURN_CODE
import com.phm.myhello.Parameter.TYPE_INCOME
import com.phm.myhello.Parameter.monthList
import com.phm.myhello.R
import com.phm.myhello.activity.DetailActivity
import com.phm.myhello.activity.MainActivity
import com.phm.myhello.adapter.MonthAdapter
import com.phm.myhello.database.DBManager
import com.phm.myhello.model.Amount
import com.phm.myhello.model.MonthAmount
import com.phm.myhello.utils.MonthData
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), MonthAdapter.OnItemClickListener {

    private var listener: MonthAdapter.OnItemClickListener = this
    private lateinit var mActivity: MainActivity
    private lateinit var dbManager: DBManager
    private lateinit var mAdapter: MonthAdapter
    private var mYear = ""
    private var amountList = mutableListOf<Amount>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mYear = it.getString(YEAR)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbManager = DBManager(mActivity)
        monthList = MonthData.monthList()
        buildRecycleView()

        amountList = dbManager.getAllDataByYear(mYear)
        updateMonthList(amountList)
        mAdapter.updateData(monthList)
    }

    private fun buildRecycleView() {
        monthRecyclerView.setHasFixedSize(true)
        monthRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        mAdapter = MonthAdapter(listener)
        monthRecyclerView.adapter = mAdapter

    }

    fun changeYear(newYear: String) {
        mYear = newYear
        if (newYear.isNotEmpty()) {
            amountList = dbManager.getAllDataByYear(mYear)
            updateMonthList(amountList)
            mAdapter.updateData(monthList)
        }
    }

    private fun updateMonthList(amountList: MutableList<Amount>) {
        setNewMonthList(MonthData.JAN.id, amountList)
        setNewMonthList(MonthData.FEB.id, amountList)
        setNewMonthList(MonthData.MAR.id, amountList)
        setNewMonthList(MonthData.APR.id, amountList)
        setNewMonthList(MonthData.MAY.id, amountList)
        setNewMonthList(MonthData.JUN.id, amountList)
        setNewMonthList(MonthData.JUL.id, amountList)
        setNewMonthList(MonthData.AUG.id, amountList)
        setNewMonthList(MonthData.SEP.id, amountList)
        setNewMonthList(MonthData.OCT.id, amountList)
        setNewMonthList(MonthData.NOV.id, amountList)
        setNewMonthList(MonthData.DEC.id, amountList)
    }

    private fun setNewMonthList(monthID: Int, amountList: MutableList<Amount>) {
        var income = 0
        var expense = 0

        for (i in 0 until amountList.size) {
            val items = amountList[i]
            if (items.month == monthID) {
                if (items.type == TYPE_INCOME) {
                    income += items.amount
                } else {
                    expense += items.amount
                }
            }
        }
        monthList[monthID - 1].amountIncome = "$income"
        monthList[monthID - 1].amountExpense = "$expense"
        monthList[monthID - 1].amountTotal = "${(income - expense)}"
        monthList[monthID - 1].year = mYear
    }

    override fun setItemClickListener(monthAmount: MonthAmount) {
        DetailActivity.startActivityForResult(mActivity, monthAmount, RETURN_CODE)
    }

    companion object {
        private const val YEAR = "mYear"

        @JvmStatic
        fun newInstance(mYear: String) = MainFragment().apply {
            arguments = Bundle().apply {
                putString(YEAR, mYear)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        }
    }
}
