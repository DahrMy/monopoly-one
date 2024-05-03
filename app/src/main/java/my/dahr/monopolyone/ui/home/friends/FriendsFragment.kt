package my.dahr.monopolyone.ui.home.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentFriendsBinding
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import my.dahr.monopolyone.ui.home.friends.adapters.FriendsAdapter
import my.dahr.monopolyone.ui.home.friends.requests.FriendsRequestsFragment
import my.dahr.monopolyone.ui.home.friends.user.UserFragment


@AndroidEntryPoint
class FriendsFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        viewModel.getFriendRequestsList()
        viewModel.getFriendList()

        setListeners()


    }

    private fun setListeners() {
        binding.layout.setOnClickListener {

            val fragment = FriendsRequestsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    private fun initObservers() {
        viewModel.friendsResultLiveData.observe(viewLifecycleOwner) {
            showFriendsList(it)
        }
        viewModel.friendsRequestsResultLiveData.observe(viewLifecycleOwner) {
            checkFriendRequests(it)
        }
    }

    private fun showFriendsList(friends: List<Friend>) {
        val adapter = FriendsAdapter(object : FriendsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int, friend: Friend) {
                val fragment = UserFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            }

        })
        adapter.submitList(friends)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFriends.layoutManager = layoutManager
        binding.rvFriends.setHasFixedSize(true)
        binding.rvFriends.adapter = adapter
    }

    private fun checkFriendRequests(requests: List<Request>) {
        if (requests.isNotEmpty()) {
            val countOfRequests = requests.size
            binding.notificationTextView.apply {
                text = countOfRequests.toString()
                visibility = View.VISIBLE
            }
        } else {
            binding.notificationTextView.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): FriendsFragment = FriendsFragment()
    }
}