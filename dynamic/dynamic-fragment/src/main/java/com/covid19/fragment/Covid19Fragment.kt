package com.covid19.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

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
 *       Author[michael]          |       Create[2020-02-21]
 * --------------------------------------------------------------
 * Description: Plugin fragment
 * --------------------------------------------------------------
 */
abstract class Covid19Fragment: Fragment() {
    private var mContext: Context? = null

    private val mFragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks by lazy {
        DynamicFragmentLifecycleCallbacks(
            requireContext()
        )
    }

    /**
     * we change context to plugin context
     */
    override fun getContext(): Context? {
        return if (null != mContext) mContext else super.getContext()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val hostContext = super.getContext()
        // maybe we attach dynamic context
        if (context != hostContext && null != hostContext) {
            mContext = context

            childFragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks, false)
        }
    }

    override fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater {
        return LayoutInflater.from(requireContext())
    }

    override fun onDetach() {
        childFragmentManager.unregisterFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks)
        super.onDetach()
    }
}