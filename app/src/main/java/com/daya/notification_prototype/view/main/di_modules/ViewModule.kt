package com.daya.notification_prototype.view.main.di_modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import android.content.Context
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ViewModule {

    @Provides
    fun provideFlexboxLayoutManager(@ActivityContext context :Context): FlexboxLayoutManager {
        return FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.COLUMN
            justifyContent = JustifyContent.SPACE_EVENLY
        }
    }
}