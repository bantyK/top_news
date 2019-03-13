package com.banty.topnews.ui.activities.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.banty.topnews.R
import com.banty.topnews.datamodels.Article
import com.banty.topnews.di.component.DaggerNewsActivityComponent
import com.banty.topnews.di.module.RepositoryModule
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.ui.activities.news.recyclerview.ItemClickListener
import com.banty.topnews.ui.activities.news.recyclerview.NewsRecyclerAdapter
import com.banty.topnews.ui.activities.webview.WebviewActivity
import com.banty.topnews.viewmodel.HeadlinesViewModel
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar_news.*
import javax.inject.Inject


class NewsActivity : AppCompatActivity(), NewsActivityPresenter.View, NavigationView.OnNavigationItemSelectedListener,
    ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    val TAG = "NewsActivity"

    private lateinit var recyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar

    private lateinit var headlinesViewModel: HeadlinesViewModel

    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout

    private var selectedCategory = "general"

    override fun showUI() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun hideUI() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun startWebViewActivity(url: String?) {
        val intent = Intent(this, WebviewActivity::class.java)
        intent.putExtra(WebviewActivity.INTENT_KEY_NEWS_ARTICLE_URL, url)
        startActivity(intent)
    }


    override fun listItemClicked(newsArticle: Article?) {
        newsActivityPresenter.handleNewsItemClicked(newsArticle)
    }

    companion object {
        @JvmStatic
        val INTENT_KEY_COUNTRY_ID = "intent.key.country.id"
    }

    private lateinit var newsActivityPresenter: NewsActivityPresenter

    @Inject
    lateinit var newsRepository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initUIElements()
        setSupportActionBar(toolbar)
        setupNavigationDrawer()

        DaggerNewsActivityComponent
            .builder()
            .repositoryModule(RepositoryModule(this.applicationContext))
            .build()
            .injectNewsActivityDependencies(this)

        headlinesViewModel = ViewModelProviders.of(this).get(HeadlinesViewModel::class.java)

        newsActivityPresenter = NewsActivityPresenterImpl(
            this,
            newsRepository,
            headlinesViewModel,
            intent.getStringExtra(INTENT_KEY_COUNTRY_ID)
        )

        /**
         * View model to update the recycler view with new data
         * */
        headlinesViewModel.getHeadlinesViewModel()
            .observe(this,
                Observer<List<Article>> {
                    Log.d(TAG, "Article list size : ${it?.size}")
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = NewsRecyclerAdapter(it, this)
                })


        newsActivityPresenter.resume()

    }

    private fun initUIElements() {
        progressBar = findViewById(R.id.new_activity_progress_bar)
        recyclerView = findViewById(R.id.news_recycler_view)
        swipeToRefreshLayout = findViewById(R.id.pullToRefresh)
        swipeToRefreshLayout.setOnRefreshListener(this)

    }

    private fun setupNavigationDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_business -> {
                selectedCategory = "business"
            }
            R.id.nav_entertainment -> {
                selectedCategory = "entertainment"
            }
            R.id.nav_general -> {
                selectedCategory = "general"
            }
            R.id.nav_health -> {
                selectedCategory = "health"
            }
            R.id.nav_science -> {
                selectedCategory = "science"
            }
            R.id.nav_sports -> {
                selectedCategory = "sports"
            }
            R.id.nav_technology -> {
                selectedCategory = "technology"
            }
        }

        newsActivityPresenter.changeArticles(selectedCategory)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRefresh() {
        swipeToRefreshLayout.isRefreshing = false
        newsActivityPresenter.getNewsHeadlines(selectedCategory, true)
    }
}
