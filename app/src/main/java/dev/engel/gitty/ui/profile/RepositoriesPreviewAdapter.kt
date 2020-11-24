package dev.engel.gitty.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.engel.gitty.databinding.HolderRepositoryPreviewBinding
import dev.engel.gitty.ui.core.Bindable
import dev.engel.gitty.ui.core.list.BindableViewHolder

class RepositoriesPreviewAdapter : RecyclerView.Adapter<RepositoriesPreviewAdapter.RepositoryPreviewViewHolder>() {

    private val records: MutableList<Record> = mutableListOf()

    fun updateRecords(records: List<Record>) {
        this.records.clear()
        this.records.addAll(records)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryPreviewViewHolder {
        val binding = HolderRepositoryPreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryPreviewViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int = records.size

    data class Record(val title: String, val content: String) : Bindable.Record

    class RepositoryPreviewViewHolder(
        binding: HolderRepositoryPreviewBinding
    ) : BindableViewHolder<Record, HolderRepositoryPreviewBinding>(binding) {
        override fun bind(record: Record) = with(binding) {
            titleTextView.text = record.title
            contentTextView.text = record.content
        }
    }

}
