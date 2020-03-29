package com.covid19.runtime.content

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.covid19.runtime.content.Covid19ContextThemeWrapper
import com.covid19.runtime.model.InstalledModule
import com.covid19.runtime.util.FileUtil
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class Covid19ContextThemeWrapperTest {
    @Test
    fun testThemeAttrs() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val dynamicApk = File(context.filesDir, "dynamic.apk")
        val srcStream = context.assets.open("plugin01-debug.apk")
        FileUtil.copy(srcStream, dynamicApk)
        val installedApk = InstalledModule("test", dynamicApk.absolutePath)

        val dynamicContext = Covid19ContextThemeWrapper(context, installedApk, "AppTheme")

//        val actionBarSizeId = dynamicContext.resources.getIdentifier("actionBarSize", "dimen", dynamicContext.packageName)
//        Assert.assertTrue(actionBarSizeId != 0)
//
//        val dimen = dynamicContext.resources.getDimension(actionBarSizeId)
//        Assert.assertTrue(dimen > 0)
    }
}