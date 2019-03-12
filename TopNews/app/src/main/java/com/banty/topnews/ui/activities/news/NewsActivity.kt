package com.banty.topnews.ui.activities.news

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.banty.topnews.R
import com.banty.topnews.datamodels.Article
import com.banty.topnews.di.component.DaggerNewsActivityComponent
import com.banty.topnews.di.module.RepositoryModule
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.ui.activities.news.recyclerview.NewsRecyclerAdapter
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.app_bar_news.*
import javax.inject.Inject

class NewsActivity : AppCompatActivity(), NewsActivityPresenter.View, NavigationView.OnNavigationItemSelectedListener {

    val TAG = "NewsActivity"

    override fun setRecyclerView(articles: List<Article>?) {
        Log.d(TAG, "Article list size : ${articles?.size}")
        val recyclerView = findViewById<RecyclerView>(R.id.news_recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NewsRecyclerAdapter(articles)
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
        setSupportActionBar(toolbar)
        setupNavigationDrawer()

        DaggerNewsActivityComponent
            .builder()
            .repositoryModule(RepositoryModule(this.applicationContext))
            .build()
            .injectNewsActivityDependencies(this)

        newsActivityPresenter = NewsActivityPresenterImpl(
            this,
            newsRepository
        )

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
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        newsActivityPresenter.resume()
    }

}
