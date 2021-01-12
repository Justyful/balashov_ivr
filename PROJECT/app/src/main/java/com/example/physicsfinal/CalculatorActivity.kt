package com.example.physicsfinal

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.AndroidException
import android.util.AndroidRuntimeException
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_calculator.*
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

class CalculatorActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        setSupportActionBar(toolbar as Toolbar)
        title = "Калькулятор"

        b1.setOnClickListener {
            input.text = input.text.toString() + "1"
        }
        b2.setOnClickListener {
            input.text = input.text.toString() + "2"
        }
        b3.setOnClickListener {
            input.text = input.text.toString() + "3"
        }
        b4.setOnClickListener {
            input.text = input.text.toString() + "4"
        }
        b5.setOnClickListener {
            input.text = input.text.toString() + "5"
        }
        b6.setOnClickListener {
            input.text = input.text.toString() + "6"
        }
        b7.setOnClickListener {
            input.text = input.text.toString() + "7"
        }
        b8.setOnClickListener {
            input.text = input.text.toString() + "8"
        }
        b9.setOnClickListener {
            input.text = input.text.toString() + "9"
        }
        b0.setOnClickListener {
            input.text = input.text.toString() + "0"
        }
        point.setOnClickListener {
            input.text = input.text.toString() + "."
        }
        backspace.setOnClickListener {
            input.text = input.text.toString().substring(0, input.text.toString().length - 1)
        }
        clear.setOnClickListener {
            input.text = ""
        }
        plus.setOnClickListener {
            input.text = input.text.toString() + "+"
        }
        minus.setOnClickListener {
            input.text = input.text.toString() + "-"
        }
        multiply.setOnClickListener {
            input.text = input.text.toString() + "×"
        }
        divide.setOnClickListener {
            input.text = input.text.toString() + "/"
        }
        power.setOnClickListener {
            val d: Double = input.text.toString().toDouble()
            val square = d * d
            input.text = square.toString()
            output.text = "$d²"
            //input.text = input.text.toString() + "÷"
        }
        sqrt.setOnClickListener {
            val value: String = input.text.toString()
            val r = sqrt(value.toDouble())
            input.text = r.toString()
        }
        pi.setOnClickListener {
            input.text = input.text.toString() + "3.141"
        }
        e.setOnClickListener {
            input.text = input.text.toString() + "2.718"
        }
        factorial.setOnClickListener {
            //input.text = input.text.toString() + "!"
            val value : Double = input.text.toString().toDouble()
            val fact = factorial(value)
            input.text = fact.toString()
            output.text = "$value!"
        }
        open_bracket.setOnClickListener {
            input.text = input.text.toString() + "("
        }
        close_bracket.setOnClickListener {
            input.text = input.text.toString() + ")"
        }
        equals.setOnClickListener {
            val value: String = input.text.toString()
            val replacedstr = value.replace('×', '*')
            val asdf = Evaluate()
            try {
                val result: Double = asdf.eval(replacedstr)
                input.text = result.toString()
                output.text = value
            }
            catch (e : AndroidException) {
                val result : Double = 0.0
                input.text = result.toString()
                output.text = value
            }
//            val text = input.text.toString()
//            var outText = text.replace("×", "*")
//           val eval = Evaluate(outText)
//           var result = eval.eval()
//           input.text = result.toString()
//           output.text = text
        }
    }

    fun factorial(number : Double) : Double {
        if (number >= 100000.0) return 0.0
        if (number - floor(number) != 0.0) return 0.0
        return try {
            if (number == 1.0 || number == 0.0) {
                1.0
            } else {
                number * factorial(number - 1)
            }
        } catch (e: AndroidRuntimeException) {
            0.0
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.calc_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.calc_info) {
            val builder = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val view = inflater.inflate(R.layout.calculator_info, null)
            builder.setView(view)
            builder.setTitle("Справка")
            builder.setPositiveButton("Ок") { dialog, which -> }
            builder.show()
        }
        return super.onOptionsItemSelected(item)
    }
}

class Evaluate {
    fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) return 0.0
                return x
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor
            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.toInt()) -> x += parseTerm() // addition
                        eat('-'.toInt()) -> x -= parseTerm() // subtraction
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.toInt()) -> x *= parseFactor() // multiplication
                        eat('/'.toInt()) -> x /= parseFactor() // division
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor() // unary plus
                if (eat('-'.toInt())) return -parseFactor() // unary minus
                var x: Double
                val startPos = pos
                if (eat('('.toInt())) { // parentheses
                    x = parseExpression()
                    eat(')'.toInt())
                }
                else
                    if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) { // numbers
                        while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()
                        x = str.substring(startPos, pos).toDouble()
                    }
                    else
                        if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) { // functions
                            while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                            val func = str.substring(startPos, pos)
                            x = parseFactor()
                            if (func == "sqrt") x = sqrt(x)
                            else  return 0.0
                        }
                        else {
                            return 0.0
                        }
                if (eat('^'.toInt())) x = x.pow(parseFactor()) // exponentiation
                return x
            }
        }.parse()
    }
}
