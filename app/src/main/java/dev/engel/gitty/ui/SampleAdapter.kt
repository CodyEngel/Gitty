package dev.engel.gitty.ui

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.engel.gitty.ui.core.list.ListViewModel

class SampleAdapter(
    private val listViewModel: ListViewModel<String>
) : RecyclerView.Adapter<SampleAdapter.Holder>() {

    init {
        listViewModel.registerDiffResult { diffResult ->
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(TextView(parent.context))

    override fun getItemCount(): Int = listViewModel.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listViewModel[position])
    }

    class Holder(itemView: TextView) : RecyclerView.ViewHolder(itemView) {
        fun bind(string: String) = with(itemView as TextView) {
            text = string
        }
    }
}