package com.hviewtech.wawupay.ui.fragment.wallet

import com.alibaba.android.vlayout.DelegateAdapter
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.base.BaseMvpFragment
import com.hviewtech.wawupay.base.MultiPresenter
import com.hviewtech.wawupay.bean.remote.wallet.BankCard
import com.hviewtech.wawupay.common.ext.setAdapters
import com.hviewtech.wawupay.contract.wallet.frag.BankCardContract
import com.hviewtech.wawupay.di.scope.ActivityScope
import com.hviewtech.wawupay.presenter.wallet.frag.BankCardPresenter
import com.hviewtech.wawupay.ui.activity.wallet.BankCardActivity
import com.hviewtech.wawupay.ui.adapter.wallet.BankCardAdapter
import com.hviewtech.wawupay.ui.adapter.wallet.BankCardAddAdapter
import kotlinx.android.synthetic.main.frag_bank_card.*
import java.util.*
import javax.inject.Inject

/**
 * @author su
 * @date 2018/3/23
 * @description
 */
@ActivityScope
class BankCardFragment @Inject constructor() : BaseMvpFragment(), BankCardContract.View, BankCardAdapter.IView,
  BankCardAddAdapter.IView {


  @Inject
  lateinit var mPresenter: BankCardPresenter

  private lateinit var mBankCardAdapter: BankCardAdapter

  override fun getLayoutId(): Int {
    return R.layout.frag_bank_card
  }


  public override fun initialize() {
    val adapters = LinkedList<DelegateAdapter.Adapter<*>>()
    val bankCardAddAdapter = BankCardAddAdapter(mContext, this)
    mBankCardAdapter = BankCardAdapter(mContext, this)
    adapters.add(bankCardAddAdapter)
    adapters.add(mBankCardAdapter)

    recyclerView.setAdapters(mContext, adapters)

    refreshLayout.setOnRefreshListener {
      mPresenter.getBankCardList()
    }



    mPresenter.getBankCardList()
  }

  override fun addBankCard() {

    AddBankFragment().title("Bank Card").show(fragmentManager, { mPresenter.getBankCardList() })
  }


  override fun showMyBankList(success: Boolean, bankCardList: List<BankCard>?) {
    refreshLayout.finishRefresh(success)

    if (success) {
      mBankCardAdapter.setDatas(bankCardList)
    }
  }

  override fun onClickManageBankCard(card: BankCard?) {
    val activity = activity
    if (activity != null && activity is BankCardActivity) {
      activity.showManageFragment(card)
    }
  }
}
