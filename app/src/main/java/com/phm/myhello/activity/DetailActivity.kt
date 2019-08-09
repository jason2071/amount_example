package com.phm.myhello.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.phm.myhello.R
import com.phm.myhello.database.DBManager
import com.phm.myhello.fragment.MonthFragment
import com.phm.myhello.fragment.NewItemFragment
import com.phm.myhello.model.MonthAmount
import kotlinx.android.synthetic.main.toolbar_layout.*

class DetailActivity : BaseActivity() {

    private lateinit var monthAmount: MonthAmount
    private lateinit var dbManager: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        getBundle()
        initInstance()
    }

    @SuppressLint("CommitTransaction")
    private fun initInstance() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "${monthAmount.monthName} ${monthAmount.year}"

        dbManager = DBManager(this)
        dbManager.getDataByMonthAmount(monthAmount)

        supportFragmentManager.beginTransaction()
        val transaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(TAG) == null) {
            transaction.add(R.id.monthContent, MonthFragment.newInstance(monthAmount.id), TAG)
        } else {
            transaction.replace(R.id.monthContent, MonthFragment.newInstance(monthAmount.id), TAG)
        }
        transaction.commitAllowingStateLoss()
    }

    private fun getBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            monthAmount = bundle.getParcelable(MONTH_AMOUNT)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "DetailActivity"
        private const val MONTH_AMOUNT = "monthAmount"

        fun startActivity(activity: Activity, monthAmount: MonthAmount) {
            val bundle = Bundle()
            bundle.putParcelable(MONTH_AMOUNT, monthAmount)
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtras(bundle)
            activity.startActivity(intent)
        }
    }
}
