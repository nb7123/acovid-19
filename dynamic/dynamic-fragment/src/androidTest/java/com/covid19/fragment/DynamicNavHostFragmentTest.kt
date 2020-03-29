package com.covid19.fragment

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.covid19.dynamic.fragment.R
import com.covid19.fragment.test.NavigationActivity
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class DynamicNavHostFragmentTest {

    @Test
    fun testLunchUI() {
        val s = ActivityScenario.launch(NavigationActivity::class.java)
        s.onActivity {  }

        Thread.sleep(50000)
    }
}

class NavigationActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dynamic_activity_layout)
        super.onCreate(savedInstanceState)
    }
}
