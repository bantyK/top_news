package com.banty.topnews.repository.remote

import com.banty.topnews.datamodels.Article
import com.banty.topnews.datamodels.TopHeadlinesResponse
import com.banty.topnews.network.retrofit.NewsApiService
import com.banty.topnews.repository.NewsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 14/03/19.
 */
class RemoteNewsRepoTest {
    lateinit var testScheduler: TestScheduler

    lateinit var remoteNewsRepo: RemoteNewsRepo

    @Mock
    lateinit var newsApiService: NewsApiService

    @Mock
    lateinit var mockCallback: NewsRepository.LoadNewsCallback

    lateinit var topHeadlinesResponse: TopHeadlinesResponse

    @Mock
    lateinit var article: Article

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        remoteNewsRepo = RemoteNewsRepo(newsApiService, testScheduler, testScheduler)
    }

    @Test
    fun shouldCallSuccessCallbackWhenDataFetchIsSuccessful() {
        topHeadlinesResponse = TopHeadlinesResponse("ok", 1, arrayListOf(article))


        `when`(
            newsApiService.getTopHeadlines(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        )
            .thenReturn(
                Observable.just(
                    topHeadlinesResponse
                )
            )

        remoteNewsRepo.getNewsArticles("", "    ", mockCallback)

        testScheduler.triggerActions()

        val articles = topHeadlinesResponse.articles!!

        Mockito.verify(mockCallback).onNewsLoaded(articles)
    }

    @Test
    fun shouldCallFailedCallbackWhenDataFetchIsNotSuccessful() {
        topHeadlinesResponse = TopHeadlinesResponse("failed", 0, arrayListOf())


        `when`(
            newsApiService.getTopHeadlines(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        )
            .thenReturn(
                Observable.just(
                    topHeadlinesResponse
                )
            )

        remoteNewsRepo.getNewsArticles("", "    ", mockCallback)

        testScheduler.triggerActions()

        val articles = topHeadlinesResponse.articles!!

        Mockito.verify(mockCallback).onNewsFailedToLoad()
    }
}