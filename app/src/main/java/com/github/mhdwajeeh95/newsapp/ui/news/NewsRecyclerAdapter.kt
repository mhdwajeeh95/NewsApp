package com.github.mhdwajeeh95.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.github.mhdwajeeh95.newsapp.R
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.ui.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.article_list_item.view.*

class NewsRecyclerAdapter : BaseRecyclerAdapter() {
    override fun createVH(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return NewsItemVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_list_item, parent, false
            )
        )
    }

    override fun itemViewType(position: Int): Int {
        return 0
    }

    @Suppress("UNCHECKED_CAST")
    override fun setData(newDataList: MutableList<Any>) {
        val diffResult = DiffUtil.calculateDiff(
            ArticleDiffCallback(
                this.dataList as MutableList<Article>,
                newDataList as MutableList<Article>
            )
        )

        dataList = newDataList
//        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    inner class NewsItemVH(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(dataItem: Any, pos: Int) {
            super.bind(dataItem, pos)

            val article = dataList[pos] as Article

            with(itemView) {

                title.text = article.title
                description.text = article.description

                //TODO: show date and time in proper way
                date.text = article.publishedAt
                source.text = article.source?.name ?: article.sourceName

                Glide.with(context).load(article.urlToImage).into(image)

            }
        }

    }

    class ArticleDiffCallback(val oldList: List<Article>, val newList: List<Article>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}