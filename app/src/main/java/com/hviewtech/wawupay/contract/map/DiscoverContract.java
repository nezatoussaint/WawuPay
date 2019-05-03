package com.hviewtech.wawupay.contract.map;

import com.hviewtech.wawupay.bean.remote.map.Category;
import com.hviewtech.wawupay.bean.remote.map.MerchantPositionList;
import com.hviewtech.wawupay.contract.Contract;

import java.util.List;

/**
 * @author Eric
 * @version 1.0
 * @description
 */

public interface DiscoverContract {
    interface View extends Contract.View {


        void showMerchantList(MerchantPositionList result);

        void updateCategories(List<Category> list);
    }

    interface Presenter extends Contract.Presenter {

    }
}
