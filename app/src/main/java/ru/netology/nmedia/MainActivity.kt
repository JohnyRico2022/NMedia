package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var postShareScoreStart = 9_999_998

        fun scoreDisplay(number: Int): String {
            val divisionK = number / 1000
            val divisionKtoDouble = number / 1000.toDouble()
            val divisionM = number / 1000000
            val divisionMtoDouble = number / 1000000.toDouble()

            return when (number) {
                in 1_000..1_099 -> {
                    "$divisionK" + "K"
                }

                in 1_100..9_999 -> {
                    val k =
                        divisionKtoDouble.toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
                    "$k" + "K"
                }

                in 10_000..999_999 -> {
                    "$divisionK" + "K"
                }

                in 1_000_000..1_099_999 -> {
                    "$divisionM" + "M"
                }

                in 1_100_000..9_999_999 -> {
                    val m =
                        divisionMtoDouble.toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
                    "$m" + "M"
                }

                in 10_000_000..999_999_999 -> {
                    "$divisionM" + "M"
                }

                else -> number.toString()
            }
        }

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

            postLikesScore.text = scoreDisplay(post.likes)

            postLikes.setOnClickListener {
                post.likeByMe = !post.likeByMe
                postLikes.setImageResource(
                    if (post.likeByMe) R.drawable.ic_favorite_red_24 else R.drawable.ic_favorite_24
                )
                if (post.likeByMe) post.likes++ else post.likes--
                postLikesScore.text = scoreDisplay(post.likes)
            }

            postShareScore.text = scoreDisplay(postShareScoreStart)

            postShare.setOnClickListener {
                postShareScoreStart++
                postShareScore.text = scoreDisplay(postShareScoreStart)
            }
        }
    }
}