package com.hviewtech.wawupay.ui.adapter.base

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter


abstract class BaseAdapter<B>(protected val mContext: Context) : DelegateAdapter.Adapter<BaseAdapter.ViewHolder>() {
  protected val mInflater: LayoutInflater
  protected val mDatas: MutableList<B> = mutableListOf()

  init {
    mInflater = LayoutInflater.from(mContext)

  }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(mInflater.inflate(getLayoutId(), parent, false));
  }


  fun getItem(position: Int): B {
    return mDatas[position]
  }

  override fun getItemCount(): Int {
    return mDatas.size
  }

  fun setDatas(datas: List<B>?) {
    with(mDatas) {
      clear()
      if (datas != null) {
        addAll(datas)
      }
      notifyDataSetChanged()
    }
  }

  fun addDatas(datas: List<B>?) {
    with(mDatas) {
      if (datas != null) {
        addAll(datas)
        notifyDataSetChanged()
      }
    }
  }

  fun setData(data: B?) {
    with(mDatas) {
      clear()
      if (data != null) {
        add(data)
      }
      notifyDataSetChanged()
    }
  }

  fun addData(data: B?) {
    with(mDatas) {
      if (data != null) {
        add(data)
        notifyDataSetChanged()
      }
    }
  }

  fun updateData(position: Int, data: B?) {
    with(mDatas) {
      if (position < 0 || position >= this.size) {
        return
      }
      if (data != null) {
        set(position, data)
        notifyDataSetChanged()
      }
    }
  }

  fun removeData(position: Int) {
    with(mDatas) {
      if (position < 0 || position >= this.size) {
        return
      }
      removeAt(position)
      notifyDataSetChanged()
    }
  }

  fun getDatas(): List<B> {
    return mDatas
  }

  abstract fun getLayoutId(): Int


  class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val mViews: SparseArray<View>

    companion object {
      @Volatile
      var existing = 0
      var createdTimes = 0
    }

    init {
      createdTimes++
      existing++
      mViews = SparseArray<View>()
    }

    @Suppress("ProtectedInFinal", "Unused")
    protected fun finalize() {
      existing--
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(@IdRes id: Int): T? {
      if (itemView != null) {
        var view: View? = mViews.get(id)
        if (view != null) {
          return view as T
        } else {
          view = itemView.findViewById<View>(id)
          if (view != null) {
            mViews.put(id, view)
            return view as T
          }
        }
      }
      return null
    }

  }
}