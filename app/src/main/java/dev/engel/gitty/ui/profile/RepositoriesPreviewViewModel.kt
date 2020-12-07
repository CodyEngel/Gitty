package dev.engel.gitty.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import dev.engel.gitty.core.Skribe
import dev.engel.gitty.repository.ViewerCardRepository
import dev.engel.gitty.ui.core.list.MutableListViewModel
import dev.engel.gitty.ui.core.list.ViewModelRecord
import kotlinx.coroutines.launch

class RepositoriesPreviewViewModel @ViewModelInject constructor(
    private val viewerCardRepository: ViewerCardRepository,
    private val skribe: Skribe
) : MutableListViewModel<RepositoryPreviewRecord>(skribe) {

    init {
        skribe tag javaClass.simpleName
        viewModelScope.launch {
            val viewer = viewerCardRepository.retrieve().viewer
            val repositoryRecords = viewer.repositories.nodes?.mapNotNull { repository ->
                repository?.run {
                    RepositoryPreviewRecord(
                        title = name,
                        content = description ?: ""
                    )
                }
            } ?: emptyList()
            skribe debug "Repository Records: $repositoryRecords"
            skribe info "Total Repository Records: ${repositoryRecords.size}"
            replaceAll(repositoryRecords)
        }
    }
}

data class RepositoryPreviewRecord(val title: String, val content: String) : ViewModelRecord
