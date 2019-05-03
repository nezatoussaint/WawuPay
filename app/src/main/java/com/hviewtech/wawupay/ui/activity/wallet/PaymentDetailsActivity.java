package com.hviewtech.wawupay.ui.activity.wallet;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.hviewtech.wawupay.R;
import com.hviewtech.wawupay.base.BaseMvpActivity;
import com.hviewtech.wawupay.bean.remote.wallet.PaymentDetail;
import com.hviewtech.wawupay.ui.fragment.wallet.PaymentDetailFragment;
import com.hviewtech.wawupay.ui.fragment.wallet.PaymentDetailsFragment;

import javax.inject.Inject;

/**
 * @author su
 * @date 2018/3/22
 * @description
 */

public class PaymentDetailsActivity extends BaseMvpActivity {
    @Inject
    PaymentDetailsFragment mPaymentDetailsFragment;
    @Inject
    PaymentDetailFragment mPaymentDetailFragment;
    String TAG_DETAIL = "PaymentDetail";
    String TAG_DETAILS = "PaymentDetails";

    @Override
    public int getLayoutId() {
        return R.layout.act_payment_details;
    }

    @Override
    public void initialize() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, mPaymentDetailsFragment, TAG_DETAILS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            transaction.commitNow();
        } else {
            transaction.commit();
        }
    }

    public void addNextFragment(PaymentDetail detail) {
        Bundle args = new Bundle();
        args.putSerializable(PaymentDetailFragment.DETAIL, detail);


        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(TAG_DETAIL);
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != null) {
            fragment.setArguments(args);
            transaction.show(fragment);
        } else {
            mPaymentDetailFragment.setArguments(args);
            transaction.add(R.id.container, mPaymentDetailFragment, TAG_DETAIL);
        }
        transaction.hide(mPaymentDetailsFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(TAG_DETAIL);
        Fragment target = manager.findFragmentByTag(TAG_DETAILS);
        if (fragment != null && fragment.isVisible() && target != null) {
            manager.beginTransaction()
                    .show(target)
                    .hide(fragment)
                    .commit();
            return;
        }
        super.onBackPressed();
    }
}
