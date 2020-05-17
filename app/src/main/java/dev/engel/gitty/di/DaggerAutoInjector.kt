package dev.engel.gitty.di

import android.app.Application
import dagger.android.DispatchingAndroidInjector

/**
 * DaggerAutoInjector provides a way to registering Dagger auto-injection classes to an [Application].
 */
class DaggerAutoInjector(
    private val application: Application
) {

    /**
     * Register's the [Application] to the Activity Lifecycle Callbacks as well as the Fragment
     * Lifecycle Callbacks.
     */
    fun register(dispatchingAndroidInjector: DispatchingAndroidInjector<Any>) {
        val fragmentLifecycleCallbacks =
            DaggerFragmentLifecycleCallbacks(dispatchingAndroidInjector)
        val activityLifecycleCallbacks =
            DaggerActivityLifecycleCallbacks(dispatchingAndroidInjector, fragmentLifecycleCallbacks)
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }
}