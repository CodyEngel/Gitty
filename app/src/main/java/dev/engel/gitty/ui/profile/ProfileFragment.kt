package dev.engel.gitty.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import dev.engel.gitty.R
import dev.engel.gitty.core.Skribe
import dev.engel.gitty.databinding.FragmentProfileBinding
import dev.engel.gitty.repository.ViewerCardRepository
import dev.engel.gitty.ui.core.list.AsyncLayoutManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewerCardRepository: ViewerCardRepository

    @Inject
    lateinit var skribe: Skribe

    private val organizationsPreviewViewModel: OrganizationsPreviewViewModel by viewModels()

    private val repositoriesPreviewViewModel: RepositoriesPreviewViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding!!

    private val organizationsPreviewAdapter by lazy { OrganizationsPreviewAdapter(organizationsPreviewViewModel) }

    private val repositoriesPreviewAdapter by lazy { RepositoriesPreviewAdapter(repositoriesPreviewViewModel) }

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
                AsyncLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            organizationsList.adapter = organizationsPreviewAdapter

            repositoriesList.layoutManager = AsyncLayoutManager(requireContext())
            repositoriesList.adapter = repositoriesPreviewAdapter

            MainScope().launch {
                val viewer = viewerCardRepository.retrieve().viewer
                nameTextView.text = viewer.name
                loginTextView.text = requireContext()
                    .getString(R.string.fragment_profile_login_text_view, viewer.login)
                profileImageView.load(viewer.avatarUrl.toString()) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
