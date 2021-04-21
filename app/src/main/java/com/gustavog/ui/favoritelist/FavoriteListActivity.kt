package com.gustavog.ui.favoritelist

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gustavog.R
import com.gustavog.system.common.activity.BaseActivity
import com.gustavog.ui.favoritelist.adapter.FavoriteListAdapter
import kotlinx.android.synthetic.main.activity_favorite_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteListActivity : BaseActivity<FavoriteListViewModel, FavoriteListViewModel.State>() {
    override val model: FavoriteListViewModel by viewModel()

    override fun layoutResource() = R.layout.activity_favorite_list

    override fun setupViews() {
        back_button.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun setupBindings() {
        model.getFavorites().observe(this, Observer {
            favorite_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            favorite_recycler.adapter = FavoriteListAdapter(it) { id ->
                navigation.goToSeriesDetails(this, false, "showId", id.toString())
            }
        })
    }

    override fun onStart() {
        model.retrieveFavorites()
        super.onStart()
    }

    override fun onStateChanged(state: FavoriteListViewModel.State) = Unit

}
