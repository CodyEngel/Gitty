package dev.engel.gitty.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import dev.engel.gitty.databinding.HolderOrganizationPreviewBinding
import dev.engel.gitty.ui.core.list.BindableViewHolder
import dev.engel.gitty.ui.core.list.ListViewModelAdapter

class OrganizationsPreviewAdapter(
    organizationsPreviewViewModel: OrganizationsPreviewViewModel
) : ListViewModelAdapter<OrganizationPreviewRecord, HolderOrganizationPreviewBinding>(organizationsPreviewViewModel) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationPreviewViewHolder {
        val binding = HolderOrganizationPreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return OrganizationPreviewViewHolder(binding)
    }

    class OrganizationPreviewViewHolder(
        binding: HolderOrganizationPreviewBinding
        ) : BindableViewHolder<OrganizationPreviewRecord, HolderOrganizationPreviewBinding>(binding) {
        override fun bind(record: OrganizationPreviewRecord) = with(binding) {
            root.load(record.avatarUrl)
            root.contentDescription = record.name
        }
    }
}
