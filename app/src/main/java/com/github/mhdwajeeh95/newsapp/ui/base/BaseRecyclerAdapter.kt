package com.github.mhdwajeeh95.newsapp.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.github.mhdwajeeh95.newsapp.R
import kotlinx.android.synthetic.main.empty_list_placeholder.view.*

abstract class BaseRecyclerAdapter(protected var dataList: MutableList<Any> = mutableListOf()) :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    var status: RecyclerAdapterStatus = RecyclerAdapterStatus.PARTIALLY_LOADED
        set(value) {

            if (field == value) return
            field = value

            notifyItemChanged(dataList.size)

        }

    var recyclerAdapterListener: RecyclerAdapterListener? = null

    @DrawableRes
    open var emptyListIconResource: Int = R.drawable.ic_empty

    @StringRes
    open var emptyListTextResource: Int = R.string.empty_list_text

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            RecyclerAdapterStatus.EMPTY_CLICK_TO_RELOAD.value -> EmptyListViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.empty_list_placeholder,
                    parent,
                    false
                )
            )

            RecyclerAdapterStatus.LOADING_ERROR.value -> ErrorViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_loading_error_item,
                    parent,
                    false
                )
            )

            RecyclerAdapterStatus.LOADING.value -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_loading_item,
                    parent,
                    false
                )
            )

            RecyclerAdapterStatus.PARTIALLY_LOADED.value -> DummyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_dummy_item,
                    parent,
                    false
                )
            )

            RecyclerAdapterStatus.ALL_LOADED.value -> ListEndViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_end_item,
                    parent,
                    false
                )
            )

            else -> createVH(parent, viewType)
        }
    }

    abstract fun createVH(parent: ViewGroup, viewType: Int): BaseViewHolder

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // if view holder related to non data item
        if (position >= dataList.size) {
            holder.bind(Any(), position)
            return
        }

        holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int {
        return dataList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size)
            status.value
        else itemViewType(position)
    }

    abstract fun itemViewType(position: Int): Int

    open fun setData(newDataList: MutableList<Any>) {
        this.dataList = newDataList
        notifyDataSetChanged()
    }

    fun getData(): List<Any> = this.dataList

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        protected var pos: Int = -1

        open fun bind(dataItem: Any, pos: Int) {
            this.pos = pos
        }

    }

    inner class EmptyListViewHolder(itemView: View) : BaseViewHolder(itemView) {

        init {
            with(itemView) {
                setOnClickListener { recyclerAdapterListener?.onReloadClicked() }

                icon.setImageResource(emptyListIconResource)
                text.text = context.getString(emptyListTextResource)
            }
        }
    }

    inner class ErrorViewHolder(itemView: View) : BaseViewHolder(itemView) {

        init {
            with(itemView) {
                setOnClickListener { recyclerAdapterListener?.onLoadMoreClicked() }
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : BaseViewHolder(itemView)

    inner class ListEndViewHolder(itemView: View) : BaseViewHolder(itemView)

    inner class DummyViewHolder(itemView: View) : BaseViewHolder(itemView)

    interface RecyclerAdapterListener {
        fun onLoadMoreClicked()
        fun onReloadClicked()

        fun onItemClick(itemObject:Any)
    }

    enum class RecyclerAdapterStatus(val value: Int) {
        PARTIALLY_LOADED(1001),
        ALL_LOADED(1002),
        LOADING(1003),
        EMPTY_CLICK_TO_RELOAD(1004),
        LOADING_ERROR(1005)
    }
}