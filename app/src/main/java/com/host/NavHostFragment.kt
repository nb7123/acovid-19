package com.host

import android.content.Context
import com.covid19.fragment.Covid19NavHostFragment
import com.covid19.runtime.Covid19ModuleManager
import com.covid19.runtime.util.FileUtil
import java.io.File

class NavHostFragment: Covid19NavHostFragment() {
    override fun onCreateDynamicModuleManager(context: Context): Covid19ModuleManager {
        return ModuleManager(context)
    }

    private class ModuleManager(context: Context): Covid19ModuleManager(context) {
        private val testModuleFile: File by lazy {
            val dest = File(context.filesDir, "plugin.apk")
            if (!dest.exists()) {
                val stream = context.assets.open("plugin01-debug.apk")
                FileUtil.copy(stream, dest)
            }

            dest
        }

        override fun findClassModule(className: String): String? {
            return if (className.contains("BlankFragment")) "test" else null
        }

        override fun moduleNeedUpdate(moduleName: String): Boolean {
            return false
        }

        override fun onGetModuleApkFilePath(moduleName: String): String {
            return testModuleFile.absolutePath
        }
    }
}