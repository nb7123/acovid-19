package com.covid19.runtime

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DynamicModuleManagerTest {
    @Test
    fun testInstallModule() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val mgr = SimpleDynamicModuleManager(context)
        mgr.cleanInstalledModules()

        val findModule = mgr.dynamicModuleForClass("com.test.Abc")
        Assert.assertNotNull(findModule)
        Assert.assertEquals("test", findModule?.installedModule?.moduleName)
    }
}