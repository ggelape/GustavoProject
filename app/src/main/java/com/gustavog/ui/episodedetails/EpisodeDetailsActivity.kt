package com.gustavog.ui.episodedetails

import android.text.Html
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gustavog.R
import com.gustavog.system.common.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_episode_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodeDetailsActivity : BaseActivity<EpisodeDetailsViewModel, EpisodeDetailsViewModel.State>() {
    override val model: EpisodeDetailsViewModel by viewModel()

    override fun layoutResource() = R.layout.activity_episode_details

    override fun setupViews() {
        back_button.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun setupBindings() {
        model.getEpisode().observe(this, Observer {
            it?.let { episode ->
                Glide.with(this)
                    .load(episode.image.original)
                    .into(episode_details_poster)

                episode_title.text = episode.name
                episode_details_title.text = episode.name
                episode_details_number.text = "Season ${episode.season} Episode ${episode.number}"
                episode_details_summary.text = Html.fromHtml(episode.summary)
            }
        })
    }

    override fun onStateChanged(state: EpisodeDetailsViewModel.State) = Unit
}
