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
import my.dahr.monopolyone.utils.BRIGADIER_GENERAL
import my.dahr.monopolyone.utils.CAPTAIN
import my.dahr.monopolyone.utils.COLONEL
import my.dahr.monopolyone.utils.CORPORAL
import my.dahr.monopolyone.utils.GENERAL
import my.dahr.monopolyone.utils.GENERAL_MAJOR
import my.dahr.monopolyone.utils.LANCE_CORPORAL
import my.dahr.monopolyone.utils.LANCE_SERGEANT
import my.dahr.monopolyone.utils.LIEUTENANT
import my.dahr.monopolyone.utils.LIEUTENANT_COLONEL
import my.dahr.monopolyone.utils.LIEUTENANT_GENERAL
import my.dahr.monopolyone.utils.LIEUTENANT_MAJOR
import my.dahr.monopolyone.utils.MAJOR
import my.dahr.monopolyone.utils.MARSHAL
import my.dahr.monopolyone.utils.MASTER_CORPORAL
import my.dahr.monopolyone.utils.MASTER_SERGEANT
import my.dahr.monopolyone.utils.RECRUIT
import my.dahr.monopolyone.utils.ROOKIE
import my.dahr.monopolyone.utils.SERGEANT
import my.dahr.monopolyone.utils.SERGEANT_MAJOR
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
            val nextLevel = friend?.xpLevel?.plus(1)
            tvNextLevel.text = nextLevel.toString()

            tvFriendNick.text = friend?.nick
            tvRankLevelNumber.text = friend?.xpLevel.toString()
            tvXp.text = showXp(friend!!)
            tvCountOfAllMatches.text = friend?.games.toString()
            tvCountOfWinningMatches.text = friend?.gamesWins.toString()

            val lvl = friend?.xpLevel
            when (lvl) {
                in 1..4 -> {
                    tvRankName.text = ROOKIE
                    setRankPhoto(0)
                    setPhoto(friend!!)
                }

                in 5..9 -> {
                    tvRankName.text = RECRUIT
                    setRankPhoto(1)
                    setPhoto(friend!!)
                }

                in 10..14 -> {
                    tvRankName.text = SOLDIER
                    setRankPhoto(2)
                    setPhoto(friend!!)
                }

                in 15..19 -> {
                    tvRankName.text = LANCE_CORPORAL
                    setRankPhoto(3)
                    setPhoto(friend!!)
                }

                in 20..24 -> {
                    tvRankName.text = CORPORAL
                    setRankPhoto(4)
                    setPhoto(friend!!)
                }

                in 25..29 -> {
                    tvRankName.text = MASTER_CORPORAL
                    setRankPhoto(5)
                    setPhoto(friend!!)
                }

                in 30..34 -> {
                    tvRankName.text = LANCE_SERGEANT
                    setRankPhoto(6)
                    setPhoto(friend!!)
                }

                in 35..39 -> {
                    tvRankName.text = SERGEANT
                    setRankPhoto(7)
                    setPhoto(friend!!)
                }

                in 40..44 -> {
                    tvRankName.text = MASTER_SERGEANT
                    setRankPhoto(8)
                    setPhoto(friend!!)
                }

                in 45..49 -> {
                    tvRankName.text = SERGEANT_MAJOR
                    setRankPhoto(5)
                    setPhoto(friend!!)
                }

                in 50..54 -> {
                    tvRankName.text = LIEUTENANT
                    setRankPhoto(6)
                    setPhoto(friend!!)
                }

                in 55..59 -> {
                    tvRankName.text = LIEUTENANT_MAJOR
                    setRankPhoto(7)
                    setPhoto(friend!!)
                }

                in 60..64 -> {
                    tvRankName.text = CAPTAIN
                    setRankPhoto(8)
                    setPhoto(friend!!)
                }

                in 65..69 -> {
                    tvRankName.text = MAJOR
                    setRankPhoto(5)
                    setPhoto(friend!!)
                }

                in 70..74 -> {
                    tvRankName.text = LIEUTENANT_COLONEL
                    setRankPhoto(6)
                    setPhoto(friend!!)
                }

                in 75..79 -> {
                    tvRankName.text = COLONEL
                    setRankPhoto(7)
                    setPhoto(friend!!)
                }

                in 80..84 -> {
                    tvRankName.text = BRIGADIER_GENERAL
                    setRankPhoto(8)
                    setPhoto(friend!!)
                }
                in 85..89 -> {
                    tvRankName.text = GENERAL_MAJOR
                    setRankPhoto(5)
                    setPhoto(friend!!)
                }

                in 90..94 -> {
                    tvRankName.text = GENERAL
                    setRankPhoto(6)
                    setPhoto(friend!!)
                }

                in 95..99 -> {
                    tvRankName.text = LIEUTENANT_GENERAL
                    setRankPhoto(7)
                    setPhoto(friend!!)
                }

                in 100..10000 -> {
                    tvRankName.text = MARSHAL
                    setRankPhoto(8)
                    setPhoto(friend!!)
                }

            }
        }
    }

    private fun showXp(friend: Friend): String {
        val totalXpForNextLevel = (2 * 250 + (friend.xpLevel - 1) * 25) * friend.xpLevel / 2
        val totalXpForThisLevel = (2 * 250 + (friend.xpLevel - 2) * 25) * (friend.xpLevel - 1) / 2
        val remainderOfXpForNextLevel = totalXpForNextLevel - friend.xp

        val xpBetweenLevels = totalXpForNextLevel - totalXpForThisLevel
        binding.progressBar.max = xpBetweenLevels
        val progress = xpBetweenLevels - remainderOfXpForNextLevel
        if (progress < 5) {
            binding.progressBar.progress = 15
        } else {
            binding.progressBar.progress = progress
        }
        return remainderOfXpForNextLevel.toString()
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