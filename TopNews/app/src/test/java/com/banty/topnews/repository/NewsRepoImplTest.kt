package com.banty.topnews.repository

import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.local.LocalNewsRepo
import com.banty.topnews.repository.remote.RemoteNewsRepo
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*


/**
 * Created by Banty on 14/03/19.
 */
class NewsRepoImplTest {

    private lateinit var newsRepo: NewsRepoImpl

    @Mock
    lateinit var remoteNewsRepo: RemoteNewsRepo

    @Mock
    lateinit var localNewsRepo: LocalNewsRepo

    @Mock
    lateinit var mockLoadNewsCallback: NewsRepository.LoadNewsCallback

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
        newsRepo = NewsRepoImpl(localNewsRepo, remoteNewsRepo)
    }

    @Test
    fun shouldReturnDataFromLocalStorageIfCacheIsNotDirty() {
        newsRepo.getNewsArticles("","", mockLoadNewsCallback)

        verify(localNewsRepo).getNewsArticles(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), anyObject())
        verifyNoMoreInteractions(remoteNewsRepo)
    }

    @Test
    fun shouldReturnDataFromRemoteRepoIfRefreshIsCalled() {
        newsRepo.refreshNews()
        newsRepo.getNewsArticles("","", mockLoadNewsCallback)

        verify(remoteNewsRepo).getNewsArticles(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), anyObject())
        verifyNoMoreInteractions(localNewsRepo)
    }

    @Test
    fun shouldCallSuccessCallbackWithArticlesWhenDataFetchIsSuccessful() {
        newsRepo.getNewsArticles("","", mockLoadNewsCallback)
        val articles = arrayListOf<Article>()

        Mockito.verify(localNewsRepo).getNewsArticles(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsLoaded(articles)

        Mockito.verify(mockLoadNewsCallback).onNewsLoaded(articles)
    }

    @Test
    fun shouldCallFailureCallbackWithArticlesWhenDataFetchFail() {
        newsRepo.refreshNews()

        newsRepo.getNewsArticles("","", mockLoadNewsCallback)

        Mockito.verify(remoteNewsRepo)
            .getNewsArticles(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsFailedToLoad()

        Mockito.verify(mockLoadNewsCallback).onNewsFailedToLoad()
    }

    @Test
    fun shouldUpdateLocalCacheAfterRemoteDataFetch() {
        newsRepo.refreshNews()

        val articles = arrayListOf<Article>()

        newsRepo.getNewsArticles("","", mockLoadNewsCallback)

        Mockito.verify(remoteNewsRepo)
            .getNewsArticles(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsLoaded(articles)

        Mockito.verify(localNewsRepo).saveNewsArticles(articles)
    }

    @Test
    fun shouldCallRemoteRepoIfLocalDataFetchFailed() {
        newsRepo.getNewsArticles("","", mockLoadNewsCallback)
        Mockito.verify(localNewsRepo).getNewsArticles(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))

        newsLoadCallbackArgumentCaptor.value.onNewsFailedToLoad()

        Mockito.verify(remoteNewsRepo).getNewsArticles(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), capture(newsLoadCallbackArgumentCaptor))
    }
}