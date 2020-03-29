package com.ncov2019.common

import android.content.Context
import com.host.BuildConfig
import com.host.constants.Dynamic
import java.io.File

/**
 * --------------------------------------------------------------|
 *                                                               |
 *                          _________-----_____                  |
 *            _____------           __      ----_                |
 *   _____----             ___------              \              |
 *        ----________        ----                 \             |
 *                    -----__    |             _____)            |
 *                         __-                /     \            |
 *             _______-----    ___--          \    /)\           |
 *     --------_______      ---____            \__/  /           |
 *                    -----__    \ --    _          /\           |
 *                           --__--__     \_____/   \_/\         |
 *                                   ----|   /          |        |
 *                                       |  |___________|        |
 *                                       |  | ((_(_)| )_)        |
 *                                       |  \_((_(_)|/(_)        |
 *                                       \             (         |
 *                                        \_____________)        |
 *                              @@@@@                            |
 * --------------------------------------------------------------|
 *       Author[michael]          |       Create[2020-02-21]
 * --------------------------------------------------------------
 * Description:
 * --------------------------------------------------------------
 */
class PluginInfo(context: Context) {
    private val filesDir = context.filesDir
    val pluginDir = File(filesDir, "plugin")
    val pluginApkDir = File(pluginDir, "apk")
    val optimizedDir = File(pluginDir, "odex")

    init {
        if (!pluginApkDir.exists()) {
            pluginApkDir.mkdirs()
        }
        if (!optimizedDir.exists()) {
            optimizedDir.mkdirs()
        }
    }

    fun dynamicApkFile(): File {
        return File(pluginApkDir, Dynamic.getNavGraphApkName(BuildConfig.DEBUG))
    }

    fun getPluginApkFile(index: String): File {
        return File(pluginApkDir, Dynamic.getDynamicFeatureApk(index, BuildConfig.DEBUG))
    }
}