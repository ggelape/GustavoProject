package com.gustavog.system.injection

import com.gustavog.model.repository.Repository
import com.gustavog.model.repository.RepositoryImpl
import org.koin.dsl.module

val repositories = module {
    single { RepositoryImpl(get()) as Repository }
}
