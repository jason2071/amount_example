package com.phm.myhello.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.phm.myhello.Parameter.RETURN_CODE
import com.phm.myhello.Parameter.TAG
import com.phm.myhello.R
import com.phm.myhello.database.DBManager
import com.phm.myhello.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private var doubleBackToExitPressedOnce = false
    private var onItemSelectedListener: AdapterView.OnItemSelectedListener = this
    private lateinit var dbManager: DBManager
    private var mYear = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initInstance()
    }

    @SuppressLint("RestrictedApi", "SimpleDateFormat")
    private fun initInstance() {
        supportActionBar?.title = ""
        spinnerYearLayout.visibility = View.VISIBLE
        fabNewItem.visibility = View.VISIBLE
        dbManager = DBManager(this)
        buildSpinnerYear()

        val c = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy")
        mYear = format.format(c.time)

        mainFragment()

        fabNewItem.setOnClickListener {
            startActivityForResult(Intent(this, NewItemActivity::class.java), RETURN_CODE)
        }
    }

    @SuppressLint("CommitTransaction")
    private fun mainFragment() {
        supportFragmentManager.beginTransaction()
        val transaction = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(TAG) == null) {
            transaction.add(R.id.contentContainer, MainFragment.newInstance(mYear), TAG)
        } else {
            transaction.replace(R.id.contentContainer, MainFragment.newInstance(mYear), TAG)
        }
        transaction.commitAllowingStateLoss()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mYear = parent?.getItemAtPosition(position).toString()
        val fm = supportFragmentManager.findFragmentByTag(TAG)
        if (fm is MainFragment) {
            fm.changeYear(mYear)
        }
    }

    private fun buildSpinnerYear() {
        val yearList = dbManager.getYear()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = adapter
        spinnerYear.onItemSelectedListener = onItemSelectedListener
    }

    @SuppressLint("CommitTransaction")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RETURN_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val saveStatus = data!!.getIntExtra("saveResult", 0)
                    if (saveStatus == -1) {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                        buildSpinnerYear()
                        val fm = supportFragmentManager.findFragmentByTag(TAG)
                        if (fm is MainFragment) {
                            fm.changeYear(mYear)
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
