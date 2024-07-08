package my.dahr.monopolyone.ui.home.inventory.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.ItemInventoryBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.domain.models.inventory.listitems.ListItem

class InventoryCraftAdapter(
    private val clickListener: (Item) -> Unit,
    private val itemCountChangedListener: (Int) -> Unit
) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffUtil()) {
    override fun onCurrentListChanged(
        previousList: MutableList<ListItem>,
        currentList: MutableList<ListItem>
    ) {
        itemCountChangedListener(currentList.count { it is ListItem.InventoryItem })
    }

    companion object {
        private const val TYPE_NUMBER = 0
        private const val TYPE_INVENTORY = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.NumberItem -> TYPE_NUMBER
            is ListItem.InventoryItem -> TYPE_INVENTORY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NUMBER -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_inventory_craft, parent, false)
                NumberViewHolder(view)
            }

            TYPE_INVENTORY -> {
                val binding =
                    ItemInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InventoryViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NumberViewHolder -> holder.bind((getItem(position) as ListItem.NumberItem).number)
            is InventoryViewHolder -> {
                val item = (getItem(position) as ListItem.InventoryItem).item
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    clickListener(item)
                    removeInventoryItem(item)
                }
            }
        }
    }

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNumber: TextView = itemView.findViewById(R.id.tvNumber)

        fun bind(number: Int) {
            textViewNumber.text = number.toString()
        }
    }

    class InventoryViewHolder(
        private var rawItemInventoryBinding: ItemInventoryBinding
    ) : RecyclerView.ViewHolder(rawItemInventoryBinding.root) {
        fun bind(item: Item) {
            setContent(item)
        }

        private fun setContent(item: Item) {
            rawItemInventoryBinding.apply {
                tvItemTitle.text = item.title
                Glide.with(root)
                    .load(item.image)
                    .into(ivItem)
            }
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return when {
                oldItem is ListItem.NumberItem && newItem is ListItem.NumberItem -> oldItem.number == newItem.number
                oldItem is ListItem.InventoryItem && newItem is ListItem.InventoryItem -> oldItem.item.itemId == newItem.item.itemId
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }

    fun addInventoryItemIfPossible(item: Item): Boolean {
        val canAdd = itemCount <= 10
        if (!canAdd) {
            return false
        }
        val emptySpotIndex = currentList.indexOfFirst { it is ListItem.NumberItem }

        if (emptySpotIndex != -1) {
            val newList = currentList.toMutableList()
            newList[emptySpotIndex] = ListItem.InventoryItem(item)
            submitList(newList)
        } else {
            Log.d("InventoryCraftAdapter", "No empty spots available.")
            return false
        }
        return true
    }

    private fun removeInventoryItem(item: Item) {
        val itemIndex = currentList.indexOfFirst { it is ListItem.InventoryItem && it.item == item }

        if (itemIndex != -1) {
            val newList = currentList.toMutableList()

            val numberItem = ListItem.NumberItem(itemIndex + 1)
            newList[itemIndex] = numberItem
            submitList(newList)
        } else {
            Log.d("InventoryCraftAdapter", "Item not found in the inventory list.")
        }
    }
}