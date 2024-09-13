package my.dahr.monopolyone.ui.friends.user.friends

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
import my.dahr.monopolyone.ui.friends.FriendsViewModel
import my.dahr.monopolyone.ui.friends.adapters.FriendsAdapter
import my.dahr.monopolyone.ui.friends.user.UserFragment
import my.dahr.monopolyone.utils.LoadingDialog

@AndroidEntryPoint
class UserFriendsFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    private lateinit var loadingDialog:LoadingDialog

    private var _binding: FragmentUserFriendsBinding? = null
    private val binding get() = _binding!!

    private var userId: Any? = null
    private var avatar: String? = null
    private var nick: String? = null
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
        loadingDialog = LoadingDialog(requireActivity())
        receiveData()
        setContent()
        initObservers()
        viewModel.getFriendListForUser(userId!!)
    }
    private fun receiveData() {
        arguments?.let {
            userId = when {
                it.containsKey(USER_ID_X_INT) -> it.getInt(USER_ID_X_INT)
                it.containsKey(USER_ID_X_STRING) -> it.getString(USER_ID_X_STRING)
                else -> null
            }
            avatar = it.getString(USER_AVATAR_X)
            nick = it.getString(USER_NICK_X)
        }
    }

    private fun initObservers() {
        viewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
            showFriendsList(it)
        }
    }

    private fun showFriendsList(friends: List<Friend>) {
        val adapter = FriendsAdapter(object : FriendsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int, friend: Friend) {
                val fragment = UserFragment.newInstance(
                    friend.userId,
                    friend.avatar,
                    friend.nick,
                    friend.xpLevel,
                    friend.xp,
                    friend.games,
                    friend.gamesWins
                )
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
            tvFriendNick.text = nick
            viewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
                tvCountOfFriends.text = it.size.toString()
            }
            Glide.with(this@UserFriendsFragment)
                .load(avatar)
                .into(ivFriend)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val USER_ID_X_INT = "id_int"
        private const val USER_ID_X_STRING = "id_string"
        private const val USER_AVATAR_X = "avatar"
        private const val USER_NICK_X = "nick"
        fun newInstance(userId: Any, avatar: String, nick: String): UserFriendsFragment {
            return UserFriendsFragment().apply {
                arguments = Bundle().apply {
                    when (userId) {
                        is Int -> putInt(USER_ID_X_INT, userId)
                        is String -> putString(USER_ID_X_STRING, userId)
                        else -> throw IllegalArgumentException("Unsupported type for id")
                    }
                    putString(USER_AVATAR_X, avatar)
                    putString(USER_NICK_X, nick)
                }
            }
        }
    }
}