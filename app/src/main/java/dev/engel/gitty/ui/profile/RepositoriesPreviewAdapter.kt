package dev.engel.gitty.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.engel.gitty.R
import kotlinx.android.synthetic.main.holder_repository_preview.view.*

class RepositoriesPreviewAdapter : RecyclerView.Adapter<RepositoriesPreviewAdapter.RepositoryPreviewViewHolder>() {

    private val records: MutableList<Record> = mutableListOf()

    fun updateRecords(records: List<Record>) {
        this.records.clear()
        this.records.addAll(records)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryPreviewViewHolder {
        val holderView = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_repository_preview, parent, false)
        return RepositoryPreviewViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: RepositoryPreviewViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int = records.size

    data class Record(val title: String, val content: String)

    class RepositoryPreviewViewHolder(private val holderView: View) : RecyclerView.ViewHolder(holderView) {
        fun bind(record: Record) = with(holderView) {
            titleTextView.text = record.title
            contentTextView.text = record.content
        }
    }

}
