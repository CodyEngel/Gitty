package dev.engel.gitty.ui.core.list

import androidx.recyclerview.widget.DiffUtil

/**
 * ListViewModel provides a way to represent a list of items in a consistent way.
 */
interface ListViewModel<T : ViewModelRecord> {

    /**
     * @return the size of the items in the [ListViewModel]
     */
    val size: Int

    /**
     * @return the item [T] at the given index.
     */
    operator fun get(index: Int): T

    /**
     * Registers a listener for responding to [DiffUtil.DiffResult], this is useful if the
     * [ListViewModel] is being used within an Adapter or another class that can respond to these
     * results.
     */
    fun registerDiffResult(onDiffResult: (diffResult: DiffUtil.DiffResult) -> Unit)
}

interface ViewModelRecord {
    fun isSameAs(other: ViewModelRecord): Boolean {
        return this == other
    }
    fun hasSameContentAs(other: ViewModelRecord): Boolean {
        return this == other
    }
}