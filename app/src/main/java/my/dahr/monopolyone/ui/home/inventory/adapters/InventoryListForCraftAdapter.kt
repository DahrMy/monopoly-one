package my.dahr.monopolyone.ui.home.inventory.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.dahr.monopolyone.databinding.ItemInventoryBinding
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.listeners.MoveListener

class InventoryListForCraftAdapter(private val moveListener: MoveListener) :
    ListAdapter<Item, InventoryListForCraftAdapter.InventoryViewHolder>(DiffUtil()) {
    class InventoryViewHolder(
        private var rawItemInventoryBinding: ItemInventoryBinding
    ) :
        RecyclerView.ViewHolder(rawItemInventoryBinding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val binding = ItemInventoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InventoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val originalNumber = holder.adapterPosition+1
            moveListener.onItemMoved(1,2, item,originalNumber )
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
    fun removeItem(item: Item) {
        val newList = currentList.toMutableList()
        newList.remove(item)
        submitList(newList)
    }

    fun addItem(item: Item){
        val newList = currentList.toMutableList()
        newList.add(0, item)
        submitList(newList)
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, item: Item)
    }
}