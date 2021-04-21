package com.gustavog

import android.app.Application
import com.gustavog.system.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TemplateApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TemplateApplication)

            modules(
                listOf(
                    viewModules,
                    repositories,
                    services,
                    storage
                )
            )

        }
    }

}
