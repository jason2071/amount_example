package com.phm.myhello.activity

import android.content.Intent
import android.os.Bundle
import com.phm.myhello.R
import com.phm.myhello.fragment.HomeFragment
import com.phm.myhello.interfaces.OnShowAmountTotalInterface
import com.phm.myhello.model.AmountTotal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar

private const val TAG_HOME_FRAGMENT = "HomeFragment"

class MainActivity : BaseActivity(), OnShowAmountTotalInterface {

    private var onShowAmountTotalInterface: OnShowAmountTotalInterface = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstance()
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, HomeFragment.newInstance(), TAG_HOME_FRAGMENT)
                .commit()

        fab.setOnClickListener {
            startActivity(Intent(this, NewItemActivity::class.java))
        }
    }

    override fun setTotalAmount(amountTotal: AmountTotal) {
        tvDisplayIncome.text = amountTotal.income.toString()
        tvDisplayExpense.text = amountTotal.expense.toString()
        tvDisplayTotal.text = amountTotal.total.toString()
    }

    val onShowAmount: OnShowAmountTotalInterface
        get() = onShowAmountTotalInterface
}
