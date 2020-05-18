package dev.engel.gitty.ui.core.list

import androidx.recyclerview.widget.DiffUtil

/**
 * A MutableListViewModel provides a way of maintaining list items. This can be useful with
 * traditional ViewModels that also manage lists of items.
 */
abstract class MutableListViewModel<T> : ListViewModel<T> {

    private val items: MutableList<T> = mutableListOf()

    private var onDiffResult: ((diffResult: DiffUtil.DiffResult) -> Unit)? = null

    override val size: Int
        get() = items.size

    override fun get(index: Int): T = items[index]

    /**
     * Sets an [item] at the given [index].
     */
    operator fun set(index: Int, item: T) {
        add(index, item)
    }

    override fun registerDiffResult(onDiffResult: (diffResult: DiffUtil.DiffResult) -> Unit) {
        this.onDiffResult = onDiffResult
    }

    /**
     * Adds a new [item] at the end of the list.
     */
    fun add(item: T) {
        items.add(item)
    }

    /**
     * Adds a new [item] at the given [index].
     */
    fun add(index: Int, item: T) {
        items[index] = item
    }

    /**
     * Removes and item at the given [index].
     */
    fun remove(index: Int) {
        items.removeAt(index)
    }

    /**
     * Removes an [item] if it exists in the list.
     */
    fun remove(item: T) {
        items.remove(item)
    }

    /**
     * Used for determining if items are the same, typically using a unique identifier such as an ID
     * from a database.
     */
    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    /**
     * Used to determine if the contents of the items are the same, this is useful when the
     * identifiers are the same but some other details have changed which should be accounted for.
     */
    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    /**
     * Replaces all items with the [newItems]. This will also notify the [onDiffResult] callback.
     */
    fun replaceAll(newItems: List<T>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemsTheSame(items[oldItemPosition], newItems[newItemPosition])
            }

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentsTheSame(items[oldItemPosition], newItems[newItemPosition])
            }
        })

        onDiffResult?.invoke(result)
    }
}