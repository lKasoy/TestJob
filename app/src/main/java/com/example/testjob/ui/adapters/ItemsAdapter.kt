package com.example.testjob.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testjob.data.entity.Item
import com.example.testjob.databinding.FragmentItemBinding

class ItemsAdapter(
    private val onClick: (Item) -> Unit
) : ListAdapter<Item, ItemsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Item = currentList[position]
        holder.bind(item, onClick)
    }

    class ViewHolder(private val binding: FragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, onItemClick: (Item) -> Unit) {
            binding.apply {
                name.text = item.name
                description.text = item.description
                Glide.with(binding.root)
                    .asBitmap()
                    .load(item.url)
                    .into(img)

                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.url == newItem.url
        }
    }
}