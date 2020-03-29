package com.covid19.runtime.classloader

import android.os.Build
import dalvik.system.DexClassLoader

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
 *       Author[michael]          |       Create[2020-02-23]
 * --------------------------------------------------------------
 * Description: dynamic apk class loader
 * --------------------------------------------------------------
 */
internal class Covid19ApkClassLoader
/**
 * @param dexPath apk file path
 * @param optimizedDirectory apk odex dir path
 * @param librarySearchPath shared library search path
 * @param parent parent class loader, maybe host class loader
 * @param sharedClasses shared classes with host
 * @param grandTimes when load shared classes, from which parent load
 */
    (
    dexPath: String,
    optimizedDirectory: String?,
    librarySearchPath: String?,
    parent: ClassLoader,
    sharedClasses: List<String> = emptyList(),
    grandTimes: Int = 1
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {
    private val mGrandParent: ClassLoader? by lazy {
        var grand: ClassLoader? = parent
        for (i in 0 until grandTimes) {
            if (null != grand) grand = grand.parent
        }

        grand
    }

    private val mSharedClassList: List<String> by lazy {
        val l = mutableListOf<String>()
        l.addAll(sharedClasses)
        l.addAll(sDefaultSharedClasses)
        l
    }

    override fun loadClass(name: String?): Class<*> {
        return loadClass(name, false)
    }

    /**
     * class load logic:
     * 1. when className in #sharedClassList, load from #parent class loader
     * 2. load class as normal
     * 3. if class not found this this class loader, load from #mGrandParent class laoder
     */
    @Throws(ClassNotFoundException::class)
    override fun loadClass(name: String?, resolve: Boolean): Class<*> {
        var isSharedClass = false
        for (c in mSharedClassList) {
            if (c == name) {
                isSharedClass = true
                break
            }
        }

        if (isSharedClass) {
            return super.loadClass(name, false)
        }

        var clazz = findLoadedClass(name)
        var suppressed: Throwable? = null
        if (null == clazz) {
            try {
                clazz = findClass(name)
            } catch (e: ClassNotFoundException) {
                suppressed = e
            }
        }

        if (null == clazz) {
            val grand = mGrandParent ?: throw ClassNotFoundException(name)
            try {
                clazz = grand.loadClass(name)
            } catch (e: ClassNotFoundException) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && null != suppressed) {
                    e.addSuppressed(suppressed)
                }

                throw e
            }
        }

        return clazz
    }

    /**
     * find a interface implementation from this class loader
     * used when host use the dynamic implementation
     * @param interfaceClass the interface class
     * @param implementationName the class name, that implement the interface
     */
    fun <T> getInterfaceImplementation(interfaceClass: Class<T>, implementationName: String): T? {
        val impClass = loadClass(implementationName)
        val instance = impClass.newInstance()

        return interfaceClass.cast(instance)
    }

    companion object {
        /* default shared class list */
        /* shared with dynamic apk and host */
        /* when dynamic apk load these class, load from parent direct */
        private val sDefaultSharedClasses = listOf(
            "androidx.fragment.app.Fragment",
            "androidx.navigation.fragment.NavHostFragment",
            "androidx.fragment.app.FragmentManager",
            "androidx.appcompat.app.AppCompatActivity",
            "com.covid19.dynamic.NavHostFragmentFactory",
            "androidx.navigation.NavController"

        )
    }
}