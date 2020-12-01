package dev.engel.gitty.ui.core.list

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import dev.engel.gitty.core.Skribe

/**
 * A MutableListViewModel provides a way of maintaining list items. This can be useful with
 * traditional ViewModels that also manage lists of items.
 */
abstract class MutableListViewModel<T : ViewModelRecord>(
    private val skribe: Skribe
) : ViewModel(), ListViewModel<T> {

    private val items: MutableList<T> = mutableListOf()

    private var onDiffResult: ((diffResult: DiffUtil.DiffResult) -> Unit)? = null

    override val size: Int
        get() = items.size.also { skribe debug "size: $it" }

    override fun get(index: Int): T = items[index].also { skribe debug "get: $it" }

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
    private fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.isSameAs(newItem)

    /**
     * Used to determine if the contents of the items are the same, this is useful when the
     * identifiers are the same but some other details have changed which should be accounted for.
     */
    private fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem.hasSameContentAs(newItem)

    /**
     * Replaces all items with the [newItems]. This will also notify the [onDiffResult] callback.
     */
    fun replaceAll(newItems: List<T>) {
        skribe debug "replaceAll"
        skribe debug "oldItems: $items"
        skribe debug "newItems: $newItems"
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemsTheSame(items[oldItemPosition], newItems[newItemPosition])
                    .also { skribe debug "areItemsTheSame: $it" }
            }

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentsTheSame(items[oldItemPosition], newItems[newItemPosition])
                    .also { skribe debug "areContentsTheSame: $it" }
            }
        })
        items.clear()
        items.addAll(newItems)

        onDiffResult?.invoke(result)
    }
}