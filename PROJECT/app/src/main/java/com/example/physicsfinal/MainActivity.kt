package com.example.physicsfinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bd = (application as PhysicsFINAL).getDB()
        guideButton.setOnClickListener {
            val intent = Intent(this, SectionsListActivity::class.java)
            startActivity(intent)
        }
        calcButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }
        graphButton.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }
        setSupportActionBar(toolbar as Toolbar?)
        title = "MyPhysics"

    }

    override fun onPause() {
        super.onPause()
        (application as PhysicsFINAL).getDB()!!.saveData()

    }
}

//
//class Evaluate (val expr : String) {
//    var position = -1
//    var char = ""
//
//    fun nextChar() {
//        char = if (++position < expr.length) expr[position].toString() else "-1"
//    }
//
//    fun isNext (ch : String) : Boolean {
//        while (char == "") nextChar()
//        if (ch == char) {
//            nextChar()
//            return true
//        }
//        else return false
//    }
//
//    fun eval() : Double {
//
//    }
//}

//var `val`: String = tvmain.getText().toString()
//var replacedstr = `val`.replace('÷', '/').replace('×', '*')
//var result: Double = MainActivity.eval(replacedstr)
//tvmain.setText(result.toString())
//tvsec.setText(`val`)