package com.gustavog.ui.searchable

import android.app.SearchManager
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.gustavog.R
import com.gustavog.system.common.activity.BaseActivity
import com.gustavog.ui.dashboard.DashboardActivity.Companion.SEARCH_SHOW
import com.gustavog.ui.searchable.adapter.PersonSearchResultsAdapter
import com.gustavog.ui.searchable.adapter.SearchResultsAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchableActivity : BaseActivity<SearchableViewModel, SearchableViewModel.State>() {

    override val model: SearchableViewModel by viewModel()

    override fun layoutResource(): Int = R.layout.activity_search

    override fun setupViews() {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                search_results.text = getString(R.string.search_results, query)
                val isSearchShow = intent.getBundleExtra(SearchManager.APP_DATA)?.getBoolean(SEARCH_SHOW) ?: false
                if (isSearchShow) {
                    model.searchShow(query)
                    return
                }
                model.searchPerson(query)
            }
        }

        back_button.setOnClickListener {
            super.onBackPressed()
        }

    }

    override fun setupBindings() {
        model.getSearchResults().observe(this, {
            search_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            search_recycler.adapter = SearchResultsAdapter(it) { id ->
                navigation.goToSeriesDetails(this, false, "showId", id.toString())
            }
        })

        model.getPersonSearchResults().observe(this, {
            search_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            search_recycler.adapter = PersonSearchResultsAdapter(it) { id ->
                navigation.goToPersonDetails(this, false, "personId", id.toString())
            }
        })
    }

    override fun onStateChanged(state: SearchableViewModel.State) = Unit
}
