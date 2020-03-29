package com.covid19.runtime.database

import androidx.room.*
import com.covid19.runtime.model.InstalledModule

@Dao
interface InstalledModuleDao {
    @Query("select * from dynamic_module")
    fun getInstalledModules(): List<InstalledModule>

    @Query("select * from dynamic_module where module_name = :moduleName")
    fun findModuleByName(moduleName: String): InstalledModule?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInstalledModules(vararg module: InstalledModule)

    @Query("delete from dynamic_module where module_name = :moduleName")
    fun deleteModule(moduleName: String)

    @Query("delete from dynamic_module")
    fun deleteAll()
}