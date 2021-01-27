package com.example.physicsfinal.Solve

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import com.example.physicsfinal.R
import kotlinx.android.synthetic.main.activity_solver.*
import java.lang.NullPointerException

class SolverActivity : AppCompatActivity() {
    var cnt = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solver)
        setSupportActionBar(toolbar as Toolbar)
        val position = intent.getIntExtra("position", -1)
        val solveList = intent.getStringArrayListExtra("solveList")
        title = solveList!![position]
        valuesList.addView(createET("Введите переменную и ее значение", 0))


        addVariable.setOnClickListener {
            cnt++
            valuesList.addView(createET("Введите переменную и ее значение", cnt))
        }

        deleteVariable.setOnClickListener {
            if (cnt > -1) {
                valuesList.removeView(valuesList[cnt])
                cnt--
            }
        }

        getAnswer.setOnClickListener {
            var variables = mutableMapOf<String, String>()
            for (i in 0..cnt) {
                val text : String = (valuesList[i] as EditText).text.toString()
                variables.put(text.substringBefore('='), text.substringAfter('='))
            }
            val answer = solveTask(position, variables)
            answerText.text = "Ответ: " + answer
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.calc_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.calc_info -> {
//
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    fun createTV (text : String, id : Int) : TextView {
//        val tv = TextView(this)
//        tv.text = text
//        tv.setTextColor(Color.BLACK)
//        tv.id = id
//        return tv
//    }

    fun createET (hint : String, id : Int) : EditText {
        val et = EditText(this)
        et.setText("")
        et.hint = hint
        et.setTextColor(Color.BLACK)
        et.isFocusedByDefault = false
        et.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        et.setAutoSizeTextTypeUniformWithConfiguration(5, 15, 1, TypedValue.COMPLEX_UNIT_IN)
        et.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        et.id = id
        return et
    }
    fun <K, V> getKey(map: Map<K, V>, target: V): K? {
        for ((key, value) in map) {
            if (target == value) {
                return key
            }
        }
        return null
    }

    fun solveTask(position : Int, map : MutableMap<String, String>) : String {
        var variables = mutableMapOf<String, Double?>()
        for (i in map.keys) {
            if (map[i] != "?") variables.put(i, map[i]!!.toDoubleOrNull())
            if (variables[i] == null) return "Неверно введены переменные"
        }
        when (position) {
            0 -> return "Error"
            1 -> {
                var m = variables["m"]
                var V1 = variables["V1"]
                var V2 = variables["V2"]
                //var V3 = variables["V3"]
                var h1 = variables["h1"]
                var h2 = variables["h2"]
                //var h3 = variables["h3"]
                var P1 = variables["П1"]
                var P2 = variables["П2"]
                //var P3 = variables["П3"]
                var K1 = variables["K1"]
                var K2 = variables["K2"]
                //var K3 = variables["K3"]
                var search = getKey(map, "?")

                when (search) {
                    "V1" -> {
                        try {
                            K2 = m!! * V2!! * V2 /2
                        }
                        catch (e : NullPointerException) {}
                        try {
                            P1 = m!! * 10 * h1!!
                        }
                        catch (e : NullPointerException) {}
                        try {
                            P2 = m!! * 10 * h2!!
                        }
                        catch (e : NullPointerException) {}
                        try {
                            V1 = Math.sqrt(2 * (K2!! + P2!! - P1!!) / m!!)
                        }
                        catch (e : NullPointerException) {V1 = Double.NaN}
                        return "V1 = " + V1.toString()
                    }
                    "V2" -> {
                        try {
                            K1 = m!! * V1!! * V1 / 2
                        }
                        catch (e : NullPointerException) {}
                        try {
                            P1 = m!! * 10 * h1!!
                        }
                        catch (e : NullPointerException) {}
                        try {
                            P2 = m!! * 10 * h2!!
                        }
                        catch (e : NullPointerException) {}
                        try {
                            V2 = Math.sqrt(2 * (K1!! + P1!! - P2!!) / m!!)
                        }
                        catch (e : NullPointerException) {V2 = Double.NaN}
                        return "V2 = " + V2.toString()
                    }
                    "h1" -> {
                        try {
                            K1 = m!! * V1!! * V1 / 2
                        }
                        catch (e : NullPointerException) {}
                        try {
                            K2 = m!! * V2!! * V2 / 2
                        }
                        catch (e : NullPointerException) {}
                        try {
                            P2 = m!! * 10 * h2!!
                        }
                        catch (e : NullPointerException) {}
                        try {
                            h1 = (K2!! + P2!! - K1!!) / (10 * m!!)
                        }
                        catch (e : NullPointerException) {h1 = Double.NaN}
                        return "h1 = " + h1.toString()
                    }
                    "h2" -> {
                        try {
                            K1 = m!! * V1!! * V1 / 2
                        }
                        catch (e : NullPointerException) {}
                        try {
                            K2 = m!! * V2!! * V2 / 2
                        }
                        catch (e : NullPointerException) {}
                        try {
                            P1 = m!! * 10 * h1!!
                        }
                        catch (e : NullPointerException) {}
                        try {
                            h2 = (K1!! + P1!! - K2!!) / (10 * m!!)
                        }
                        catch (e : NullPointerException) {h1 = Double.NaN}
                        return "h1 = " + h1.toString()}
                    "P1" -> {return "prikol"}
                    "P2" -> {return "prikol"}
                    "K1" -> {return "prikol"}
                    "K2" -> {return "prikol"}
                    "m" -> {return "prikol"}
                }


            }











        }

        return ""
    }

}