package dev.engel.gitty.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.engel.gitty.databinding.HolderRepositoryPreviewBinding
import dev.engel.gitty.ui.core.list.BindableViewHolder
import dev.engel.gitty.ui.core.list.ListViewModelAdapter

class RepositoriesPreviewAdapter(
    repositoriesPreviewViewModel: RepositoriesPreviewViewModel
) : ListViewModelAdapter<RepositoryPreviewRecord, HolderRepositoryPreviewBinding>(repositoriesPreviewViewModel) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryPreviewViewHolder {
        val binding = HolderRepositoryPreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryPreviewViewHolder(binding)
    }

    class RepositoryPreviewViewHolder(
        binding: HolderRepositoryPreviewBinding
    ) : BindableViewHolder<RepositoryPreviewRecord, HolderRepositoryPreviewBinding>(binding) {
        override fun bind(record: RepositoryPreviewRecord) = with(binding) {
            titleTextView.text = record.title
            contentTextView.text = record.content
        }
    }

}
