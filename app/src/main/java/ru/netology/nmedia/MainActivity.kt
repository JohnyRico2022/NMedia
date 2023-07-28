package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var postShareScoreStart = 9_999_998

        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            "21 мая в 18:36",
            likes = 999,
            likeByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            if (post.likeByMe) {
                postLikes.setImageResource(R.drawable.ic_favorite_red_24)
            }

            postLikesScore.text = RoundingNumbers().scoreDisplay(post.likes)

            postLikes.setOnClickListener {
                post.likeByMe = !post.likeByMe
                postLikes.setImageResource(
                    if (post.likeByMe) R.drawable.ic_favorite_red_24 else R.drawable.ic_favorite_24
                )
                if (post.likeByMe) post.likes++ else post.likes--
                postLikesScore.text = RoundingNumbers().scoreDisplay(post.likes)
            }

            postShareScore.text = RoundingNumbers().scoreDisplay(postShareScoreStart)

            postShare.setOnClickListener {
                postShareScoreStart++
                postShareScore.text = RoundingNumbers().scoreDisplay(postShareScoreStart)
            }
        }
    }
}