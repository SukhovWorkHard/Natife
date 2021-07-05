package test.sukhov.natife.ui.giflist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.sukhov.natife.R
import test.sukhov.natife.databinding.ItemGifBinding
import test.sukhov.natife.ui.models.GifItemResponse

class GifListAdapter(
    private val gifClickListener: (position: Int) -> Unit
) : PagingDataAdapter<GifItemResponse, GifListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemGifBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_gif,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                gifClickListener(absoluteAdapterPosition)
            }
        }

        fun bind(item: GifItemResponse) {
            with(binding) {
                gif = item
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GifItemResponse>() {
        override fun areItemsTheSame(oldItem: GifItemResponse, newItem: GifItemResponse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GifItemResponse, newItem: GifItemResponse): Boolean {
            return oldItem.id == newItem.id
        }
    }
}