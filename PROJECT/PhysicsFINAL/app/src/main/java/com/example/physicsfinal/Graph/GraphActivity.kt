package com.example.physicsfinal.Graph

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.physicsfinal.R
import kotlinx.android.synthetic.main.activity_articles_list.toolbar
import kotlinx.android.synthetic.main.activity_graph.*
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
    var signConst : String = ""
    var signPower : String = ""
    var funArray = ArrayList<String>()
    var algNum : Int = 0
    var isBefore : Boolean = false
    var constD : Boolean = false
    var powerD : Boolean = false
    var constValI : Int? = 0
    var constValD : Double? = 0.0
    var powerValI :Int? = 1
    var powerValD :Double? = 1.0
    var sqrt : Double = 0.0
    var graphArray1 = arrayOf<Double>(0.0, 0.0, 0.0, 1.0, 1.0, 0.0)
    var graphArray2 = arrayOf<Double>(0.0, 0.0, 0.0, 1.0, 1.0, 0.0)
    var graphArray3 = arrayOf<Double>(0.0, 0.0, 0.0, 1.0, 1.0, 0.0)
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

        var function = findViewById<TextView>(R.id.function1)

        new_graph1.setOnClickListener {
            function = findViewById(R.id.function2)
            function2.visibility = View.VISIBLE
            new_graph1.visibility = View.GONE
            new_graph2.visibility = View.VISIBLE
            function1.setTextColor(Color.rgb(120, 120, 120))
            graphArray1[0] = createAlg().toDouble()
            graphArray1[1] = (constValD!!)
            graphArray1[2] = (constValI!!.toDouble())
            graphArray1[3] = (powerValD!!)
            graphArray1[4] = (powerValI!!.toDouble())
            graphArray1[5] = (sqrt)
            reset()
        }

        new_graph2.setOnClickListener {
            function = findViewById(R.id.function3)
            function3.visibility = View.VISIBLE
            new_graph2.visibility = View.GONE
            function2.setTextColor(Color.rgb(120, 120, 120))
            graphArray2[0] = createAlg().toDouble()
            graphArray2[1] = (constValD!!)
            graphArray2[2] = (constValI!!.toDouble())
            graphArray2[3] = (powerValD!!)
            graphArray2[4] = (powerValI!!.toDouble())
            graphArray2[5] = (sqrt)
            reset()
        }

        clear_button.setOnClickListener {
            function = findViewById(R.id.function1)
            function2.visibility = View.GONE
            function3.visibility = View.GONE
            function1.setTextColor(Color.rgb(0, 0, 0))
            function2.setTextColor(Color.rgb(0, 0, 0))
            function3.setTextColor(Color.rgb(0, 0, 0))
            new_graph1.visibility = View.VISIBLE
            new_graph2.visibility = View.GONE
            function1.text = "y = x"
            function2.text = "y = x"
            function3.text = "y = x"
            reset()
            resetArrays()
        }

        title = "Построение графиков"

        power.setOnClickListener {
            if (!isPower) {
                val builder = AlertDialog.Builder(this)
                val inflater = this.layoutInflater
                val view = inflater.inflate(R.layout.power_choose, null)
                val signs = ArrayList<String>()
                signs.add("+")
                signs.add("-")
                builder.setView(view)
                builder.setTitle("Возведение в степень")
                val signsAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, signs)
                view.power_sign.adapter = signsAdapter
                builder.setNegativeButton("Отмена") { dialog, which -> }
                builder.setPositiveButton("Ок") { dialog, which ->
                    val text = view.power_value.text.toString()
                    if (text == "0") {
                        Toast.makeText(this, "Значение должно отличаться от нуля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    //for (i in text) {
                    //    if (alphabet.contains(i)) { Toast.makeText(this, "Значение не может содержать буквы", Toast.LENGTH_SHORT).show()
                    //        return@setPositiveButton
                    //    }
                    //}
                    n = view.power_value.text.toString()
                    isPower = true
                    signPower = view.findViewById<Spinner>(R.id.power_sign).selectedItem.toString()
//                    funArray.add("power")
                    if (signPower == "+") function.text = function.text.toString().replace("x", "x^⟨$n⟩")
                    else function.text = function.text.toString().replace("x", "x^⟨$signPower$n⟩")
                }
                builder.show()
            }
            else {
                isPower = false
                if (signPower == "+") function.text = function.text.toString().replace("x^⟨$n⟩", "x")
                else function.text = function.text.toString().replace("x^⟨$signPower$n⟩", "x")
//                funArray.remove("power")
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
                    //if (text[0] == '0') {
                    //    Toast.makeText(this, "Значение не может начинаться с нуля", Toast.LENGTH_SHORT).show()
                    //    return@setPositiveButton
                    //}
                    if (text == "0") {
                        Toast.makeText(this, "Значение должно отличаться от нуля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    else {
                        isConst = true
                        constant = text
                        funArray.add("const")
                        signConst = view.findViewById<Spinner>(R.id.constant_sign).selectedItem.toString()
                        function.text = function.text.toString() + " " + signConst + " " + constant
                    }
                }
                builder.show()
            }
            else {
                isConst = false
                function.text = function.text.toString().replace(" $signConst $constant", "")
                constant = ""
                signConst = ""
                funArray.remove("const")
            }
        }

        sqrt_btn.setOnClickListener {
            if (!isSqrt) {
                isSqrt = true
                sqrt = 1.0
//                funArray.add("sqrt")
                function.text = function.text.toString().replace("x", "√x")
            }
            else {
                isSqrt = false
                sqrt = 0.0
//                funArray.remove("sqrt")
                function.text = function.text.toString().replace("√x", "x")
            }
        }

        cos.setOnClickListener {
            if (!isCos) {
                if (isSin) {
                    isCos = true
                    funArray.add("cos")
                    function.text = function.text.toString().replace("y = ", "y = cos(").replace(")", "))")
                }
                else {
                    isCos = true
                    funArray.add("cos")
                    function.text = function.text.toString().replace("y = ", "y = cos(") + ")"
                }

            }
            else {
                isCos = false
                funArray.remove("cos")
                function.text = function.text.toString().replace("cos(", "").replaceFirst(")", "")
            }
        }

        sin.setOnClickListener {
            if (!isSin) {
                if (isCos) {
                    isSin = true
                    funArray.add("sin")
                    function.text = function.text.toString().replace("y = ", "y = sin(").replace(")", "))")
                }
                else {
                    isSin = true
                    funArray.add("sin")
                    function.text = function.text.toString().replace("y = ", "y = sin(") + ")"
                }

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
            if (function2.visibility == View.GONE) {
                graphArray1[0] = createAlg().toDouble()
                graphArray1[1] = (constValD!!)
                graphArray1[2] = (constValI!!.toDouble())
                graphArray1[3] = (powerValD!!)
                graphArray1[4] = (powerValI!!.toDouble())
                graphArray1[5] = (sqrt)
            }

            if (function2.visibility == View.VISIBLE && function3.visibility == View.GONE) {
                graphArray2[0] = createAlg().toDouble()
                graphArray2[1] = (constValD!!)
                graphArray2[2] = (constValI!!.toDouble())
                graphArray2[3] = (powerValD!!)
                graphArray2[4] = (powerValI!!.toDouble())
                graphArray2[5] = (sqrt)
            }

            if (function3.visibility == View.VISIBLE) {
                graphArray3[0] = createAlg().toDouble()
                graphArray3[1] = (constValD!!)
                graphArray3[2] = (constValI!!.toDouble())
                graphArray3[3] = (powerValD!!)
                graphArray3[4] = (powerValI!!.toDouble())
                graphArray3[5] = (sqrt)
            }
            intent.putExtra("10", graphArray1[0])
            intent.putExtra("11", graphArray1[1])
            intent.putExtra("12", graphArray1[2])
            intent.putExtra("13", graphArray1[3])
            intent.putExtra("14", graphArray1[4])
            intent.putExtra("15", graphArray1[5])

            intent.putExtra("20", graphArray2[0])
            intent.putExtra("21", graphArray2[1])
            intent.putExtra("22", graphArray2[2])
            intent.putExtra("23", graphArray2[3])
            intent.putExtra("24", graphArray2[4])
            intent.putExtra("25", graphArray2[5])

            intent.putExtra("30", graphArray3[0])
            intent.putExtra("31", graphArray3[1])
            intent.putExtra("32", graphArray3[2])
            intent.putExtra("33", graphArray3[3])
            intent.putExtra("34", graphArray3[4])
            intent.putExtra("35", graphArray3[5])

            startActivity(intent)
        }



    }

    fun createAlg() : Int {
        if (isConst) {
            if (constant.contains(".")) { constValD = constant.toDoubleOrNull(); constD = true }
            else { constValI = constant.toIntOrNull() }
        }
        else constValI = 0

        if (signConst != "+" && isConst) {
            if (constD) constValD = -constValD!!
            else constValI = -constValI!!
        }


        if (isPower) {
            if (n.contains(".")) { powerValD = n.toDoubleOrNull(); powerD = true }
            else { powerValI = n.toIntOrNull() }
        }
        else powerValI = 1

        if (signPower != "+" && isPower) {
            if (powerD) powerValD = -powerValD!!
            else powerValI = -powerValI!!
        }



//        if (funArray.contains("const") && (funArray.contains("sin") || funArray.contains("cos") || funArray.contains("tan") || funArray.contains("ctg"))) {
//            if (funArray.indexOf("const") == 0) isBefore = true
//            else {
//                if (funArray.indexOf("const") == 1) {
//                    if (funArray.indexOf("sin") == 0 || funArray.indexOf("cos") == 0) isBefore = false
//                    else isBefore = true
//                }
//            }
//        }
        isBefore = funArray.indexOf("const") == 0

        if (!isSin && !isCos) return 1

        if (isSin && isCos) {
            return when (isBefore) {
                true -> {
                    if (funArray.indexOf("sin") < funArray.indexOf("cos")) 2
                    else 4
                }
                false -> {
                    if (funArray.indexOf("sin") < funArray.indexOf("cos")) 6
                    else 8
                }
            }
        }

        if (isSin || !isCos) {
            return when (isBefore) {
                true -> 3
                false -> 7
            }
        }

        if (!isSin || isCos) {
            return when (isBefore) {
                true -> 5
                false -> 9
            }
        }

        return 0
    }

    fun reset() {
        constValD = 0.0
        constValI = 0
        powerValD = 1.0
        powerValI = 1
        sqrt = 0.0
        funArray.clear()
        n = ""
        constant = ""
        isSqrt = false
        isSin = false
        isBefore = false
        isCos = false
        isConst = false
        isPower = false
        constD = false
        powerD = false
    }

    fun resetArrays() {

        graphArray1[0] = 0.0
        graphArray1[1] = (constValD!!)
        graphArray1[2] = (constValI!!.toDouble())
        graphArray1[3] = (powerValD!!)
        graphArray1[4] = (powerValI!!.toDouble())
        graphArray1[5] = (sqrt)

        graphArray2[0] = 0.0
        graphArray2[1] = (constValD!!)
        graphArray2[2] = (constValI!!.toDouble())
        graphArray2[3] = (powerValD!!)
        graphArray2[4] = (powerValI!!.toDouble())
        graphArray2[5] = (sqrt)

        graphArray3[0] = 0.0
        graphArray3[1] = (constValD!!)
        graphArray3[2] = (constValI!!.toDouble())
        graphArray3[3] = (powerValD!!)
        graphArray3[4] = (powerValI!!.toDouble())
        graphArray3[5] = (sqrt)
    }

}



/*
ALG EXPLANATION
0 -> error
1 -> x + с
2 -> cos(sin(x + c))
3 -> sin(x + c)
4 -> sin(cos(x + c)
5 -> cos(x + c)
6 -> cos(sin(x)) + c
7 -> sin(x) + c
8 -> sin(cos(x)) + c
9 -> cos(x) + c
 */