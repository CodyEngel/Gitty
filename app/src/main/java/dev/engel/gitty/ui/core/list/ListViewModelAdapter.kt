package dev.engel.gitty.ui.core.list

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ListViewModelAdapter<VMR : ViewModelRecord, VB : ViewBinding>(
    private val listViewModel: ListViewModel<VMR>
): RecyclerView.Adapter<BindableViewHolder<VMR, VB>>() {

    init {
        listViewModel.registerDiffResult { diffResult ->
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onBindViewHolder(holder: BindableViewHolder<VMR, VB>, position: Int) {
        holder.bind(listViewModel[position])
    }

    override fun getItemCount(): Int = listViewModel.size
}