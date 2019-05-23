package com.phm.myhello

import com.phm.myhello.model.Amount
import com.phm.myhello.model.NewAmount

object Common {
    const val TYPE_INCOME = "+"
    const val TYPE_EXPENSE = "-"
    var amountList = ArrayList<Amount>()
    var newAmount = ArrayList<NewAmount>()
}