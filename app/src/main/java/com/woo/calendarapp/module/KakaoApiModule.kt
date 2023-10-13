package com.woo.calendarapp.module

import com.woo.calendarapp.KakaoRetrofit
import com.woo.calendarapp.schedule.ScheduleDao
import com.woo.calendarapp.schedule.ScheduleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class KakaoApiModule {

    @Provides
    fun provideKakaoKeyword(): KakaoRetrofit {
        return KakaoRetrofit()
    }

}