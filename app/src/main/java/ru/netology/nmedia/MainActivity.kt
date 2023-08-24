package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object: OnInteractionListener{
            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun edit(post: Post) {
                binding.group.visibility = View.VISIBLE
                binding.editOriginalText.text = post.content
                viewModel.edit(post)
            }

            override fun share(post: Post) {
                viewModel.shareCounter(post.id)
            }
        })

        binding.recyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts){
                if (newPost){
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
        }

        viewModel.edited.observe(this) {
            if (it.id != 0L) {
                binding.editText.setText(it.content)
                binding.editText.focusAndShowKeyboard()
            }
        }

        binding.save.setOnClickListener {
            val text = binding.editText.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.changeContent(text)
            viewModel.save()

            binding.editText.setText("")
            binding.editText.clearFocus()
            AndroidUtils.hideKeyboard(it)

            binding.editOriginalText.text = ""
            binding.editText.setText("")
            binding.group.visibility = View.GONE

        }

        binding.editCancel.setOnClickListener {
            binding.editOriginalText.text = ""
            binding.editText.setText("")
            binding.group.visibility = View.GONE

            viewModel.save()

        }
    }
}