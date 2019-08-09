package com.phm.myhello

import com.phm.myhello.model.Amount
import com.phm.myhello.model.MonthAmount

object Parameter {
    const val TAG = "MainActivityA"
    const val RETURN_CODE = 1000
    const val TYPE_INCOME = "+"
    const val TYPE_EXPENSE = "-"
    var monthList = mutableListOf<MonthAmount>()
    var newAmountList = mutableListOf<MutableList<Amount>>()
}