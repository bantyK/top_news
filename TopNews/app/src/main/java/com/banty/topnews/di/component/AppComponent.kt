package com.banty.topnews.di.component

import com.banty.topnews.MainActivity
import com.banty.topnews.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Banty on 10/03/19.
 * * Interface for dagger to implement which will inject the dependencies to Activities.
 *
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectDependencies(mainActivity: MainActivity)
}