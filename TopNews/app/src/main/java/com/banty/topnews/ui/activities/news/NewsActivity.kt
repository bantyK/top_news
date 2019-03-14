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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.banty.topnews.R
import com.banty.topnews.datamodels.Article
import com.banty.topnews.di.component.DaggerNewsActivityComponent
import com.banty.topnews.di.module.RepositoryModule
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.ui.activities.news.recyclerview.ItemClickListener
import com.banty.topnews.ui.activities.news.recyclerview.NewsRecyclerAdapter
import com.banty.topnews.ui.activities.webview.WebviewActivity
import com.banty.topnews.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar_news.*
import javax.inject.Inject


class NewsActivity : AppCompatActivity(), NewsActivityPresenter.View, NavigationView.OnNavigationItemSelectedListener,
    ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    val TAG = "NewsActivity"

    private lateinit var recyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar

    private lateinit var headlinesViewModel: NewsViewModel

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

    /**
     * Start the web activity with the url passed from presenter.
     * */
    override fun startWebViewActivity(url: String?) {
        val intent = Intent(this, WebviewActivity::class.java)
        intent.putExtra(WebviewActivity.INTENT_KEY_NEWS_ARTICLE_URL, url)
        startActivity(intent)
    }


    /**
     * Callback from Recycler adapter when a list item is clicked.
     * Delegated the processing to the presenter
     * */
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

        // inject the dependencies
        DaggerNewsActivityComponent
            .builder()
            .repositoryModule(RepositoryModule(this.applicationContext))
            .build()
            .injectNewsActivityDependencies(this)

        // initiate the view model
        headlinesViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)


        // initiate the presenter
        newsActivityPresenter = NewsActivityPresenterImpl(
            this,
            newsRepository,
            headlinesViewModel,
            "in"
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
                    Toast.makeText(this, getString(R.string.pull_to_refresh_message), Toast.LENGTH_LONG).show()
                })


        newsActivityPresenter.resume()

    }

    /**
     * Initialise the UI elements
     * */
    private fun initUIElements() {
        progressBar = findViewById(R.id.new_activity_progress_bar)
        recyclerView = findViewById(R.id.news_recycler_view)
        swipeToRefreshLayout = findViewById(R.id.pullToRefresh)
        swipeToRefreshLayout.setOnRefreshListener(this)

    }

    /**
     * Set up of side drawer
     * */
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

    /**
     * Item click listener of drawer items
     * Sends the item data from drawer to the presenter and closes the drawer
     * */
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

    /**
     * SwipeRefreshLayout's listener. Gets called when user swipe down on the recycler view to update the view
     *
     * Informs the presenter to update the local cache and update the UI
     * */
    override fun onRefresh() {
        swipeToRefreshLayout.isRefreshing = false
        newsActivityPresenter.getNewsHeadlines(selectedCategory, true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.news_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.change_country_settings -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    /**
     * Shows an error message in the UI when data fetching fails.
     * */
    override fun showDataFetchErrorMessage() {
        progressBar.visibility = View.GONE
        Toast.makeText(this, getString(R.string.news_fetch_error_message), Toast.LENGTH_LONG).show()
    }

}
