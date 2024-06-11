package my.dahr.monopolyone.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentProfileBinding
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
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private val friendsViewModel: FriendsViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get () = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.getListOfUsers()

    }
    private fun setListeners(){
        val user = viewModel.getUser()
        binding.LayoutCountOfFriends.setOnClickListener{
            val fragment = UserFriendsFragment.newInstance(user!!.userId, user.avatar, user.nick)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    private fun initObservers(){
        viewModel.usersResultLiveData.observe(viewLifecycleOwner) {
            val user = it[0]
            viewModel.setUser(user)
//            setInfo()
            setListeners()
        }
    }

//    private fun setInfo(){
//        val user = viewModel.getUser()
//
//        friendsViewModel.getFriendListForUser(user!!.userId)
//
//        binding.apply {
//            friendsViewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
//                binding.tvCountOfFriends.text = it.size.toString()
//            }
//            val nextLevel = user.xpLevel.plus(1)
//            tvNextLevel.text = nextLevel.toString()
//
//            tvFriendNick.text = user.nick
//            tvRankLevelNumber.text = user.xpLevel.toString()
//            tvXp.text = showXp(user.xpLevel, user.xp)
//            tvCountOfAllMatches.text = user.games.toString()
//            tvCountOfWinningMatches.text = user.gamesWins.toString()
//
//            val lvl = user.xpLevel
//            when (lvl) {
//                in 1..4 -> {
//                    tvRankName.text = ROOKIE
//                    setRankPhoto(0)
//                    setPhoto(user.avatar)
//                }
//
//                in 5..9 -> {
//                    tvRankName.text = RECRUIT
//                    setRankPhoto(1)
//                    setPhoto(user.avatar)
//                }
//
//                in 10..14 -> {
//                    tvRankName.text = SOLDIER
//                    setRankPhoto(2)
//                    setPhoto(user.avatar)
//                }
//
//                in 15..19 -> {
//                    tvRankName.text = LANCE_CORPORAL
//                    setRankPhoto(3)
//                    setPhoto(user.avatar)
//                }
//
//                in 20..24 -> {
//                    tvRankName.text = CORPORAL
//                    setRankPhoto(4)
//                    setPhoto(user.avatar)
//                }
//
//                in 25..29 -> {
//                    tvRankName.text = MASTER_CORPORAL
//                    setRankPhoto(5)
//                    setPhoto(user.avatar)
//                }
//
//                in 30..34 -> {
//                    tvRankName.text = LANCE_SERGEANT
//                    setRankPhoto(6)
//                    setPhoto(user.avatar)
//                }
//
//                in 35..39 -> {
//                    tvRankName.text = SERGEANT
//                    setRankPhoto(7)
//                    setPhoto(user.avatar)
//                }
//
//                in 40..44 -> {
//                    tvRankName.text = MASTER_SERGEANT
//                    setRankPhoto(8)
//                    setPhoto(user.avatar)
//                }
//
//                in 45..49 -> {
//                    tvRankName.text = SERGEANT_MAJOR
//                    setRankPhoto(9)
//                    setPhoto(user.avatar)
//                }
//
//                in 50..54 -> {
//                    tvRankName.text = LIEUTENANT
//                    setRankPhoto(10)
//                    setPhoto(user.avatar)
//                }
//
//                in 55..59 -> {
//                    tvRankName.text = LIEUTENANT_MAJOR
//                    setRankPhoto(11)
//                    setPhoto(user.avatar)
//                }
//
//                in 60..64 -> {
//                    tvRankName.text = CAPTAIN
//                    setRankPhoto(12)
//                    setPhoto(user.avatar)
//                }
//
//                in 65..69 -> {
//                    tvRankName.text = MAJOR
//                    setRankPhoto(13)
//                    setPhoto(user.avatar)
//                }
//
//                in 70..74 -> {
//                    tvRankName.text = LIEUTENANT_COLONEL
//                    setRankPhoto(14)
//                    setPhoto(user.avatar)
//                }
//
//                in 75..79 -> {
//                    tvRankName.text = COLONEL
//                    setRankPhoto(15)
//                    setPhoto(user.avatar)
//                }
//
//                in 80..84 -> {
//                    tvRankName.text = BRIGADIER_GENERAL
//                    setRankPhoto(16)
//                    setPhoto(user.avatar)
//                }
//
//                in 85..89 -> {
//                    tvRankName.text = GENERAL_MAJOR
//                    setRankPhoto(17)
//                    setPhoto(user.avatar)
//                }
//
//                in 90..94 -> {
//                    tvRankName.text = GENERAL
//                    setRankPhoto(18)
//                    setPhoto(user.avatar)
//                }
//
//                in 95..99 -> {
//                    tvRankName.text = LIEUTENANT_GENERAL
//                    setRankPhoto(19)
//                    setPhoto(user.avatar)
//                }
//
//                in 100..10000 -> {
//                    tvRankName.text = MARSHAL
//                    setRankPhoto(20)
//                    setPhoto(user.avatar)
//                }
//
//            }
//        }
//    }

    private fun showXp(xpLevel: Int, xp: Int): String {
        val totalXpForNextLevel = (2 * 250 + (xpLevel - 1) * 25) * xpLevel / 2
        val totalXpForThisLevel =
            (2 * 250 + (xpLevel - 2) * 25) * (xpLevel - 1) / 2
        val remainderOfXpForNextLevel = totalXpForNextLevel - xp

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

    private fun setPhoto(avatar: String?) {
        Glide.with(this)
            .load(avatar)
            .into(binding.ivFriend)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}