package dev.engel.gitty.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.engel.gitty.ui.profile.ProfileFragment

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector abstract fun contributesProfileFragment(): ProfileFragment
}
