package com.example.recyclerviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewtest.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PostRepository {
    @GET("posts")
    suspend fun getPosts():DataPosts

    @GET("search/{text}")
    suspend fun getPostsFromSearch(@Path("text") text: String):DataPosts
}

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: PostAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var buttonSearch: Button
    lateinit var buttonLoad: Button
    lateinit var postTitleText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.rv_posts)
        adapter = PostAdapter()
        recyclerView.adapter = adapter

        postTitleText = findViewById(R.id.postTitle)
        buttonSearch = findViewById(R.id.buttonSearch)
        buttonLoad = findViewById(R.id.buttonLoad)

        buttonLoad.setOnClickListener {
            val titlePost = postTitleText.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val server = retrofit.create(PostRepository::class.java)
                val list = server.getPosts()
                runOnUiThread {
                    adapter.setList(list)
                }
            }
        }

        buttonSearch.setOnClickListener {
            val titlePost = postTitleText.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val server = retrofit.create(PostRepository::class.java)
                val list = server.getPostsFromSearch(titlePost)
                runOnUiThread {
                    adapter.setList(list)
                }
            }
        }
    }



    companion object{
        const val BASE_URL = "http://10.0.2.2:10001/"
    }

}