package dev.engel.gitty

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Overriding the [App] provides an easy way to setup Dagger and handle other tasks which must occur
 * when the app launches.
 */
@HiltAndroidApp
class App : Application()
