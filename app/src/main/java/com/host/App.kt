package com.host

import android.app.Application
import com.covid19.runtime.util.FileUtil
import com.host.constants.Dynamic
import com.ncov2019.common.PluginInfo
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
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        copyPlugins()
    }

    private fun copyPlugins() {
        val pluginInfo = PluginInfo(this)

//        val apkName = Dynamic.getNavGraphApkName(BuildConfig.DEBUG)
//        val navHostApkFile = File(pluginInfo.pluginApkDir, apkName)
//        FileUtil.copy(assets.open(apkName), navHostApkFile)

        val plugin01ApkFile = pluginInfo.getPluginApkFile("01")
        plugin01ApkFile.deleteOnExit()
        FileUtil.copy(assets.open(plugin01ApkFile.name), plugin01ApkFile)
    }
}