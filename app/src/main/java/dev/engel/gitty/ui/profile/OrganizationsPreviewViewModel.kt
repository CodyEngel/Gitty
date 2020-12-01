package dev.engel.gitty.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import dev.engel.gitty.core.Skribe
import dev.engel.gitty.repository.ViewerCardRepository
import dev.engel.gitty.ui.core.Bindable
import dev.engel.gitty.ui.core.list.MutableListViewModel
import dev.engel.gitty.ui.core.list.ViewModelRecord
import kotlinx.coroutines.launch

class OrganizationsPreviewViewModel @ViewModelInject constructor(
    private val viewerCardRepository: ViewerCardRepository,
    private val skribe: Skribe
) : MutableListViewModel<OrganizationPreviewRecord>(skribe) {

    init {
        skribe tag javaClass.simpleName
        viewModelScope.launch {
            val viewer = viewerCardRepository.retrieve().viewer
            val organizationRecords = viewer.organizations.nodes?.mapNotNull { organization ->
                organization?.run {
                    OrganizationPreviewRecord(
                        id = id,
                        name = name!!,
                        avatarUrl = avatarUrl.toString()
                    )
                }
            } ?: emptyList()
            skribe debug "Organization Records: $organizationRecords"
            skribe info "Total Organization Records: ${organizationRecords.size}"
            replaceAll(organizationRecords)
        }
    }
}

data class OrganizationPreviewRecord(
    val id: String,
    val name: String,
    val avatarUrl: String
) : Bindable.Record, ViewModelRecord {
    override fun isSameAs(other: ViewModelRecord): Boolean {
        return when (other) {
            is OrganizationPreviewRecord -> {
                id == other.id
            }
            else -> false
        }
    }

    override fun hasSameContentAs(other: ViewModelRecord): Boolean {
        return when (other) {
            is OrganizationPreviewRecord -> {
                name == other.name && avatarUrl == other.avatarUrl
            }
            else -> false
        }
    }
}