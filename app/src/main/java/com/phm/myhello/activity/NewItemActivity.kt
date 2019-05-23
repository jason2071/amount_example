package com.phm.myhello.activity

import android.os.Bundle
import android.view.MenuItem
import com.phm.myhello.R
import com.phm.myhello.fragment.NewItemFragment
import kotlinx.android.synthetic.main.toolbar_layout.*


class NewItemActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)
        initInstance()
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.txt_new_item)

        supportFragmentManager.beginTransaction()
                .replace(R.id.newItemContentContainer, NewItemFragment.newInstance())
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
