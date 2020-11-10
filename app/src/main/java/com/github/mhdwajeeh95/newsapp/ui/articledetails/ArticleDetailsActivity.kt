package com.github.mhdwajeeh95.newsapp.ui.articledetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.mhdwajeeh95.newsapp.R
import com.github.mhdwajeeh95.newsapp.app.MyApplication
import com.github.mhdwajeeh95.newsapp.models.Article
import com.github.mhdwajeeh95.newsapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_article_details.*
import javax.inject.Inject

class ArticleDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ArticleDetailsViewModel by viewModels { viewModelFactory }

    private var mOptionsMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MyApplication)
            .appComponent.inject(this)

        setContentView(R.layout.activity_article_details)

        initUI()

        registerObservers()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_details_menu, menu);

        mOptionsMenu = menu

        // menu is initialized after the onCreate method
        intent.extras?.let { viewModel.article.postValue(Article(it)) }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.fav_btn -> {
                viewModel.article.value?.apply {
                    viewModel.setArticleFavorite(!isFav)
                }
                true
            }
            R.id.share_btn -> {
                viewModel.article.value?.let {
                    ShareCompat.IntentBuilder.from(this@ArticleDetailsActivity)
                        .setType("text/plain")
                        .setChooserTitle(R.string.action_share)
                        .setText(it.url)
                        .startChooser()
                }

                true
            }
            else -> false
        }
    }

    private fun initUI() {

    }

    private fun registerObservers() {
        viewModel.article.observe(this, {
            it?.let { article ->

                // set favorıte button
                mOptionsMenu?.apply {
                    findItem(R.id.fav_btn).setIcon(if (article.isFav) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
                }

                Glide.with(this).load(article.urlToImage).into(image)

                article_title.text = article.title
                author.text = article.author
                date.text = article.publishedAt
                content.text = article.content
                source.text = article.source?.name ?: article.sourceName


            }
        })
    }
}