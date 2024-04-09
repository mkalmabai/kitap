package com.example.kitap.ui.fragments.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kitap.databinding.ItemBookBinding
import com.example.kitap.model.Item

class RecyclerViewHomeFragment: RecyclerView.Adapter<RecyclerViewHomeFragment.ViewHolder>() {
    private var onItemClickListener: ((Item) -> Unit)? = null
    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
//            return oldItem.items.map {
//                it.id } == newItem.items.map {
//                    it.id }
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    inner class ViewHolder(val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = differ.currentList[position]
        println(position)
        with(holder.binding) {
            title.text = book.volumeInfo.title
            Glide.with(bookImage).load(book.volumeInfo.imageLinks.thumbnail).into(bookImage)

            val listPrice = book.saleInfo?.listPrice
            if (listPrice != null) {
                val priceAndCurrency = listPrice.amount.toString() + " " + listPrice.currencyCode
                author.text = priceAndCurrency
            } else {
                author.text = "Price not available"
            }
            Link.setOnClickListener {
                val linkUrl = book.volumeInfo.infoLink
                if (!linkUrl.isNullOrEmpty()) {
                    // Open the link using Intent
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                    it.context.startActivity(intent)
                } else {
                    // Handle the case when link is not available
                    Toast.makeText(it.context, "Link not available", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
        fun submitList(list: List<Item>) {
            differ.submitList(list)
        }



}