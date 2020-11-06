package dev.engel.gitty.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dev.engel.gitty.App
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
class AppModule {
    @Provides
    fun providesContext(app: App): Context = app.applicationContext

    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("gitty_prefs", Context.MODE_PRIVATE)
    }

    @CoroutineIODispatcher
    @Provides
    fun providesCoroutineIODisptacher(): CoroutineDispatcher = Dispatchers.IO
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineIODispatcher
