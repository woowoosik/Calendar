package com.woo.calendarapp.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.woo.calendarapp.schedule.ScheduleDao
import com.woo.calendarapp.schedule.ScheduleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ScheduleDatabase {
        return Room.databaseBuilder(
            context,
            ScheduleDatabase::class.java,
            "scheduleDB")
            .addMigrations(
                object:Migration(1,2){
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("ALTER TABLE SCHEDULE ADD COLUMN 'x' DOUBLE NOT NULL default 0.0")
                        database.execSQL("ALTER TABLE SCHEDULE ADD COLUMN 'y' DOUBLE NOT NULL default 0.0")
                    }
                }
            )
            .build()

    }

    @Provides
    fun provideScheduleDao(database: ScheduleDatabase): ScheduleDao {
        return database.ScheduleDao()
    }

}