package com.phm.myhello.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.phm.myhello.Parameter.TYPE_INCOME
import com.phm.myhello.Parameter.monthList
import com.phm.myhello.Parameter.newAmountList
import com.phm.myhello.R
import com.phm.myhello.activity.DetailActivity
import com.phm.myhello.adapter.AmountDetailAdapter
import com.phm.myhello.database.DBManager
import com.phm.myhello.model.Amount
import com.phm.myhello.model.MonthAmount
import com.phm.myhello.utils.log
import kotlinx.android.synthetic.main.fragment_month.*

class MonthFragment : Fragment(), AmountDetailAdapter.OnItemClickDetailListener, DialogEditFragment.OnUpdateResponse {

    private var listener: AmountDetailAdapter.OnItemClickDetailListener = this
    private var dialogListener: DialogEditFragment.OnUpdateResponse = this
    private lateinit var mActivity: DetailActivity
    private lateinit var amountDetailAdapter: AmountDetailAdapter
    private lateinit var monthAmount: MonthAmount
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            monthAmount = it.getParcelable(MONTH_AMOUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbManager = DBManager(mActivity)

        tvAmountIncome.text = monthAmount.amountIncome
        tvAmountExpense.text = monthAmount.amountExpense
        tvAmountTotal.text = monthAmount.amountTotal

        val data = newAmountList[monthAmount.id - 1]

        if (data.isNotEmpty()) {
            emptyLayout.visibility = View.GONE
            detailRecyclerView.visibility = View.VISIBLE
            buildRecyclerView(data)
        } else {
            emptyLayout.visibility = View.VISIBLE
            detailRecyclerView.visibility = View.GONE
        }
    }

    private fun buildRecyclerView(data: MutableList<Amount>) {
        detailRecyclerView.setHasFixedSize(true)
        detailRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        amountDetailAdapter = AmountDetailAdapter(mActivity, data, listener)
        detailRecyclerView.adapter = amountDetailAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeItem(viewHolder.itemView.tag as Int)
            }
        }).attachToRecyclerView(detailRecyclerView)
    }

    private fun removeItem(id: Int) {
        dbManager.delete(id)
        dbManager.getDataByMonthAmount(monthAmount.year!!)
        setNewMonthList(monthAmount.id, newAmountList[monthAmount.id - 1])
        amountDetailAdapter.updateData(newAmountList[monthAmount.id - 1])
    }

    override fun setOnItemClickDetailListener(amount: Amount, position: Int) {
        val dialog = DialogEditFragment()
        dialog.setEditData(amount, position)
        dialog.setDialogListener(dialogListener)
        dialog.show(mActivity.supportFragmentManager, DIALOG_EDIT_TAG)
    }

    override fun setOnUpdateListener(detailData: Amount, position: Int) {
        newAmountList[monthAmount.id - 1][position] = detailData
        amountDetailAdapter.updateData(newAmountList[monthAmount.id - 1])
        setNewMonthList(detailData.month, newAmountList[monthAmount.id - 1])
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
        monthAmount = monthList[monthID - 1]

        tvAmountIncome.text = monthAmount.amountIncome
        tvAmountExpense.text = monthAmount.amountExpense
        tvAmountTotal.text = monthAmount.amountTotal
    }

    companion object {
        private const val DIALOG_EDIT_TAG = "DialogEditFragment"
        private const val MONTH_AMOUNT = "MonthAmount"
        @JvmStatic
        fun newInstance(monthAmount: MonthAmount) = MonthFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MONTH_AMOUNT, monthAmount)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DetailActivity) {
            mActivity = context
        }
    }
}
