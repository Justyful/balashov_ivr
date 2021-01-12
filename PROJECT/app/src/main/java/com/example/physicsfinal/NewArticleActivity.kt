package com.example.physicsfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_new_article.*

class NewArticleActivity : AppCompatActivity() {
    var defaultSection : String? = null
    var sectionKey : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_article)
        setSupportActionBar(toolbar as Toolbar?)
        title = "Создание новой статьи"
        defaultSection = intent.getStringExtra("section_name")
        val sections = (application as PhysicsFINAL).db!!.sections.minus("Избранное")
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sections)
        spinner.adapter = spinnerAdapter
        spinner.setSelection(spinnerAdapter.getPosition(defaultSection))
        articleText.isVerticalScrollBarEnabled = true
        articleName.isVerticalScrollBarEnabled = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.new_article, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.check) {
            val newSectionName = spinner.selectedItem.toString()
            val newSectionKey = (application as PhysicsFINAL).db!!.getKeyByName(newSectionName)
            val newName = articleName.text.toString()
            val newText = articleText.text.toString()
            if ((application as PhysicsFINAL).db!!.addArticle(newSectionKey!!, newName, newText) == false) {
                Toast.makeText(this, "Статья с таким именем уже существует", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, ArticlesListActivity::class.java)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}