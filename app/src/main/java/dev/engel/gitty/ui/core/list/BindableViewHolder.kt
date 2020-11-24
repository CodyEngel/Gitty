package dev.engel.gitty.ui.core.list

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dev.engel.gitty.ui.core.Bindable

abstract class BindableViewHolder<R : Bindable.Record, VB : ViewBinding>(
    protected val binding: VB
) : RecyclerView.ViewHolder(binding.root), Bindable<R>