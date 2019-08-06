package com.phm.myhello.utils

object MonthData {
    val JAN = MonthModel(1, "มกราคม")
    val FEB = MonthModel(2, "กุมภาพันธ์")
    val MAR = MonthModel(3, "มีนาคม")
    val APR = MonthModel(4, "เมษายน")
    val MAY = MonthModel(5, "พฤษภาคม")
    val JUN = MonthModel(6, "มิถุนายน")
    val JUL = MonthModel(7, "กรกฎาคม")
    val AUG = MonthModel(8, "สิงหาคม")
    val SEP = MonthModel(9, "กันยายน")
    val OCT = MonthModel(10, "ตุลาคม")
    val NOV = MonthModel(11, "พฤษจิกายน")
    val DEC = MonthModel(12, "ธันวาคม")

    fun monthList(): MutableList<MonthAmount> {
        val list = mutableListOf<MonthAmount>()
        list.add(MonthAmount(1, "มกราคม"))
        list.add(MonthAmount(2, "กุมภาพันธ์"))
        list.add(MonthAmount(3, "มีนาคม"))
        list.add(MonthAmount(4, "เมษายน"))
        list.add(MonthAmount(5, "พฤษภาคม"))
        list.add(MonthAmount(6, "มิถุนายน"))
        list.add(MonthAmount(7, "กรกฎาคม"))
        list.add(MonthAmount(8, "สิงหาคม"))
        list.add(MonthAmount(9, "กันยายน"))
        list.add(MonthAmount(10, "ตุลาคม"))
        list.add(MonthAmount(11, "พฤษจิกายน"))
        list.add(MonthAmount(12, "ธันวาคม"))
        return list
    }

    fun getMonthNameByNumber(number: Int): String {
        return when (number) {
            MonthData.JAN.id -> MonthData.JAN.name
            MonthData.FEB.id -> MonthData.FEB.name
            MonthData.MAR.id -> MonthData.MAR.name
            MonthData.APR.id -> MonthData.APR.name
            MonthData.MAY.id -> MonthData.MAY.name
            MonthData.JUN.id -> MonthData.JUN.name
            MonthData.JUL.id -> MonthData.JUL.name
            MonthData.AUG.id -> MonthData.AUG.name
            MonthData.SEP.id -> MonthData.SEP.name
            MonthData.OCT.id -> MonthData.OCT.name
            MonthData.NOV.id -> MonthData.NOV.name
            MonthData.DEC.id -> MonthData.DEC.name
            else -> ""
        }
    }

    fun getNumberByMonthName(monthName: String): Int {
        return when (monthName) {
            MonthData.JAN.name -> MonthData.JAN.id
            MonthData.FEB.name -> MonthData.FEB.id
            MonthData.MAR.name -> MonthData.MAR.id
            MonthData.APR.name -> MonthData.APR.id
            MonthData.MAY.name -> MonthData.MAY.id
            MonthData.JUN.name -> MonthData.JUN.id
            MonthData.JUL.name -> MonthData.JUL.id
            MonthData.AUG.name -> MonthData.AUG.id
            MonthData.SEP.name -> MonthData.SEP.id
            MonthData.OCT.name -> MonthData.OCT.id
            MonthData.NOV.name -> MonthData.NOV.id
            MonthData.DEC.name -> MonthData.DEC.id
            else -> 0
        }
    }
}

data class MonthModel(val id: Int, val name: String)
data class MonthAmount(val id: Int, val monthName: String, var amountIncome: Long = 0, var amountExpense: Long = 0, var amountTotal: Long = 0)