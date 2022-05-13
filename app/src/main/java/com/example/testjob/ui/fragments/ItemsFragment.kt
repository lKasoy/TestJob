package com.example.testjob.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.testjob.R
import com.example.testjob.constants.Constants.ITEMS_FRAGMENT
import com.example.testjob.constants.Constants.KEY
import com.example.testjob.constants.Constants.SOME_ITEM_FRAGMENT
import com.example.testjob.data.entity.Item
import com.example.testjob.databinding.FragmentItemListBinding
import com.example.testjob.ui.adapters.ItemsAdapter
import com.example.testjob.ui.viewModels.ItemsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemsFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private val itemsViewModel by viewModel<ItemsViewModel>()
    private val itemsAdapter = ItemsAdapter(
        onClick = { item: Item ->
            val itemBundle = bundleOf(
                KEY to item.key
            )
            parentFragmentManager.commit {
                replace<SomeItemFragment>(R.id.container, SOME_ITEM_FRAGMENT, args = itemBundle)
                setReorderingAllowed(true)
                addToBackStack(ITEMS_FRAGMENT)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = itemsAdapter
        subscribeData()
        itemsViewModel.readData()
    }

    private fun subscribeData() {
        itemsViewModel.items.observe(viewLifecycleOwner, {
            itemsAdapter.submitList(it)
        })
    }
}