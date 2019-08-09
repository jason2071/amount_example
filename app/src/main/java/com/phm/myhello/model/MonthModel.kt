package com.phm.myhello.model

import android.os.Parcel
import android.os.Parcelable

data class MonthModel(val id: Int, val name: String)

data class MonthAmount(
        val id: Int,
        val monthName: String?,
        var amountIncome: String? = "",
        var amountExpense: String? = "",
        var amountTotal: String? = "",
        var year: String? = ""
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(monthName)
        writeString(amountIncome)
        writeString(amountExpense)
        writeString(amountTotal)
        writeString(year)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MonthAmount> = object : Parcelable.Creator<MonthAmount> {
            override fun createFromParcel(source: Parcel): MonthAmount = MonthAmount(source)
            override fun newArray(size: Int): Array<MonthAmount?> = arrayOfNulls(size)
        }
    }
}