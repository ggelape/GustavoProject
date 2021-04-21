package com.gustavog.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gustavog.R
import com.gustavog.model.domain.SeriesResponse
import kotlinx.android.synthetic.main.series_list_item.view.*

class SeriesGridAdapter(
    private var series: ArrayList<SeriesResponse.SeriesResponseItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SeriesGridAdapter.SeriesGridViewHolder>() {

    companion object {
        private const val radius = 16
    }

    fun addItems(items: ArrayList<SeriesResponse.SeriesResponseItem>) {
        series.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesGridViewHolder {
        return SeriesGridViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.series_list_item, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: SeriesGridViewHolder, position: Int) {
        holder.bind(series[position])
    }

    override fun getItemCount() = series.size

    class SeriesGridViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(show: SeriesResponse.SeriesResponseItem) = with(itemView) {
            show.image?.let {
                Glide.with(itemView)
                    .load(it.medium)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
                    .into(series_item_image)
            }

            series_item_title.text = show.name

            this.setOnClickListener {
                onItemClick.invoke(show.id)
            }
        }
    }
}
