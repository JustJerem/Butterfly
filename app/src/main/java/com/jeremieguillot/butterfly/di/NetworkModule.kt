package com.jeremieguillot.butterfly.di

import com.jeremieguillot.butterfly.BuildConfig
import com.jeremieguillot.butterfly.data.network.client.ApiClient
import com.moczul.ok2curl.CurlInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(getOkHttpService())
            .build()
    }

    @Singleton
    @Provides
    fun provideOlympicApi(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    private fun getOkHttpService(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.connectTimeout(1, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(interceptor)
            httpClient.addInterceptor(
                CurlInterceptor { message ->
                    Timber.tag("OkHttp").v(message)
                }
            )
        }

        return httpClient.build()
    }
}