package my.dahr.monopolyone.ui.home.friends.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.dahr.monopolyone.databinding.ItemFriendBinding
import my.dahr.monopolyone.domain.models.friends.list.Friend

class FriendsAdapter(private val onItemClickListener: OnItemClickListener) : ListAdapter<Friend, FriendsAdapter.FriendsViewHolder>(DiffUtil()) {
    class FriendsViewHolder(private var rawItemFriendBinding: ItemFriendBinding,

        ) :
        RecyclerView.ViewHolder(rawItemFriendBinding.root) {
        fun bind(friend: Friend) {
            setContent(friend)
        }

        private fun setContent(friend: Friend){
            rawItemFriendBinding.apply {
                tvFriendNick.text = friend.nick
                Glide.with(root)
                    .load(friend.avatar)
                    .into(ivFriend)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val friend = getItem(position)
        holder.bind(friend)
        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClicked(position, friend)
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(position: Int, friend: Friend)
    }

}