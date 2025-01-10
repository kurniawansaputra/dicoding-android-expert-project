package com.kurniawan.capstoneproject.bookmark.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.kurniawan.capstoneproject.bookmark.R
import com.kurniawan.capstoneproject.bookmark.databinding.ActivityBookmarkBinding
import com.kurniawan.capstoneproject.bookmark.di.bookmarkModule
import com.kurniawan.capstoneproject.detail.DetailNewsActivity
import com.kurniawan.capstoneproject.ui.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class BookmarkActivity : AppCompatActivity() {

    private val bookmarkViewModel: BookmarkViewModel by viewModel()
    private lateinit var binding: ActivityBookmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadKoinModules(bookmarkModule)

        setToolbar()
        getList()
    }

    private fun setToolbar() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun getList() {
        val newsAdapter = NewsAdapter()
        newsAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_NEWS, selectedData)
            startActivity(intent)
        }

        bookmarkViewModel.bookmarkNews.observe(this) { news ->
            newsAdapter.submitList(news)
            binding.containerEmpty.root.isVisible = news.isEmpty()
        }

        with(binding.rvBookmark) {
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }
}