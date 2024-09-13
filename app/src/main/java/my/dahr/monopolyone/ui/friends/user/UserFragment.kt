package my.dahr.monopolyone.ui.friends.user

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentUserBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.ui.friends.FriendsFragment
import my.dahr.monopolyone.ui.friends.FriendsViewModel
import my.dahr.monopolyone.ui.friends.user.friends.UserFriendsFragment
import my.dahr.monopolyone.ui.inventory.InventoryFragment
import my.dahr.monopolyone.ui.inventory.InventoryItemFragment
import my.dahr.monopolyone.ui.inventory.InventoryViewModel
import my.dahr.monopolyone.ui.inventory.adapters.InventoryAdapter
import my.dahr.monopolyone.utils.LoadingDialog
import my.dahr.monopolyone.utils.RankConverter

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val viewModel: FriendsViewModel by viewModels()
    private val inventoryViewModel: InventoryViewModel by viewModels()

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingDialog: LoadingDialog

    private var userId: Int? = null
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
        loadingDialog = LoadingDialog(requireActivity())
        receiveData()
        viewModel.checkIfFriend(userId!!)
        viewModel.getFriendListForUser(userId!!)
        inventoryViewModel.getItemList()
        initObservers()
        setListeners()
    }

    private fun receiveData() {
        arguments?.let {
            userId = when {
                it.containsKey(USER_ID_INT) -> it.getInt(USER_ID_INT)
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
        binding.apply {
            layoutAddToFriends.setOnClickListener {
                viewModel.addFriend(userId!!)
            }
            layoutMore.setOnClickListener {
                showPopupMenu(it)
            }

            ivBack.setOnClickListener {
                val fragment = FriendsFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            }
            layoutCountOfFriends.setOnClickListener {
                val fragment = UserFriendsFragment.newInstance(userId!!, avatar!!, nick!!)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            }
            clShowAllItems.setOnClickListener {
                val fragment = InventoryFragment.newInstance()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            }
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

    private fun initObservers() {
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

            inventoryViewModel.itemsResultLiveData.observe(viewLifecycleOwner) {
                val firstThreeElements = it.take(3)
                showInventory(firstThreeElements)
                setInfo(it)
            }
        }
    }

    private fun showInventory(items: List<Item>) {
        val inventoryAdapter = InventoryAdapter(object : InventoryAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int, item: Item) {
                val fragment = InventoryItemFragment.newInstance(
                    item.image,
                    item.title,
                    item.type,
                    item.description,
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            }
        })
        inventoryAdapter.submitList(items)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvInventory.layoutManager = layoutManager
        binding.rvInventory.setHasFixedSize(true)
        binding.rvInventory.adapter = inventoryAdapter
    }


    private fun setInfo(items: List<Item>) {
        binding.apply {
            val nextLevel = xpLevel?.plus(1)
            tvNextLevel.text = nextLevel.toString()

            tvFriendNick.text = nick
            tvRankLevelNumber.text = xpLevel.toString()
            tvXp.text = showXp(xpLevel!!, xp!!)
            tvCountOfAllMatches.text = games.toString()
            tvCountOfWinningMatches.text = wins.toString()

            tvCountOfItems.text = items.size.toString()

            val rankName = RankConverter.fromNumberToRank(xpLevel!!)
            tvRankName.text = rankName
            setPhoto(avatar)

            val rankImage = RankConverter.fromNumberToRankImageId(xpLevel!!)

            if (rankImage != null) {
                setRankImage(rankImage)
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

    private fun setRankImage(number: Int) {
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


