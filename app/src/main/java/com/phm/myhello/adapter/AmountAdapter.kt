package com.phm.myhello.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phm.myhello.R
import com.phm.myhello.model.NewAmount
import kotlinx.android.synthetic.main.item_amount_child_layout.view.*
import kotlinx.android.synthetic.main.item_amount_layout.view.*

class AmountAdapter(private val amountList: ArrayList<NewAmount>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = when (viewType) {
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.item_amount_layout, parent, false)
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_amount_child_layout, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        }
        return AmountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (amountList.isEmpty()) 0 else amountList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AmountViewHolder -> holder.bindAmount(amountList[position], position)
            is ChildViewHolder -> holder.bindChild(amountList[position])
        }
    }

    class AmountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindAmount(amount: NewAmount, position: Int) {
            itemView.tvAmountDate?.let { it.text = position.toString() }

        }
    }

    class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindChild(amount: NewAmount) {
            itemView.tvAmountType?.let { it.text = amount.type }
            itemView.tvAmountTitle?.let { it.text = amount.title }
            itemView.tvAmountMoney?.let { it.text = amount.amount.toString() }
        }
    }
}