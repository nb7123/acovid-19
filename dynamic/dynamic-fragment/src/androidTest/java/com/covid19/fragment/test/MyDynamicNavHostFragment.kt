package com.covid19.fragment.test

import android.content.Context
import com.covid19.runtime.Covid19ModuleManager
import com.covid19.fragment.Covid19NavHostFragment

class MyDynamicNavHostFragment: Covid19NavHostFragment() {
    override fun onCreateDynamicModuleManager(context: Context): Covid19ModuleManager {
        return SimpleDynamicModuleManager(context)
    }
}