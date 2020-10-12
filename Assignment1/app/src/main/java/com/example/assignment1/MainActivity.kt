package com.example.assignment1

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
private const val INITIAL_PEOPLE = 1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sbTip.progress = INITIAL_TIP_PERCENT
        tvTipPercent.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescription(sbTip.progress)
        tvTipAmount.text = "N/A"
        tvTotalAmount.text = "N/A"


        sbTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvTipPercent.text = "$p1% "

                updateTipDescription(p1)
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        etBase.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                computeTipAndTotal()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        etPeopleAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                computeTipAndTotal()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription: String
        when (tipPercent) {
            in 0..9 -> tipDescription = "🤬 poor"
            in 10..14 -> tipDescription = "😐 acceptable"
            in 15..19 -> tipDescription = "😬 good"
            in 20..24 -> tipDescription = "🥳 great"
            else -> tipDescription = "🤩 amazing"
        }

        tvTipDescrip.text = tipDescription
        val color = ArgbEvaluator().evaluate(
            tipPercent / sbTip.max.toFloat(),
            ContextCompat.getColor(this, R.color.colorWorstTip),
            ContextCompat.getColor(this, R.color.colorBestTip)
        ) as Int

        tvTipDescrip.setTextColor(color)

    }

    private fun computeTipAndTotal() {
        // guard against empty case
        if (etBase.text.isBlank()) {
            tvTipAmount.text = "N/A"
            tvTotalAmount.text = "N/A"
            return
        }

        // get value of base, tip percentage, # of people
        val base = etBase.text.toString().toDouble()
        val tipPercent = sbTip.progress
        var numPeople = 1

        if (etPeopleAmount.text.isBlank()) {
            etPeopleAmount.setText("1")
        } else if (etPeopleAmount.text.toString().toInt() > 0) {
            numPeople = etPeopleAmount.text.toString().toInt()
        }

        // calculate vars
        val tipAmount = tipPercent * base / 100
        val totalAmount = (tipAmount + base) / numPeople

        // set UI
        tvTipAmount.text = "$" + "%.2f".format(tipAmount)
        tvTotalAmount.text = "$" + "%.2f".format(totalAmount)

    }
}
