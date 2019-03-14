package com.banty.topnews.ui.activities.news

import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.viewmodel.NewsViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`

/**
 * Created by Banty on 14/03/19.
 */
class NewsActivityPresenterImplTest {

    @Mock
    lateinit var mockView: NewsActivityPresenter.View

    @Mock
    lateinit var newsRepository: NewsRepository

    @Mock
    lateinit var newsViewModel: NewsViewModel

    @Mock
    lateinit var article: Article

    lateinit var newsActivityPresenter: NewsActivityPresenter

    @Captor
    lateinit var newsLoadCallbackArgumentCaptor: ArgumentCaptor<NewsRepository.LoadNewsCallback>

    /**
     * Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException
     * when null is returned.
     */
    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsActivityPresenter = NewsActivityPresenterImpl(mockView, newsRepository, newsViewModel, "in")
    }

    @Test
    fun shouldHideUiWhenMakingCallToRepository() {
        newsActivityPresenter.getNewsHeadlines("category", false)
        Mockito.verify(mockView).hideUI()
    }

    @Test
    fun shouldRefreshRepositoryIfRefreshFlagIsTrue() {
        newsActivityPresenter.getNewsHeadlines("category", true)
        Mockito.verify(newsRepository).refreshNews()
    }

    @Test
    fun shouldUpdateViewModelOnDataFetch() {
        newsActivityPresenter.getNewsHeadlines("category", true)

        val articles = arrayListOf<Article>()

        Mockito.verify(newsRepository)
            .getNewsArticles(ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsLoaded(articles)

        Mockito.verify(newsViewModel).setHeadlinesViewModel(articles)
    }

    @Test
    fun shouldUpdateViewOnDataFetch() {
        newsActivityPresenter.getNewsHeadlines("category", true)

        val articles = arrayListOf<Article>()

        Mockito.verify(newsRepository)
            .getNewsArticles(ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsLoaded(articles)

        Mockito.verify(mockView).showUI()
    }

    @Test
    fun shouldShowErrorMessageWhenDataLoadFailed() {
        newsActivityPresenter.getNewsHeadlines("category", true)

        Mockito.verify(newsRepository)
            .getNewsArticles(ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsFailedToLoad()

        Mockito.verify(mockView).showDataFetchErrorMessage()
    }

    @Test
    fun shouldStartWebActivityWhenNewsItemIsClicked() {
        `when`(article.url).thenReturn("https://url.com")
        newsActivityPresenter.handleNewsItemClicked(article)
        Mockito.verify(mockView).startWebViewActivity("https://url.com")
    }

    @Test
    fun shouldRefreshDataOnCallingChangeArticle() {
        newsActivityPresenter.changeArticles("")

        val articles = arrayListOf<Article>()

        Mockito.verify(newsRepository).getNewsArticles(ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsLoaded(articles)

        Mockito.verify(newsViewModel).setHeadlinesViewModel(articles)
        Mockito.verify(mockView).showUI()
    }
}