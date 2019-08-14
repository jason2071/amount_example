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
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.phm.myhello.Parameter.TYPE_EXPENSE
import com.phm.myhello.Parameter.TYPE_INCOME
import com.phm.myhello.R
import com.phm.myhello.activity.NewItemActivity
import com.phm.myhello.database.DBManager
import com.phm.myhello.model.NewAmount
import com.phm.myhello.utils.log
import kotlinx.android.synthetic.main.fragment_new_item.*
import java.text.SimpleDateFormat
import java.util.*


class NewItemFragment : Fragment() {

    private lateinit var mActivity: NewItemActivity
    private lateinit var dbManager: DBManager
    private var mType = TYPE_EXPENSE
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

        buildAutoText()
        setCurrentDate()

        btnSaveData.setOnClickListener { saveNewItemData() }
    }

    private fun buildAutoText() {
        val keywords = dbManager.getKeyword()
        val adapter = ArrayAdapter(mActivity, android.R.layout.simple_dropdown_item_1line, keywords)
        editNewTitle.setAdapter(adapter)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NewItemActivity) {
            mActivity = context
        }
    }

    private fun saveNewItemData() {
        mType = if (radioIncome.isChecked) TYPE_INCOME else TYPE_EXPENSE
        mAmount = if (editAmount.text.toString().trim().isEmpty()) 0 else editAmount.text.toString().toInt()
        mDate = tvDate.text!!.toString()
        mTitle = editNewTitle.text.toString()

        // save amount
        val insertResult = dbManager.insert(NewAmount(mDate, mType, mTitle, mAmount)).toInt()

        // save keyword
        val keywords = dbManager.getKeyword()
        if (!keywords.contains(mTitle)) {
            dbManager.insertKeyword(mTitle)
        }

        // return to main
        val returnIntent = Intent()
        returnIntent.putExtra(INSERT_RESULT, insertResult)
        mActivity.setResult(Activity.RESULT_OK, returnIntent)
        mActivity.finish()
    }

    private fun setCurrentDate() {
        val c = Calendar.getInstance()
        tvDate.setText(simpleDateFormat(c.time)!!)
    }

    @SuppressLint("SimpleDateFormat")
    private fun simpleDateFormat(time: Date): String? {
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(time)
    }

    companion object {
        private const val INSERT_RESULT = "insertResult"
        @JvmStatic
        fun newInstance() = NewItemFragment().apply {
            arguments = Bundle().apply {}
        }
    }
}
