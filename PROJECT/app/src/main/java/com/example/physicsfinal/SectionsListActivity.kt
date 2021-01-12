package com.example.physicsfinal

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_sections_list.*

class SectionsListActivity : AppCompatActivity() {
    var sections : ArrayList<String>? = null
    var sectionsKeys : ArrayList<String>? = null
    var allArticles : MutableList<String>? = null

    var sectionsAdapter : ArrayAdapter<String>? = null
    var articlesAdapter : ArrayAdapter<String>? = null

    val context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections_list)
        setSupportActionBar(toolbar as Toolbar?)
        title = "Справочник"
        sections = (application as PhysicsFINAL).db!!.sections
        sectionsKeys = (application as PhysicsFINAL).db!!.sections_keys
        allArticles = (application as PhysicsFINAL).db!!.getAllArticles()
        val asdfasdf = (application as PhysicsFINAL).getDB()

        sectionsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sections!!)
        articlesAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allArticles!!)

        sectionsView.adapter = sectionsAdapter
        sectionsView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ArticlesListActivity::class.java)
            val position = sections!!.indexOf(sectionsAdapter!!.getItem(position))
            if (sectionsAdapter!!.getItem(position) == "Избранное") {
                intent.putExtra("section_name", "Избранное")
                intent.putExtra("section_key", "favorites")
                intent.putExtra("is_fav", true)
                startActivity(intent)
            }
            else {
                intent.putExtra("section_name", sections!![position])
                intent.putExtra("section_key", sectionsKeys!![position])
                intent.putExtra("is_fav", false)
                startActivity(intent)
            }
        }

        articlesView.adapter = articlesAdapter
        articlesView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ArticleContentActivity::class.java)
            val articleName = articlesAdapter!!.getItem(position)
            intent.putExtra("article_name", articleName)
            (application as PhysicsFINAL).db!!.saveHistory()
            if ((application as PhysicsFINAL).db!!.searchHistory.size > 10) {(application as PhysicsFINAL).db!!.searchHistory.removeAt(10)}
            startActivity(intent)
        }


    }

    fun switchVisibility() {
        if (sectionsView.visibility == View.VISIBLE) {
            sectionsView.visibility = View.GONE
            articlesView.visibility = View.VISIBLE
        }
        else {
            sectionsView.visibility = View.VISIBLE
            articlesView.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.sections_menu, menu)
        val searchManager : SearchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu!!.findItem(R.id.search)
        var searchView : SearchView? = null
        if (searchItem != null) {searchView = searchItem.actionView as SearchView? }
        if (searchView != null) {searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))}

        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                switchVisibility()
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                switchVisibility()
                return true
            }
        })

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                allArticles!!.clear()
                val tempList = (application as PhysicsFINAL).db!!.getAllArticles()
                if (query == null) return false
                val search = query.toLowerCase().trim()
                tempList.forEach {
                    if (it.toLowerCase().contains(search)) {allArticles!!.add(it)}
                }
                articlesAdapter!!.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null) return true
                val tempList = (application as PhysicsFINAL).db!!.getAllArticles()
                if (newText.isEmpty()) {
                    allArticles!!.clear()
                    allArticles!!.addAll(tempList)
                    articlesAdapter!!.notifyDataSetChanged()
                }
                else {
                    allArticles!!.clear()
                    val search = newText.toLowerCase().trim()
                    tempList.forEach {
                        if (it.toLowerCase().contains(search)) {
                            allArticles!!.add(it)
                        }
                    }
                    articlesAdapter!!.notifyDataSetChanged()
                }
                (application as PhysicsFINAL).db!!.lastSearch = newText.toLowerCase()
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchHistory -> {
                val intent = Intent(context, HistoryActivity::class.java)
                intent.putExtra("section_key", "")
                startActivity(intent)
            }

            R.id.addSection -> openDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        (application as PhysicsFINAL).db!!.saveData()
    }

    fun openDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.new_section, null)
        builder.setView(view)
        builder.setTitle("Новый раздел")
        val newSectionView = view.findViewById<EditText>(R.id.newSectionName)
        builder.setNegativeButton("Отмена") { dialog, which ->  }
        builder.setPositiveButton("Создать") { dialog, which ->
            val newSectionName = newSectionView.text.toString()
            if((application as PhysicsFINAL).db!!.listByKey[newSectionName] == null) {
            (application as PhysicsFINAL).db!!.addSection(newSectionName)
            sectionsAdapter!!.notifyDataSetChanged() }
            else { Toast.makeText(this, "Такой раздел уже существует", Toast.LENGTH_SHORT).show() }
        }
        builder.show()
    }
}

