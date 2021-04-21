package com.gustavog.ui.searchable.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gustavog.R
import com.gustavog.model.domain.PersonSearchResponse
import kotlinx.android.synthetic.main.search_result_item.view.*

class PersonSearchResultsAdapter(private var searchResult: ArrayList<PersonSearchResponse.PersonSearchResponseItem>,
                                 private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PersonSearchResultsAdapter.PersonSearchResultsViewHolder>() {

    companion object {
        private const val radius = 8
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonSearchResultsViewHolder {
        return PersonSearchResultsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: PersonSearchResultsViewHolder, position: Int) {
        holder.bind(searchResult[position])
    }

    override fun getItemCount() = searchResult.size

    class PersonSearchResultsViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(person: PersonSearchResponse.PersonSearchResponseItem) = with(itemView) {
            person.person.image?.let {
                Glide.with(itemView)
                    .load(it.medium)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
                    .into(search_result_image)
            }

            search_result_title.text = person.person.name

            this.setOnClickListener {
                onItemClick.invoke(person.person.id)
            }
        }
    }
}
