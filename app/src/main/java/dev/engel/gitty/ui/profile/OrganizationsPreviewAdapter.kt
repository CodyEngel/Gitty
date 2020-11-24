package dev.engel.gitty.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.engel.gitty.R
import dev.engel.gitty.ui.core.Bindable

class OrganizationsPreviewAdapter : RecyclerView.Adapter<OrganizationsPreviewAdapter.OrganizationPreviewViewHolder>() {

    private val records: MutableList<Record> = mutableListOf()

    fun updateRecords(records: List<Record>) {
        this.records.clear()
        this.records.addAll(records)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrganizationPreviewViewHolder {
        val holderView = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_organization_preview, parent, false) as ImageView
        return OrganizationPreviewViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: OrganizationPreviewViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int = records.size

    data class Record(val name: String, val avatarUrl: String) : Bindable.Record

    class OrganizationPreviewViewHolder(
        private val holderView: ImageView
        ) : RecyclerView.ViewHolder(holderView), Bindable<Record> {
        override fun bind(record: Record) {
            with(holderView) {
                contentDescription = record.name
                load(record.avatarUrl)
            }
        }
    }
}
