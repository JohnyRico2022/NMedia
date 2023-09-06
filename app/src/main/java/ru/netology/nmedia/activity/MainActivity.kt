package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : OnInteractionListener {

            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            val editPostLauncher = registerForActivityResult(PostResultContract()) { result ->
                result ?: return@registerForActivityResult
                viewModel.changeContent(result)
                viewModel.save()
            }
            override fun edit(post: Post) {
                editPostLauncher.launch(post.content)
                viewModel.edit(post)
            }

            override fun share(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.shareCounter(post.id)
            }

            override fun video(post: Post) {

                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY")
                }

                val playVideo = Intent.createChooser(intent, "play Video")
                startActivity(playVideo)
 //               Toast.makeText(this@MainActivity, "video play", Toast.LENGTH_SHORT).show()
            }
        })




        binding.recyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
        }

        val newPostLauncher = registerForActivityResult(PostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        binding.addPostButton.setOnClickListener {
            newPostLauncher.launch("")
        }
    }
}