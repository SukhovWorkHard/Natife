package test.sukhov.natife.ui.gifdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.sukhov.natife.R
import test.sukhov.natife.databinding.ItemDetailBinding
import test.sukhov.natife.ui.models.GifItemResponse

class GifDetailAdapter : PagingDataAdapter<GifItemResponse, GifDetailAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemDetailBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_detail,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
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