package dev.engel.gitty.core

import android.util.Log

interface Skribe {
    infix fun tag(tag: String)

    infix fun level(level: SkribeLevel)

    infix fun trace(message: String)

    infix fun debug(message: String)

    infix fun info(message: String)

    infix fun warn(message: String)

    infix fun error(message: String)

    infix fun fatal(message: String)
}

enum class SkribeLevel(val priority: Int) {
    TRACE(priority = 0),
    DEBUG(priority = 1),
    INFO(priority = 2),
    WARN(priority = 3),
    ERROR(priority = 4),
    FATAL(priority = 5)
}

class AndroidSkribe : Skribe {
    private var tag: String = "null"
    private var level: SkribeLevel = SkribeLevel.TRACE

    override fun tag(tag: String) {
        this.tag = tag
    }

    override fun level(level: SkribeLevel) {
        this.level = level
    }

    override fun trace(message: String) {
        logIf(SkribeLevel.TRACE) { Log.v(tag, message) }
    }

    override fun debug(message: String) {
        logIf(SkribeLevel.DEBUG) { Log.d(tag, message) }
    }

    override fun info(message: String) {
        logIf(SkribeLevel.INFO) { Log.i(tag, message) }
    }

    override fun warn(message: String) {
        logIf(SkribeLevel.WARN) { Log.w(tag, message) }
    }

    override fun error(message: String) {
        logIf(SkribeLevel.ERROR) { Log.e(tag, message) }
    }

    override fun fatal(message: String) {
        logIf(SkribeLevel.FATAL) { Log.wtf(tag, message) }
    }

    private fun logIf(logLevel: SkribeLevel, log: () -> Unit) {
        if (logLevel.priority >= level.priority) {
            log()
        }
    }
}
