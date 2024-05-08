package my.dahr.monopolyone.ui.home.friends.user

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentUserBinding
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.ui.home.friends.FriendsFragment
import my.dahr.monopolyone.ui.home.friends.FriendsViewModel
import my.dahr.monopolyone.ui.home.friends.user.friends.UserFriendsFragment
import my.dahr.monopolyone.utils.CORPORAL
import my.dahr.monopolyone.utils.RECRUIT
import my.dahr.monopolyone.utils.ROOKIE
import my.dahr.monopolyone.utils.SOLDIER

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private var friend: Friend? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friend = arguments?.getSerializable("friend", Friend::class.java)
        viewModel.getFriendListForUser(friend!!)

        setInfo()
        setListeners()
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            val fragment = FriendsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
        binding.LayoutCountOfFriends.setOnClickListener {
            val fragment = UserFriendsFragment.newInstance(friend!!)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setInfo() {
        binding.apply {
            viewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
                binding.tvCountOfFriends.text = it.size.toString()
            }
            tvFriendNick.text = friend?.nick
            tvRankLevelNumber.text = friend?.xpLevel.toString()
            tvXp.text = friend?.xp.toString()
            tvCountOfAllMatches.text = friend?.games.toString()
            tvCountOfWinningMatches.text = friend?.gamesWins.toString()

            val lvl = friend?.xpLevel
            when (lvl) {
                in 1..4 -> {
                    tvRankName.text = ROOKIE
                    setRankPhoto(0)

                    if (friend != null) {
                        setPhoto(friend!!)
                    }
                }

                in 5..9 -> {
                    tvRankName.text = RECRUIT
                    setRankPhoto(1)

                    if (friend != null) {
                        setPhoto(friend!!)
                    }
                }

                in 10..14 -> {
                    tvRankName.text = SOLDIER

                    setRankPhoto(2)

                    if (friend != null) {
                        setPhoto(friend!!)
                    }
                }

                in 15..19 -> tvRankName.text = CORPORAL
            }

        }

    }

    private fun setRankPhoto(number: Int) {
        Glide.with(this)
            .load("https://cdn2.kirick.me/libs/monopoly/badges_xp/$number.png")
            .into(binding.ivRank)
    }

    private fun setPhoto(friend: Friend) {
        Glide.with(this)
            .load(friend.avatar)
            .into(binding.ivFriend)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(friend: Friend): UserFragment {
            val fragment = UserFragment()
            val bundle = Bundle()
            bundle.putSerializable("friend", friend)
            fragment.arguments = bundle
            return fragment
        }
    }
}