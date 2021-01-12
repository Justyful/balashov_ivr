package com.example.physicsfinal;

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_articles_list.toolbar
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.const_choose.view.*
import kotlinx.android.synthetic.main.power_choose.view.*

class GraphActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    var isPower : Boolean = false
    var n : String = ""
    var isCos : Boolean = false
    var isSin : Boolean = false
//    var isTan : Boolean = false
//    var isCtg : Boolean = false
    var isSqrt : Boolean = false
    var isConst : Boolean = false
    var constant : String = ""
    var sign : String = ""
    var funArray = ArrayList<String>()
    var algNum : Int = 0
    var isBefore : Boolean = false
    var constD : Boolean = false
    var powerD : Boolean = false
    var constvalI : Int? = 0
    var constvalD : Double? = 0.0
    var powerValI :Int? = 0
    var powerValD :Double? = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        setSupportActionBar(toolbar as Toolbar)
        val alphabet = "abcdefghijklmnopqrstuvwxyz!№;$%:^?&*/".toCharArray()
//        var isPower : Boolean = false
//        var n : String = ""
//        var isCos : Boolean = false
//        var isSin : Boolean = false
//        var isTan : Boolean = false
//        var isCtg : Boolean = false
//        var isSqrt : Boolean = false
//        var isConst : Boolean = false
//        var constant : String = ""
//        var sign : String = ""
//        var funArray = ArrayList<String>()
//        var executionArray = ArrayList<String>()
        title = "Построение графиков"
        power.setOnClickListener {
            if (!isPower) {
                val builder = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val view = inflater.inflate(R.layout.power_choose, null)
                builder.setView(view)
                builder.setTitle("Возведение в степень")
                builder.setNegativeButton("Отмена") { dialog, which -> }
                builder.setPositiveButton("Ок") { dialog, which ->
                    val text = view.value.text.toString()
                    if (text[0] == '0') { Toast.makeText(this, "Значение не может начинаться с нуля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    for (i in text) {
                        if (alphabet.contains(i)) { Toast.makeText(this, "Значение не может содержать буквы", Toast.LENGTH_SHORT).show()
                            return@setPositiveButton
                        }
                    }
                    n = view.value.text.toString()
                    isPower = true
                    funArray.add("power")
                    function.text = function.text.toString().replace("x", "x^$n")
                }
                builder.show()
            }
            else {
                isPower = false
                function.text = function.text.toString().replace("x^$n", "x")
                funArray.remove("power")
                n = ""
            }
        }

        constantB.setOnClickListener {
            if (!isConst) {
                val builder = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val view = inflater.inflate(R.layout.const_choose, null)
                val signs = ArrayList<String>()
                signs.add("+")
                signs.add("-")
                builder.setView(view)
                builder.setTitle("Добавление константы")
                val signsAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, signs)
                view.constant_sign.adapter = signsAdapter
                builder.setNegativeButton("Отмена") { dialog, which -> }
                builder.setPositiveButton("Ок") { dialog, which ->
                    val text = view.constant_value.text.toString()
                    if (text[0] == '0') {
                        Toast.makeText(this, "Значение не может начинаться с нуля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    if (text == "0") {
                        Toast.makeText(this, "Значение должно отличаться от нуля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    else {
                        isConst = true
                        constant = text
                        funArray.add("const")
                        sign = view.findViewById<Spinner>(R.id.constant_sign).selectedItem.toString()
                        function.text = function.text.toString() + " " + sign + " " + constant
                    }
                }
                builder.show()
            }
            else {
                isConst = false
                function.text = function.text.toString().replace(" $sign $constant", "")
                constant = ""
                sign = ""
                funArray.remove("const")
            }
        }

        sqrt.setOnClickListener {
            if (!isSqrt) {
                isSqrt = true
                funArray.add("sqrt")
                function.text = function.text.toString().replace("x", "√x")
            }
            else {
                isSqrt = false
                funArray.remove("sqrt")
                function.text = function.text.toString().replace("√x", "x")
            }
        }

        cos.setOnClickListener {
            if (!isCos) {
                isCos = true
                funArray.add("cos")
                function.text = function.text.toString().replace("y = ", "y = cos(") + ")"
            }
            else {
                isCos = false
                funArray.remove("cos")
                function.text = function.text.toString().replace("cos(", "").replaceFirst(")", "")
            }
        }

        sin.setOnClickListener {
            if (!isSin) {
                isSin = true
                funArray.add("sin")
                function.text = function.text.toString().replace("y = ", "y = sin(") + ")"
            }
            else {
                isSin = false
                funArray.remove("sin")
                function.text = function.text.toString().replace("sin(", "").replaceFirst(")", "")
            }
        }

//        tan.setOnClickListener {
//            if (!isTan) {
//                isTan = true
//                funArray.add("tan")
//                function.text = function.text.toString().replace("y = ", "y = tan(") + ")"
//            }
//            else {
//                isTan = false
//                funArray.remove("tan")
//                function.text = function.text.toString().replace("tan(", "").replaceFirst(")", "")
//            }
//        }
//
//        ctg.setOnClickListener {
//            if (!isCtg) {
//                isCtg = true
//                funArray.add("ctg")
//                function.text = function.text.toString().replace("y = ", "y = ctg(") + ")"
//            }
//            else {
//                isCtg = false
//                funArray.remove("ctg")
//                function.text = function.text.toString().replace("ctg(", "").replaceFirst(")", "")
//            }
//        }

        start.setOnClickListener {
            val intent = Intent(this, GraphViewActivity::class.java)
            algNum = createAlg()
            intent.putExtra("alg", algNum)
            intent.putExtra("constD", constD)
            intent.putExtra("powerD", powerD)
            intent.putExtra("constValD", constvalD)
            intent.putExtra("constValI", constvalI)
            intent.putExtra("powerValD", powerValD)
            intent.putExtra("powerValI", powerValI)
            val asdf = 1234
            startActivity(intent)
        }



    }

    fun createAlg() : Int {
        if (isConst) {
            if (constant.contains(".")) { constvalD = constant.toDoubleOrNull(); constD = true }
            else { constvalI = constant.toIntOrNull() }
        }
        else constvalI = 0

        if (sign != "+" && isConst) {
            if (constD) constvalD = -constvalD!!
            else constvalI = -constvalI!!
        }


        if (isPower) {
            if (n.contains(".")) { powerValD = n.toDoubleOrNull(); powerD = true }
            else { powerValI = n.toIntOrNull() }
        }
        else powerValI = 0

        if ((funArray.contains("const")) && (funArray.contains("sin") || funArray.contains("cos") || funArray.contains("tan") || funArray.contains("ctg"))) {
            if (funArray.indexOf("const") == 0) isBefore = true
            else {
                if (funArray.indexOf("const") == 1) {
                    if (funArray.indexOf("sin") == 0 || funArray.indexOf("cos") == 0) isBefore =
                        false
                    else isBefore = true
                }
            }
        }

        if (isSin == isCos == true) {
            when (isBefore) {
                true -> {
                    if (funArray.indexOf("sin") < funArray.indexOf("cos")) return 2
                    else return 4
                }
                false -> {
                    if (funArray.indexOf("sin") < funArray.indexOf("cos")) return 6
                    else return 8
                }
            }
        }

        if (isSin || !isCos) {
            when (isBefore) {
                true -> return 3
                false -> return 7
            }
        }

        if (!isSin || isCos) {
            when (isBefore) {
                true -> return 5
                false -> return 9
            }
        }

        return 1
    }

}