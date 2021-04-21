package com.gustavog.ui.seriesdetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gustavog.R
import com.gustavog.model.domain.ShowDetailsResponse
import kotlinx.android.synthetic.main.episodes_item.view.*

class EpisodesAdapter(private var episodes: List<ShowDetailsResponse.Embedded.Episode>,
                      private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        return EpisodesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.episodes_item, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size

    class EpisodesViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(currentEpisode: ShowDetailsResponse.Embedded.Episode) = with(itemView) {
            episode.text = "E${currentEpisode.number} -"
            episode_title.text = currentEpisode.name

            this.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }
    }
}
