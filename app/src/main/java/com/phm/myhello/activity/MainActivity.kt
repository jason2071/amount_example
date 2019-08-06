package com.phm.myhello.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.phm.myhello.Parameter.RETURN_CODE
import com.phm.myhello.Parameter.TAG_MAIN_FRAGMENT
import com.phm.myhello.R
import com.phm.myhello.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private var onItemSelectedListener: AdapterView.OnItemSelectedListener = this
    private var mYear = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInstance()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        buildSpinnerYear()

        val c = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy")
        mYear = format.format(c.time)

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, MainFragment.newInstance(), TAG_MAIN_FRAGMENT)
                .commit()

        fab.setOnClickListener {
            startActivityForResult(Intent(this, NewItemActivity::class.java), RETURN_CODE)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mYear = parent?.getItemAtPosition(position).toString()

        val fm = supportFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT) as MainFragment
        fm.changeYear(mYear)
    }

    private fun buildSpinnerYear() {
        val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.yearItem,
                android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = adapter
        spinnerYear.onItemSelectedListener = onItemSelectedListener
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RETURN_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val fm = supportFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT) as MainFragment
                fm.changeYear(mYear)
            }
        }
    }
}
