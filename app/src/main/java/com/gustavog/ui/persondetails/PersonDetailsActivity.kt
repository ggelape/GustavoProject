package com.gustavog.ui.persondetails

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gustavog.R
import com.gustavog.system.common.activity.BaseActivity
import com.gustavog.ui.persondetails.adapter.PersonShowDetailsAdapter
import kotlinx.android.synthetic.main.activity_person_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class PersonDetailsActivity : BaseActivity<PersonDetailsViewModel, PersonDetailsViewModel.State>() {
    override val model: PersonDetailsViewModel by viewModel()

    override fun layoutResource() = R.layout.activity_person_details

    override fun setupViews() {
        intent.getStringExtra("personId")?.let { model.fetchPersonDetails(it) }
    }

    override fun setupBindings() {
        model.getPersonDetails().observe(this, { personDetails ->
            personDetails?.let { details ->
                person_title.text = details.name
                person_details_title.text = details.name
                details.image?.let {
                    Glide.with(this)
                        .load(it.medium)
                        .into(person_details_poster)
                }
            }
        })

        model.getPersonShowDetails().observe(this, {
            person_details_series.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            person_details_series.adapter = PersonShowDetailsAdapter(it) { id ->
                navigation.goToSeriesDetails(this, false, "showId", id.toString())
            }
        })
    }

    override fun onStateChanged(state: PersonDetailsViewModel.State) = Unit

}
