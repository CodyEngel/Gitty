package dev.engel.gitty.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.engel.gitty.R
import dev.engel.gitty.ui.core.Bindable
import dev.engel.gitty.ui.core.list.ListViewModel

class OrganizationsPreviewAdapter(
    private val listViewModel: ListViewModel<OrganizationPreviewRecord>
) : RecyclerView.Adapter<OrganizationsPreviewAdapter.OrganizationPreviewViewHolder>() {

    init {
        listViewModel.registerDiffResult { diffResult ->
            diffResult.dispatchUpdatesTo(this)
        }
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
        holder.bind(listViewModel[position])
    }

    override fun getItemCount(): Int = listViewModel.size

    class OrganizationPreviewViewHolder(
        private val holderView: ImageView
        ) : RecyclerView.ViewHolder(holderView), Bindable<OrganizationPreviewRecord> {
        override fun bind(record: OrganizationPreviewRecord) {
            with(holderView) {
                contentDescription = record.name
                load(record.avatarUrl)
            }
        }
    }
}
