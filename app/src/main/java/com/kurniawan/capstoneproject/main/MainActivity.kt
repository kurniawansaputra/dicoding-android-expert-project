package com.kurniawan.capstoneproject.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kurniawan.capstoneproject.R
import com.kurniawan.capstoneproject.core.data.Resource
import com.kurniawan.capstoneproject.ui.NewsAdapter
import com.kurniawan.capstoneproject.databinding.ActivityMainBinding
import com.kurniawan.capstoneproject.detail.DetailNewsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getList()
        optionMenu()
    }

    private fun getList() {
        val newsAdapter = NewsAdapter()
        newsAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_NEWS, selectedData)
            startActivity(intent)
        }

        mainViewModel.news.observe(this) { news ->
            if (news != null) {
                when (news) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        newsAdapter.submitList(news.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.containerError.root.visibility = View.VISIBLE
                    }
                }
            }
        }

        with(binding.rvNews) {
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun optionMenu() {
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuBookmark -> {
                    val uri = Uri.parse("capstoneproject://bookmark")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { _, insets -> insets }
        binding.rvNews.adapter = null
        _binding = null
    }
}
