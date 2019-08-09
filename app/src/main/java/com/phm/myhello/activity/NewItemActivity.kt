package com.phm.myhello.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import com.phm.myhello.R
import com.phm.myhello.fragment.NewItemFragment
import kotlinx.android.synthetic.main.toolbar_layout.*

private const val TAG = "NewItemActivity"

class NewItemActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        setSupportActionBar(toolbar)
        initInstance()
    }

    @SuppressLint("CommitTransaction")
    private fun initInstance() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.txt_new_item)

        supportFragmentManager.beginTransaction()
        val transaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(TAG) == null) {
            transaction.add(R.id.newItemContentContainer, NewItemFragment.newInstance(), TAG)
        } else {
            transaction.replace(R.id.newItemContentContainer, NewItemFragment.newInstance(), TAG)
        }
        transaction.commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
