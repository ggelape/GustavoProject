package com.gustavog.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gustavog.R
import com.gustavog.system.common.activity.BaseActivity
import com.gustavog.ui.dashboard.adapter.SeriesGridAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.koin.android.viewmodel.ext.android.viewModel


class DashboardActivity : BaseActivity<DashboardViewModel, DashboardViewModel.State>() {

    companion object {
        const val THRESHOLD_TO_LOAD_PAGE = 5
        const val SEARCH_SHOW = "search_show"
    }

    override val model: DashboardViewModel by viewModel()
    private lateinit var adapter: SeriesGridAdapter
    private var isShowSearch = false

    override fun layoutResource(): Int = R.layout.activity_dashboard

    override fun setupViews() {
        recycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        adapter = SeriesGridAdapter(ArrayList()) { id ->
            navigation.goToSeriesDetails(this, false, "showId", id.toString())
        }
        recycler.adapter = adapter
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    override fun onSearchRequested(): Boolean {
        val appData = Bundle().apply {
            putBoolean(SEARCH_SHOW, isShowSearch)
        }
        startSearch(null, false, appData, false)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_person -> {
                isShowSearch = false
                onSearchRequested()
                true
            }
            R.id.search_series -> {
                isShowSearch = true
                onSearchRequested()
                true
            }
            R.id.favorites -> {
                navigation.goToFavorites(this, false)
                true
            }
            R.id.lock -> {
                model.lockOrUnlock()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setupBindings() {
        model.getShowsLiveData().observe(this, {
            adapter.addItems(it)
            recycler.addOnScrollListener(endlessScrollListener)
        })
    }

    override fun onStateChanged(state: DashboardViewModel.State) {
        when (state) {
            DashboardViewModel.State.REMOVE_LOCK -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.remove_password_dialog_title))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        model.removePassword()
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            DashboardViewModel.State.ADD_LOCK -> {
                navigation.goToPasswordProtect(this, false)
            }
        }
    }

    private val endlessScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if ((recycler.layoutManager as GridLayoutManager).findLastVisibleItemPosition() >=
                adapter.itemCount.minus(THRESHOLD_TO_LOAD_PAGE) && adapter.itemCount > 0) {
                    model.getShows()
                    recycler.removeOnScrollListener(this)
            }
        }
    }

}
