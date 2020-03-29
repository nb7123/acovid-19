package com.covid19.runtime.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.covid19.runtime.database.InstalledModuleDao
import com.covid19.runtime.model.InstalledModule

@Database(entities = [InstalledModule::class], version = 3)
abstract class InstalledModuleDatabase: RoomDatabase() {
    abstract fun installedModuleDao(): InstalledModuleDao
}