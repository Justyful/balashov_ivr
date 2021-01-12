package com.example.physicsfinal

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import kotlinx.android.synthetic.main.activity_articles_list.*
import java.util.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList

class ArticlesListActivity : AppCompatActivity() {
    var articlesList : ArrayList<String>? = null
    var articlesAdapter : ArrayAdapter<String>? = null
    var sectionKey : String? = null
    var section : String? = null
    var isFav : Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_list)
        setSupportActionBar(toolbar as Toolbar?)
        isFav = intent.getBooleanExtra("is_fav", false)
        section = intent.getStringExtra("section_name")
        title = section
        sectionKey = intent.getStringExtra("section_key")
        //val sections = (application as PhysicsFINAL).db!!.sections
        //val sectionsKeys = (application as PhysicsFINAL).db!!.sections_keys
        articlesList = (application as PhysicsFINAL).db!!.listByKey[sectionKey]

        articlesAdapter = articlesList?.let { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, it) }
        articlesView.adapter = articlesAdapter

        articlesView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ArticleContentActivity::class.java)
            val articleName = articlesAdapter!!.getItem(position)
            intent.putExtra("article_name", articleName)
            intent.putExtra("is_fav", isFav!!)
            (application as PhysicsFINAL).db!!.saveHistory()
            startActivity(intent)
        }
    }

    override fun onPostResume() {
        articlesAdapter!!.notifyDataSetChanged()
        super.onPostResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        if (isFav!!) inflater.inflate(R.menu.fav_menu, menu)
        else inflater.inflate(R.menu.sections_menu, menu)
        val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu!!.findItem(R.id.search)
        var searchView : SearchView? = null
        if (searchItem != null) {searchView = searchItem.actionView as SearchView?}
        if (searchView != null) {searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))}

        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                articlesAdapter!!.filter.filter("")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                articlesAdapter!!.filter.filter("")       
                return true
            }
        })
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                articlesAdapter!!.filter.filter(query!!.toLowerCase().trim())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                articlesAdapter!!.filter.filter(newText!!.toLowerCase().trim())
                (application as PhysicsFINAL).db!!.lastSearch = newText.toString().toLowerCase()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchHistory -> {
                val intent = Intent(this, HistoryActivity::class.java)
                intent.putExtra("section_key", sectionKey)
                startActivity(intent)
            }
            R.id.addSection -> addSection()
        }
        return super.onOptionsItemSelected(item)
    }

    fun addSection() {
        val intent = Intent(this, NewArticleActivity::class.java)
        intent.putExtra("section_name", section)
        intent.putExtra("section_key", sectionKey)
        startActivity(intent)
        return
    }

    override fun onPause() {
        super.onPause()
        (application as PhysicsFINAL).db!!.saveData()
    }

    override fun onResume() {
        articlesAdapter!!.notifyDataSetChanged()
        super.onResume()
    }
}