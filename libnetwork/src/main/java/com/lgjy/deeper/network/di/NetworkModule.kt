package com.lgjy.deeper.network.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lgjy.deeper.common.util.LogP
import com.lgjy.deeper.network.BuildConfig
import com.lgjy.deeper.network.wanandroid.WanCookie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by LGJY on 2021/11/18.
 * Email：yujye@sina.com
 */

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://www.wanandroid.com")  // todo
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .cookieJar(WanCookie())
            .apply {
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor { message -> LogP.d("===httplog===", message) }
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    this.addNetworkInterceptor(logging)
                }
            }.build()
}
