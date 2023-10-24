package com.woo.calendarapp.module

import android.content.Context
import com.woo.calendarapp.LoadingDialog
import com.woo.calendarapp.PermissionCheck
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(FragmentComponent::class)
class ScheduleModule {

    @Provides
    fun loadingDialogProvides(@ActivityContext context: Context): LoadingDialog {
        return LoadingDialog(context)
    }

    @Provides
    fun permissionCheckProvides(@ActivityContext context: Context): PermissionCheck {
        return PermissionCheck(context)
    }

}
