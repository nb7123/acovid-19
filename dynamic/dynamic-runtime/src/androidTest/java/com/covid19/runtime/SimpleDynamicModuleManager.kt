package com.covid19.runtime

import android.content.Context
import com.covid19.runtime.util.FileUtil
import com.covid19.runtime.Covid19ModuleManager
import java.io.File

internal class SimpleDynamicModuleManager(context: Context): Covid19ModuleManager(context) {
    private val testModuleFile: File by lazy {
        val dest = File(context.filesDir, "plugin.apk")
        if (!dest.exists()) {
            val stream = context.assets.open("plugin01-debug.apk")
            FileUtil.copy(stream, dest)
        }

        dest
    }

    override fun findClassModule(className: String): String {
        return "test"
    }

    override fun moduleNeedUpdate(moduleName: String): Boolean {
        return false
    }

    override fun onGetModuleApkFilePath(moduleName: String): String {
        return testModuleFile.absolutePath
    }
}