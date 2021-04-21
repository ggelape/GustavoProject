package com.gustavog.system.common.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gustavog.R
import com.gustavog.system.common.viewmodel.BaseViewModel
import com.gustavog.ui.navigation.Navigation
import kotlinx.android.synthetic.main.activity_base.*
import org.koin.android.ext.android.inject

abstract class BaseActivity<M : BaseViewModel<S>, S : BaseViewModel.BaseState> : AppCompatActivity() {

    protected abstract val model: M

    val navigation: Navigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)

        content.addView(LayoutInflater.from(this).inflate(layoutResource(), null))

        setupViews()
        setupCommonBindings()
        setupBindings()
    }

    private fun setupCommonBindings() {
        model.getState().observe(this, { state ->
            onStateChanged(state)
        })

        model.getError().observe(this, { error ->
            handleErrorState(error)
        })
    }

    private fun handleErrorState(error: BaseViewModel.Error) {

        when (error) {
            BaseViewModel.Error.TIMEOUT -> onTimeout()
            else -> onUnknownError()
        }
    }

    open fun onTimeout() {
        Toast.makeText(this, "Timeout Error", Toast.LENGTH_LONG).show()
    }

    open fun onUnknownError() {
        Toast.makeText(this, "Unknown Error", Toast.LENGTH_LONG).show()
    }

    abstract fun layoutResource(): Int
    abstract fun setupViews()
    abstract fun setupBindings()
    abstract fun onStateChanged(state: S)
}
