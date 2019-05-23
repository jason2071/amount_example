package com.phm.myhello.activity


import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import com.phm.myhello.interfaces.MessageDialogInterface
import dmax.dialog.SpotsDialog
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

open class BaseActivity : AppCompatActivity() {

    private lateinit var mTextViewScreenTitle: TextView
    private lateinit var mImageButtonBack: ImageButton
    private lateinit var mLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        mLoading = SpotsDialog.Builder().setContext(this).build()
    }

    fun showDialogMessage(title: String, message: String, isCancelable: Boolean, messageDialogInterface: MessageDialogInterface) {
        val messageDialog = android.support.v7.app.AlertDialog.Builder(this)
        messageDialog.setTitle(title)
        messageDialog.setMessage(message)
        messageDialog.setCancelable(isCancelable)
        messageDialog.setPositiveButton("YES") { dialog, which ->
            messageDialogInterface.onClickPositiveButton("onClickPositiveButton : YES")
        }
        messageDialog.setNegativeButton("No") { dialog, which ->
            messageDialogInterface.onClickNegativeButton("onClickNegativeButton : NO")
        }
        messageDialog.show()
    }

    fun setScreenTitle(resId: Int) {
        mTextViewScreenTitle.text = getString(resId)
    }

    fun setScreenTitle(title: String) {
        mTextViewScreenTitle.text = title
    }

    fun getBackButton(): ImageButton {
        return mImageButtonBack;
    }

    fun showLoading() {
        if (!mLoading.isShowing) {
            mLoading.show()
        }
    }

    fun hideLoading() {
        if (mLoading.isShowing) {
            mLoading.dismiss()
        }
    }

}
