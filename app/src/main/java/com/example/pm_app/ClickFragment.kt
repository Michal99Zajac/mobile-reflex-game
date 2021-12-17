package com.example.pm_app

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.coroutines.delay

class ClickFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.click_fragment, container, false)
    }

    // return button after delay
    suspend fun runFun(): Button? {
        val click = view?.findViewById<Button>(R.id.click_button)

        delay((1000 * (1..4).random()).toLong())
        click?.setBackgroundColor(Color.parseColor("#05fc47"))

        return click
    }

    fun getClick(): Button? {
        return view?.findViewById<Button>(R.id.click_button)
    }
}