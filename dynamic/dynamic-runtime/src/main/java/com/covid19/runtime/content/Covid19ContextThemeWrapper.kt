package com.covid19.runtime.content

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.view.LayoutInflater
import com.covid19.runtime.classloader.Covid19ApkClassLoader
import com.covid19.runtime.layoutinflater.Covid19LayoutInflater
import com.covid19.runtime.model.InstalledModule

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
 *       Author[michael]          |       Create[2020-01-30]
 * --------------------------------------------------------------
 * Description: plugin context，see{@link android.view.ContextThemeWrapper}
 * --------------------------------------------------------------
 */
class Covid19ContextThemeWrapper(
    base: Context,
    installedApk: InstalledModule
) : ContextWrapper(base) {
    private val mThemeResourceName: String = installedApk.themeName
    private var mThemeResource: Int = 0
    private val mInflater: LayoutInflater by lazy { Covid19LayoutInflater(this) }
    private val mResources: Resources
    private val mClassLoader: ClassLoader
    private val mPackageName: String

    init {
        val apkPath = installedApk.apkPath
        val packageManager = base.packageManager

        // TODO jni library not handled
        val packageArchiveInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA)!!
        packageArchiveInfo.applicationInfo.publicSourceDir = apkPath
        packageArchiveInfo.applicationInfo.sourceDir = apkPath

        mPackageName = packageArchiveInfo.packageName
        mResources = packageManager.getResourcesForApplication(packageArchiveInfo.applicationInfo)

        mClassLoader = Covid19ApkClassLoader(
            apkPath,
            installedApk.odexPath,
            null,
            base.classLoader
        )
    }

    override fun getPackageName(): String = mPackageName
    override fun getClassLoader(): ClassLoader = mClassLoader
    override fun getResources(): Resources = mResources

    override fun getSystemService(name: String): Any? {
        if (Context.LAYOUT_INFLATER_SERVICE == name) {
            return mInflater
        }
        return super.getSystemService(name)
    }

    override fun getAssets(): AssetManager { // Ensure we're returning assets with the correct configuration.
        return resources.assets
    }

    /**
     * created from host context, R class will use host class loader,
     * so we dynamic get theme resource id
     */
    fun getThemeResId(): Int {
        if (mThemeResource == 0) {
            mThemeResource = mResources.getIdentifier(mThemeResourceName, "style", mPackageName)
        }
        return mThemeResource
    }

    private var mTheme: Theme? = null

    override fun setTheme(resid: Int) {
        if (mThemeResource != resid) {
            mThemeResource = resid
            initializeTheme()
        }
    }

    override fun getTheme(): Theme? {
        if (mTheme != null) {
            return mTheme
        }
        if (mThemeResource == 0) {
            getThemeResId()
        }
        initializeTheme()
        return mTheme
    }

    /**
     * Called by [.setTheme] and [.getTheme] to apply a theme
     * resource to the current Theme object.  Can override to change the
     * default (simple) behavior.  This method will not be called in multiple
     * threads simultaneously.
     *
     * @param theme The Theme object being modified.
     * @param resid The theme style resource being applied to <var>theme</var>.
     * @param first Set to true if this is the first time a style is being
     * applied to <var>theme</var>.
     */
    protected fun onApplyThemeResource(
        theme: Theme?,
        resid: Int,
        first: Boolean
    ) {
        theme?.applyStyle(resid, true)
    }

    private fun initializeTheme() {
        val first = mTheme == null
        if (first) {
            mTheme = resources.newTheme()
            val theme = baseContext.theme
            if (theme != null) {
                mTheme?.setTo(theme)
            }
        }
        onApplyThemeResource(mTheme, mThemeResource, first)
    }
}