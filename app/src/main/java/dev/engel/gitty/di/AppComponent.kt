package dev.engel.gitty.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.engel.gitty.App
import dev.engel.gitty.repository.AuthModule
import dev.engel.gitty.repository.NetworkModule
import javax.inject.Singleton

/**
 * Main component of this application. It is responsible for dependencies that should be around for
 * the lifetime of the app.
 */
@Singleton
@Component(
    modules = [
        ActivityModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        AuthModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun app(@BindsInstance app: App): Builder
    }
}
