package dev.engel.gitty.core.android

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Provides a simple way to implement [Application.ActivityLifecycleCallbacks] without having to
 * override every function.
 */
abstract class SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
        // Does nothing, override if you need to.
    }

    override fun onActivityStarted(activity: Activity) {
        // Does nothing, override if you need to.
    }

    override fun onActivityDestroyed(activity: Activity) {
        // Does nothing, override if you need to.
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Does nothing, override if you need to.
    }

    override fun onActivityStopped(activity: Activity) {
        // Does nothing, override if you need to.
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // Does nothing, override if you need to.
    }

    override fun onActivityResumed(activity: Activity) {
        // Does nothing, override if you need to.
    }
}