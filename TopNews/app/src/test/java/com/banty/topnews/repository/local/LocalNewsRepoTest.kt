package com.banty.topnews.repository.local

import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.repository.local.files.FileManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 15/03/19.
 */
class LocalNewsRepoTest {

    @Mock
    lateinit var fileManager: FileManager

    @Mock
    lateinit var articles: List<Article>

    @Mock
    lateinit var mockCallback: NewsRepository.LoadNewsCallback

    lateinit var localNewsRepo: LocalNewsRepo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        localNewsRepo = LocalNewsRepo(fileManager)
    }

    @Test
    fun shouldCallSuccessCallbackWhenDataIsReadFromFile() {
        `when`(fileManager.getArticlesFromFile()).thenReturn(articles)

        localNewsRepo.getNewsArticles("", "", mockCallback)

        Mockito.verify(mockCallback).onNewsLoaded(articles)
    }

    @Test
    fun shouldCallFailedCallbackWhenDataIsNotReadFromFile() {
        `when`(fileManager.getArticlesFromFile()).thenReturn(null)

        localNewsRepo.getNewsArticles("", "", mockCallback)

        Mockito.verify(mockCallback).onNewsFailedToLoad()
    }

    @Test
    fun shouldBeAbleToSaveFileInLocalCache() {
        localNewsRepo.saveNewsArticles(articles)

        Mockito.verify(fileManager).saveArticlesToFile(articles)
    }
}