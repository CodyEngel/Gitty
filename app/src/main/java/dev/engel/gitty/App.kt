package dev.engel.gitty

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dev.engel.gitty.di.DaggerAppComponent
import dev.engel.gitty.di.DaggerAutoInjector
import javax.inject.Inject

/**
 * Overriding the [App] provides an easy way to setup Dagger and handle other tasks which must occur
 * when the app launches.
 */
class App : Application(), HasAndroidInjector {

    private val daggerAutoInjector = DaggerAutoInjector(this)

    /**
     * Provides a way for Dagger to automatically inject dependencies into an Activity or Fragment.
     */
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.builder()
            .app(this)
            .build()
        appComponent.inject(this)
        daggerAutoInjector.register(dispatchingAndroidInjector)
    }
}
