package dev.engel.gitty.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.engel.gitty.core.AndroidSkribe
import dev.engel.gitty.core.Skribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("gitty_prefs", Context.MODE_PRIVATE)
    }

    @CoroutineIODispatcher
    @Provides
    fun providesCoroutineIODisptacher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providesSkribe(): Skribe = AndroidSkribe()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineIODispatcher
