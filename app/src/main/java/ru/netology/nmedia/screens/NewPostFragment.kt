package ru.netology.nmedia.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by activityViewModels()

        val text = arguments?.getString("content")
        if (text != null) {
            binding.edit.setText(text)
        }

        binding.apply {

            edit.requestFocus()

            ok.setOnClickListener {
                if (!binding.edit.text.isNullOrBlank()) {
                    val content = binding.edit.text.toString()
                    viewModel.changeContent(content)
                    viewModel.save()
                }
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}