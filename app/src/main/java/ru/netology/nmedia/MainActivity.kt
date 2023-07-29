package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

  //      var postShareScoreStart = 10

        val viewModel2 by viewModels<PostViewModel>()
        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content

                postLikes.setImageResource(if (post.likeByMe)R.drawable.ic_favorite_red_24 else R.drawable.ic_favorite_24)
                postLikesScore.text = RoundingNumbers.scoreDisplay(post.likes)

                postShareScore.text = post.share.toString()
                postShareScore.text = RoundingNumbers.scoreDisplay(post.share)


            }
        }
        viewModel2.data.observe(this){

        }

        binding.postLikes.setOnClickListener {
            viewModel.like()
        }

       binding.postShare.setOnClickListener {
           viewModel.share()
  //        binding.postShareScore.text = RoundingNumbers.scoreDisplay(post.share)


        }
    }
}