package my.dahr.monopolyone.ui.home.friends.user

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentUserBinding
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

    private var userId: Any? = null
    private var avatar: String? = null
    private var nick: String? = null
    private var xpLevel: Int? = null
    private var xp: Int? = null
    private var games: Int? = null
    private var wins: Int? = null

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
        receiveData()
        viewModel.checkIfFriend(userId!!)
        viewModel.getFriendListForUser(userId!!)

        setInfo()
        setListeners()
    }

    private fun receiveData() {
        arguments?.let {
            userId = when {
                it.containsKey(USER_ID_INT) -> it.getInt(USER_ID_INT)
                it.containsKey(USER_ID_STRING) -> it.getString(USER_ID_STRING)
                else -> null
            }
            avatar = it.getString(USER_AVATAR)
            nick = it.getString(USER_NICK)
            xpLevel = it.getInt(USER_XP_LEVEL)
            xp = it.getInt(USER_XP)
            games = it.getInt(USER_GAMES)
            wins = it.getInt(USER_GAME_WINS)
        }
    }

    private fun setListeners() {
        binding.layoutAddToFriends.setOnClickListener {
            viewModel.addFriend(userId!!)
        }
        binding.layoutMore.setOnClickListener {
            showPopupMenu(it)
        }

        binding.ivBack.setOnClickListener {
            val fragment = FriendsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
        binding.LayoutCountOfFriends.setOnClickListener {
            val fragment = UserFriendsFragment.newInstance(userId!!, avatar!!, nick!!)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.drop_down_menu_for_friends, popup.menu)
        popup.setOnMenuItemClickListener { item -> handleMenuItemClick(item) }
        popup.show()
    }

    private fun handleMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                viewModel.deleteFriend(userId!!)
                true
            }

            else -> false
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setInfo() {
        binding.apply {
            if (!viewModel.checkIfMe(userId!!)) {
                viewModel.isFriend.observe(viewLifecycleOwner) {
                    if (it == true) {
                        binding.layoutAddToFriends.visibility = View.INVISIBLE
                        binding.layoutYouAreFriends.visibility = View.VISIBLE
                        binding.layoutMore.visibility = View.VISIBLE
                    } else {
                        binding.layoutAddToFriends.visibility = View.VISIBLE
                        binding.layoutYouAreFriends.visibility = View.INVISIBLE
                        binding.layoutMore.visibility = View.INVISIBLE
                    }
                }
            } else {
                binding.layoutAddToFriends.visibility = View.INVISIBLE
                binding.layoutYouAreFriends.visibility = View.INVISIBLE
                binding.layoutMore.visibility = View.INVISIBLE
            }

            viewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
                binding.tvCountOfFriends.text = it.size.toString()
            }
            val nextLevel = xpLevel?.plus(1)
            tvNextLevel.text = nextLevel.toString()

            tvFriendNick.text = nick
            tvRankLevelNumber.text = xpLevel.toString()
            tvXp.text = showXp(xpLevel!!, xp!!)
            tvCountOfAllMatches.text = games.toString()
            tvCountOfWinningMatches.text = wins.toString()

            val lvl = xpLevel
            when (lvl) {
                in 1..4 -> {
                    tvRankName.text = ROOKIE
                    setRankPhoto(0)
                    setPhoto(avatar)
                }

                in 5..9 -> {
                    tvRankName.text = RECRUIT
                    setRankPhoto(1)
                    setPhoto(avatar)
                }

                in 10..14 -> {
                    tvRankName.text = SOLDIER
                    setRankPhoto(2)
                    setPhoto(avatar)
                }

                in 15..19 -> {
                    tvRankName.text = LANCE_CORPORAL
                    setRankPhoto(3)
                    setPhoto(avatar)
                }

                in 20..24 -> {
                    tvRankName.text = CORPORAL
                    setRankPhoto(4)
                    setPhoto(avatar)
                }

                in 25..29 -> {
                    tvRankName.text = MASTER_CORPORAL
                    setRankPhoto(5)
                    setPhoto(avatar)
                }

                in 30..34 -> {
                    tvRankName.text = LANCE_SERGEANT
                    setRankPhoto(6)
                    setPhoto(avatar)
                }

                in 35..39 -> {
                    tvRankName.text = SERGEANT
                    setRankPhoto(7)
                    setPhoto(avatar)
                }

                in 40..44 -> {
                    tvRankName.text = MASTER_SERGEANT
                    setRankPhoto(8)
                    setPhoto(avatar)
                }

                in 45..49 -> {
                    tvRankName.text = SERGEANT_MAJOR
                    setRankPhoto(5)
                    setPhoto(avatar)
                }

                in 50..54 -> {
                    tvRankName.text = LIEUTENANT
                    setRankPhoto(6)
                    setPhoto(avatar)
                }

                in 55..59 -> {
                    tvRankName.text = LIEUTENANT_MAJOR
                    setRankPhoto(7)
                    setPhoto(avatar)
                }

                in 60..64 -> {
                    tvRankName.text = CAPTAIN
                    setRankPhoto(8)
                    setPhoto(avatar)
                }

                in 65..69 -> {
                    tvRankName.text = MAJOR
                    setRankPhoto(5)
                    setPhoto(avatar)
                }

                in 70..74 -> {
                    tvRankName.text = LIEUTENANT_COLONEL
                    setRankPhoto(6)
                    setPhoto(avatar)
                }

                in 75..79 -> {
                    tvRankName.text = COLONEL
                    setRankPhoto(7)
                    setPhoto(avatar)
                }

                in 80..84 -> {
                    tvRankName.text = BRIGADIER_GENERAL
                    setRankPhoto(8)
                    setPhoto(avatar)
                }

                in 85..89 -> {
                    tvRankName.text = GENERAL_MAJOR
                    setRankPhoto(5)
                    setPhoto(avatar)
                }

                in 90..94 -> {
                    tvRankName.text = GENERAL
                    setRankPhoto(6)
                    setPhoto(avatar)
                }

                in 95..99 -> {
                    tvRankName.text = LIEUTENANT_GENERAL
                    setRankPhoto(7)
                    setPhoto(avatar)
                }

                in 100..10000 -> {
                    tvRankName.text = MARSHAL
                    setRankPhoto(8)
                    setPhoto(avatar)
                }

            }
        }
    }

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

        private const val USER_ID_INT = "id_int"
        private const val USER_ID_STRING = "id_string"
        private const val USER_AVATAR = "avatar"
        private const val USER_NICK = "nick"
        private const val USER_XP_LEVEL = "xpLevel"
        private const val USER_XP = "xp"
        private const val USER_GAMES = "games"
        private const val USER_GAME_WINS = "wins"
        fun newInstance(
            id: Any,
            avatar: String,
            nick: String,
            xpLevel: Int,
            xp: Int,
            games: Int,
            wins: Int
        ): UserFragment {
            return UserFragment().apply {
                arguments = Bundle().apply {
                    when (id) {
                        is Int -> putInt(USER_ID_INT, id)
                        is String -> putString(USER_ID_STRING, id)
                        else -> throw IllegalArgumentException("Unsupported type for id")
                    }
                    putString(USER_AVATAR, avatar)
                    putString(USER_NICK, nick)
                    putInt(USER_XP_LEVEL, xpLevel)
                    putInt(USER_XP, xp)
                    putInt(USER_GAMES, games)
                    putInt(USER_GAME_WINS, wins)
                }
            }
        }
    }
}


