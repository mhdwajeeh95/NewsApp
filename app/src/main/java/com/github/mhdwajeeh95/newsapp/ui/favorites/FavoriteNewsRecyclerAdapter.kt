package com.github.mhdwajeeh95.newsapp.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.mhdwajeeh95.newsapp.R
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.ui.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.article_favorite_list_item.view.*
import kotlinx.android.synthetic.main.article_list_item.view.date
import kotlinx.android.synthetic.main.article_list_item.view.description
import kotlinx.android.synthetic.main.article_list_item.view.image
import kotlinx.android.synthetic.main.article_list_item.view.source
import kotlinx.android.synthetic.main.article_list_item.view.title

class FavoriteNewsRecyclerAdapter : BaseRecyclerAdapter() {

    init {
        status = RecyclerAdapterStatus.PARTIALLY_LOADED
    }

    var onDeleteListener: OnDeleteListener? = null

    override fun createVH(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return NewsItemVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_favorite_list_item, parent, false
            )
        )
    }

    override fun itemViewType(position: Int): Int {
        return 0
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

                delete_btn.setOnClickListener {
                    onDeleteListener?.onDelete(dataItem as Article)
                }

            }
        }

    }

    interface OnDeleteListener {
        fun onDelete(article: Article)
    }

}