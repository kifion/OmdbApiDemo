package com.example.omdbapidemo.presentation.feature.home

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.omdbapidemo.R
import com.example.omdbapidemo.domain.model.Movie
import kotlinx.android.synthetic.main.recycler_item_search_list.view.*

class SearchListAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    var items: List<Movie> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_search_list, parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(items[position], clickListener)
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(searchItem: Movie, clickListener: ClickListener): Unit = with(itemView) {
            searchTitle.text = searchItem.name
            searchListYear.text = searchItem.year

            val poster = if (searchItem.poster != "N/A") {
                searchItem.poster
            } else {
                "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1665px-No-Image-Placeholder.svg.png"
            }

            Glide
                .with(context)
                .load(Uri.parse(poster))
                .centerCrop()
                .into(searchListPoster)

            setOnClickListener {
                clickListener.onItemClicked(searchItem)
            }
        }
    }

    interface ClickListener {
        fun onItemClicked(searchItem: Movie)
    }
}