package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFeedsBinding
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.common.list.DefaultDiffCallback
import com.example.bikini_android.ui.common.list.DefaultListAdapter
import com.example.bikini_android.ui.feeds.adapter.FeedLinearItemViewModel

/**
 * @author bsgreentea
 */

class FeedsFragment : BaseFragment() {

    private lateinit var binding: FragmentFeedsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFeedsBinding>(
            inflater,
            R.layout.fragment_feeds,
            container,
            false
        )

        var adapter = DefaultListAdapter(DefaultDiffCallback<FeedLinearItemViewModel>())

        var testFeeds = mutableListOf<FeedLinearItemViewModel>()

        testFeeds.add(FeedLinearItemViewModel().apply {
            userId = "sample1"
        })
        testFeeds.add(FeedLinearItemViewModel().apply {
            userId = "sample2"
        })
        testFeeds.add(FeedLinearItemViewModel().apply {
            userId = "sample3"
        })
        testFeeds.add(FeedLinearItemViewModel().apply {
            userId = "sample4"
        })


        adapter.submitList(testFeeds)

        binding.feeds.adapter = adapter

        return binding.root
    }

    companion object {
        fun newInstance(): FeedsFragment {
            return FeedsFragment()
        }
    }
}