//Name: Pavan Kumar Mathari
//CWID: 885186924
//E-mail: pavan.mathari@csu.fullerton.edu

package com.mypackage.rgb

//imports required for this project
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.google.android.material.slider.Slider
import java.text.DecimalFormat
import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    // initializing initial values of EDIT-Text to Zero
    var redValue = 0f
    var greenValue = 0f
    var blueValue = 0f

    // Slider variables
    lateinit var redSeekBar: Slider
    lateinit var greenSeekBar: Slider
    lateinit var blueSeekBar: Slider


    // Edittext variables
    lateinit var redEditText: EditText
    lateinit var greenEditText: EditText
    lateinit var blueEditText: EditText

    // Switch variables
    lateinit var redSwitch: Switch
    lateinit var greenSwitch: Switch
    lateinit var blueSwitch: Switch

    // Reset Button variable
    private lateinit var resetButton: Button

    private val viewModel: viewModel by lazy {
        PreferencesDataStore.initialize(this)
        //ViewModelProvider(this)[MyViewModel::class.java]
        viewModel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Variables initialization
        redSeekBar = findViewById(R.id.redSeekBar)
        greenSeekBar = findViewById(R.id.greenSeekBar)
        blueSeekBar = findViewById(R.id.blueSeekBar)

        redEditText=findViewById(R.id.redEditText)
        blueEditText=findViewById(R.id.blueEditText)
        greenEditText=findViewById(R.id.greenEditText)

        redSwitch = findViewById(R.id.redSwitch)
        greenSwitch = findViewById(R.id.greenSwitch)
        blueSwitch = findViewById(R.id.blueSwitch)
        val dka= findViewById(R.id.DKA) as Switch

        this.viewModel.loadInputs(this)


        //dont keep activities switch listener
        dka?.setOnCheckedChangeListener({ _ , isChecked ->
            if (isChecked) {

                this.viewModel.saveInput("",1)
                this.viewModel.saveInput("",2)
                this.viewModel.saveInput("",3)

                this.viewModel.saveInput("true",4)
                this.viewModel.saveInput("true",5)
                this.viewModel.saveInput("true",6)

            }
            this.redEditText.setText(redSeekBar.value.toString())
            this.greenEditText.setText(greenSeekBar.value.toString())
            this.blueEditText.setText(blueSeekBar.value.toString())
        })



        //listener for red edit text
        this.redEditText.doOnTextChanged { text, start, before, count ->
            try {
                if(!dka.isChecked()){
                    this.viewModel.saveInput(this.redEditText.text.toString(),1)}

                val value = redEditText.text.toString().toFloat()
                if (value >= 0 && value <= 1) {
                    redSeekBar.value = value
                    redValue = value * 255
                    updateColor()
                }
            } catch (e: java.lang.NumberFormatException) {
//                Toast.makeText(this@MainActivity, "", Toast.LENGTH_LONG)
//                    .show()

            }
        }


        //listener for green edit text
        this.greenEditText.doOnTextChanged { text, start, before, count ->
            try {
                if(!dka.isChecked()) {
                    this.viewModel.saveInput(this.greenEditText.text.toString(), 2)
                }
                val value = greenEditText.text.toString().toFloat()
                if (value >= 0 && value <= 1) {
                    greenSeekBar.value = value
                    greenValue = value * 255
                    updateColor()
                }
            } catch (e: java.lang.NumberFormatException) {
//                Toast.makeText(this@MainActivity, "", Toast.LENGTH_LONG)
//                    .show()

            }
        }




        //listener for blue edit text
        this.blueEditText.doOnTextChanged { text, start, before, count ->
            try {
                if(!dka.isChecked()){
                    this.viewModel.saveInput(this.blueEditText.text.toString(),3)}
                val value = blueEditText.text.toString().toFloat()
                if (value >= 0 && value <= 1) {
                    blueSeekBar.value = value
                    blueValue = value * 255
                    updateColor()
                }
            } catch (e: java.lang.NumberFormatException) {
//                Toast.makeText(this@MainActivity, "", Toast.LENGTH_LONG)
//                    .show()

            }
        }



        val df = DecimalFormat("#.##")

        //listener for red slider
        redSeekBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            // Do nothing when the user starts touching the slider
        }

            override fun onStopTrackingTouch(slider: Slider) {
                val roundoff = df.format(redSeekBar.value) // to limit the edit text values to two decimal places
                redValue = redSeekBar.value * 255
                redEditText.setText(roundoff.toString())
                updateColor()
            }
})

        //listener for green slider
        greenSeekBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Do nothing when the user starts touching the slider
            }

            override fun onStopTrackingTouch(slider: Slider) {
                val roundoff = df.format(greenSeekBar.value) // to limit the edit text values to two decimal places
                greenValue = greenSeekBar.value * 255
                greenEditText.setText(roundoff.toString())
                updateColor()
            }
        })

        //listener for blue slider
        blueSeekBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Do nothing when the user starts touching the slider
            }

            override fun onStopTrackingTouch(slider: Slider) {
                val roundoff = df.format(blueSeekBar.value) // to limit the edit text values to two decimal places
                blueValue = blueSeekBar.value * 255
                blueEditText.setText(roundoff.toString())
                updateColor()
            }
        })



        //if red switch is checked / unchecked
        redSwitch?.setOnCheckedChangeListener({ _ , isChecked ->
            if (isChecked) {
                redSeekBar.isEnabled = true
                redEditText.isEnabled=true

                if(!dka.isChecked()){
                    this.viewModel.saveInput(this.redSwitch.isChecked().toString(),4)}
                redValue=redSeekBar.value * 255
                updateColor()
            }
            else {
                redSeekBar.isEnabled = false
                redSeekBar.value=0.0F
                this.redEditText.setText("")
                if(!dka.isChecked()) {
                    this.viewModel.saveInput(this.redEditText.text.toString(), 1)
                    this.viewModel.saveInput(this.redSwitch.isChecked().toString(), 4)
                }
                redValue=redSeekBar.value * 255
                updateColor()
                redEditText.isEnabled=false

            }

        })


        //if green switch is checked / unchecked
        greenSwitch?.setOnCheckedChangeListener({ _ , isChecked ->
            if (isChecked){ greenSeekBar.isEnabled=true
                greenEditText.isEnabled=true
                if(!dka.isChecked()){

                    this.viewModel.saveInput(this.greenSwitch.isChecked().toString(),5)}
                greenValue=greenSeekBar.value * 255
                updateColor()

            }
            else {
                greenSeekBar.isEnabled = false

                greenSeekBar.value=0.0F
                this.greenEditText.setText("")
                if(!dka.isChecked()){

                    this.viewModel.saveInput(this.greenEditText.text.toString(),2)
                this.viewModel.saveInput(this.greenSwitch.isChecked().toString(),5)}
                greenValue=greenSeekBar.value * 255
                updateColor()
                greenEditText.isEnabled=false

            }
        })


        //if blue switch is checked / unchecked
        blueSwitch?.setOnCheckedChangeListener({ _ , isChecked ->
            if (isChecked){ blueSeekBar.isEnabled=true
                blueEditText.isEnabled=true
                if(!dka.isChecked()){
                    this.viewModel.saveInput(this.blueSwitch.isChecked().toString(),6)}
                blueValue=blueSeekBar.value * 255
                updateColor()
            }
            else {
                blueSeekBar.isEnabled = false
                blueSeekBar.value=0.0F
                this.blueEditText.setText("")
                if(!dka.isChecked()) {
                    this.viewModel.saveInput(this.blueEditText.text.toString(), 3)
                    this.viewModel.saveInput(this.blueSwitch.isChecked().toString(), 6)
                }
                blueValue=blueSeekBar.value * 255
                updateColor()
                blueEditText.isEnabled=false
            }
        })


        resetButton = findViewById<Button>(R.id.resetButton)

        resetButton.setOnClickListener()
        {
            // Reset red seek bar
            redSeekBar.value = 0f
            redSwitch.isChecked = true
            val z = 0
            this.redEditText.setText(z.toString())

            // Reset green seek bar
            greenSeekBar.value = 0f
            greenSwitch.isChecked = true
            this.greenEditText.setText(z.toString())

            // Reset blue seek bar
            blueSeekBar.value = 0f
            blueSwitch.isChecked = true
            this.blueEditText.setText(z.toString())

            Toast.makeText(this@MainActivity, "Reset button clicked", Toast.LENGTH_LONG)
                .show()


        }}
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save important variables to the bundle object
        outState.putString("redEditText", redEditText.text.toString())
        outState.putString("greenEditText", greenEditText.text.toString())
        outState.putString("blueEditText", blueEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val retval= savedInstanceState.getString("redEditText")
        this.redEditText.setText(retval)
        val getval= savedInstanceState.getString("greenEditText")
        this.greenEditText.setText(getval)
        val betval= savedInstanceState.getString("blueEditText")
        this.blueEditText.setText(betval)
    }

    override fun onDestroy() {
        super.onDestroy()


    }

    fun updateColor() { // created a function to update color of view
        val colorView = findViewById<View>(R.id.colorView)
        colorView.setBackgroundColor(
            Color.rgb(
                redValue.toInt(),
                greenValue.toInt(),
                blueValue.toInt()
            )
        )
    }


    // checking if switches are on or off
    // switches on: edit text and slider work
    // switches off: edit text and slider stop working
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