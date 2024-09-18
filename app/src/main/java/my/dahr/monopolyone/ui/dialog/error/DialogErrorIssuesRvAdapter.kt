package my.dahr.monopolyone.ui.dialog.error

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import my.dahr.monopolyone.R
import my.dahr.monopolyone.databinding.ItemIssueBinding
import my.dahr.monopolyone.domain.model.ParamInvalidError
import javax.inject.Inject

class DialogErrorIssuesRvAdapter @Inject constructor(
    private val context: Context
) : RecyclerView.Adapter<DialogErrorIssuesRvAdapter.ViewHolder>() {


    private val items = mutableListOf<ParamInvalidError.Issue>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateList(newItems: List<ParamInvalidError.Issue>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(
        private val binding: ItemIssueBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParamInvalidError.Issue) {

            binding.tvKey.text = context.resources.getString(
                R.string.dialog_error_2_tv_key_text,
                item.key
            )

            binding.tvMassage.text = context.resources.getString(
                R.string.dialog_error_2_tv_massage_text,
                item.message
            )

        }
    }


}


class MyDiffCallback(
    private val oldList: List<ParamInvalidError.Issue>,
    private val newList: List<ParamInvalidError.Issue>
) : DiffUtil.Callback() {


    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].key == newList[newItemPosition].key


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]


}