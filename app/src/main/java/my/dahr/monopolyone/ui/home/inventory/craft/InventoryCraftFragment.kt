package my.dahr.monopolyone.ui.home.inventory.craft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentInventoryCraftBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.domain.models.inventory.listitems.ListItem
import my.dahr.monopolyone.ui.home.inventory.InventoryViewModel
import my.dahr.monopolyone.ui.home.inventory.adapters.InventoryCraftAdapter
import my.dahr.monopolyone.ui.home.inventory.adapters.InventoryListForCraftAdapter
import my.dahr.monopolyone.ui.home.inventory.items.InventoryItemFragment

@AndroidEntryPoint
class InventoryCraftFragment : Fragment() {
    private val inventoryViewModel: InventoryViewModel by viewModels()

    private var _binding: FragmentInventoryCraftBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryCraftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFirstList()
        inventoryViewModel.getItemList()
        initObservers()
    }

    private fun initObservers() {
        inventoryViewModel.itemsResultLiveData.observe(viewLifecycleOwner) {
            showSecondList(it)
        }
    }

    private fun showSecondList(items: List<Item>) {
        val inventoryListForCraftAdapter =
            InventoryListForCraftAdapter(object : InventoryListForCraftAdapter.OnItemClickListener {
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
        inventoryListForCraftAdapter.submitList(items)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvItems.layoutManager = layoutManager
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.adapter = inventoryListForCraftAdapter
    }

    private fun showFirstList() {
        val inventoryCraftAdapter =
            InventoryCraftAdapter(object : InventoryCraftAdapter.OnItemClickListener {
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
        val initialItems = (1..10).map { ListItem.NumberItem(it) }
        inventoryCraftAdapter.submitList(initialItems)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvFreeSpots.layoutManager = layoutManager
        binding.rvFreeSpots.setHasFixedSize(true)
        binding.rvFreeSpots.adapter = inventoryCraftAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): InventoryCraftFragment = InventoryCraftFragment()
    }

}