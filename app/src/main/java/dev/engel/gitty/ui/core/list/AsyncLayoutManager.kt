package dev.engel.gitty.ui.core.list

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class AsyncLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}
