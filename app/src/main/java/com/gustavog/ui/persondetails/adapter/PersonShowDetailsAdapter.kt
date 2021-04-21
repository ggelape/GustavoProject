package com.gustavog.ui.persondetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gustavog.R
import com.gustavog.model.domain.PersonShowDetails
import kotlinx.android.synthetic.main.person_show_details_item.view.*

class PersonShowDetailsAdapter(private var personShowDetails: ArrayList<PersonShowDetails.PersonShowDetailsItem>,
                               private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PersonShowDetailsAdapter.PersonShowDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonShowDetailsViewHolder {
        return PersonShowDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.person_show_details_item, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: PersonShowDetailsViewHolder, position: Int) {
        holder.bind(personShowDetails[position])
    }

    override fun getItemCount() = personShowDetails.size

    class PersonShowDetailsViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(show: PersonShowDetails.PersonShowDetailsItem) = with(itemView) {
            show.embedded.show.image?.let {
                Glide.with(itemView)
                    .load(it.medium)
                    .into(person_details_series_poster)
            }

            person_details_series_name.text = show.embedded.show.name

            this.setOnClickListener {
                onItemClick.invoke(show.embedded.show.id)
            }
        }
    }
}
