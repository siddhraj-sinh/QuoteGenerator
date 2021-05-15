package com.example.quotegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity() {
    // initializing views
    private lateinit var textQuote: TextView
    private lateinit var textAuthor: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var nextButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // binding all views using findViewById
        textQuote = findViewById(R.id.tv_quote)
        textAuthor = findViewById(R.id.tv_author)
        progressBar = findViewById(R.id.progressBar)
        nextButton = findViewById(R.id.btn_next)
        // fetching data
        fetchData()
    }

    private fun fetchData() {
        // make progressbar visible
        progressBar.visibility= View.VISIBLE
        // api url
        val url = "https://api.quotable.io/random"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,Response.Listener { response ->
            val jsonContent = response.getString("content")
            val jsonAuthor = response.getString("author")
         // callling set data method
            setData(jsonContent,jsonAuthor)

        },Response.ErrorListener { error ->
            // error toast
            Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
        })
          // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun setData(text: String, author: String){
        // make progress bar invisible
        progressBar.visibility=View.GONE
        //
        textQuote.visibility=View.VISIBLE
        textAuthor.visibility=View.VISIBLE
        // set quote and author
        textQuote.text = text
        textAuthor.text = "~ $author"
    }

    fun nextQuote(view: View) {
        //
        textAuthor.visibility =View.GONE
        textQuote.visibility = View.GONE
        fetchData()
    }

}