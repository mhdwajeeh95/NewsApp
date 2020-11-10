package com.github.mhdwajeeh95.newsapp.ui.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mhdwajeeh95.newsapp.R
import com.github.mhdwajeeh95.newsapp.app.MyApplication
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.ui.articledetails.ArticleDetailsActivity
import com.github.mhdwajeeh95.newsapp.ui.base.BaseFragment
import com.github.mhdwajeeh95.newsapp.ui.base.BaseRecyclerAdapter
import com.github.mhdwajeeh95.newsapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject


class NewsFragment : BaseFragment(), BaseRecyclerAdapter.RecyclerAdapterListener {

    var newsRecyclerAdapter: NewsRecyclerAdapter = NewsRecyclerAdapter().apply {
        recyclerAdapterListener = this@NewsFragment
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: NewsViewModel by activityViewModels {
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
        val root = inflater.inflate(R.layout.fragment_news, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        registerObservers()
    }

    private fun initUI() {
//        search_view.apply {
//            isIconified = false
//        }

        recycler_view.apply {
            adapter = newsRecyclerAdapter
            layoutManager = LinearLayoutManager(requireActivity())

        }

        initScrollListener()

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // reset page counter
                viewModel.clearData()
                viewModel.searchNews(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun initScrollListener() {

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (newsRecyclerAdapter.status == BaseRecyclerAdapter.RecyclerAdapterStatus.PARTIALLY_LOADED) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == newsRecyclerAdapter.itemCount - 1) {
                        //bottom of list!
                        viewModel.searchNews()
                    }
                }
            }
        })
    }

    private fun registerObservers() {
        viewModel.searchQueryText.observe(viewLifecycleOwner, {
            search_view.setQuery(it, false)
        })

        viewModel.recyclerLoadingStatus.observe(viewLifecycleOwner, {
            newsRecyclerAdapter.status = when (it) {
                NewsViewModel.RecyclerLoadingStatus.LOADING_ERROR -> BaseRecyclerAdapter.RecyclerAdapterStatus.LOADING_ERROR
                NewsViewModel.RecyclerLoadingStatus.LOADING -> BaseRecyclerAdapter.RecyclerAdapterStatus.LOADING
                NewsViewModel.RecyclerLoadingStatus.PARTIALLY_LOADED -> BaseRecyclerAdapter.RecyclerAdapterStatus.PARTIALLY_LOADED
                NewsViewModel.RecyclerLoadingStatus.ALL_LOADED -> BaseRecyclerAdapter.RecyclerAdapterStatus.ALL_LOADED
                NewsViewModel.RecyclerLoadingStatus.EMPTY_CLICK_TO_RELOAD -> BaseRecyclerAdapter.RecyclerAdapterStatus.EMPTY_CLICK_TO_RELOAD
            }
        })

        viewModel.articles.observe(viewLifecycleOwner, { articleList ->
            newsRecyclerAdapter.setData(articleList.toMutableList())
        })
    }

    override fun onLoadMoreClicked() {
        viewModel.searchNews()
    }

    override fun onReloadClicked() {

    }

    override fun onItemClick(itemObject: Any) {
        startActivity(Intent(requireActivity(),ArticleDetailsActivity::class.java).apply {
            putExtras((itemObject as Article).toBundle())
        })
    }
}