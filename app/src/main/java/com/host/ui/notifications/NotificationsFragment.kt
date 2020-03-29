package com.host.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.host.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            text_notifications.text = it
        })
    }
}
