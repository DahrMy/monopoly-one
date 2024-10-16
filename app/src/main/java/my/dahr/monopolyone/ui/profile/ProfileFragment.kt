package my.dahr.monopolyone.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentProfileBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.ui.friends.FriendsViewModel
import my.dahr.monopolyone.ui.friends.user.friends.UserFriendsFragment
import my.dahr.monopolyone.ui.inventory.InventoryFragment
import my.dahr.monopolyone.ui.inventory.InventoryItemFragment
import my.dahr.monopolyone.ui.inventory.InventoryViewModel
import my.dahr.monopolyone.ui.inventory.adapters.InventoryAdapter
import my.dahr.monopolyone.data.utils.RankConverter

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private val friendsViewModel: FriendsViewModel by viewModels()
    private val inventoryViewModel: InventoryViewModel by viewModels()


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
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
        inventoryViewModel.getItemList()
    }

    private fun setListeners() {
        val user = viewModel.getUser()
        binding.LayoutCountOfFriends.setOnClickListener {
            val fragment = UserFriendsFragment.newInstance(user!!.userId, user.avatar, user.nick)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
        binding.clShowAllItems.setOnClickListener {
            val fragment = InventoryFragment.newInstance()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    private fun initObservers() {
        viewModel.usersResultLiveData.observe(viewLifecycleOwner) {
            val user = it[0]
            viewModel.setUser(user)
        }

        inventoryViewModel.itemsResultLiveData.observe(viewLifecycleOwner) {
            val firstThreeElements = it.take(3)
            showInventory(firstThreeElements)
            setInfo(it)
            setListeners()
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
        val user = viewModel.getUser()

        friendsViewModel.getFriendListForUser(user!!.userId)

        binding.apply {
            friendsViewModel.friendForUserResultLiveData.observe(viewLifecycleOwner) {
                binding.tvCountOfFriends.text = it.size.toString()
            }
            val nextLevel = user.xpLevel.plus(1)
            tvNextLevel.text = nextLevel.toString()

            tvCountOfItems.text = items.size.toString()

            tvFriendNick.text = user.nick
            tvRankLevelNumber.text = user.xpLevel.toString()
            tvXp.text = showXp(user.xpLevel, user.xp)
            tvCountOfAllMatches.text = user.games.toString()
            tvCountOfWinningMatches.text = user.gamesWins.toString()

            val rankName = RankConverter.fromNumberToRank(user.xpLevel)
            tvRankName.text = rankName
            setPhoto(user.avatar)

            val rankImage = RankConverter.fromNumberToRankImageId(user.xpLevel)

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
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}