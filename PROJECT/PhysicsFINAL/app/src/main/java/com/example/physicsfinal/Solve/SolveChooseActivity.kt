package com.example.physicsfinal.Solve

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import com.example.physicsfinal.MainActivity
import com.example.physicsfinal.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_solve_choose.*

class SolveChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solve_choose)

        setSupportActionBar(toolbar as Toolbar)
        title = "Решение задач"
        val solveList = arrayListOf<String>(
            "Сободное падение тела, закон сохранения энергии",
            "fwaefawefwe",
            "bxzcvbxcv")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solveList)
        solve_list_view.adapter = adapter
        solve_list_view.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, SolverActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("solveList", solveList)
            startActivity(intent)
        }

    }
}