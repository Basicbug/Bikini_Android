package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFeedsBinding
import com.example.bikini_android.ui.common.list.DefaultListAdapter
import com.example.bikini_android.ui.feeds.adapter.FeedItemDefaultViewModel
import com.example.bikini_android.ui.feeds.adapter.FeedItemDiffCallback
import com.example.bikini_android.ui.holder.MainHolderFragment

/**
 * @author bsgreentea
 */

class FeedsFragment : MainHolderFragment() {

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

        var adapter = DefaultListAdapter(FeedItemDiffCallback<FeedItemDefaultViewModel>())

        var testFeeds = mutableListOf<FeedItemDefaultViewModel>()

        testFeeds.add(FeedItemDefaultViewModel().apply {
            userId = "sample1"
        })
        testFeeds.add(FeedItemDefaultViewModel().apply {
            userId = "sample2"
        })
        testFeeds.add(FeedItemDefaultViewModel().apply {
            userId = "sample3"
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