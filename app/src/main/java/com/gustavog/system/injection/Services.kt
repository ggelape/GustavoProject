package com.gustavog.system.injection

import com.gustavog.model.api.ConsumerApiClient
import com.gustavog.model.api.ConsumerApiClientImpl
import com.gustavog.system.cryptography.CryptographyManager
import com.gustavog.system.cryptography.CryptographyManagerImpl
import com.gustavog.ui.navigation.Navigation
import com.gustavog.ui.navigation.NavigationImpl
import org.koin.dsl.module

val services = module {
    single { ConsumerApiClientImpl() as ConsumerApiClient }
    single { NavigationImpl() as Navigation }
    single { CryptographyManagerImpl(get()) as CryptographyManager }
}
