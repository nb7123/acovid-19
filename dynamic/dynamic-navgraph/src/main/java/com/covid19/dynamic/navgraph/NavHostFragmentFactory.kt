package com.covid19.dynamic.navgraph

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

abstract class NavHostFragmentFactory {
    abstract fun onCreateNavHostFragment(context: Context): NavHostFragment
    abstract fun onGetStartDestinationArgs(): Bundle?
}