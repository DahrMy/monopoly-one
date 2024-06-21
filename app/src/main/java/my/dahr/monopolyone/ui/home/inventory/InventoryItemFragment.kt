package my.dahr.monopolyone.ui.home.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.FragmentInventoryItemBinding
import my.dahr.monopolyone.utils.ItemTypeConverter

class InventoryItemFragment : Fragment() {
    private var _binding: FragmentInventoryItemBinding? = null
    private val binding get() = _binding!!

    private var image: String? = null
    private var title: String? = null
    private var type: Int? = null
    private var description: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveData()
        setListeners()
        setInfo()
    }

    private fun setInfo() {
        Glide.with(this)
            .load(image)
            .into(binding.ivItem)

        val type = ItemTypeConverter.fromNumber(type!!)

        binding.apply {
            tvItemTitle.text = title
            tvDescription.text = description
            tvItemType.text = type
        }
    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            val fragment = InventoryFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    private fun receiveData() {
        arguments?.let {
            image = it.getString(ITEM_IMAGE)
            title = it.getString(ITEM_TITLE)
            type = it.getInt(ITEM_TYPE)
            description = it.getString(ITEM_DESCRIPTION)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ITEM_IMAGE = "image"
        private const val ITEM_TITLE = "title"
        private const val ITEM_TYPE = "type"
        private const val ITEM_DESCRIPTION = "description"
        fun newInstance(
            image: String,
            title: String,
            type: Int,
            description: String,
        ): InventoryItemFragment {
            return InventoryItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ITEM_IMAGE, image)
                    putString(ITEM_TITLE, title)
                    putInt(ITEM_TYPE, type)
                    putString(ITEM_DESCRIPTION, description)
                }
            }
        }
    }
}