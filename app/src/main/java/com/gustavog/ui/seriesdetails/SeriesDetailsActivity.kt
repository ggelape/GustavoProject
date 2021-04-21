package com.gustavog.ui.seriesdetails

import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gustavog.R
import com.gustavog.model.domain.ShowDetailsResponse
import com.gustavog.system.common.activity.BaseActivity
import com.gustavog.ui.seriesdetails.adapter.EpisodesAdapter
import kotlinx.android.synthetic.main.activity_series_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class SeriesDetailsActivity : BaseActivity<SeriesDetailsViewModel, SeriesDetailsViewModel.State>() {
    override val model: SeriesDetailsViewModel by viewModel()

    override fun layoutResource() = R.layout.activity_series_details

    override fun setupViews() {
        intent.getStringExtra("showId")?.let { model.getDetails(it) }
        series_favorite_button.setOnClickListener {
            model.saveFavorite()
        }

        back_button.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun setupBindings() {
        model.getShowDetails().observe(this, {
            fillDetails(it)
        })
    }

    override fun onStateChanged(state: SeriesDetailsViewModel.State) {
        when (state) {
            SeriesDetailsViewModel.State.FAVORITE -> {
                series_favorite_button.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
            }
            SeriesDetailsViewModel.State.NO_FAVORITE -> {
                series_favorite_button.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
            }
        }
    }

    private fun fillDetails(details: ShowDetailsResponse) {
        Glide.with(this)
            .load(details.image.original)
            .into(series_details_poster)

        series_details_title.text = details.name
        series_title.text = details.name

        var seriesAirtime = "At " + details.schedule.time + " every "
        for ((i, day) in details.schedule.days.withIndex()) {
            if (i > 0) seriesAirtime+="/"
            seriesAirtime += day
        }
        series_details_airtime.text = seriesAirtime
        series_details_summary.text = Html.fromHtml(details.summary)
        series_details_genres.text = details.genres.joinToString()

        val seasons = details.embedded.episodes.groupBy { it.season }
        val seasonsName = ArrayList<String>()
        for (seasonNumber in seasons.keys) {
            seasonsName.add("Season $seasonNumber")
        }

        ArrayAdapter(this, R.layout.spinner_item, seasonsName).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            seasons_spinner.adapter = adapter
        }
        seasons_spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                series_details_episodes.layoutManager =
                    LinearLayoutManager(this@SeriesDetailsActivity, LinearLayoutManager.VERTICAL, false)

                seasons[pos+1]?.let { episodes ->
                    series_details_episodes.adapter = EpisodesAdapter(episodes) { position ->
                        model.saveSelectedEpisode(episodes[position])
                        navigation.goToEpisodeDetails(this@SeriesDetailsActivity, false)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) = Unit
        }

    }

}
