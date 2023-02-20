//Name: Pavan Kumar Mathari
//CWID: 885186924
//E-mail: pavan.mathari@csu.fullerton.edu

package com.mypackage.rgb

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.google.android.material.slider.Slider
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    var redValue = 0f
    var greenValue = 0f
    var blueValue = 0f

    lateinit var redSeekBar: Slider
    lateinit var greenSeekBar: Slider
    lateinit var blueSeekBar: Slider

    lateinit var redEditText: EditText
    lateinit var greenEditText: EditText
    lateinit var blueEditText: EditText

    private lateinit var redSwitch: Switch
    private lateinit var greenSwitch: Switch
    private lateinit var blueSwitch: Switch

    private lateinit var currentSeekBar: Slider
    private lateinit var resetButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redSeekBar = findViewById(R.id.redSeekBar)
        greenSeekBar = findViewById(R.id.greenSeekBar)
        blueSeekBar = findViewById(R.id.blueSeekBar)
        currentSeekBar = redSeekBar

        redSwitch = findViewById(R.id.redSwitch)
        greenSwitch = findViewById(R.id.greenSwitch)
        blueSwitch = findViewById(R.id.blueSwitch)

        redEditText = findViewById(R.id.redEditText)
        greenEditText = findViewById(R.id.greenEditText)
        blueEditText = findViewById(R.id.blueEditText)

        val df = DecimalFormat("#.##")

        redSeekBar.addOnChangeListener { slider, value, fromUser ->
            redValue = redSeekBar.value * 255
            redEditText.setText(df.format(value).toString());
            updateColor()
        }
        greenSeekBar.addOnChangeListener { slider, value, fromUser ->
            greenValue = greenSeekBar.value * 255
            greenEditText.setText(df.format(value).toString());
            updateColor()
        }
        blueSeekBar.addOnChangeListener { slider, value, fromUser ->
            blueValue = blueSeekBar.value * 255
            blueEditText.setText(df.format(value).toString());
            updateColor()
        }

        redSwitch.setOnCheckedChangeListener(switchCheckedChangeListener)
        greenSwitch.setOnCheckedChangeListener(switchCheckedChangeListener)
        blueSwitch.setOnCheckedChangeListener(switchCheckedChangeListener)

        redEditText.doOnTextChanged { text, start, before, count ->
            try {
                val value = redEditText.text.toString().toFloat()
                if (value >= 0 && value <= 1) {
                    redSeekBar.value = value
                    redValue = value * 255
                }
            } catch (e: java.lang.NumberFormatException) {
                Toast.makeText(this@MainActivity, "Values range from 0 and 1", Toast.LENGTH_LONG)
                    .show()
            }
        }
        greenEditText.doOnTextChanged { text, start, before, count ->
            try {
                val value = greenEditText.text.toString().toFloat()
                if (value >= 0 && value <= 1) {
                    greenSeekBar.value = value
                    greenValue = value * 255
                }
            } catch (e: java.lang.NumberFormatException) {
                Toast.makeText(this@MainActivity, "Values range from 0 and 1", Toast.LENGTH_LONG)
                    .show()
            }
        }
        blueEditText.doOnTextChanged { text, start, before, count ->
            try {
                val value = blueEditText.text.toString().toFloat()
                if (value >= 0 && value <= 1) {
                    blueSeekBar.value = value
                    blueValue = value * 255
                }
            } catch (e: java.lang.NumberFormatException) {
                Toast.makeText(this@MainActivity, "Values range from 0 and 1", Toast.LENGTH_LONG)
                    .show()
            }
        }

        resetButton = findViewById<Button>(R.id.resetButton)

        resetButton.setOnClickListener()
        {
            // Reset red seek bar
            redSeekBar.value = 0f
            redSwitch.isChecked = true
            val z = 0
            redEditText.setText(z.toString())

            // Reset green seek bar
            greenSeekBar.value = 0f
            greenSwitch.isChecked = true
            greenEditText.setText(z.toString())

            // Reset blue seek bar
            blueSeekBar.value = 0f
            blueSwitch.isChecked = true
            blueEditText.setText(z.toString())

            Toast.makeText(this@MainActivity, "Reset button clicked", Toast.LENGTH_LONG)
                .show()
        }

    }


    fun updateColor() {
        val colorView = findViewById<View>(R.id.colorView)
        colorView.setBackgroundColor(
            Color.rgb(
                redValue.toInt(),
                greenValue.toInt(),
                blueValue.toInt()
            )
        )
    }


    private val switchCheckedChangeListener =
        object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                buttonView?.let {
                    when (it.id) {
                        R.id.redSwitch -> {
                            redSeekBar.isEnabled = isChecked
                            redEditText.isEnabled = isChecked
                            redSeekBar.value = 0f
                        }
                        R.id.greenSwitch -> {
                            greenSeekBar.isEnabled = isChecked
                            greenEditText.isEnabled = isChecked
                            greenSeekBar.value = 0f

                        }
                        R.id.blueSwitch -> {
                            blueSeekBar.isEnabled = isChecked
                            blueEditText.isEnabled = isChecked
                            blueSeekBar.value = 0f
                        }
                    }
                }
            }
        }
}