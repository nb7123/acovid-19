package com.covid19.sample.plugin01


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.covid19.fragment.Covid19Fragment
import com.covid19.fragment.Covid19NavigatorExtras
import com.covid19.fragment.utils.NavControllerCompat
import kotlinx.android.synthetic.main.fragment_blank.*

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Covid19Fragment() {
    companion object{
        private val TAG = BlankFragment::class.java.simpleName
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        Log.d(TAG, "onCreateView(inflater: ${inflater.javaClass.name}, container: $container, savedInstanceState: $savedInstanceState)")
        Log.d(TAG, "inflater context: ${inflater.context.javaClass.name}")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            Log.d(TAG, "Fab clicked, nav to host module")
            val navController = findNavController()
            val navCompat = NavControllerCompat(navController, requireContext())

            navCompat.navigate(R.id.navigation_dashboard)
        }
    }
}
