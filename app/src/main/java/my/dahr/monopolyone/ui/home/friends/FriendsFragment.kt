package my.dahr.monopolyone.ui.home.friends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.databinding.FragmentFriendsBinding
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import my.dahr.monopolyone.ui.home.friends.adapters.FriendsAdapter
import my.dahr.monopolyone.ui.home.friends.add.AddFriendsFragment
import my.dahr.monopolyone.ui.home.friends.requests.FriendsRequestsFragment
import my.dahr.monopolyone.ui.home.friends.user.UserFragment
import my.dahr.monopolyone.utils.LoadingDialog


@AndroidEntryPoint
class FriendsFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    private var _binding: FragmentFriendsBinding? = null
    private lateinit var loadingDialog: LoadingDialog


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
        loadingDialog = LoadingDialog(requireActivity())

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
        binding.ibAdd.setOnClickListener {
            val fragment = AddFriendsFragment()
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
        viewModel.requestStatusLiveData.observe(viewLifecycleOwner) { status ->
            when (status) {
                RequestStatus.Success -> {
                    loadingDialog.isDismiss()
                }

                RequestStatus.Failure -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.dialog_failure_title))
                        .setPositiveButton(resources.getString(R.string.dialog_bt_ok)) { _, _ -> }
                        .setMessage(R.string.dialog_failure_text)
                        .show()
                }

                RequestStatus.Loading -> {
                    loadingDialog.startLoading()
                }

                else -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.dialog_error_title))
                        .setPositiveButton(resources.getString(R.string.dialog_bt_ok)) { _, _ -> }
                        .setMessage(viewModel.loadErrorMessage(status))
                        .show()
                }
            }
        }
    }

    private fun showFriendsList(friends: List<Friend>) {
        val adapter = FriendsAdapter(object : FriendsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int, friend: Friend) {
                val fragment = UserFragment.newInstance(friend.userId,
                    friend.avatar,
                    friend.nick,
                    friend.xpLevel,
                    friend.xp,
                    friend.games,
                    friend.gamesWins)
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