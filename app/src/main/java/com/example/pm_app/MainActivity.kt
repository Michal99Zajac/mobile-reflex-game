package com.example.pm_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init main fragment view
        val replacer = supportFragmentManager
        val fragment = GameFragment(replacer)
        replacer.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}