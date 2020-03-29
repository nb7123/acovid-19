package com.covid19.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.covid19.runtime.Covid19ModuleManager
import com.covid19.runtime.layoutinflater.Covid19LayoutInflater

/**
 * The [NavHostFragment] for dynamic features.
 */
abstract class Covid19NavHostFragment : NavHostFragment() {
    private lateinit var mDynamicModuleMgr: Covid19ModuleManager

    private val fragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks by lazy {
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                super.onFragmentAttached(fm, f, context)
                val dynamicModule = mDynamicModuleMgr.dynamicModuleForClass(f.javaClass.name)
                if (null != dynamicModule) {
                    f.onAttach(dynamicModule.getModuleContext())
                }
            }
        }
    }

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        mDynamicModuleMgr = onCreateDynamicModuleManager(requireContext())
        childFragmentManager.fragmentFactory = DynamicFragmentFactory(mDynamicModuleMgr)

        childFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, false)
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        return Covid19LayoutInflater(requireContext())
    }

    /**
     * Create dynamic module manager
     */
    abstract fun onCreateDynamicModuleManager(context: Context): Covid19ModuleManager
}
