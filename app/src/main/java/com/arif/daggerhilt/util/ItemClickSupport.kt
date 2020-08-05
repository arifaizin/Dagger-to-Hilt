package com.arif.daggerhilt.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arif.daggerhilt.R


class ItemClickSupport(private val mRecyclerView: RecyclerView) {
    private var mOnItemClickListener: OnItemClickListener? = null
    private val mOnClickListener = View.OnClickListener { v ->
        mOnItemClickListener?.let {
            val holder = mRecyclerView.getChildViewHolder(v)
            it.onItemClicked(mRecyclerView, holder.adapterPosition, v)
        }
    }

    private val mAttachListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener)
            }
        }

        override fun onChildViewDetachedFromWindow(view: View) {}
    }

    init {
        mRecyclerView.setTag(R.id.item_click_support, this)
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View)
    }

    companion object {
        fun addTo(view: RecyclerView): ItemClickSupport {
            var support: ItemClickSupport? = view.getTag(R.id.item_click_support) as ItemClickSupport?
            if (support == null) {
                support = ItemClickSupport(view)
            }
            return support
        }

    }
}