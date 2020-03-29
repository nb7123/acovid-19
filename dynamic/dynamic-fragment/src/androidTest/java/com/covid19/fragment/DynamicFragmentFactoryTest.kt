package com.covid19.fragment

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.covid19.fragment.test.SimpleDynamicModuleManager
import com.covid19.runtime.util.FileUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class DynamicFragmentFactoryTest {
    @Test
    fun testInitFragment() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val dynamicModuleManager = SimpleDynamicModuleManager(context)

        val dest = File(context.filesDir, "plugin.apk")
        if (!dest.exists()) {
            val stream = context.assets.open("plugin01-debug.apk")
            FileUtil.copy(stream, dest)
        }

        val fragmentClassName = "com.covid19.sample.plugin01.BlankFragment"
        val fragmentFactory = DynamicFragmentFactory(dynamicModuleManager)
        val fragment = fragmentFactory.instantiate(context.classLoader, fragmentClassName)
        Assert.assertNotNull(fragment)
        Assert.assertEquals(fragmentClassName, fragment.javaClass.name)
    }
}