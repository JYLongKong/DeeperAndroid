package com.lgjy.deeper.login.di

import com.lgjy.deeper.login.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Created by LGJY on 2021/11/18.
 * Email：yujye@sina.com
 */

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    fun provideApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)
}
