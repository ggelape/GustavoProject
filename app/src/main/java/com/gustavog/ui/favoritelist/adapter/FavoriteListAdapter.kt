package com.gustavog.ui.favoritelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gustavog.R
import com.gustavog.model.domain.ShowDetailsResponse
import kotlinx.android.synthetic.main.favorite_list_item.view.*

class FavoriteListAdapter(private var searchResult: ArrayList<ShowDetailsResponse>,
                          private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        return FavoriteListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bind(searchResult[position])
    }

    override fun getItemCount() = searchResult.size

    class FavoriteListViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(show: ShowDetailsResponse) = with(itemView) {
            show.image?.let {
                Glide.with(itemView)
                    .load(it.medium)
                    .circleCrop()
                    .into(favorite_image)
            }

            favorite_title.text = show.name

            this.setOnClickListener {
                onItemClick.invoke(show.id)
            }
        }
    }
}

