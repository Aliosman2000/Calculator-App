package com.egitim.my_applicationn

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat




class MainActivity : AppCompatActivity() {

    private lateinit var textViewResult : TextView
    private var sonnumara : Boolean = false
    private var sonnokta : Boolean = false
    private var ilksayi : Double = 0.0
    private var mevcutoperator : String = ""
    private var operatorbasma: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textViewResult = findViewById(R.id.textView2)
    }

    fun sayilar(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        val mevcutmetin = textViewResult.text.toString()

        if (mevcutmetin == "0" && buttonText != ".") {
            textViewResult.text = buttonText
        }
        else if(operatorbasma)
        {
            textViewResult.text = buttonText
        }
        else
        {
            if (buttonText == "." && sonnokta)
            {
                return
            }
            textViewResult.append(buttonText)
        }

        sonnumara = true
        sonnokta = buttonText == "."
        operatorbasma = false
    }

    @SuppressLint("SuspiciousIndentation")
    fun estt(view: View) {

        if(sonnumara && mevcutoperator.isNotEmpty())
        {
            val ikincisayi = textViewResult.text.toString().toDouble()
            val result = Hesaplama(ilksayi,ikincisayi,mevcutoperator)
                if(result != null)
                {
                    textViewResult.text = Numara_Format(result)
                    ilksayi = result
                }
                else
                {
                    textViewResult.text = "Hata"
                }
                sıfırla()
                sonnumara = true

            }
        }

    fun islem(view: View) {

        val button = view as Button
        val buttonText = button.text.toString()

        if (sonnumara &&  !operatorbasma) {
            if(mevcutoperator.isEmpty())
            {
                ilksayi = textViewResult.text.toString().toDouble()
                mevcutoperator = buttonText
                textViewResult.text = "0"
            }
            else {
                val ikincisayi = textViewResult.text.toString().toDouble()
                val result = Hesaplama(ilksayi,ikincisayi,mevcutoperator)

                if (result != null) {
                    ilksayi = result
                    mevcutoperator = buttonText
                    textViewResult.text = Numara_Format(ilksayi)
                }

                else
                {
                    textViewResult.text = "Error"
                    sıfırla()
                }
            }
            operatorbasma = true
            sonnumara = false
            sonnokta = false
        }
    }

    private fun Numara_Format(numara: Double) : String {
        return if(numara.rem(1) == 0.0)
        {
            numara.toLong().toString()
        }

        else
        {
            numara.toString()
        }
    }



    private fun sıfırla() {
        ilksayi = 0.0
        mevcutoperator = ""
        sonnumara = false
        sonnokta = false

    }
    fun temzlee(view: View) {

        textViewResult.text = "0"
        sıfırla()

    }

    private fun Hesaplama(say1: Double, say2: Double, operator: String): Double? {
        return when (operator) {
            "+" -> say1 + say2
            "-" -> say1 - say2
            "*" -> say1 * say2
            "/" -> if (say2 == 0.0) null else say1 / say2
            else -> null
        }
    }


}