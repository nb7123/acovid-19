package com.covid19.runtime

import android.content.Context
import android.os.AsyncTask
import com.covid19.runtime.database.DatabaseHelper
import com.covid19.runtime.model.InstalledModule
import com.covid19.runtime.util.FileUtil
import java.io.File
import java.io.IOException
import java.lang.StringBuilder
import java.security.MessageDigest
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

abstract class Covid19ModuleManager(private val context: Context) {
    private var installedModuleDir: File

    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper(context)
    }

    init {
        sExecutor.execute {
            val modules = dbHelper.installedModules()
            modules.forEach {module ->
                mLoadedModules[module.moduleName] = Covid19Module(context, module)
            }
        }

        val parent = context.filesDir
        val dir = File(parent, MODULE_INSTALL_DIR)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        installedModuleDir = dir
    }

    /**
     * loaded class and module map
     * mean: the class (key) witch module contains it(value)
     */
    private val mClassModuleMap = HashMap<String, String>()
    private val mLoadedModules = HashMap<String, Covid19Module>()

    /**
     * reload module info
     */
    @Throws(IOException::class)
    fun installDynamicModule(moduleName: String, apkPath: String): Future<Boolean> {
        return sExecutor.submit<Boolean> {
            val installed = performInstall(moduleName, File(apkPath))
            dbHelper.saveInstalledModule(installed)
            mLoadedModules[moduleName] = Covid19Module(context, installed)

            true
        }
    }

    @Throws(IOException::class)
    private fun performInstall(moduleName: String, apkFile: File): InstalledModule {
        val moduleDir = File(installedModuleDir, generateMd5(moduleName))

        val file = File(installedModuleDir, apkFile.name)
        val odexDir = File(moduleDir, "odex")
        if (!odexDir.exists()) odexDir.mkdirs()

        if (file.exists()) {
            file.delete()
        }

        FileUtil.copy(apkFile, file)
        val module = InstalledModule(moduleName, file.absolutePath,
            odexDir.absolutePath, onGetModuleTheme(moduleName))
        dbHelper.saveInstalledModule(module)

        return module
    }

    private fun generateMd5(content: String): String {
        return try {
            val bytes = MessageDigest.getInstance("MD5").digest(content.toByteArray())
            val hex = StringBuilder(bytes.size * 2)
            for (b in bytes) {
                val value = b.toInt() and 0xFF
                if (value < 0x10) {
                    hex.append("0")
                }
                hex.append(Integer.toHexString(value))
            }

            hex.toString()
        } catch (e: Exception) {
            content
        }
    }

    /**
     * delete installed module
     */
    fun deleteModule(moduleName: String): Boolean {
        mLoadedModules.remove(moduleName)
        dbHelper.deleteModule(moduleName)

        return true
    }

    /**
     * clear
     */
    fun cleanInstalledModules() {
        mLoadedModules.clear()
        dbHelper.deleteAll()
    }

    /**
     * find witch dynamic module contains the class
     * @param className
     * @return dynamic module contains the class implementation
     */
    fun dynamicModuleForClass(className: String): Covid19Module? {
        var moduleName = mClassModuleMap[className]
        if (null == moduleName) {
            moduleName = findClassModule(className)
        }

        if (null != moduleName) {
            mClassModuleMap[className] = moduleName

            if (null == mLoadedModules[moduleName] || moduleNeedUpdate(moduleName)) {
                installDynamicModule(moduleName, onGetModuleApkFilePath(moduleName)).get()
            }
        }

        return mLoadedModules[moduleName]
    }

    /**
     * managed dynamic module
     */
    fun getManagedCovid19Module(moduleName: String): Covid19Module? {
        return mLoadedModules[moduleName]
    }

    /**
     * find the class belongs to witch module
     * this method call only once when this class first load,
     * if class module invalid, should call #invalidateModule(moduleName)
     * @param className the class
     * @return the module name that contains the class implementation
     */
    abstract fun findClassModule(className: String): String?

    /**
     * weather update installed dynamic module
     * @param moduleName the installed module name, managed by this manager
     * @return true if module need update
     */
    abstract fun moduleNeedUpdate(moduleName: String): Boolean

    /**
     * request dynamic module file path
     * @param moduleName the dynamic module name
     * @return the dynamic module file path
     */
    abstract fun onGetModuleApkFilePath(moduleName: String): String

    /**
     * default app theme name
     */
    open fun onGetModuleTheme(moduleName: String): String = "Theme.AppCompat.Light"

    companion object{
        private val sExecutor = AsyncTask.THREAD_POOL_EXECUTOR as ExecutorService
        private const val MODULE_INSTALL_DIR = "modules"
    }
}