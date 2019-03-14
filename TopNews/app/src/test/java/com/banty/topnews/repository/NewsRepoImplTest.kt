package com.banty.topnews.repository

import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.local.LocalNewsRepo
import com.banty.topnews.repository.remote.RemoteNewsRepo
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer

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
    lateinit var loadNewsCallback: NewsRepository.LoadNewsCallback

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()

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
        newsRepo.getNewsArticles("", loadNewsCallback)

        verify(localNewsRepo).getNewsArticles(ArgumentMatchers.anyString(), anyObject())
        verifyNoMoreInteractions(remoteNewsRepo)
    }

    @Test
    fun shouldReturnDataFromRemoteRepoIfRefreshIsCalled() {
        newsRepo.refreshNews()
        newsRepo.getNewsArticles("", loadNewsCallback)

        verify(remoteNewsRepo).getNewsArticles(ArgumentMatchers.anyString(), anyObject())
        verifyNoMoreInteractions(localNewsRepo)
    }
}