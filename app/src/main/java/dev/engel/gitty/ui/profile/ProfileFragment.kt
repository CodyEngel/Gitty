package dev.engel.gitty.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import dev.engel.gitty.R
import dev.engel.gitty.repository.ViewerCardRepository
import dev.engel.gitty.ui.core.CoreFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : CoreFragment() {

    @Inject
    lateinit var viewerCardRepository: ViewerCardRepository

    private val organizationsPreviewAdapter by lazy { OrganizationsPreviewAdapter() }

    private val repositoriesPreviewAdapter by lazy { RepositoriesPreviewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        organizationsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        organizationsList.adapter = organizationsPreviewAdapter

        repositoriesList.layoutManager = LinearLayoutManager(requireContext())
        repositoriesList.adapter = repositoriesPreviewAdapter

        MainScope().launch {
            val viewer = viewerCardRepository.retrieve().viewer
            nameTextView.text = viewer.name
            loginTextView.text = requireContext()
                .getString(R.string.fragment_profile_login_text_view, viewer.login)
            profileImageView.load(viewer.avatarUrl.toString()) {
                transformations(CircleCropTransformation())
            }

            val organizationRecords = viewer.organizations.nodes?.mapNotNull { organization ->
                organization?.run {
                    OrganizationsPreviewAdapter.Record(
                        name = name!!,
                        avatarUrl = avatarUrl.toString()
                    )
                }
            } ?: emptyList()
            organizationsPreviewAdapter.updateRecords(organizationRecords)

            val repositoryRecords = viewer.repositories.nodes?.mapNotNull { repository ->
                repository?.run {
                    RepositoriesPreviewAdapter.Record(
                        title = name,
                        content = description ?: ""
                    )
                }
            } ?: emptyList()
            repositoriesPreviewAdapter.updateRecords(repositoryRecords)
        }
    }
}
