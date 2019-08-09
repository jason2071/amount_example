package com.phm.myhello.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.phm.myhello.R
import com.phm.myhello.model.Amount
import kotlinx.android.synthetic.main.fragment_dialog_edit.view.*

class DialogEditFragment : AppCompatDialogFragment() {

    private lateinit var detailData: Amount

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder = AlertDialog.Builder(activity!!)
        val inflater: LayoutInflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.fragment_dialog_edit, null)

        view.editTitle.setText(detailData.title)
        view.editMoney.setText(detailData.amount.toString())

        view.btnSave.setOnClickListener {
            val title = view.editTitle.text.toString()
            val money = view.editMoney.text.toString()
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }

    fun setEditData(amount: Amount) {
        this.detailData = amount
    }
}