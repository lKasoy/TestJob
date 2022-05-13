package com.example.testjob.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.testjob.constants.Constants.KEY
import com.example.testjob.databinding.FragmentSomeItemBinding
import com.example.testjob.ui.viewModels.SomeItemViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SomeItemFragment : Fragment() {

    private lateinit var binding: FragmentSomeItemBinding
    private val someItemViewModel by viewModel<SomeItemViewModel>(parameters = { parametersOf(key) })

    private val key by lazy {
        requireArguments().getString(KEY) ?: throw IllegalStateException("No key")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSomeItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        someItemViewModel.someItem.observe(viewLifecycleOwner, {
            binding.apply {
                name.text = it.name
                description.text = it.description
                Glide.with(binding.root)
                    .asBitmap()
                    .load(it.url)
                    .into(image)
            }
        })
        someItemViewModel.renderSomeItem()
    }
}