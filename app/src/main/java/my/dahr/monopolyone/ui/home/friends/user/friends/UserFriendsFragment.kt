package my.dahr.monopolyone.ui.home.friends.user.friends

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentUserFriendsBinding
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.ui.home.friends.FriendsViewModel
import my.dahr.monopolyone.ui.home.friends.adapters.FriendsAdapter
import my.dahr.monopolyone.ui.home.friends.user.UserFragment

@AndroidEntryPoint
class UserFriendsFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    private var _binding: FragmentUserFriendsBinding? = null
    private val binding get() = _binding!!

    private var friend: Friend? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friend = arguments?.getSerializable("user_friends", Friend::class.java)
        setContent()
        initObservers()
        viewModel.getFriendListForUser(friend!!)
    }

    private fun initObservers() {
        viewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
            showFriendsList(it)
        }
    }

    private fun showFriendsList(friends: List<Friend>) {
        val adapter = FriendsAdapter(object : FriendsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int, friend: Friend) {
                val fragment = UserFragment.newInstance(friend)
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setContent() {
        binding.apply {
            tvFriendNick.text = friend?.nick
            viewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
                tvCountOfFriends.text = it.size.toString()
            }
            Glide.with(this@UserFriendsFragment)
                .load(friend?.avatar)
                .into(ivFriend)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(friend: Friend): UserFriendsFragment {
            val fragment = UserFriendsFragment()
            val bundle = Bundle()
            bundle.putSerializable("user_friends", friend)
            fragment.arguments = bundle
            return fragment
        }
    }
}