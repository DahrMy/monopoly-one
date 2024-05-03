package my.dahr.monopolyone.ui.home.friends.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.dahr.monopolyone.databinding.ItemFriendRequestsBinding
import my.dahr.monopolyone.domain.models.friends.requests.Request
import okhttp3.internal.notify

class FriendRequestsAdapter(private val onAcceptFriendRequestClickListener: OnAcceptFriendRequestClickListener,
    private val onRejectFriendRequestClickListener: OnRejectFriendRequestClickListener) :
    ListAdapter<Request, FriendRequestsAdapter.FriendRequestsViewHolder>(
        DiffUtil()
    ) {

    class FriendRequestsViewHolder(
        private var rawItemFriendRequestsBinding: ItemFriendRequestsBinding,
        private val onAcceptFriendRequestClickListener: OnAcceptFriendRequestClickListener,
        private val onRejectFriendRequestClickListener: OnRejectFriendRequestClickListener
    ) :
        RecyclerView.ViewHolder(rawItemFriendRequestsBinding.root) {
        fun bind(request: Request) {
            setContent(request)
            rawItemFriendRequestsBinding.ivAccept.setOnClickListener {
                onAcceptFriendRequestClickListener.onAcceptFriendRequestClicked(request.userId)
            }
            rawItemFriendRequestsBinding.ivReject.setOnClickListener {
                onRejectFriendRequestClickListener.onRejectFriendRequestClicked(request.userId)
            }
        }

        private fun setContent(request: Request) {
            rawItemFriendRequestsBinding.apply {
                tvFriendNick.text = request.nick
                Glide.with(root)
                    .load(request.avatar)
                    .into(ivFriend)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestsViewHolder {
        val binding =
            ItemFriendRequestsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendRequestsViewHolder(binding,onAcceptFriendRequestClickListener, onRejectFriendRequestClickListener)
    }

    override fun onBindViewHolder(holder: FriendRequestsViewHolder, position: Int) {
        val friendRequest = getItem(position)
        holder.bind(friendRequest)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Request>() {
        override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem == newItem
        }
    }

    interface OnAcceptFriendRequestClickListener {
        fun onAcceptFriendRequestClicked(userId: Any)
    }
    interface OnRejectFriendRequestClickListener {
        fun onRejectFriendRequestClicked(userId: Any)
    }
}
