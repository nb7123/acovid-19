package com.covid19.runtime.database

import android.content.Context
import androidx.room.Room
import com.covid19.runtime.model.InstalledModule

class DatabaseHelper(context: Context) {
    private val db = Room.databaseBuilder(context, InstalledModuleDatabase::class.java, MODULE_DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

    fun findInstalledModule(moduleName: String): InstalledModule? {
        return db.installedModuleDao().findModuleByName(moduleName)
    }

    fun saveInstalledModule(module: InstalledModule): Boolean {
        db.installedModuleDao().insertInstalledModules(module)

        return true
    }

    fun installedModules(): List<InstalledModule> {
        return db.installedModuleDao().getInstalledModules()
    }

    companion object{
        private const val MODULE_DB_NAME = "dynamic-module.db"
    }

    fun deleteModule(moduleName: String) {
        db.installedModuleDao().deleteModule(moduleName)
    }

    fun deleteAll() {
        db.installedModuleDao().deleteAll()
    }
}