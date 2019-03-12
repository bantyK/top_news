package com.banty.topnews.di.module

import com.banty.topnews.ui.presenter.MainActivityPresenter
import com.banty.topnews.ui.presenter.impl.MainActivityPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Banty on 10/03/19.
 */
@Module
class PresenterModule(val view: MainActivityPresenter.View) {

    @Provides
    @Singleton
    fun provideMainActivityPresenter(): MainActivityPresenter =
        MainActivityPresenterImpl(view)

}