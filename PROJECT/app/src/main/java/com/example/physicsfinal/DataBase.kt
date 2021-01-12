package com.example.physicsfinal

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

class DataBase (val context : Context) {
    val HAS_VISITED = "hasVisited"
    val LISTS_DICT = "lists_dictionary"
    val TEXTS_DICT  = "texts_dictionary"
    val APP_PREFERENCES = "app_preferences"
    val ALL_ARTICLES = "all_articles"

    var listByKey = HashMap<String, ArrayList<String>>()
    var textByKey = HashMap<String, String>()
    var hasVisited : Boolean
    val prefs : SharedPreferences
    val sections = ArrayList<String>()
    val sections_keys = ArrayList<String>()
    val articles_count = 4
    var lastSearch : String = ""
    var searchHistory = ArrayList<String>()
    val trans = Transliterator()


    init {
        prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        hasVisited = prefs.getBoolean(HAS_VISITED, false)
        if (!hasVisited) {
            val editor = prefs.edit()
            editor.putBoolean(HAS_VISITED, true)
            editor.apply()
        }
    }


    fun saveRefs(context: Context, name : String, key: String, Object : Any?) {
        val prefs = context.getSharedPreferences(name, 0)
        val editor = prefs.edit()
        val gson = Gson()
        val jObject = gson.toJson(Object)
        editor.putString(key, jObject)
        editor.apply()
    }

    fun saveData() {
        saveRefs(context, APP_PREFERENCES, LISTS_DICT, listByKey)
        saveRefs(context, APP_PREFERENCES, TEXTS_DICT, textByKey)
    }

    open fun <GenericClass> getRefs (context: Context, name: String?, key: String?, classType: Class<GenericClass>?): GenericClass {
        val prefs = context.getSharedPreferences(name, 0)
        val gson = Gson()
        return gson.fromJson(prefs.getString(key, ""), classType)

    }

    private fun restoreData() {
        listByKey = getRefs(context, APP_PREFERENCES, LISTS_DICT, listByKey.javaClass)
        textByKey = getRefs(context, APP_PREFERENCES, TEXTS_DICT, textByKey.javaClass)
        listByKey.get("sections")?.let { sections.addAll(it) }
        listByKey.get("sections_keys")?.let { sections_keys.addAll(it) }
    }

    private fun importFromResources() {
        sections.addAll(context.resources.getStringArray(R.array.sections))
        listByKey["sections"] = sections
        sections_keys.addAll(context.resources.getStringArray(R.array.sections_keys))
        listByKey["sections_keys"] = sections_keys
        val resids = intArrayOf(
            R.array.mechanics_articles,
            R.array.thermodynamics_articles,
            R.array.electricity_articles,
            R.array.magnetism_articles)
        for (i in resids.indices) {
            val list = ArrayList<String>()
            list.addAll(context.resources.getStringArray(resids[i]))
            listByKey[sections_keys[i]] = list
        }
    }

    fun saveHistory() {
        if (listByKey["search_history"] == null) { listByKey["search_history"] = searchHistory }
        if (searchHistory.isEmpty()) {
            if (!lastSearch.isBlank()) {
                searchHistory.reverse()
                searchHistory.add(lastSearch)
                searchHistory.reverse()
            }
        }
        else {
            if (searchHistory[0] != lastSearch) {
                searchHistory.reverse()
                searchHistory.add(lastSearch)
                searchHistory.reverse()
            }
        }
        if (searchHistory.size > 10) {searchHistory.removeAt(10)}
        listByKey["search_history"] = searchHistory
    }

    fun readRawTextFile(context: Context, id : Int) : String {
        val inputStream = context.resources.openRawResource(id)
        val reader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(reader)
        var line : String
        var builder = StringBuilder()

        try {
            while (bufferedReader.readLine().also {line = it} != null) {
                builder.append(line)
                builder.append("\n")
            }
        }
        catch (e : Exception) {
            return builder.toString()
        }
        return builder.toString()
    }

    fun saveText(text : String, resName : String) {
        textByKey.put(resName, text)
    }

    private fun importFromRaw() {
        for (section_key in sections_keys) {
            for (article_name in listByKey[section_key]!!) {
                val resName = trans.transliterateRUtoEN(article_name)
                val resID = context.resources.getIdentifier(resName, "raw", "com.example.physicsfinal")
                if (resID != null) {
                    val text = readRawTextFile(context, resID)
                    saveText(text, resName)
                }
            }
        }
    }

    fun importFavorites() {
        sections_keys.add("favorites")
        sections.add("Избранное")
        listByKey["favorites"] = ArrayList<String>()
    }


    fun START() {
        if (hasVisited) {
            restoreData()
        }
        else {
            importFromResources()
            importFromRaw()
            importFavorites()
        }
    }

    fun getListsWithoutFavs(): MutableMap<String, ArrayList<String>> {
        val list = listByKey.toMutableMap()
        return list.minus("favorites").toMutableMap()
    }

    fun getAllArticles(): MutableList<String> {
        val allArticles : ArrayList<String> = ArrayList()
        for (i in sections_keys.minus("favorites")) {
            listByKey[i]?.let { allArticles.addAll(it) }
        }
        return allArticles.distinct().toMutableList()
    }
    fun getSectionByArticle(name : String) : String {
        val newList = getListsWithoutFavs()
        for (i in sections_keys.minus("favorites")) {
            if (newList!![i]!!.contains(name)) return i
        }
        return ""
    }

    fun isInFavs(articleName : String) : Boolean {
        return listByKey["favorites"]!!.contains(articleName)
    }

    fun addSection(name: String) {
        listByKey["sections"]!!.add(name)
        listByKey["sections_keys"]!!.add(name)
        val newList = ArrayList<String>()
        listByKey.put(name, newList)
    }

    fun addArticle(newSection: String, newName: String, newText: String) : Boolean {
        if (getAllArticles().contains(newName)) {return false}
        else {
            listByKey[newSection]!!.add(newName)
            textByKey[trans.transliterateRUtoEN(newName)] = newText
            return true
        }
    }

    fun getKeyByName(sectionName : String) : String {
        val sectionID = sections.indexOf(sectionName)
        return sections_keys[sectionID]
    }

    fun getNameByKey(sectionKey : String) : String {
        val sectionID = sections_keys.indexOf(sectionKey)
        return sections[sectionID]
    }

    fun moveArticle(articleName : String, newSectionKey : String) : Boolean {
        if (getSectionByArticle(articleName) == newSectionKey) {return false}
        else {
            val oldSectionName = getSectionByArticle(articleName)
            listByKey[oldSectionName]!!.remove(articleName)
            listByKey[newSectionKey]!!.add(articleName)
            return true
        }
    }

    fun addToFavs(articleName: String) {
        listByKey["favorites"]!!.add(articleName)
    }

    fun removeFromFavs(articleName: String) {
        listByKey["favorites"]!!.remove(articleName)
    }

    fun changeTextName(oldName : String, newName: String, sectionKey: String) {
        val transOldName = trans.transliterateRUtoEN(oldName)
        val transNewName = trans.transliterateRUtoEN(newName)
        val text = textByKey[transOldName]
        textByKey[transNewName] = text!!
        textByKey.remove(transOldName)
        listByKey[sectionKey]!![listByKey[sectionKey!!]!!.indexOf(oldName)] = newName
        //listByKey[sectionKey]!!.add(newName)
    }

    fun changeText(name : String, text : String) {
        textByKey[trans.transliterateRUtoEN(name)] = text
    }
}