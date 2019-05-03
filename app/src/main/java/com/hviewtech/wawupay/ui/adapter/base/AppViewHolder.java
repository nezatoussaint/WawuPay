package com.hviewtech.wawupay.ui.adapter.base;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;

public class AppViewHolder {
    public final View itemView;
    private SparseArray<View> mViews;

    public AppViewHolder(View root) {
        itemView = root;
        mViews = new SparseArray<>();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public <T extends View> T getView(@IdRes int id) {
        if (itemView != null) {
            View view = mViews.get(id);
            if (view != null) {
                return (T) view;
            } else {
                view = itemView.findViewById(id);
                if (view != null) {
                    mViews.put(id, view);
                    return (T) view;
                }
            }
        }
        return null;
    }
}