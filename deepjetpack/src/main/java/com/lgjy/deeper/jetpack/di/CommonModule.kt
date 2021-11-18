package com.lgjy.deeper.jetpack.di

import android.content.Context
import com.lgjy.deeper.common.storage.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by LGJY on 2021/10/25.
 * Email：yujye@sina.com
 *
 * Tip13: Hilt module
 */

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore = DataStore(context)

}
