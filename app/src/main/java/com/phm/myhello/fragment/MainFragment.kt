package com.phm.myhello.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.phm.myhello.Parameter.monthList

import com.phm.myhello.R
import com.phm.myhello.activity.MainActivity
import com.phm.myhello.adapter.MonthAdapter
import com.phm.myhello.database.DBManager
import com.phm.myhello.model.Amount
import com.phm.myhello.utils.MonthAmount
import com.phm.myhello.utils.MonthData
import com.phm.myhello.utils.log
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
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbManager = DBManager(mActivity)
        monthList = MonthData.monthList()
        buildRecycleView()
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
            amountList = dbManager.getAllItems(mYear)

            newMonthList(amountList)

            mAdapter.updateData(monthList)
        }
    }

    private fun newMonthList(amountList: MutableList<Amount>) {

        for (i in 0 until amountList.size) {
            val items = amountList[i]

            "month: ${items.month}".log()

            when (items.month) {
                MonthData.JAN.id -> {}
                MonthData.FEB.id -> {}
                MonthData.MAR.id -> {}
                MonthData.APR.id -> {}
                MonthData.MAY.id -> {}
                MonthData.JUN.id -> {}
                MonthData.JUL.id -> {}
                MonthData.AUG.id -> {}
                MonthData.SEP.id -> {}
                MonthData.OCT.id -> {}
                MonthData.NOV.id -> {}
                MonthData.DEC.id -> {}
            }
        }

    }

    override fun setItemClickListener(monthAmount: MonthAmount) {
        Gson().toJson(monthAmount).log()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment().apply {
            arguments = Bundle().apply {}
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        }
    }
}
