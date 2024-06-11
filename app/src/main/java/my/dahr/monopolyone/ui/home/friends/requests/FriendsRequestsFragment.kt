package my.dahr.monopolyone.ui.home.friends.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentFriendsRequestsBinding
import my.dahr.monopolyone.ui.home.friends.FriendsFragment
import my.dahr.monopolyone.ui.home.friends.FriendsViewModel
import my.dahr.monopolyone.ui.home.friends.adapters.FriendRequestsAdapter

@AndroidEntryPoint
class FriendsRequestsFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    private var _binding: FragmentFriendsRequestsBinding? = null
    private val binding get() = _binding!!

    private val adapter: FriendRequestsAdapter by lazy {
        FriendRequestsAdapter(object :
            FriendRequestsAdapter.OnAcceptFriendRequestClickListener {
            override fun onAcceptFriendRequestClicked(userId: Int) {
                viewModel.addFriend(userId)

            }
        }, object : FriendRequestsAdapter.OnRejectFriendRequestClickListener {
            override fun onRejectFriendRequestClicked(userId: Int) {
                viewModel.deleteFriend(userId)
                viewModel.getFriendRequestsList()
            }
        }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsRequestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setListeners()
        viewModel.getFriendRequestsList()
    }

    private fun initObservers() {
        viewModel.friendsRequestsResultLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvFriendRequests.layoutManager = layoutManager
            binding.rvFriendRequests.setHasFixedSize(true)
            binding.rvFriendRequests.adapter = adapter
        }
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            val fragment = FriendsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): FriendsRequestsFragment = FriendsRequestsFragment()
    }
}