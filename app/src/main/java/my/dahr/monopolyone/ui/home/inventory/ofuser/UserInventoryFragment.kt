package my.dahr.monopolyone.ui.home.inventory.ofuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentUserInventoryBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.ui.home.inventory.InventoryViewModel
import my.dahr.monopolyone.ui.home.inventory.adapters.InventoryAdapter
import my.dahr.monopolyone.ui.home.inventory.items.InventoryItemFragment

@AndroidEntryPoint
class UserInventoryFragment : Fragment() {
    private val viewModel: InventoryViewModel by viewModels()

    private var _binding: FragmentUserInventoryBinding? = null
    private val binding get() = _binding!!

    private var userId: Any? = null
    private var avatar: String? = null
    private var nick: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveData()
        viewModel.getItemListForUser(userId!!)
        setContent()
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
        }
    }


    private fun setContent() {
        binding.apply {
            tvFriendNick.text = nick
            viewModel.itemsResultLiveData.observe(viewLifecycleOwner) {
                tvCountOfItems.text = it.size.toString()
                showList(it)
            }
            Glide.with(this@UserInventoryFragment)
                .load(avatar)
                .into(ivFriend)
        }
    }

    private fun showList(list: List<Item>){
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
        inventoryAdapter.submitList(list)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvItems.layoutManager = layoutManager
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.adapter = inventoryAdapter
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

        fun newInstance(userId: Any, userNick: String, userAvatar: String): UserInventoryFragment {
            return UserInventoryFragment().apply {
                arguments = Bundle().apply {
                    when (userId) {
                        is Int -> putInt(USER_ID_INT, userId)
                        is String -> putString(USER_ID_STRING, userId)
                        else -> throw IllegalArgumentException("Unsupported type for id")
                    }
                    putString(USER_AVATAR, userAvatar)
                    putString(USER_NICK, userNick)
                }
            }
        }
    }
}