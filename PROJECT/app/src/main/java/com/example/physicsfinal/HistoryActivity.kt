package com.example.physicsfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    var allArticles : ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar as Toolbar?)
        title = "История поиска"
        articlesView.visibility = View.GONE
        val asdfasdf = (application as PhysicsFINAL).getDB()
        allArticles = ArrayList()
        val historyList = (application as PhysicsFINAL).db!!.listByKey["search_history"]
        if (intent.getStringExtra("section_key") != "") {
            allArticles = (application as PhysicsFINAL).db!!.listByKey[intent.getStringExtra("section_key")!!]
        }
        else {
            allArticles!!.addAll((application as PhysicsFINAL).db!!.getAllArticles())
        }
        val showArticles = ArrayList<String>()

        val historyAdapter = historyList?.let { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, it) }
        val articlesAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, showArticles)

        historyView.adapter = historyAdapter
        articlesView.adapter = articlesAdapter

        historyView.setOnItemClickListener { parent, view, position, id ->
            allArticles!!.forEach { if (it.toLowerCase().contains(historyList!![position])) {showArticles.add(it)} }
            articlesAdapter.notifyDataSetChanged()
            switchVisibility()
        }

        articlesView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ArticleContentActivity::class.java)
            intent.putExtra("article_name", showArticles.get(position))
            startActivity(intent)
        }
    }

    fun switchVisibility() {
        if (historyView.visibility == View.GONE) {
            historyView.visibility = View.VISIBLE
            articlesView.visibility = View.GONE
        }
        else {
            historyView.visibility = View.GONE
            articlesView.visibility = View.VISIBLE
        }
    }
}