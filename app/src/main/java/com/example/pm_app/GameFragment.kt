package com.example.pm_app

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class GameFragment(val replacer: FragmentManager) : Fragment() {
    private var counter: Int = 1
    private var clicks: ArrayList<ClickFragment> = arrayListOf(ClickFragment())
    private var result: Long = 0
    private var clickedCount: Int = 0
    private var isFail: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_fragment, container, false)

        // get elements and setup initial values
        val startButton = view.findViewById<Button>(R.id.start_button)
        val incrementButton = view.findViewById<Button>(R.id.increment_button)
        incrementButton.isEnabled = true
        val decrementButton = view.findViewById<Button>(R.id.decrement_button)
        decrementButton.isEnabled = false
        val infoButton = view.findViewById<Button>(R.id.info_button)

        // set default value of text
        val counterText = view.findViewById<TextView>(R.id.counter_text)
        counterText.setText(this.counter.toString())

        // set click fragment
        replacer.beginTransaction().replace(R.id.clickContainer, this.clicks[0]).commit()

        // set onClick listeners
        infoButton.setOnClickListener {
            replaceFragment(InfoFragment(replacer))
        }

        incrementButton.setOnClickListener {
            val newClickFragment = ClickFragment()
            setCounter(this.counter + 1, incrementButton, decrementButton)
            counterText.setText(this.counter.toString())
            addCLickFragment(newClickFragment)
            clicks.add(newClickFragment)
        }

        decrementButton.setOnClickListener {
            setCounter(this.counter - 1, incrementButton, decrementButton)
            counterText.setText(this.counter.toString())
            val oldClickFragment = clicks.removeLast()
            removeClickFragment(oldClickFragment)
        }

        startButton.setOnClickListener {
            startGame()
        }

        return view
    }

    private fun setCounter(newValue: Int, incrementButton: Button, decrementButton: Button) {
        // set +/- buttons to disabled or enabled
        if (newValue == 1) {
            decrementButton.isEnabled = false
            incrementButton.isEnabled = true
        } else if (newValue == 3) {
            decrementButton.isEnabled = true
            incrementButton.isEnabled = false
        } else {
            decrementButton.isEnabled = true
            incrementButton.isEnabled = true
        }

        this.counter = newValue
    }

    private fun replaceFragment(fragment: Fragment) {
        replacer.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun removeClickFragment(click: Fragment) {
        replacer.beginTransaction().remove(click).commit()
    }

    private fun addCLickFragment(click: Fragment) {
        replacer.beginTransaction().add(R.id.clickContainer, click).commit()
    }

    private fun disableButtons() {
        view?.findViewById<Button>(R.id.start_button)?.isEnabled = false
        view?.findViewById<Button>(R.id.increment_button)?.isEnabled = false
        view?.findViewById<Button>(R.id.decrement_button)?.isEnabled = false
        view?.findViewById<Button>(R.id.info_button)?.isEnabled = false
    }

    private fun startGame() {
        // disable all buttons apart from click buttons
        disableButtons()

        // set default behave
        for (click in clicks) {
            val clickButton = click.getClick()
            clickButton?.setOnClickListener {
                isFail = true
            }
        }

        // async game
        GlobalScope.launch {
            async { waitForClicks() }

            for (click in clicks.toMutableList().shuffled()) {
                val deferredButton = async { click.runFun() }
                val clickButton = deferredButton.await()
                val start = Date().getTime()

                clickButton?.setOnClickListener {
                    result += (Date().getTime() - start)
                    clickedCount += 1
                    clickButton.isEnabled = false
                    clickButton.setBackgroundColor(Color.parseColor("#0f6109"))
                }
            }
        }
    }

    // wait to game end
    private suspend fun waitForClicks() {
        while (true) {
            if (clickedCount == clicks.size) {
                break
            }

            if (isFail) {
                replaceFragment(WinFragment(replacer, (-1).toFloat()))
                return
            }
        }

        replaceFragment(WinFragment(replacer, result.toFloat()))
    }
}