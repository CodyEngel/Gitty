package dev.engel.gitty.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import dev.engel.gitty.R
import dev.engel.gitty.databinding.FragmentProfileBinding
import dev.engel.gitty.repository.ViewerCardRepository
import dev.engel.gitty.ui.core.CoreFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : CoreFragment() {

    @Inject
    lateinit var viewerCardRepository: ViewerCardRepository

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding!!

    private val organizationsPreviewAdapter by lazy { OrganizationsPreviewAdapter() }

    private val repositoriesPreviewAdapter by lazy { RepositoriesPreviewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
