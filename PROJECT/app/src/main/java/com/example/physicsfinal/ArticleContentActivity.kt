package com.example.physicsfinal

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_article_content.*
import kotlinx.android.synthetic.main.activity_articles_list.toolbar
import kotlinx.android.synthetic.main.move_article.view.*

class ArticleContentActivity : AppCompatActivity() {
    var articleName : String? = null
    var sections : ArrayList<String>? = null
    var sectionKey : String? = null
    var sectionName : String? = null
    var articleText : String? = null
    var isFav : Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_content)
        val trans = Transliterator()
        val adsfasdf = (application as PhysicsFINAL).getDB()
        articleName = intent.getStringExtra("article_name")
        val sectionsTemp = (application as PhysicsFINAL).db!!.sections.minus("Избранное").toList()
        sections = ArrayList(sectionsTemp)
        isFav = intent.getBooleanExtra("is_fav", false)
        if (isFav!!) {
            sectionKey = "favorites"
            sectionName = "Избранное"
        }
        else {
            sectionKey = (application as PhysicsFINAL).db!!.getSectionByArticle(articleName!!)
            sectionName = (application as PhysicsFINAL).db!!.getNameByKey(sectionKey!!)
        }
        setSupportActionBar(toolbar as Toolbar?)
        title = articleName
        articleText = (application as PhysicsFINAL).db!!.textByKey[trans.transliterateRUtoEN(articleName!!)]
        textView.text = articleText
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        if (isFav!!) {
            inflater.inflate(R.menu.article_in_fav, menu)
        }
        else {
            inflater.inflate(R.menu.article_menu, menu)
            if ((application as PhysicsFINAL).db!!.isInFavs(articleName!!)) {
                menu!!.findItem(R.id.star_empty).isVisible = false
            } else {
                menu!!.findItem(R.id.star_colored).isVisible = false
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.move -> moveDialog()
            R.id.edit -> editArticle()
            R.id.star_empty -> {
                addToFavs()
                invalidateOptionsMenu()}
            R.id.star_colored -> {
                removeFromaFavs()
                invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if ((application as PhysicsFINAL).db!!.isInFavs(articleName!!)) {
            menu!!.findItem(R.id.star_empty).isVisible = false
            menu!!.findItem(R.id.star_colored).isVisible = true
        }
        else {
            menu!!.findItem(R.id.star_empty).isVisible = true
            menu!!.findItem(R.id.star_colored).isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        (application as PhysicsFINAL).db!!.saveData()
    }

    fun moveDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.move_article, null)
        builder.setView(view)
        builder.setTitle("Поместить статью в раздел")
        val spinnerAdapter = sections?.let { ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, it) }
        view.sectionMove.adapter = spinnerAdapter
        view.sectionMove.setSelection(sections!!.indexOf(sectionName))
        builder.setPositiveButton("Ок") { dialog, which ->
            val newSectionName = view.findViewById<Spinner>(R.id.sectionMove).selectedItem.toString()
            val newSectionKey = (application as PhysicsFINAL).db!!.getKeyByName(newSectionName)
            if ((application as PhysicsFINAL).db!!.moveArticle(articleName!!, newSectionKey) == false) {
                Toast.makeText(this, "Статья уже в этом разделе", Toast.LENGTH_SHORT).show()
            }
            else {
                finish()
            }
        }
        builder.setNegativeButton("Отмена", {dialog, which ->  })
        builder.show()
    }

    fun editArticle() {
        val intent = Intent(this, EditArticleActivity::class.java)
        intent.putExtra("section_name", sectionName)
        intent.putExtra("section_key", sectionKey)
        intent.putExtra("article_name", articleName)
        intent.putExtra("article_text", articleText)
        startActivity(intent)
    }

    fun addToFavs() {
        (application as PhysicsFINAL).db!!.addToFavs(articleName!!)
        Toast.makeText(this, "Статья добавлена в избранное", Toast.LENGTH_SHORT).show()
    }

    fun removeFromaFavs() {
        (application as PhysicsFINAL).db!!.removeFromFavs(articleName!!)
        Toast.makeText(this, "Статья удалена из избранного", Toast.LENGTH_SHORT).show()
    }
}