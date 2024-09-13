package my.dahr.monopolyone.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentInventoryBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.ui.inventory.adapters.InventoryAdapter
import my.dahr.monopolyone.utils.LoadingDialog

@AndroidEntryPoint
class InventoryFragment : Fragment() {
    private val inventoryViewModel: InventoryViewModel by viewModels()

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
        initObservers()
        inventoryViewModel.getItemList()
    }

    private fun initObservers() {
        inventoryViewModel.itemsResultLiveData.observe(viewLifecycleOwner) {
            showRecycler(it)
        }
    }

    private fun showRecycler(items: List<Item>) {
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
        fun newInstance(): InventoryFragment = InventoryFragment()
    }
}