package my.dahr.monopolyone.ui.home.inventory.craft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.databinding.FragmentInventoryCraftBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.domain.models.inventory.listitems.ListItem
import my.dahr.monopolyone.ui.home.inventory.InventoryViewModel
import my.dahr.monopolyone.ui.home.inventory.adapters.InventoryCraftAdapter
import my.dahr.monopolyone.ui.home.inventory.adapters.InventoryListForCraftAdapter

@AndroidEntryPoint
class InventoryCraftFragment : Fragment() {
    private val inventoryViewModel: InventoryViewModel by viewModels()

    private var _binding: FragmentInventoryCraftBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterForCraft: InventoryCraftAdapter
    private lateinit var adapterOfItemsForCraft: InventoryListForCraftAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryCraftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showListForCraft()
        inventoryViewModel.getItemList()
        initObservers()
    }

    private fun initObservers() {
        inventoryViewModel.itemsResultLiveData.observe(viewLifecycleOwner) {
            showItemsList(it)
        }
    }

    private fun showItemsList(items: List<Item>) {
        adapterOfItemsForCraft = InventoryListForCraftAdapter {
            adapterForCraft.addInventoryItemIfPossible(it)
        }
        adapterOfItemsForCraft.submitList(items)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvItems.layoutManager = layoutManager
        binding.rvItems.setHasFixedSize(true)
        binding.rvItems.adapter = adapterOfItemsForCraft
    }

    private fun showListForCraft() {
        adapterForCraft = InventoryCraftAdapter(
            clickListener = { adapterOfItemsForCraft.addItem(it) },
            itemCountChangedListener = {
                    binding.btnCraft.isVisible = it == 10
            }
        )
        val initialItems = (1..10).map { ListItem.NumberItem(it) }
        adapterForCraft.submitList(initialItems)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvFreeSpots.layoutManager = layoutManager
        binding.rvFreeSpots.setHasFixedSize(true)
        binding.rvFreeSpots.adapter = adapterForCraft
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): InventoryCraftFragment = InventoryCraftFragment()
    }
}