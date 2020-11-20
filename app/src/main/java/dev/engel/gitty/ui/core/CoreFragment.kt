package dev.engel.gitty.ui.core

import androidx.fragment.app.Fragment
import dev.engel.gitty.core.Skribe
import dev.engel.gitty.di.AutoInject
import javax.inject.Inject

abstract class CoreFragment : Fragment(), AutoInject {
    @Inject protected lateinit var skribe: Skribe
}
