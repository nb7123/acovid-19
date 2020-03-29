package com.covid19.sample.plugin01

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.appbar.AppBarLayout

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
 *       Author[michael]          |       Create[2020-02-22]
 * --------------------------------------------------------------
 * Description:
 * --------------------------------------------------------------
 */
class CustomAppBarLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null): AppBarLayout(context, attrs) {
    companion object{
        private val TAG = CustomAppBarLayout::class.java.simpleName
    }
    init {
        Log.d(TAG, "context: ${context.javaClass.name}")
    }
}