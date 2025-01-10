package com.kurniawan.capstoneproject.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.kurniawan.capstoneproject.R
import com.kurniawan.capstoneproject.core.domain.model.News
import com.kurniawan.capstoneproject.core.utils.dateFormatter
import com.kurniawan.capstoneproject.databinding.ActivityDetailNewsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailNewsActivity : AppCompatActivity() {

    private val detailNewsViewModel: DetailNewsViewModel by viewModel()
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setToolbar()
        setDetail()
    }

    private fun setToolbar() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDetail() {
        val news = getParcelableExtra(intent, EXTRA_NEWS, News::class.java)
        if (news != null) {
            binding.apply {
                Glide.with(this@DetailNewsActivity)
                    .load(news.urlToImage)
                    .into(ivNews)

                textTitle.text = news.title
                textAuthor.text = resources.getString(R.string.by, news.author)
                textDescription.text = news.description
                textPublishedAt.text = dateFormatter(news.publishedAt)

                var statusBookmark = news.isBookmark
                isBookmark(statusBookmark)

                topAppBar.setOnMenuItemClickListener { clickedMenuItem ->
                    when (clickedMenuItem.itemId) {
                        R.id.menuBookmark -> {
                            statusBookmark = !statusBookmark
                            detailNewsViewModel.setFavoriteNews(news, statusBookmark)
                            isBookmark(statusBookmark)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    private fun isBookmark(statusBookmark: Boolean) {
        binding.apply {
            topAppBar.menu.findItem(R.id.menuBookmark).setIcon(
                if (statusBookmark) R.drawable.ic_bookmark_selected
                else R.drawable.ic_bookmark_unselected
            )
        }
    }

    companion object {
        const val EXTRA_NEWS = "extra_news"
    }
}