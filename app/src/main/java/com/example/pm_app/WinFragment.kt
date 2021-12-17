package com.example.pm_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class WinFragment(val replacer: FragmentManager, val result: Float): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.win_fragment, container, false)

        // if result is negative it means user lose
        if (result < 0) {
            view.findViewById<TextView>(R.id.result_text).setText(R.string.lose_result)
        } else {
            view.findViewById<TextView>(R.id.result_text).setText("${result} ms")
        }

        // return to game view
        val restartButton = view.findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener {
            replaceFragment(GameFragment(replacer))
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        replacer.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}