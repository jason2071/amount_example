package com.phm.myhello.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phm.myhello.R
import com.phm.myhello.model.MonthAmount
import kotlinx.android.synthetic.main.item_month_layout.view.*

class MonthAdapter(private val listener: OnItemClickListener)
    : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    private var monthList = mutableListOf<MonthAmount>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month_layout, parent, false)
        return MonthViewHolder(view)
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bindItem(monthList[position])
    }

    fun updateData(newList: MutableList<MonthAmount>) {
        monthList = newList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun setItemClickListener(monthAmount: MonthAmount)
    }

    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItem(items: MonthAmount) {

            itemView.tvMonthName.text = items.monthName
            itemView.tvAmountIncome.text = items.amountIncome
            itemView.tvAmountExpense.text = items.amountExpense
            itemView.tvAmountTotal.text = items.amountTotal

            itemView.setOnClickListener {
                listener.setItemClickListener(items)
            }
        }
    }
}