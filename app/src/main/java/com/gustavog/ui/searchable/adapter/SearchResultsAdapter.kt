package com.gustavog.ui.searchable.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gustavog.R
import com.gustavog.model.domain.SearchResponse
import kotlinx.android.synthetic.main.search_result_item.view.*

class SearchResultsAdapter(private var searchResult: ArrayList<SearchResponse.SearchResponseItem>,
                           private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {

    companion object {
        private const val radius = 8
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        return SearchResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        holder.bind(searchResult[position])
    }

    override fun getItemCount() = searchResult.size

    class SearchResultsViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(show: SearchResponse.SearchResponseItem) = with(itemView) {
            show.show.image?.let {
                Glide.with(itemView)
                    .load(it.medium)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
                    .into(search_result_image)
            }

            search_result_title.text = show.show.name

            this.setOnClickListener {
                onItemClick.invoke(show.show.id)
            }
        }
    }
}
