package com.covid19.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.covid19.runtime.Covid19ModuleManager

internal class DynamicFragmentFactory(private val dynamicManager: Covid19ModuleManager): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        Log.d(TAG, "instantiate(classloader: $classLoader,\n className: $className")
        val dynamicModule = dynamicManager.dynamicModuleForClass(className)
        val cl = dynamicModule?.getClassLoader() ?: classLoader

        return super.instantiate(cl, className)
    }

    companion object{
        private val TAG = DynamicFragmentFactory::class.java.simpleName
    }
}