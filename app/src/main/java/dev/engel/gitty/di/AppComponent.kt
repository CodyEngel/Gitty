package dev.engel.gitty.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.engel.gitty.App
import javax.inject.Singleton

/**
 * Main component of this application. It is responsible for dependencies that should be around for
 * the lifetime of the app.
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<App>