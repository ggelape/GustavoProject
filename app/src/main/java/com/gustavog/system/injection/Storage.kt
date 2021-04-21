package com.gustavog.system.injection

import com.gustavog.model.storage.DataStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storage = module {
    single { DataStorage(androidContext()) }
}
