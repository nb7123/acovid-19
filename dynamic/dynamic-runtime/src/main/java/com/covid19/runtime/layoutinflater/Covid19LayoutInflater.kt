package com.covid19.runtime.layoutinflater

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View

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
 *       Author[michael]          |       Create[2020-02-23]
 * --------------------------------------------------------------
 * Description: dynamic apk view layout inflater
 * @see com.android.internal.policy.PhoneLayoutInflater
 * --------------------------------------------------------------
 */
class Covid19LayoutInflater: LayoutInflater {
    constructor(context: Context): super(context)
    constructor(original: LayoutInflater, context: Context): super(original, context)

    override fun cloneInContext(newContext: Context): LayoutInflater {
        return Covid19LayoutInflater(this, newContext)
    }

    override fun onCreateView(name: String?, attrs: AttributeSet?): View {
        Log.d(TAG, "onCreateView[$name]")
        for (prefix in sClassPrefixList) {
            try {
                val view = createView(name, prefix, attrs)
                if (view != null) {
                    return view
                }
            } catch (e: ClassNotFoundException) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }

        return super.onCreateView(name, attrs);
    }

    companion object {
        private val TAG = Covid19LayoutInflater::class.java.simpleName
        private val sClassPrefixList = arrayOf(
            "android.widget.",
            "android.webkit.",
            "android.app."
        )
    }
}