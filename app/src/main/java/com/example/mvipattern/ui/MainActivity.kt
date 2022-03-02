package com.example.mvipattern.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.mvipattern.R
import com.example.mvipattern.databinding.ActivityMainBinding
import com.example.mvipattern.model.Blog
import com.example.mvipattern.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG: String = "AppDebug"

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null) binding.text.text = message else binding.text.text = "Unknown error."
    }

    private fun appendBlogTitles(blogs: List<Blog>) {
        val sb = StringBuilder()
        for (blog in blogs) {
            sb.append(blog.title + "\n")
        }
        binding.text.text = sb.toString()
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

}