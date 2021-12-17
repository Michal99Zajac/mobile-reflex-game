package com.example.pm_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class InfoFragment(val replacer: FragmentManager) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.info_fragment, container, false)

        // set button for navigation
        val returnButton = view.findViewById<Button>(R.id.return_button)
        returnButton.setOnClickListener {
            replaceFragment(GameFragment(replacer))
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        replacer.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}