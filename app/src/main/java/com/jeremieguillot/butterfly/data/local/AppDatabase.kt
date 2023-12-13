package com.jeremieguillot.butterfly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeremieguillot.butterfly.data.local.converters.DateConverter
import com.jeremieguillot.butterfly.data.local.converters.StringConverter
import com.jeremieguillot.butterfly.data.local.converters.VisibleMonthConverter
import javax.inject.Singleton

@Database(
    entities = [
        ButterflyEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    StringConverter::class,
    VisibleMonthConverter::class,
)
@Singleton
abstract class AppDatabase : RoomDatabase() {
    abstract fun butterflyDao(): ButterflyDao
}
