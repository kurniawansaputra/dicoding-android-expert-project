package com.kurniawan.capstoneproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kurniawan.capstoneproject.core.databinding.ItemRowNewsBinding
import com.kurniawan.capstoneproject.core.domain.model.News

class NewsAdapter: ListAdapter<News, NewsAdapter.ViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder (
            ItemRowNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ViewHolder(private var binding: ItemRowNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .into(binding.ivNews)

            binding.textTitle.text = news.title
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<News> =
            object : DiffUtil.ItemCallback<News>() {
                override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
