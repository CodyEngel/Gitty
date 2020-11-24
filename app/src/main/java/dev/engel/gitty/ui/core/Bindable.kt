package dev.engel.gitty.ui.core

interface Bindable<R : Bindable.Record> {
    fun bind(record: R)

    interface Record
}