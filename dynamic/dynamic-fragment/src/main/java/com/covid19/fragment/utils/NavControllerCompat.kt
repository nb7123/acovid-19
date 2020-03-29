package com.covid19.fragment.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import java.lang.reflect.Method

class NavControllerCompat(private val navController: NavController,
                          private val moduleContext: Context) {
    companion object{
        private val sMethodGetContext: Method by lazy {
            val clazz = NavController::class.java
            clazz.getDeclaredMethod("getContext")
        }
    }

    private val hostContext: Context by lazy {
        val accessible = sMethodGetContext.isAccessible
        sMethodGetContext.isAccessible = true
        val context = sMethodGetContext.invoke(navController) as Context
        sMethodGetContext.isAccessible = accessible
        context
    }

    /**
     * Navigate to a destination from the current navigation graph. This supports both navigating
     * via an [action][NavDestination.getAction] and directly navigating to a destination.
     *
     * @param resId an [action][NavDestination.getAction] id or a destination id to
     * navigate to
     */
    fun navigate(@IdRes resId: Int) {
        navigate(resId, null)
    }

    /**
     * Navigate to a destination from the current navigation graph. This supports both navigating
     * via an [action][NavDestination.getAction] and directly navigating to a destination.
     *
     * @param resId an [action][NavDestination.getAction] id or a destination id to
     * navigate to
     * @param args arguments to pass to the destination
     */
    fun navigate(@IdRes resId: Int, args: Bundle?) {
        navigate(resId, args, null)
    }

    /**
     * Navigate to a destination from the current navigation graph. This supports both navigating
     * via an [action][NavDestination.getAction] and directly navigating to a destination.
     *
     * @param resId an [action][NavDestination.getAction] id or a destination id to
     * navigate to
     * @param args arguments to pass to the destination
     * @param navOptions special options for this navigation operation
     */
    fun navigate(
        @IdRes resId: Int, args: Bundle?,
        navOptions: NavOptions?
    ) {
        navigate(resId, args, navOptions, null)
    }

    /**
     * Navigate to a destination from the current navigation graph. This supports both navigating
     * via an [action][NavDestination.getAction] and directly navigating to a destination.
     *
     * @param resId an [action][NavDestination.getAction] id or a destination id to
     * navigate to
     * @param args arguments to pass to the destination
     * @param navOptions special options for this navigation operation
     * @param navigatorExtras extras to pass to the Navigator
     */
    fun navigate(
        @IdRes resId: Int, args: Bundle?, navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        val resName = moduleContext.resources.getResourceEntryName(resId)
        val realResId = hostContext.resources.getIdentifier(resName, "id", hostContext.packageName)
        Log.d("NavControllerCompat", "res name: [$resName], Real resource id: $realResId")

        navController.navigate(realResId, args, navOptions, navigatorExtras)
    }
}