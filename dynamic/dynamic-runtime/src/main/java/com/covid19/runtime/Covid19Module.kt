package com.covid19.runtime

import android.content.Context
import com.covid19.runtime.content.Covid19ContextThemeWrapper
import com.covid19.runtime.model.InstalledModule
import java.lang.ref.WeakReference

class Covid19Module(val hostContext: Context,
                    val installedModule: InstalledModule
) {
    private var mContextRef: WeakReference<Context>? = null

    fun getModuleContext(): Context {
        var context = mContextRef?.get()
        if (null != context && context is Covid19ContextThemeWrapper) {
            return context
        }

        context = createModuleContext(hostContext)
        mContextRef = WeakReference(context)

        return context
    }

    fun getClassLoader(): ClassLoader {
        return getModuleContext().classLoader
    }

    private fun createModuleContext(base: Context): Context {
        return Covid19ContextThemeWrapper(base, installedModule)
    }
}