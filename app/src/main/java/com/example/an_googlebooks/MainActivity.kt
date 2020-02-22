package com.example.an_googlebooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //MAIN - Properties
    private lateinit var books: ArrayList<Book>
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    //MAIN - Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        menu?.let {
            val item = it.findItem(R.id.main_search)
            val searchView = item.actionView as SearchView
            searchView.queryHint = "Search Google Books"
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                //triggered once search button tapped
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //reset search text and close keyboard
                    searchView.setQuery("", false)
                    searchView.clearFocus()
                    //make api request
                    query?.let { safeQuery ->
                        val sanitized = safeQuery.replace("\\s".toRegex(), "+")
                        get(sanitized)
                    }
                    return true
                }
                //triggered every key tap
                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        return true
    }


    //MAIN - Functions
    private fun setup() {
        recyclerView = findViewById(R.id.main_recycler_view)
        books = arrayListOf()
        progressBar = findViewById(R.id.main_progress_bar)
        val adapter = MainAdapter(books)  {
            //callback to get item click position - go to next activity
            goToDetail(books[it])
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        get("michael+jordan")
    }


    private fun get(search: String) {
        progressBar.visibility = View.VISIBLE
        progressBar.animate()
        BookService.getBooks(search) {
            //for RV to update you MUST: 1. clear data source, 2. call add all to data source to set again
            this.books.clear()
            recyclerView.recycledViewPool.clear()
            this.books.addAll(ArrayList(it))
            runOnUiThread {
                recyclerView.adapter!!.notifyDataSetChanged()
                progressBar.animate().cancel()
                progressBar.visibility = View.GONE
                println("TAG: Book Count: ${it.size}")
            }
        }
    }

    private fun goToDetail(book: Book) {
        //intent - go to next activity
        val detailIntent = Intent(this, DetailActivity::class.java)
        //pass book
        detailIntent.putExtra("book", book)
        //start detail
        startActivity(detailIntent)
    }


}
