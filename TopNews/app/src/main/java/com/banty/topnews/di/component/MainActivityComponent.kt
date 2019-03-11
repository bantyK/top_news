package com.banty.topnews.di.component

import com.banty.topnews.ui.activities.main.MainActivity
import com.banty.topnews.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Banty on 10/03/19.
 * * Interface for dagger to implement which will inject the dependencies to Activities.
 *
 */
@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {
    fun injectRepository(mainActivity: MainActivity)
}