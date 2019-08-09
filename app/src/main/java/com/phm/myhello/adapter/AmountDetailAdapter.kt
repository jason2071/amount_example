package com.phm.myhello.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phm.myhello.Parameter.TYPE_EXPENSE
import com.phm.myhello.Parameter.TYPE_INCOME
import com.phm.myhello.R
import com.phm.myhello.model.Amount
import kotlinx.android.synthetic.main.item_amount_detail_layout.view.*

class AmountDetailAdapter(private val mContext: Context, private val list: MutableList<Amount>, private val listener: OnItemClickDetailListener)
    : RecyclerView.Adapter<AmountDetailAdapter.AmountDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmountDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_amount_detail_layout, parent, false)
        return AmountDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AmountDetailViewHolder, position: Int) {
        holder.bindDetail(list[position])
    }

    interface OnItemClickDetailListener {
        fun setOnItemClickDetailListener(amount: Amount)
    }

    inner class AmountDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindDetail(amount: Amount) {
            itemView.tvDetailTitle.text = amount.title

            when (amount.type) {
                TYPE_INCOME -> {
                    itemView.tvDetailMoney.text = "+${amount.amount}"
                    itemView.tvDetailMoney.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark))
                }
                TYPE_EXPENSE -> {
                    itemView.tvDetailMoney.text = "-${amount.amount}"
                    itemView.tvDetailMoney.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark))
                }
            }

            itemView.setOnClickListener {
                listener.setOnItemClickDetailListener(amount)
            }
        }
    }
}