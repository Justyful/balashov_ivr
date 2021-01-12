package com.example.physicsfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_new_article.*

class EditArticleActivity : AppCompatActivity() {
    var oldName : String? = null
    var sectionKey : String? = null
    var sectionName : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_article)
        setSupportActionBar(toolbar as Toolbar?)
        title = "Редактирование статьи"
        oldName = intent.getStringExtra("article_name")
        sectionKey = intent.getStringExtra("section_key")
        sectionName = intent.getStringExtra("section_name")
        val oldText = intent.getStringExtra("article_text")
        articleName.setText(oldName)
        articleText.setText(oldText)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.new_article, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.check -> saveNewArticle()
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveNewArticle() {
        (application as PhysicsFINAL).db!!.changeTextName(oldName!!, articleName.text.toString(), sectionKey!!)
        (application as PhysicsFINAL).db!!.changeText(articleName.text.toString(), articleText.text.toString())
        (application as PhysicsFINAL).db!!.saveData()
        val intent = Intent(this, ArticlesListActivity::class.java)
        intent.putExtra("section_key", sectionKey)
        intent.putExtra("section_name", sectionName)
        startActivity(intent)
    }
}