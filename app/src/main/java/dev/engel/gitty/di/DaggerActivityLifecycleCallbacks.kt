package dev.engel.gitty.di

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dev.engel.gitty.core.android.SimpleActivityLifecycleCallbacks

/**
 * DaggerActivityLifecycleCallbacks provides a simple way of injecting Dagger dependencies into an
 * [Activity] without the Activity needing knowledge of how the injection occurs. This also takes
 * care of registering lifecycle callbacks for [Fragment]s which are within the Activity.
 */
class DaggerActivityLifecycleCallbacks(
    private val dispatchingAndroidInjector: DispatchingAndroidInjector<Any>,
    private val daggerFragmentLifecycleCallbacks: DaggerFragmentLifecycleCallbacks
) : SimpleActivityLifecycleCallbacks() {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        injectActivity(activity)
        registerFragmentLifecycle(activity)

        super.onActivityCreated(activity, savedInstanceState)
    }

    private fun injectActivity(activity: Activity) {
        if (activity is AutoInject) {
            dispatchingAndroidInjector.inject(activity)
        }
    }

    private fun registerFragmentLifecycle(activity: Activity) {
        if (activity is AppCompatActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(daggerFragmentLifecycleCallbacks, true)
        }
    }
}