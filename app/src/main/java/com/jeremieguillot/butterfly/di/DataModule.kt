package com.jeremieguillot.butterfly.di

import android.content.Context
import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.jeremieguillot.butterfly.data.repository.ButterflyRepositoryImpl
import com.jeremieguillot.butterfly.domain.repository.ButterflyRepository
import com.jeremieguillot.olympicgame.data.network.util.NetworkHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAthleteRepository(
        networkHandler: NetworkHandlerImpl,
        apiClient: ApiClient,
    ): ButterflyRepository {
        return ButterflyRepositoryImpl(
            networkHandler,
            apiClient,
        )
    }


    @Singleton
    @Provides
    fun providesNetworkHandler(@ApplicationContext context: Context): NetworkHandlerImpl {
        return NetworkHandlerImpl(context)
    }
}