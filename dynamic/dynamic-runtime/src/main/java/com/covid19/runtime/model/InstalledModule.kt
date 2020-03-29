package com.covid19.runtime.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * installed dynamic module info
 */
@Entity(tableName = "dynamic_module")
data class InstalledModule(
    @ColumnInfo(name = "module_name")
    @PrimaryKey val moduleName: String,
    @ColumnInfo(name = "apk_path") val apkPath: String,
    @ColumnInfo(name = "odex_path") val odexPath: String? = null,
    @ColumnInfo(name = "library_search_path") val librarySearchPath: String? = null,
    @ColumnInfo(name = "app_theme_name") val themeName: String = "Theme.AppCompat.Light"
)