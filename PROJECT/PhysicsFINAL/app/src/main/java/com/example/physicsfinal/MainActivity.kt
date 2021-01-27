package com.example.physicsfinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.physicsfinal.Calculator.CalculatorActivity
import com.example.physicsfinal.Graph.GraphActivity
import com.example.physicsfinal.Guide.DataBase
import com.example.physicsfinal.Guide.SectionsListActivity
import com.example.physicsfinal.Solve.SolveChooseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bd = (application as MyPhysics).getDB()
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
        solveButton.setOnClickListener {
            val intent = Intent(this, SolveChooseActivity::class.java)
            startActivity(intent)
        }
        setSupportActionBar(toolbar as Toolbar?)
        title = "MyPhysics"
        val asdf = (application as MyPhysics).getDB()
        val asdfdd = 1234

    }

    override fun onPause() {
        super.onPause()
        (application as MyPhysics).getDB()!!.saveData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset_db)  {
            (application as MyPhysics).db!!.restart()
            (application as MyPhysics).db!!.editor.clear()
            (application as MyPhysics).db!!.editor.commit()
            (application as MyPhysics).db!!.editor.apply()
            (application as MyPhysics).db = DataBase(this)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
            if (this is Activity) {
                (this as Activity).finish()
            }
            Runtime.getRuntime().exit(0)
        }
        return super.onOptionsItemSelected(item)
    }
}