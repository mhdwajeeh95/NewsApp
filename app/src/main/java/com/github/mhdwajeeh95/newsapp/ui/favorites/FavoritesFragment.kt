package com.github.mhdwajeeh95.newsapp.ui.favorites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mhdwajeeh95.newsapp.R
import com.github.mhdwajeeh95.newsapp.app.MyApplication
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.ui.articledetails.ArticleDetailsActivity
import com.github.mhdwajeeh95.newsapp.ui.base.BaseFragment
import com.github.mhdwajeeh95.newsapp.ui.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class FavoritesFragment : BaseFragment(), FavoriteNewsRecyclerAdapter.OnDeleteListener,
    BaseRecyclerAdapter.RecyclerAdapterListener {

    var favoriteNewsRecyclerAdapter: FavoriteNewsRecyclerAdapter =
        FavoriteNewsRecyclerAdapter().apply {
            onDeleteListener = this@FavoritesFragment
            recyclerAdapterListener = this@FavoritesFragment
        }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FavoritesViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as MyApplication)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        registerObservers()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFavoriteArticles()

    }
    private fun initUI() {

        recycler_view.apply {
            adapter = favoriteNewsRecyclerAdapter
            layoutManager = LinearLayoutManager(requireActivity())

        }
    }

    private fun registerObservers() {
        viewModel.articles.observe(viewLifecycleOwner, { articleList ->
            if (articleList.size == 0)
                favoriteNewsRecyclerAdapter.status =
                    BaseRecyclerAdapter.RecyclerAdapterStatus.EMPTY_CLICK_TO_RELOAD
            favoriteNewsRecyclerAdapter.setData(articleList.toMutableList())
        })

        viewModel.deleteArticleError.observe(viewLifecycleOwner, {
            if (it.getContentIfNotHandled() == true)
                toast(R.string.message_remove_article_from_fav_error)
        })
    }

    override fun onDelete(article: Article) {
        viewModel.removeArticleFromFavorites(article)
    }

    override fun onLoadMoreClicked() {

    }

    override fun onReloadClicked() {
        viewModel.getFavoriteArticles()
    }

    override fun onItemClick(itemObject: Any) {
        startActivity(Intent(requireActivity(), ArticleDetailsActivity::class.java).apply {
            putExtras((itemObject as Article).toBundle())
        })
    }
}