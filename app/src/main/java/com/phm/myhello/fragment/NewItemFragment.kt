package com.phm.myhello.fragment


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.phm.myhello.Parameter
import com.phm.myhello.R
import com.phm.myhello.activity.NewItemActivity
import com.phm.myhello.database.DBManager
import com.phm.myhello.model.NewAmount
import com.phm.myhello.utils.log
import kotlinx.android.synthetic.main.fragment_new_item.*
import java.text.SimpleDateFormat
import java.util.*


class NewItemFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var onItemSelectedListener: AdapterView.OnItemSelectedListener = this
    private lateinit var mActivity: NewItemActivity
    private lateinit var dbManager: DBManager
    private var mType = Parameter.TYPE_EXPENSE
    private var mAmount = 0
    private var mTitle = ""
    private var mDate = ""
    private lateinit var incomeArray: Array<String>
    private lateinit var expenseArray: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbManager = DBManager(mActivity)

        incomeArray = resources.getStringArray(R.array.incomeItem)
        expenseArray = resources.getStringArray(R.array.expenseItem)

        buildSpinner()
        setCurrentDate()

        radioIncome.setOnClickListener {
            radioIncome.isChecked = true
            buildSpinner()
        }

        radioExpense.setOnClickListener {
            radioExpense.isChecked = true
            buildSpinner()
        }

        btnSaveData.setOnClickListener { saveNewItemData() }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (position == incomeArray.size - 1 || position == expenseArray.size - 1) {
            editOther.visibility = View.VISIBLE
            tvOtherTitle.visibility = View.VISIBLE
        } else {
            editOther.visibility = View.GONE
            tvOtherTitle.visibility = View.GONE
        }

        mTitle = parent?.getItemAtPosition(position).toString()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NewItemActivity) {
            mActivity = context
        }
    }

    private fun saveNewItemData() {
        mType = if (radioIncome.isChecked) Parameter.TYPE_INCOME else Parameter.TYPE_EXPENSE
        mAmount = if (editAmount.text.toString().trim().isEmpty()) 0 else editAmount.text.toString().toInt()
        mDate = tvDate.text!!.toString()

        // save
        val result = dbManager.insert(NewAmount(mDate, mType, mTitle, mAmount)).toInt()

        val returnIntent = Intent()
        returnIntent.putExtra("saveResult", result)
        mActivity.setResult(Activity.RESULT_OK, returnIntent)
        mActivity.finish()
    }

    private fun setCurrentDate() {
        val c = Calendar.getInstance()
        tvDate.setText(simpleDateFormat(c.time)!!)
    }

    private fun buildSpinner() {
        val resource = if (radioIncome.isChecked) R.array.incomeItem else R.array.expenseItem

        val adapter = ArrayAdapter.createFromResource(
                mActivity,
                resource,
                android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = onItemSelectedListener
    }

    @SuppressLint("SimpleDateFormat")
    private fun simpleDateFormat(time: Date): String? {
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(time)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewItemFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}
