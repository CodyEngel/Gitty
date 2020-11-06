package dev.engel.gitty.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.engel.gitty.ui.AuthenticationActivity
import dev.engel.gitty.ui.MainActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector abstract fun contributesAuthenticationActivity(): AuthenticationActivity
    @ContributesAndroidInjector abstract fun contributesMainActivity(): MainActivity
}
