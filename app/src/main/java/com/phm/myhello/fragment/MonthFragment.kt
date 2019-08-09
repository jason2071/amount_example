package com.phm.myhello.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phm.myhello.Parameter.newAmountList
import com.phm.myhello.R
import com.phm.myhello.activity.DetailActivity
import com.phm.myhello.adapter.AmountDetailAdapter
import com.phm.myhello.model.Amount
import kotlinx.android.synthetic.main.fragment_month.*

class MonthFragment : Fragment(), AmountDetailAdapter.OnItemClickDetailListener {

    private var listener: AmountDetailAdapter.OnItemClickDetailListener = this
    private lateinit var mActivity: DetailActivity
    private lateinit var amountDetailAdapter: AmountDetailAdapter
    private var monthId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            monthId = it.getInt(POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = newAmountList[monthId - 1]

        detailRecyclerView.setHasFixedSize(true)
        detailRecyclerView.layoutManager = LinearLayoutManager(mActivity)
        amountDetailAdapter = AmountDetailAdapter(mActivity, data, listener)
        detailRecyclerView.adapter = amountDetailAdapter
    }

    override fun setOnItemClickDetailListener(amount: Amount) {
        val dialog = DialogEditFragment()
        dialog.setEditData(amount)
        dialog.show(mActivity.supportFragmentManager, "DialogEditFragment")
    }

    companion object {
        private const val POSITION = "monthId"
        @JvmStatic
        fun newInstance(monthId: Int) = MonthFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, monthId)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DetailActivity) {
            mActivity = context
        }
    }
}
