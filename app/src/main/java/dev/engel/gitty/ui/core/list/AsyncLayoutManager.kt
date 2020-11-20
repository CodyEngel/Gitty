package dev.engel.gitty.ui.core.list

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AsyncLayoutManager(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverse: Boolean = false
) : LinearLayoutManager(context, orientation, reverse) {
    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}
