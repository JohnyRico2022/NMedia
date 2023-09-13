package ru.netology.nmedia.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentDetailBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.screens.FeedFragment.Companion.textArg
import ru.netology.nmedia.viewmodel.PostViewModel


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

        val listener = object : OnInteractionListener {

            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun remove(post: Post) {
                viewModel.removeById(post.id)
                findNavController().navigate(R.id.action_detailFragment_to_feedFragment)
            }
            override fun edit(post: Post) {
                val text = post.content
                findNavController()
                    .navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        bundleOf("content" to text)
                    )
                viewModel.edit(post)
            }

            override fun share(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
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
            }

            override fun actionOnFragment(post: Post) {

            }
        }

        val currentPostId = requireArguments().textArg!!.toLong()


        binding.post.apply{

            viewModel.data.observe(viewLifecycleOwner){it ->
                val viewHolder = PostViewHolder(binding.post, listener)
                val post = it.find { it.id == currentPostId}
                post?.let {
                    viewHolder.bind(post) }

            }
        }
        return binding.root
    }
}