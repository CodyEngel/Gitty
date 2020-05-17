package dev.engel.gitty.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.DispatchingAndroidInjector

/**
 * DaggerFragmentLifecycleCallbacks provides a simple way of injecting Dagger dependencies into a
 * [Fragment] without the Fragment needing knowledge of how the injection occurs.
 */
class DaggerFragmentLifecycleCallbacks(
    private val dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        dispatchingAndroidInjector.inject(f)
        super.onFragmentAttached(fm, f, context)
    }
}