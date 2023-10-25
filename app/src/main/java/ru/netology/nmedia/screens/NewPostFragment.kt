package ru.netology.nmedia.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

       requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
 //              viewModel.draft = binding.edit.text.toString()
               findNavController().navigateUp()
       }

        arguments?.textArg?.let(binding.edit::setText)
        binding.edit.requestFocus()

            binding.apply {

                ok.setOnClickListener {
                    if (!binding.edit.text.isNullOrBlank()) {
                        val content = binding.edit.text.toString()
                        viewModel.changeContent(content)
                        viewModel.save()
  //                      viewModel.draft = null
                        findNavController().navigateUp()
                    }
                }

 //               edit.setText(
 //                   if(viewModel.draft != null) viewModel.draft else arguments?.textArg
 //               )
            }
//        viewModel.postCreated.observe(viewLifecycleOwner){
//
//        }

        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }
}
